package xtb;

import pro.xstore.api.message.codes.PERIOD_CODE;

import java.io.File;
import java.io.IOException;

public class StructFile {

    private static String TRAIN_DIR;
    private static String MODEL_DIR;

    public static String TRAIN_FILE;
    public static String TEST_FILE;
    public static String MODEL_FILE;
    public static String FILE_FROM_XTB;

    private static String makeDir(String nameDir) {
        File dir = new File(nameDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    public static void create(String symbol, PERIOD_CODE periodCode) throws IOException {
        //create dir&string data/symbol/train.csv
        //create string data/symbol/test.csv
        TRAIN_DIR=StructFile.makeDir("data/" + symbol);
        TRAIN_FILE=TRAIN_DIR+"/"+"train.csv";
        TEST_FILE=TRAIN_DIR+"/"+"test.csv";
        FILE_FROM_XTB=TRAIN_DIR+"/"+"data_"+symbol+"_"+periodCode.getCode()+".json";

        //create dir&String models/symbol_model.mod
        MODEL_DIR=StructFile.makeDir("models");
        MODEL_FILE=MODEL_DIR+"/"+symbol;

    }


}
