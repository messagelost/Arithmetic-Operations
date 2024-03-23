package org.example;

public class ContrlNumber {
    public static void main(String[] args) {
        boolean hasN = false;
        boolean hasR = false;

        //-n -r输入参数
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-n")) {
                hasN = true;
                if (i + 1 < args.length) {
                    try {
                        CreateOperation.nValue = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        // 处理无法解析为整数的情况
                        System.err.println("Invalid value after -n parameter.");
                    }
                }
            }
            if (args[i].equals("-r")) {
                hasR = true;
                if (i + 1 < args.length) {
                    try {
                        CreateOperation.rValue = Integer.parseInt(args[i + 1]);
                    } catch (NumberFormatException e) {
                        // 处理无法解析为整数的情况
                        System.err.println("Invalid value after -r parameter.");
                    }
                }
                break; // 找到-r参数后跳出循环
            }
        }



    }
}
