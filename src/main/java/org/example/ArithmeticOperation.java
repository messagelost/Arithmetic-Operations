package org.example;

import java.util.Optional;
import java.util.Stack;

import static org.example.CreateOperation.*;

public class ArithmeticOperation {

    public static Fraction evaluateExpression(char[] operators, Fraction[]operands) {
        if (operators.length != operands.length - 1) {
            throw new IllegalArgumentException("运算符数量与运算数不匹配");
        }

        Stack<Fraction> numberStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        int operandIndex = 0;
        for (int i = 0; i < operators.length; i++) {
            char operator = operators[i];
            Fraction operand = operands[operandIndex++];
            numberStack.push(operand);

            while (!operatorStack.isEmpty() && hasPrecedence(operator, operatorStack.peek())) {
                performOperation(numberStack, operatorStack);
            }

            operatorStack.push(operator);
        }

        numberStack.push(operands[operandIndex]);

        while (!operatorStack.isEmpty()) {
            performOperation(numberStack, operatorStack);
        }

        return numberStack.pop();
    }

    private static boolean hasPrecedence(char op1, char op2) {
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



    public static void main(String[] args) {
        int r=10;
        char []operater=Operator(r);
        int []decide=Decide(operater);
        int []calNumber=CalNumber(decide,r);
        TransFraction trans=new TransFraction(operater,calNumber,decide);
        Fraction[] append=trans.tr();
        Fraction  result=evaluateExpression(operater,append);
        System.out.println(operater);
        System.out.println("运算结果为：" + result.getDenominator());
       /* char []operater=Operator(r);
        int []decide=Decide(operater);
        int []calNumber=CalNumber(decide,r);
        Fraction[] operands ={new Fraction(1,1,3),new Fraction(0,2,3)};
        Fraction result = evaluateExpression(operators, operands);
        System.out.println("运算结果为：" + result.getWholeNumber());*/
    }
}

