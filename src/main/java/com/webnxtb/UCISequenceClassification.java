package com.webnxtb;

import com.webnxtb.DataBase.Konfiguracja;
import com.webnxtb.helpers.Filer;
import com.webnxtb.helpers.ScalingIn;
import com.webnxtb.helpers.ScalingOut;
import org.apache.commons.io.FileUtils;
import org.datavec.api.records.reader.SequenceRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVSequenceRecordReader;
import org.datavec.api.split.NumberedFileInputSplit;
import org.deeplearning4j.datasets.datavec.SequenceRecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.InvocationType;
import org.deeplearning4j.optimize.listeners.EvaluativeListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.common.primitives.Pair;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Nadam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import pro.xstore.api.message.codes.PERIOD_CODE;
import pro.xstore.api.message.error.APICommunicationException;
import web.loger.Loger;
import xtb.ConnectXTB;
import xtb.DownloadDataFromXTB;
import xtb.User;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class UCISequenceClassification {


    public static void main(String[] args) throws Exception {
        Konfiguracja konfiguracja = new Konfiguracja();
        konfiguracja.setIloscNeuronow("[100]");
        konfiguracja.setLiczbaWarstw(1);
        konfiguracja.setNazwaKonfiguracji("nowa");
        konfiguracja.setPeriodCode("1");
        konfiguracja.setSymbol("USDJPY");
        konfiguracja.setUzytkownikXtb("14795721");
        konfiguracja.setHasloXtb("");
        konfiguracja.setId(9999);
        konfiguracja.setCzestotliwoscWyswietlaniaWynikow("10");
        UCISequenceClassification.run(konfiguracja);
    }

    private static final File baseDir = new File("src/main/resources/uci/");
    private static final File baseTrainDir = new File(baseDir, "train");
    private static final File featuresDirTrain = new File(baseTrainDir, "features");
    private static final File labelsDirTrain = new File(baseTrainDir, "labels");
    private static final File baseTestDir = new File(baseDir, "test");
    private static final File featuresDirTest = new File(baseTestDir, "features");
    private static final File labelsDirTest = new File(baseTestDir, "labels");
    private static final int numLabelClasses = 16;
    private static final int miniBatchSize = 30;
    private static final int numIn = 3;

    public static void run(Konfiguracja konfiguracja) throws Exception {


        long id = Long.parseLong(konfiguracja.getUzytkownikXtb());
        String password = konfiguracja.getHasloXtb();
        String symbol = konfiguracja.getSymbol();
        long code = Long.parseLong(konfiguracja.getPeriodCode());
        PERIOD_CODE periodCode = new PERIOD_CODE(code);

        int frequencyListener = Integer.parseInt(konfiguracja.getCzestotliwoscWyswietlaniaWynikow());
        int liczbaWarstw = konfiguracja.getLiczbaWarstw();
        int[] iloscNeuronow = Arrays.stream(konfiguracja.getIloscNeuronow()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(" ", "").split(",")).mapToInt(Integer::parseInt)
                .toArray();

        downloadUCIData(
                new User(id, password)
                , symbol
                , periodCode);


        int numberTrain = Filer.CountFilesInDirectory(featuresDirTrain.getAbsolutePath()) - 1;
        int numberTest = Filer.CountFilesInDirectory(featuresDirTest.getAbsolutePath()) - 1;
        Loger.log("Train: " + numberTrain + "  Test: " + numberTest);

        SequenceRecordReader trainFeatures = new CSVSequenceRecordReader();
        trainFeatures.initialize(new NumberedFileInputSplit(featuresDirTrain.getAbsolutePath() + "/%d.csv", 0, numberTrain));
        SequenceRecordReader trainLabels = new CSVSequenceRecordReader();
        trainLabels.initialize(new NumberedFileInputSplit(labelsDirTrain.getAbsolutePath() + "/%d.csv", 0, numberTrain));


        DataSetIterator trainData = new SequenceRecordReaderDataSetIterator(trainFeatures, trainLabels, miniBatchSize, numLabelClasses,
                false, SequenceRecordReaderDataSetIterator.AlignmentMode.ALIGN_END);

        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(trainData);              //Collect training data statistics
        trainData.reset();

        trainData.setPreProcessor(normalizer);

        SequenceRecordReader testFeatures = new CSVSequenceRecordReader();
        testFeatures.initialize(new NumberedFileInputSplit(featuresDirTest.getAbsolutePath() + "/%d.csv", 0, numberTest));
        SequenceRecordReader testLabels = new CSVSequenceRecordReader();
        testLabels.initialize(new NumberedFileInputSplit(labelsDirTest.getAbsolutePath() + "/%d.csv", 0, numberTest));

        DataSetIterator testData = new SequenceRecordReaderDataSetIterator(testFeatures, testLabels, miniBatchSize, numLabelClasses,
                false, SequenceRecordReaderDataSetIterator.AlignmentMode.ALIGN_END);

        testData.setPreProcessor(normalizer);


        LSTM[] lstm = new LSTM[liczbaWarstw];
        lstm[0] = new LSTM.Builder().activation(Activation.TANH).nIn(numIn).nOut(iloscNeuronow[0]).build();
        Loger.log("Konfiguracja sieci:");
        Loger.log("Layer 0: in:" + numIn + " out:" + iloscNeuronow[0]);
        int i = 1;
        for (; i < liczbaWarstw; i++) {
            lstm[i] = new LSTM.Builder().activation(Activation.TANH).nIn(iloscNeuronow[i - 1]).nOut(iloscNeuronow[i]).build();
            Loger.log("Layer " + i + ": in:" + iloscNeuronow[i - 1] + " out:" + iloscNeuronow[i]);
        }


        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(123)    //Random number generator seed for improved repeatability. Optional.
                .weightInit(WeightInit.XAVIER)
                .updater(new Nadam())
                .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)  //Not always required, but helps with this data set
                .gradientNormalizationThreshold(0.5)
                .list(lstm)
                .layer(i, new RnnOutputLayer.Builder(LossFunctions.LossFunction.MCXENT)
                        .activation(Activation.SOFTMAX).nIn(iloscNeuronow[i - 1]).nOut(numLabelClasses).build())
                .build();
        Loger.log("Layer out " + i + ": in:" + iloscNeuronow[i - 1] + " out:" + numLabelClasses);


//        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        MultiLayerNetwork net = MultiLayerNetwork.load(new File("TEMP_MODEL"),true);

        net.init();

        Loger.log("Starting training...");


        net.setListeners(
                new EvaluativeListener(testData, 1, InvocationType.EPOCH_END) {
                    @Override
                    public void onEpochEnd(Model model) {
                        super.onEpochEnd(model);
                        try {
                            net.save(new File("TEMP_MODEL"), true);
                            System.out.println("Save file");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
//                        Evaluation eval = net.evaluate(testData);
//                        String stats = eval.stats();
//                        Loger.log(stats);
                    }


                },
                new ScoreIterationListener(frequencyListener) {

                    @Override
                    public void iterationDone(Model model, int iteration, int epoch) {
                        super.iterationDone(model, iteration, epoch);
                        if (iteration % frequencyListener == 0) {
//                            Loger.log("Iteration:" + iteration + " Score: " + model.score());
                        }
                    }
                }
        );

        int nEpochs = 200;
        net.fit(trainData, nEpochs);


        Loger.log("Evaluating...");
        Evaluation eval = net.evaluate(testData);
        String stats = eval.stats();
        Loger.log(stats);
        Loger.log("----- Example Complete -----");
    }
    private static void downloadUCIData(User user, String symbol, PERIOD_CODE periodCode) throws Exception {


        String data = null;
        int dataLenght;
        Filer.deleteDir(baseDir.getAbsolutePath());

        if (ConnectXTB.login(user)) {
            try {
                Loger.log("Pobieranie danych");
                data = DownloadDataFromXTB.get(symbol, periodCode, true,miniBatchSize);
                Filer.save("dataUCI", data);
            } catch (APICommunicationException e) {
                throw new RuntimeException(e);
            }
        } else {
            Loger.log("Błąd połączenia");
            data = Filer.load("dataUCI");
        }


        String[] lines = data.split("\n");
        dataLenght = lines.length;
        //Create directories
        baseDir.mkdir();
        baseTrainDir.mkdir();
        featuresDirTrain.mkdir();
        labelsDirTrain.mkdir();
        baseTestDir.mkdir();
        featuresDirTest.mkdir();
        labelsDirTest.mkdir();

        int lineCount = 0;
        List<Pair<String, Integer>> contentAndLabels = new ArrayList<>();

        ArrayList<String> arrayListOut = new ArrayList<>();

        for (String line : lines) {
            arrayListOut.add(line.split(":")[1]);
        }
        int[] outArray = arrayListOut.stream()
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] scalOut = ScalingOut.scal(outArray);


        for (String line : lines) {
            String[] splitLine = line.split(":");
            String in = splitLine[0];
            String[] inArray = in.replace(" ", ",").split(",");
            int[] inArrayInt = new int[inArray.length];

            for (int i = 0; i < inArray.length; i++) {
                inArrayInt[i] = Integer.parseInt(inArray[i]);
            }

            int[] scalIn = ScalingIn.scal(inArrayInt);
            StringBuilder stringBuilder = new StringBuilder();
            int moduloCounter = 1;
            for (int i = 0; i < scalIn.length - 1; i++) {
                stringBuilder.append(scalIn[i]);

                if (moduloCounter % numIn == 0) {
                    stringBuilder.append(" ");
                } else {
                    stringBuilder.append(",");
                }
                moduloCounter++;
            }
            stringBuilder.append(scalIn[scalIn.length - 1]).append("\n");
            String transposed = stringBuilder.toString().replaceAll(" +", "\n");
            contentAndLabels.add(new Pair<>(transposed, scalOut[lineCount++]));
        }

        Collections.shuffle(contentAndLabels, new Random(12345));

        int percentTrainDta = 75;
        int nTrain = dataLenght * percentTrainDta / 100;
        int trainCount = 0;
        int testCount = 0;
        for (Pair<String, Integer> p : contentAndLabels) {
            File outPathFeatures;
            File outPathLabels;
            if (trainCount < nTrain) {
                outPathFeatures = new File(featuresDirTrain, trainCount + ".csv");
                outPathLabels = new File(labelsDirTrain, trainCount + ".csv");
                trainCount++;
            } else {
                outPathFeatures = new File(featuresDirTest, testCount + ".csv");
                outPathLabels = new File(labelsDirTest, testCount + ".csv");
                testCount++;
            }

            FileUtils.writeStringToFile(outPathFeatures, p.getFirst(), (Charset) null);
            FileUtils.writeStringToFile(outPathLabels, p.getSecond().toString(), (Charset) null);
        }
        System.out.println("dataLenght: " + dataLenght);
    }


}