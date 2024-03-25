package org.example;

import java.io.*;

public class Filerw {

    public static void writeToFile(String fileName, String data) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String fileName) {
        StringBuilder data = new StringBuilder();
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                data.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    public static void main(String[] args) {
        // 写入 exercisefile.txt
        writeToFile("exercisefile.txt", "1/6 + 1/8 =");
        // 写入 answerfile.txt
        writeToFile("answerfile.txt", "7/24");
        // 读取 exercisefile.txt 和 answerfile.txt
        String exerciseContent = readFromFile("exercisefile.txt");
        String answerContent = readFromFile("answerfile.txt");
        System.out.println("exercisefile.txt 内容：\n" + exerciseContent);
        System.out.println("answerfile.txt 内容：\n" + answerContent);
    }
}