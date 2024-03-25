package org.example;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;

import static org.example.CreateOperation.*;

public class ArithmeticOperation {

    public static Fraction evaluateExpression(char[] operators, Fraction[]operands, int[] hesis) {
        if ((operators.length != operands.length - 1) && hesis[0] == 0) {
            throw new IllegalArgumentException("运算符数量与运算数不匹配");
        }
        if(((operators.length-2) != operands.length - 1) && hesis[0] == 1){
            throw new IllegalArgumentException("运算符数量与运算数不匹配");
        }
        Stack<Fraction> numberStack = new Stack<>();//数值栈
        Stack<Character> operatorStack = new Stack<>();//运算符栈



        if(hesis[0]==0){//无括号情况
            int operandIndex = 0;//数值数
            int[] start = new int[1];
            start[0] = 1;
            for (int i = 0; i < operators.length; i++) {
                char operator = operators[i];//获取运算符
                Fraction operand = operands[operandIndex++];//获取分数
                numberStack.push(operand);//将分数入栈
                operatorStack.push(operator);//运算符入栈
            }

            numberStack.push(operands[operandIndex]);

            while (!operatorStack.isEmpty()) {
                performOperation(numberStack, operatorStack);
            }

            return numberStack.pop();
        }else{
            int operandIndex = 0;//数值数
            for (int i = 0; i < operators.length; i++) {
                char operator = operators[i];
                if (operator == '(') {
                    operatorStack.push(operator);
                } else if (operator == ')') {
                    while (operatorStack.peek() != '(') {
                        performOperation(numberStack, operatorStack);
                    }
                    operatorStack.pop(); // Pop the '('
                } else if (operator == '+' || operator == '-' || operator == '\u00D7' || operator == '\u00F7') {
                    while (!numberStack.isEmpty() && !operatorStack.isEmpty() && hasPrecedence(operator, operatorStack.peek())) {
                        performOperation(numberStack, operatorStack);
                    }
                    operatorStack.push(operator);
                }
                if(operandIndex<operands.length){
                    Fraction operand = operands[operandIndex++];//获取分数
                    numberStack.push(operand);}//将分数入栈
            }

            while (!operatorStack.isEmpty()) {
                performOperation(numberStack, operatorStack);
            }

            return numberStack.pop();
        }

    }

    static boolean hasPrecedence(char op1, char op2) {
        if (((op1 == '\u00D7' || op1 == '\u00F7') && (op2 == '+' || op2 == '-'||op2=='('))||((op1 == '+' || op1 == '-') && (op2=='('))) {
            return false;
        }
        return true;
    }

    private static void performOperation(Stack<Fraction> numberStack, Stack<Character> operatorStack) {
        char operator = operatorStack.pop();
        Fraction operand2 = numberStack.pop();
        Fraction operand1 = numberStack.pop();
        Fraction result=new Fraction(0,0,1) ;

        switch (operator) {
            case '+':
                result = operand1.add(operand2) ;
                break;
            case '-':
                result = operand1.subtract(operand2);
                break;
            case '\u00D7':
                result = operand1.multiply(operand2);
                break;
            case '\u00F7':

                result = operand1.divide(operand2);
                break;
        }
        numberStack.push(result);
    }

    public static char[] removeDigits(char[] charArray) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < charArray.length; i++) {
            if((charArray[i]=='+')||(charArray[i]=='-')||(charArray[i]=='\u00D7')||(charArray[i]=='\u00F7')||(charArray[i]=='(')||(charArray[i]==')')){
                result.append(charArray[i]);
            }
        }

        return result.toString().toCharArray();
    }

    public static String getResult(Fraction result){//将Fraction类型结果转化为分数或自然数
        String answer = new String();
        if(result.getWholeNumber()==0){
            if(result.getNumerator()==0){
                answer = answer + String.valueOf(0);
                return answer;
            }else{
                answer = answer + String.valueOf(result.getNumerator()) + "/" + String.valueOf(result.getDenominator());
                return answer;
            }
        }else{
            if(result.getNumerator()==0){
                answer = answer + String.valueOf(result.getWholeNumber());
                return answer;
            }else{
                answer = answer + String.valueOf(result.getWholeNumber()) + "'" + String.valueOf(result.getNumerator()) + "/" + String.valueOf(result.getDenominator());
                return answer;
            }
        }
    }

    public static void main(String[] args) {
        int r=10;
        char []operater=Operator(r);
        int []decide=Decide(operater);
        int []calNumber=CalNumber(decide,r);

        Random random = new Random();
       // System.out.println(hesis);
        char[] e = My_string(decide,operater,calNumber).toCharArray();//数组e储存初始式子
        System.out.println(e);
        int[] hesis=new int[1];
        hesis[0]=1;
        if(hesis[0]==1){
            char[] e1 = Final_Expresion(String.valueOf(e),decide,operater,calNumber,hesis);//最终式子
            System.out.println(e1);
            operater = removeDigits(e1);//带括号的运算符数组
            System.out.println(operater);
        }

        TransFraction trans=new TransFraction(operater,calNumber,decide);
        Fraction[] append=trans.tr();
        Fraction  result=evaluateExpression(operater,append,hesis);
        System.out.println(operater);
        System.out.println("运算结果为：" + getResult(result));
       /* char []operater=Operator(r);
        int []decide=Decide(operater);
        int []calNumber=CalNumber(decide,r);
        Fraction[] operands ={new Fraction(1,1,3),new Fraction(0,2,3)};
        Fraction result = evaluateExpression(operators, operands);
        System.out.println("运算结果为：" + result.getWholeNumber());*/
    }
}

