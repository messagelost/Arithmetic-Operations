package org.example;

import java.util.*;

import static org.example.ArithmeticOperation.hasPrecedence;
import static org.example.ArithmeticOperation.removeDigits;
import static org.example.CreateOperation.*;

public class ArithmeticExpressionEquivalence {


    public static String PexpretoSexpre(char[]operation,int[]decide,Fraction[]operands){
        /*逆波兰算法
      将中缀表达式转换为后缀表达式，用来观察各个式子运算顺序是否相同*/
        StringBuilder postfix = new StringBuilder();//数值栈
        Stack<String> operatorStack = new Stack<>();//运算符栈

        int operandNUmber = 0;
        String newArray = new String(operation);
        String[] newoperation = newArray.split(" ");
        //System.out.println(Arrays.toString(newoperation));

        for (int i = 0; i < newoperation.length; ) {//遍历中缀表达式
            if(isNumeric(newoperation[i])){
                postfix.append(operands[operandNUmber++].toDecimal());
                postfix.append('|');
                i++;
            }else if(newoperation[i].equals("(")){
                operatorStack.push(newoperation[i]);
                i++;
            }else if(newoperation[i].equals(")")){
                while(!operatorStack.isEmpty()){
                    String operator = operatorStack.pop();
                    if(!operator.equals("(")){
                        postfix.append(operator);
                        postfix.append('|');
                    }else{
                        break;
                    }
                }
                i++;
            }else{//运算符
                String operator = newoperation[i];
                if(!operatorStack.isEmpty()){
                    if(operatorStack.peek().equals("(")){//如果栈顶是左括号 "(", 则将运算符压入栈
                        operatorStack.push(operator);
                    }else if(precedence(operator)>precedence(operatorStack.peek())){//若当前运算符的优先级高于栈顶运算符，则将当前运算符压入栈
                        operatorStack.push(operator);
                    }else {//若当前运算符的优先级低于或等于栈顶运算符，则将栈顶运算符弹出并加入postfix
                        postfix.append(operatorStack.pop());
                        postfix.append('|');
                        operatorStack.push(operator);
                    }
                }else operatorStack.push(operator);//若栈为空压入栈
                i++;
            }
        }
        while(!operatorStack.isEmpty()){
            String operator = operatorStack.pop();
            postfix.append(operator);
            postfix.append('|');
        }
        return postfix.toString();
    }

    static boolean isNumeric(String str) {
        char[] newstr = str.toCharArray();
        if(Character.isDigit(newstr[0])){
            return true;
        }
        return false;
    }

    private static int precedence(String operator) {//运算符权重
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "\u00D7":
            case "\u00F7":
                return 2;
            default:
                return 0;
        }
    }

    public  static  String CommutativeProperty(String postfix){
        //不影响运算顺序的加法与乘法交换律
        // 分割字符串并存储到数组
        String[] elements = postfix.split("\\|");
        String newpostfix = postfix;
        StringBuilder mix = new StringBuilder();

        for (int i = 0; i < elements.length; i++) {
            if((elements[i].equals("\u00D7")||elements[i].equals("+"))&&isNumeric(elements[i-2])){
                String temp = elements[i-1];
                elements[i-1] = elements[i-2];
                elements[i-2] = temp;
                for (String element : elements) {
                    mix.append(element).append("|");
                }
                newpostfix =  mix.toString();
                return newpostfix;
            }
        }
        return newpostfix;
    }


    public static boolean UniqueStringGenerator (HashSet<String>set,String postfix){
        if(set.contains(postfix)||set.contains(CommutativeProperty(postfix))) {
            return false;
        }
        return true;
    }

}
