package org.example;
import static org.example.ArithmeticOperation.*;
import static org.example.CreateOperation.*;

public class Main {
    public static void main(String[] args) {

        int r =10;
        r =10;
        char []operater=Operator(r);
        int []decide=Decide(operater);
        int []calNumber=CalNumber(decide,r);
        TransFraction trans=new TransFraction(operater,calNumber,decide);
        Fraction[] append=trans.tr();
        Fraction  result=evaluateExpression(operater,append);
        System.out.println(operater);
        System.out.println("运算结果为：" + result.getDenominator());

    }
}