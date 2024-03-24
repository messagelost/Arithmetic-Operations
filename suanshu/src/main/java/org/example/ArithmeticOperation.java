package org.example;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;

import static org.example.CreateOperation.*;

public class ArithmeticOperation {

    public static Fraction evaluateExpression(char[] operators, Fraction[]operands, int hesis) {
        if ((operators.length != operands.length - 1) && hesis == 0) {
            throw new IllegalArgumentException("运算符数量与运算数不匹配");
        }
        if(((operators.length-2) != operands.length - 1) && hesis == 1){
            throw new IllegalArgumentException("运算符数量与运算数不匹配");
        }
        Stack<Fraction> numberStack = new Stack<>();//数值栈
        Stack<Character> operatorStack = new Stack<>();//运算符栈

        if(hesis==0){//无括号情况
            int start = 1;
            int operandIndex = 0;//数值数
            for (int i = 0; i < operators.length; i++) {
                char operator = operators[i];//获取运算符
                Fraction operand = operands[operandIndex++];//获取分数
                numberStack.push(operand);//将分数入栈

                while (!operatorStack.isEmpty() && hasPrecedence(operator, operatorStack.peek(),hesis)) {
                    performOperation(numberStack, operatorStack);
                }
                operatorStack.push(operator);//运算符入栈
            }

            numberStack.push(operands[operandIndex]);

            while (!operatorStack.isEmpty()) {
                performOperation(numberStack, operatorStack);
            }

            return numberStack.pop();
        }else{
            int operandIndex = 0;//数值数
            int start = 0;
            for (int i = 0; i < operators.length-1; i++) {
                char operator = operators[i];//获取运算符
                Fraction operand = operands[operandIndex++];//获取分数
                numberStack.push(operand);//将分数入栈

                while (!operatorStack.isEmpty() && hasPrecedence(operator, operatorStack.peek(),start)) {
                    performOperation(numberStack, operatorStack);
                }
                if(operator=='('){
                    operatorStack.push(operator);//符号入栈
                    i++;
                    char operator1 = operators[i];//获取运算符
                    operatorStack.push(operator1);//运算符入栈
                }else if(operator==')'){
                    operatorStack.pop();
                    if(operatorStack.isEmpty()){
                        i++;
                        char operator1 = operators[i];//获取运算符
                        operatorStack.push(operator1);//运算符入栈
                    }
                }else{
                    operatorStack.push(operator);//符号入栈
                }
            }

            if(operandIndex==operators.length){
                performOperation(numberStack, operatorStack);
                return numberStack.pop();

            }else{
                numberStack.push(operands[operandIndex]);

                while (!operatorStack.isEmpty()) {
                    performOperation(numberStack, operatorStack);
                }

                return numberStack.pop();
            }
        }

    }

    private static boolean hasPrecedence(char op1, char op2, int start) {
        if(op1=='('){
            start = 1;
            return false;
        }
        if(start==0){
            return false;
        }
        if ((op1 == '\u00D7' || op1 == '\u00F7') && (op2 == '+' || op2 == '-')) {
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

    public static void main(String[] args) {
        int r=10;
        char []operater=Operator(r);
        int []decide=Decide(operater);
        int []calNumber=CalNumber(decide,r);

        Random random = new Random();
        int hesis = random.nextInt(2);//选择是否添加括号
        System.out.println(hesis);
        char[] e = My_string(decide,operater,calNumber).toCharArray();//数组e储存初始式子
        System.out.println(e);
        if(hesis==1){
            char[] e1 = Final_Expresion(String.valueOf(e),decide,operater,calNumber,hesis);//最终式子
            System.out.println(e1);
            operater = removeDigits(e1);//带括号的运算符数组
            System.out.println(operater);
        }

        TransFraction trans=new TransFraction(operater,calNumber,decide);
        Fraction[] append=trans.tr();
        Fraction  result=evaluateExpression(operater,append,hesis);
        System.out.println(operater);
        System.out.println("运算结果为：" + result.toDecimal());
       /* char []operater=Operator(r);
        int []decide=Decide(operater);
        int []calNumber=CalNumber(decide,r);
        Fraction[] operands ={new Fraction(1,1,3),new Fraction(0,2,3)};
        Fraction result = evaluateExpression(operators, operands);
        System.out.println("运算结果为：" + result.getWholeNumber());*/
    }
}

