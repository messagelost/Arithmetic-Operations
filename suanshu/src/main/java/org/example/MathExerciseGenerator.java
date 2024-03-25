package org.example;
import java.io.*;
import java.util.*;

import static org.example.ArithmeticExpressionEquivalence.PexpretoSexpre;
import static org.example.ArithmeticExpressionEquivalence.UniqueStringGenerator;
import static org.example.CreateOperation.*;
import static org.example.ArithmeticOperation.*;
public class MathExerciseGenerator {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("参数不足，请按以下格式输入：Myapp.exe -n <题目数量> -r <数值范围>");
            return;
        }

        if (args[0].equals("-n")) {
            int n = Integer.parseInt(args[1]);
            int r = Integer.parseInt(args[3]);
            generateExercises(n, r);
        } else if (args[0].equals("-e") && args[2].equals("-a")) {
            String exerciseFile = args[1];
            String answerFile = args[3];
            checkAnswers(exerciseFile, answerFile);
        } else {
            System.out.println("请输入正确的参数格式：-n <题目数量> -r <数值范围> 或 -e <题目文件名> -a <答案文件名>");
        }
    }

    public static void generateExercises(int n, int r) {
        // 生成题目并计算答案
        HashSet<String>set = new HashSet<>();
        List<String> exercises = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            while(true){
                Random random = new Random();
                String e2=new String();
                char []operater=Operator(r);
                int []decide=Decide(operater);
                int []calNumber=CalNumber(decide,r);

                TransFraction trans=new TransFraction(operater,calNumber,decide);
                Fraction[] append=trans.tr();

                char[]e = Create(decide,operater,calNumber);
                if(UniqueStringGenerator(set,PexpretoSexpre(e,decide,append))){//判断是否重复
                    // 进行四则运算，可以使用随机数生成器来生成题目内容

                    Fraction  result=evaluateExpression(e,decide,append);
                    // 计算答案
                    String answer = "题目"+i+ "答案" + getResult(result);
                    exercises.add("题目" + i+ e2+ '='); // 将题目添加到列表
                    answers.add(answer); // 将答案添加到列表
                    break;
                }

            }
        }

        // 写入文件
        try (PrintWriter writer = new PrintWriter("answerfile.txt")) {
            for (String answer : answers) {
                writer.println(answer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (PrintWriter writer = new PrintWriter("exercisefile.txt")) {
            for (String exercise : exercises) {
                writer.println(exercise);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void checkAnswers(String exerciseFile, String answerFile) {
        List<String> exerciseList = readLinesFromFile(exerciseFile);
        List<String> answerList = readLinesFromFile(answerFile);

        int total = exerciseList.size();
        int correct = 0;
        List<Integer> correctList = new ArrayList<>();
        List<Integer> wrongList = new ArrayList<>();

        for (int i = 0; i < total; i++) {
            // 检查答案是否正确，可以根据题目和答案列表中的内容进行比较
            // 比较是否重复
            if (true) {
                correct++;
                correctList.add(i + 1);
            } else {
                wrongList.add(i + 1);
            }
        }

        // 输出批改结果
        try (PrintWriter writer = new PrintWriter("Grade.txt")) {
            writer.println("Correct: " + correct + " (" + String.join(", ", correctList.toString()) + ")");
            writer.println("Wrong: " + (total - correct) + " (" + String.join( wrongList.toString()) + ")");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readLinesFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
