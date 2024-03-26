package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import static org.example.ArithmeticExpressionEquivalence.PexpretoSexpre;
import static org.example.CreateOperation.*;

public class ArithmeticOperation {

    public static Fraction evaluateExpression(char[]operation,int[]decide, Fraction[]operands) {

        //System.out.println(operation);

        Stack<Fraction> calculateStack = new Stack<>();//数值栈
        Stack<Character> operatorStack = new Stack<>();//运算符栈

        int operandNUmber = 0;
        int decideNumber = 0;

        for (int i = 0; i < operation.length; ) {//遍历中缀表达式
            if(Character.isDigit(operation[i])){
                calculateStack.push(operands[operandNUmber]);
                i += OperandNumber(operands[operandNUmber], decide[decideNumber++]);
                operandNUmber++;
            } else if(operation[i]==' '){
                i++;
            } else if(operation[i]=='('){
                operatorStack.push(operation[i]);
                i++;
            }else if(operation[i]==')'){
                while(!operatorStack.isEmpty()){
                    char operator = operatorStack.pop();
                    if(operator!='('){
                        calculateStack.push(AccordOperator(operator));
                    }else{
                        break;
                    }
                }
                i++;
            }else{//运算符
                char operator = operation[i];
                if(!operatorStack.isEmpty()){
                    if(operatorStack.peek().equals('(')){//如果栈顶是左括号 "(", 则将运算符压入栈
                        operatorStack.push(operator);
                    }else if(precedence(operator)>precedence(operatorStack.peek())){//若当前运算符的优先级高于栈顶运算符，则将当前运算符压入栈
                        operatorStack.push(operator);
                    }else {//若当前运算符的优先级低于或等于栈顶运算符，则将栈顶运算符弹出并加入postfix
                        calculateStack.push(AccordOperator(operatorStack.pop()));
                        operatorStack.push(operator);
                    }
                }else operatorStack.push(operator);//若栈为空压入栈
                i++;
            }
        }

        while(!operatorStack.isEmpty()){
            char operator = operatorStack.pop();
            calculateStack.push(AccordOperator(operator));
        }

//        while(!calculateStack.isEmpty()){
//            System.out.println(calculateStack.pop().toDecimal());;
//        }

        Stack<Fraction> upsidedown = new Stack<>();
        Fraction sign=new Fraction(1000,0,1);
        upsidedown.push(sign);
        while(!calculateStack.isEmpty()){
            upsidedown.push(calculateStack.pop());
        }

        //System.out.println(PexpretoSexpre(operation,decide,operands));

        Fraction Error =new Fraction(9999,0,1);

        while(!upsidedown.isEmpty()&&upsidedown.peek().getWholeNumber()!=1000){
            Fraction calculate = upsidedown.pop();
            if(calculate.getWholeNumber()==666||calculate.getWholeNumber()==777||calculate.getWholeNumber()==888||calculate.getWholeNumber()==999){
                Fraction operand2 = calculateStack.pop();
                Fraction operand1 = calculateStack.pop();
                Fraction result=new Fraction(0,0,1);
                switch (calculate.getWholeNumber()) {
                    case 666:
                        result = operand1.add(operand2) ;
                        break;
                    case 777:
                        result = operand1.subtract(operand2);
                        if(result.toDecimal()<0){
                            return Error;
                        }
                        break;
                    case 888:
                        result = operand1.multiply(operand2);
                        break;
                    case 999:
                        if(operand2.getWholeNumber()==0&&operand2.getNumerator()==0){
                            return Error;
                        }else{
                            result = operand1.divide(operand2);
                        }
                        break;
                }
                //System.out.println(result.toDecimal());
                calculateStack.push(result);
            }else{
                calculateStack.push(calculate);
            }
        }
        Fraction result=new Fraction(0,0,1);
        if(!calculateStack.isEmpty()){
            result=calculateStack.pop();
        }
        //System.out.println(result.toDecimal());
        return result;
    }

    static int precedence(char operator) {//运算符权重
        return switch (operator) {
            case '+', '-' -> 1;
            case '\u00D7', '\u00F7' -> 2;
            default -> 0;
        };
    }
    static Fraction AccordOperator(char operator){
        Fraction accord = new Fraction(0,0,1);
        switch (operator){
            case '+':
                accord = new Fraction(666,0,1);
                break;
            case '-':
                accord = new Fraction(777,0,1);
                break;
            case '\u00D7':
                accord = new Fraction(888,0,1);
                break;
            case '\u00F7':
                accord = new Fraction(999,0,1);
                break;
        }
        return accord;
    }
    static int OperandNumber(Fraction operand,int decide){
        int count = 0;
        int wholenumber = operand.getWholeNumber();
        int numerator = operand.getNumerator();
        int denominator = operand.getDenominator();
        if(decide==0){
            String w = String.valueOf(wholenumber);
            count +=w.length();//实部的位数
        }else if(decide==1){
            String n = String.valueOf(numerator);
            count += n.length();//分子的位数
            String d = String.valueOf(denominator);
            count += d.length();//分母的位数
            count++;//斜杠
        }else{
            String w = String.valueOf(wholenumber);
            count += w.length();//实部的位数
            String n = String.valueOf(numerator);
            count += n.length();
            String d = String.valueOf(denominator);
            count += d.length();
            count +=2;
        }
        return count;
    }
    static boolean hasPrecedence(char op1, char op2) {
        if (((op1 == '\u00D7' || op1 == '\u00F7') && (op2 == '+' || op2 == '-'||op2=='('))||((op1 == '+' || op1 == '-') && (op2=='('))) {
            return false;
        }
        return true;
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
        int r=15;
        Random random = new Random();
        //System.out.println(hesis);
        for (int i = 0; i < 5; i++) {
            System.out.println("test" + i);
            char[] operater = Operator(r);
            System.out.println(operater);
            int[] decide = Decide(operater);
            System.out.println(Arrays.toString(decide));
            int[] calNumber = CalNumber(decide, r);
            System.out.println(Arrays.toString(calNumber));
            int[] hesis = new int[1];
            char[] e = Create(decide, operater, calNumber);//数组e储存初始式子
            //System.out.println(e);
            operater = removeDigits(e);
            System.out.print(e);
            System.out.println(" = ");

            TransFraction trans = new TransFraction(operater, calNumber, decide);
            Fraction[] append = trans.tr();

            System.out.println(PexpretoSexpre(e, decide, append));
            Fraction result = evaluateExpression(e,decide, append);
            System.out.println(operater);
            System.out.println("运算结果为：" + getResult(result));
            System.out.println();
        }
       /* char []operater=Operator(r);
        int []decide=Decide(operater);
        int []calNumber=CalNumber(decide,r);
        Fraction[] operands ={new Fraction(1,1,3),new Fraction(0,2,3)};
        Fraction result = evaluateExpression(operators, operands);
        System.out.println("运算结果为：" + result.getWholeNumber());*/
    }
}

