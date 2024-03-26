package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import static org.example.ArithmeticExpressionEquivalence.PexpretoSexpre;
import static org.example.ArithmeticOperation.*;
import static org.example.CreateOperation.*;

class ArithmeticOperationTest {

    @Test
    void evaluateexpression() {
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
            System.out.println("Calculate Answer:" + getResult(result));
            System.out.println();
        }
    }

    @Test
    void  operandnumber(){
        char[]operater1 = new char[0];
        Fraction operand1 =new Fraction(20,0,1);
        int decide1 = 0;
        Fraction operand2 =new Fraction(0,4,15);
        int decide2 = 1;
        Fraction operand3 =new Fraction(10,7,14);
        int decide3 = 2;
        int wholenumber = operand3.getWholeNumber();
        //System.out.println("实"+wholenumber);
        int numerator = operand3.getNumerator();
        //System.out.println("子"+numerator);
        int denominator = operand3.getDenominator();
        //System.out.println("母"+denominator);
        int number1 = OperandNumber(operand1,decide1);
        System.out.println(number1);
        int number2 = OperandNumber(operand2,decide2);
        System.out.println(number2);
        int number3 = OperandNumber(operand3,decide3);
        System.out.println(number3);
    }
}