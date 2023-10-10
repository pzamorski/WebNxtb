package com.webnxtb.helpers;

import java.io.*;


public class Filer {

    public static void save(String fileName,String content) {

        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: Unable to save the file.");
        }
    }
    public static String load(String fileName){

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringWriter stringWriter = new StringWriter();
            String line;
            while ((line = reader.readLine()) != null) {
                stringWriter.write(line);
                stringWriter.write("\n");
            }
            System.out.println("File load successfully.");
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int CountFilesInDirectory(String directoryPath){
        int fileCount = 0;
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                fileCount = files.length;
            } else {
                System.err.println(directoryPath+" Katalog jest pusty.");
            }
        } else {
            System.err.println(directoryPath+ "Katalog nie istnieje lub nie jest katalogiem.");
        }
        return fileCount;
    }
    public static boolean deleteDir(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Katalog nie istnieje lub nie jest katalogiem.");
            return false;
        }

        return deleteDirectory(directory);
    }
    public static boolean deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }


}