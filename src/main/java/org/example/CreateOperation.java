package org.example;

import java.util.Arrays;
import java.util.Random;

public class CreateOperation {    public static int nValue;//生成题目个数

    public static int rValue;//题目中数值范围
     public static void main(String[] args) {
        int n = nValue;
        int r = rValue;
        r =10;
        char []operater=Operator(r);
        int []decide=Decide(operater);
        int []calNumber=CalNumber(decide,r);
        char[] e = My_string(decide,operater,calNumber).toCharArray();//数组e储存式子
        boolean CorreatOperation = true;
        while(true) {//判读式子是否正确
            for (int i = 0; i < e.length - 1; i++) {
                if (e[i] == '\u00F7' && e[i + 1] == '0') {//如果式子有除以0
                    CorreatOperation = false;
                }
            }
            if (CorreatOperation) {
                break;//式子合格跳出循环
            } else {
                CorreatOperation = true;
                e = My_string(decide,operater,calNumber).toCharArray();//再次生成式子
            }
        }
        System.out.println(Arrays.toString(e));
    }

    public static char[] Operator(int r) {
        Random random = new Random();
        int operatorNumber = random.nextInt(3) + 1;//随机生成1到3个运算符
        char[] operator = new char[operatorNumber];//数组存储运算符
        for (int i = 0; i < operatorNumber; i++) {//随机生成运算符
            int randomnumber = random.nextInt(4) + 1;
            switch (randomnumber) {
                case 1://生成加号
                    operator[i] = '+';
                    break;
                case 2://生成减号
                    operator[i] = '-';
                    break;
                case 3://生成乘号
                    operator[i] = '\u00D7';
                    break;
                case 4://生成除号
                    operator[i] = '\u00F7';
                    break;
            }
        }
        return operator;
    }
        public static int[]Decide(char [] operater){

        int operatorNumber=operater.length+1;
        Random random = new Random();
        int calnumber = 0;
        int[] decide = new int[operatorNumber +1];//数组记录决定生成自然数（记为0）或真分数（记为1）
        for (int i = 0; i <operatorNumber; i++) {//决定生成自然数或者真分数
            int randomnumber = random.nextInt(2);
            if (randomnumber == 0) {
                decide[i] = 0;
                calnumber++;//自然数在数组中需要一位储存
            } else {
                decide[i] = 1;
                calnumber = calnumber + 3;//分数在数组中需要三位储存
            }
        }
        decide[decide.length-1]=calnumber;
        return decide;
    }

       public static int[]CalNumber(int[]decide,int r) {
           Random random = new Random();
           int calnumber = decide[decide.length - 1];
           int[] calNumber = new int[calnumber];//数组储存要进行运算的数字
           int j = 0;
           for (int i = 0; i < calnumber; ) {//随机生成数字
               if (decide[j] == 0) {//生成自然数
                   int randomnumber = random.nextInt(r);
                   calNumber[i] = randomnumber;
                   j++;
                   i++;
               } else {//生成分数
                   int[] faction = ProperFaction(r);
                   for (int i1 = i; i1 < i + 3; i1++) {
                       calNumber[i1] = faction[i1 - i];
                   }
                   j++;
                   i = i + 3;
               }
           }
           return calNumber;
       }
       public static String My_string(int[]decide,char []operator,int []calNumber){
           String fomula =  new String();
           int count = 0;
           for (int i = 0; i < decide.length - 2; i++) {
               if (decide[i] == 0) {
                   fomula = fomula + String.valueOf(calNumber[count++]) + operator[i];
               } else {
                   fomula = fomula + String.valueOf(calNumber[count]) + "'" + String.valueOf(calNumber[count + 1]) + "/" + String.valueOf(calNumber[count + 2]) + operator[i];
                   count += 3;
               }
           }
           fomula = fomula + String.valueOf(calNumber[count]);
           System.out.println(fomula);
        return fomula;
       }

    /*      int operationnumber = calnumber+operatorNumber*3+2;//储存生成的式子的式子长度(包括括号)
        int[] operation = new int[operationnumber];
        int input = 0;//input为0的时候录入数值，为1的时候录入符号
        j = 0;//记录决定数组
        int k = 0;//记录数值数组
        int m = 0;//记录符号数组
        for (int i = 0; i < operationnumber-2; ) {
            if(input==0){
                if(decide[j]==0){
                    operation[i]=calNumber[k];
                    i++;//表达式数组增加一位
                    j++;//决定数组增加一位
                    k++;//数值数组增加一位
                    input = 1;//下一个录入运算符号
                }else{
                    for (int i1 = i; i1 < i+3; i1++) {
                        operation[i1]=calNumber[k];
                        k++;
                    }
                    i = i+3;//表达式数组增加三位
                    j++;//决定数组增加一位
                    input = 1;//下一个录入运算符号
                }
            }else if(input==1){
                operation[i]=' ';
                operation[i+1]=operator[m];
                operation[i+2]=' ';
                i=i+3;//表达式数组增加三位
                m++;//符号数组增加一位
                input = 0;//下一个录入运算符号
            }
        }

        int hesis = random.nextInt(2);//选择是否添加括号
        int chooseNumber1;//前括号的后一个数值
        int chooseNumber2;//后括号的前一个数值
        while(true){
            if(hesis==0||operatorNumber==1)break;//hesis值为0则不添加括号
            else{
                chooseNumber2 = random.nextInt(operatorNumber-1)+2;
                chooseNumber1 = random.nextInt(chooseNumber2-1)+1;//随机选择

                int count1 = 0;
                int count2 = 0;
                for (int i = 0; i < chooseNumber1-1; i++) {//前括号前面表达式数组有几位
                    if(decide[i]==0)count1 = count1 + 4;
                    else count1 = count1 + 6;
                }

                for (int i = chooseNumber1-1; i <= chooseNumber2-1; i++) {
                    if(decide[i]==0)count2 = count2 + 4;
                    else count2 = count2+6;
                }
                count2 = count2 - 2;//括号里有几位

                for (int i = operationnumber-3; i >= count1; i--) {//前括号的位置及其后字符后移一位
                    operation[i+1] = operation[i];
                }
                operation[count1]='(';//添加前括号

                for (int i = operationnumber-2; i >= count2+count1; i--) {//后括号的位置及其后后移一位
                    operation[i+1] = operation[i];
                }
                operation[count2+count1]=')';//添加后括号
                break;
            }
        }
*/



    public static int[] ProperFaction(int r) {
        Random random = new Random();
        int[] faction = new int[3];
        int real=random.nextInt(r-1)+1;
        int denominator = random.nextInt(r-1)+1;//生成分母
        int numerator = random.nextInt(denominator)+1;//生成分子
        faction[0] = real;
        faction[1] = numerator;
        faction[2] =  denominator;
        return faction;

    }
    public static void Answer(String s){


    }
}
