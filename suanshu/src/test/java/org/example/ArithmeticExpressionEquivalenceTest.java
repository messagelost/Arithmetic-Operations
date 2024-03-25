package org.example;

import java.util.HashSet;

import static org.example.ArithmeticExpressionEquivalence.CommutativeProperty;
import static org.example.ArithmeticExpressionEquivalence.PexpretoSexpre;
import static org.example.CreateOperation.*;
import static org.example.CreateOperation.Create;
import static org.junit.jupiter.api.Assertions.*;

class ArithmeticExpressionEquivalenceTest {

    @org.junit.jupiter.api.Test
    void pexpretoSexpre() {
        int r = 15;

        for (int i = 0; i < 10; i++) {
            char []operater=Operator(r);
            int []decide=Decide(operater);
            int []calNumber=CalNumber(decide,r);

            TransFraction trans=new TransFraction(operater,calNumber,decide);
            Fraction[] operand=trans.tr();

            char[]e = Create(decide,operater,calNumber);
            System.out.println(e);
            String str = PexpretoSexpre(e,decide,operand);
            System.out.println("逆波兰表达式："+str);
        }
    }

    @org.junit.jupiter.api.Test
    void commutativeProperty() {
        HashSet<String> set = new HashSet<>();

        //test1
        char []operater={'+','\u00D7'};
        int []decide={0,0,0,3};
        int []calNumber1={15,6,8};
        int []calNumber2={6,15,8};
        TransFraction trans1=new TransFraction(operater,calNumber1,decide);
        Fraction[] operand1=trans1.tr();
        char[]e1 = {'(',' ','1','5',' ','+',' ','6',' ',')',' ','\u00D7',' ','8'};
        System.out.println(e1);
        String str1 = PexpretoSexpre(e1,decide,operand1);
        str1 = CommutativeProperty(str1);//不改变运算顺序的情况下实现交换律
        System.out.println(str1);

        //test2
        TransFraction trans2=new TransFraction(operater,calNumber2,decide);
        Fraction[] operand2=trans2.tr();
        char[]e2  = {'(',' ','6',' ','+',' ','1','5',' ',')',' ','\u00D7',' ','8'};
        System.out.println(e2);
        String str2 = PexpretoSexpre(e2,decide,operand2);
        str2 = CommutativeProperty(str2);//不改变运算顺序的情况下实现交换律
        System.out.println(str2);
        set.add(str1);

        //test3
        char []operater1={'+','\u00D7'};
        int []decide1={0,0,0,3};
        int []calNumber3={6,6,8};
        int []calNumber4={8,6,8};
        TransFraction trans3=new TransFraction(operater1,calNumber3,decide1);
        Fraction[] operand3=trans3.tr();
        char[]e3 = {'6',' ','+',' ','6',' ','\u00D7',' ','8'};
        System.out.println(e3);
        String str3 = PexpretoSexpre(e3,decide1,operand3);
        str3 = CommutativeProperty(str3);//不改变运算顺序的情况下实现交换律
        System.out.println(str3);

        //test4
        TransFraction trans4=new TransFraction(operater1,calNumber4,decide1);
        Fraction[] operand4=trans4.tr();
        char[]e4  = {'8',' ','+',' ','6',' ','\u00D7',' ','8'};
        System.out.println(e4);
        String str4 = PexpretoSexpre(e4,decide1,operand4);
        str4 = CommutativeProperty(str4);//不改变运算顺序的情况下实现交换律
        System.out.println(str4);
        set.add(str1);
    }

    @org.junit.jupiter.api.Test
    void uniqueStringGenerator() {

    }
}