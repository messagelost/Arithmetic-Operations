package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

import static org.example.ArithmeticOperation.hasPrecedence;
import static org.example.ArithmeticOperation.removeDigits;
import static org.example.CreateOperation.*;

public class ArithmeticExpressionEquivalence {


    public static String PexpretoSexpre(char[]operation,int[]decide,Fraction[]operands){
        /*逆波兰算法
      将中缀表达式转换为后缀表达式，用来观察各个式子运算顺序是否相同*/
        StringBuilder postfix = new StringBuilder();//数值栈
        Stack<Character> operatorStack = new Stack<>();//运算符栈

        int decideNumber = 0;
        int operandNUmber = 0;

        for (int i = 0; i < operation.length; ) {//遍历中缀表达式
            if(Character.isDigit(operation[i])){
                postfix.append(operands[operandNUmber++].toDecimal());
                postfix.append('|');
                switch(decide[decideNumber++]){
                    case 0:
                        i += 1;
                        break;
                    case 1:
                        i += 3;
                        break;
                    case 2:
                        i += 5;
                        break;
                }
            }else if(operation[i]==' '){
                i++;
            }else if(operation[i]=='('){
                operatorStack.push(operation[i]);
                i++;
            }else if(operation[i]==')'){
                while(!operatorStack.isEmpty()){
                    char operator = operatorStack.pop();
                    if(operator!='('){
                        postfix.append(operator);
                        postfix.append('|');
                    }else{
                        break;
                    }
                }
                i++;
            }else{//运算符
                char operator = operation[i];
                if(!operatorStack.isEmpty()){
                    if(operatorStack.peek()=='('){//如果栈顶是左括号 "(", 则将运算符压入栈
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
            char operator = operatorStack.pop();
            postfix.append(operator);
            postfix.append('|');
        }
        return postfix.toString();
    }

    private static int precedence(char operator) {//运算符权重
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '\u00D7':
            case '\u00F7':
                return 2;
            default:
                return 0;
        }
    }

    public  static  String CommutativeProperty(String postfix){
        //加法与乘法交换律
        // 分割字符串并存储到数组
        String[] elements = postfix.split("\\|");
        String newpostfix = postfix;
        StringBuilder mix = new StringBuilder();

        for (int i = 0; i < elements.length; i++) {
            if(elements[i].equals("\u00D7")||elements[i].equals("+")){
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

    public static void main(String[] args) {//测试
        int r=10;
        Random random = new Random();

        HashSet<String> set = new HashSet<>();
        String str = new String();

//        //判重测试
//            for (int i = 0; i < 10; ) {
//            char []operater=Operator(r);
//            int []decide=Decide(operater);
//            int []calNumber=CalNumber(decide,r);
//
//            TransFraction trans=new TransFraction(operater,calNumber,decide);
//            Fraction[] operand=trans.tr();
//
//            char[]e = Create(decide,operater,calNumber);
//            str = PexpretoSexpre(e,decide,operand);
//            while(UniqueStringGenerator(set,str)){
//                set.add(str);
//                i++;
//                System.out.println(e);
//                System.out.println(str);
//            }
//        }

        char []operater={'+','\u00D7'};
        int []decide={0,0,0,3};
        int []calNumber1={3,6,8};
        int []calNumber2={6,3,8};

        TransFraction trans1=new TransFraction(operater,calNumber1,decide);
        Fraction[] operand1=trans1.tr();
        char[]e1 = {'(','3',' ','+',' ','6',')',' ','\u00D7',' ','8'};
        System.out.println(e1);
        String str1 = PexpretoSexpre(e1,decide,operand1);
        System.out.println(str1);

        TransFraction trans2=new TransFraction(operater,calNumber2,decide);
        Fraction[] operand2=trans2.tr();
        char[]e2  = {'(','6',' ','+',' ','3',')',' ','\u00D7',' ','8'};
        System.out.println(e2);
        String str2 = PexpretoSexpre(e2,decide,operand2);
        System.out.println(str2);

        set.add(str1);
        System.out.println(UniqueStringGenerator(set,str2));

        /*交换律测试
            char []operater=Operator(r);
            int []decide=Decide(operater);
            int []calNumber=CalNumber(decide,r);

            TransFraction trans=new TransFraction(operater,calNumber,decide);
            Fraction[] operand=trans.tr();

            char[]e = Create(decide,operater,calNumber);
            str = PexpretoSexpre(e,decide,operand);
            System.out.println(str);
            String newstr  = CommutativeProperty(str);
            System.out.println(newstr);
         */


    }
}
