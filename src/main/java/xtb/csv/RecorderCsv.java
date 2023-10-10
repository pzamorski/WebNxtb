package xtb.csv;

import xtb.StructFile;

import java.io.*;
import java.util.ArrayList;

public class RecorderCsv {


    public static void save(ArrayList<String> factory,double split) throws IOException {
        BufferedWriter train = new BufferedWriter(new FileWriter(StructFile.TRAIN_FILE));
        BufferedWriter test = new BufferedWriter(new FileWriter(StructFile.TEST_FILE));
        int splitOftest=0;
        if((int) (factory.size() * split)<=100){
            splitOftest = (int) (factory.size() * split);
        }else{
            splitOftest=100;
        }

        int splitOfTrain=factory.size()-splitOftest;

        for (int i = 0; i < splitOftest; i++) {
            try {
                test.write(factory.get(i)+"\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        test.close();

        for (int i = 0; i < splitOfTrain; i++) {
            try {
                train.write(factory.get(splitOftest+i)+"\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        train.close();



    }
    public static void save(String factory) throws IOException {
        BufferedWriter train = new BufferedWriter(new FileWriter(StructFile.TRAIN_FILE));
            try {
                train.write(factory+"\n");
                train.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }






    public static ArrayList<String> load(String FileName){
        BufferedReader reader;
        ArrayList<String> result = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(FileName));
            String line = null;
            try {
                line = reader.readLine();
                result.add(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (line != null) {
//                System.out.println(line);
                // read next line
                line = reader.readLine();
                result.add(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
