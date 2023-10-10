package xtb.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Integrator {
    private FilterRateInfoInput filterRateInfoInput;
    private FilterRateInfoOutput filterRateInfoOutput;
    private ArrayList<FilterRateInfoInput> filterRateInfoInputArrayList = new ArrayList<>();
    private ArrayList<FilterRateInfoOutput> filterRateInfoOutputArrayList = new ArrayList<>();
    private ArrayList<String> in = new ArrayList<>();
    private ArrayList<String> out = new ArrayList<>();

    public static int INPUT;
    public static int OUTPUT;
    public static int LENGHT_INPUT_IN_STRING;
    public static int LENGHT_ONE_INPUT_IN_STRING;
    public static int LENGHT_ONE_OUTPUT_IN_STRING;
    public static int TOTAL_BATCHES;
    public static int TOTAL_SYMBOL;


    public Integrator addIn(FilterRateInfoInput filterRateInfoInput) {
        filterRateInfoInputArrayList.add(filterRateInfoInput);
        return this;
    }

    public Integrator addIn(ArrayList<FilterRateInfoInput> filterRateInfoInputArrayList) {
        this.filterRateInfoInputArrayList = new ArrayList<>(filterRateInfoInputArrayList);
        return this;
    }

    public Integrator addOut(FilterRateInfoOutput filterRateInfoOutput) {
        filterRateInfoOutputArrayList.add(filterRateInfoOutput);
        return this;
    }

    public ArrayList<String> getInputString() {
        return concatInputWitoutLoss(filterRateInfoInputArrayList);
    }

    public ArrayList<String> getOutputString() {
        return concatOutput(filterRateInfoOutputArrayList);
    }

    private ArrayList<String> concatInput(ArrayList<FilterRateInfoInput> filterRateInfoInputArrayList) {

        ArrayList<String> integretedListString = new ArrayList<>();
        int lastElement = filterRateInfoInputArrayList.size() - 1;
        int sizeLastElement = filterRateInfoInputArrayList.get(lastElement).factory().size();

        StringBuilder sb;
        for (int i = 0; i < sizeLastElement; i++) {
            sb = new StringBuilder();

            for (int i1 = 0; i1 < filterRateInfoInputArrayList.size(); i1++) {
                sb.append(filterRateInfoInputArrayList.get(i1).factory().get(i));

            }
            integretedListString.add(sb.toString());
        }


        ;


//        return integretedListString;
        return convertToPercent(integretedListString);

    }

    private ArrayList<String> concatInputWitoutLoss(ArrayList<FilterRateInfoInput> filterRateInfoInputArrayList) {

        ArrayList<String> integretedListString = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = filterRateInfoInputArrayList.size() - 1; i >= 0; i--) {
            ArrayList<String> factory = filterRateInfoInputArrayList.get(i).factory();
            sb.append(factory.get(factory.size() - 1));
        }
        integretedListString.add(sb.toString());

        return convertToPercent(integretedListString);
//        return convertToPercent(integretedListString);

    }

    private ArrayList<String> concatOutput(ArrayList<FilterRateInfoOutput> filterRateInfoOutputArrayList) {
        ArrayList<String> integretedListString = new ArrayList<>();
        int lastElement = filterRateInfoOutputArrayList.size();
        int sizeLastElement = filterRateInfoOutputArrayList.get(lastElement - 1).factory().size();

        StringBuilder sb;
        for (int i = 0; i < sizeLastElement; i++) {
            sb = new StringBuilder();
            for (int i1 = 0; i1 < filterRateInfoOutputArrayList.size(); i1++) {
                sb.append(filterRateInfoOutputArrayList.get(i1).factory().get(i));
            }
            integretedListString.add(sb.toString());
        }


        return integretedListString;

    }

    public ArrayList<String> factory() throws InterruptedException {
        ArrayList<String> integretedListString = new ArrayList<>();
        in = concatInput(filterRateInfoInputArrayList);
        out = concatOutput(filterRateInfoOutputArrayList);


        for (int i = 0; i < out.size(); i++) {
            integretedListString.add(in.get(i) + out.get(i));
        }

        calcStructFile();
        return integretedListString;
    }


    private int getInput() {
        return in.get(0).split(";").length;
    }

    private int getOutput() {
        return out.get(0).split(";").length;
    }

    private int getLenghtInputInString() {
        return (in.get(0).length()) - (in.get(0).split(";").length);
    }

    private int getLenghtOneInputInString() {
        return ((in.get(0).length()) - (in.get(0).split(";").length)) / getInput();
    }

    private int getLenghtOneOutputInString(ArrayList<String>... lists) {
        int max=0;
        int outLenght=0;
        for (int i = 0; i < out.size(); i++) {
            outLenght=out.get(i).length()-1;
            if(outLenght>max){
                max=outLenght;
            }
        }
        return max;
    }

    private int getTotalSymbol(){
        int oldLenghtIn=0;
        int oldLenghtOut=0;
        for (int i = 0; i < in.size(); i++) {
            int newLengthIn = in.get(i).length()-Integrator.INPUT;
            if(newLengthIn>oldLenghtIn){
                oldLenghtIn=newLengthIn;
            }

        }
        for (int i = 0; i < out.size(); i++) {
            int newLengthOut = out.get(i).length()-Integrator.OUTPUT;
            if(newLengthOut>oldLenghtOut){
                oldLenghtOut=newLengthOut;
            }

        }
        return oldLenghtIn+oldLenghtOut;
    }

    private void calcStructFile() {
        Integrator.INPUT = getInput();
        Integrator.OUTPUT = getOutput();
        Integrator.LENGHT_INPUT_IN_STRING = getLenghtInputInString();
        Integrator.LENGHT_ONE_INPUT_IN_STRING = getLenghtOneInputInString();
        Integrator.LENGHT_ONE_OUTPUT_IN_STRING = getLenghtOneOutputInString();
        Integrator.TOTAL_BATCHES = out.size();
        Integrator.TOTAL_SYMBOL=getTotalSymbol();
    }

    public ArrayList<String> convertToPercent(ArrayList<String> stringArrayList) {

        List<String> collect = stringArrayList.stream()
                .map(s -> {
                    int maxValue = 0;
                    String[] split = s.split(";");
                    Integer[] integers = new Integer[split.length];
                    for (int i = 0; i < split.length; i++) {
                        integers[i] = Integer.valueOf(split[i]);
                        if (integers[i] > maxValue) {
                            maxValue = integers[i];
                        }
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < integers.length; i++) {
//                        System.out.print((100 * integers[i]) / maxValue+" ");
                        stringBuilder.append((maxValue - integers[i]) + ";");
                    }
                    return stringBuilder.toString();
                })
                .collect(Collectors.toList());

        ArrayList<String> listInputInPercent = new ArrayList<>(collect);
        return listInputInPercent;

    }


}
