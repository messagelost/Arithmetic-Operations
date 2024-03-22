package com.jacob;

import java.util.Arrays;
import java.util.Random;

public class CreateOperation {
    public static int nValue;
    public static int rValue;

    public static void main(String[] args) {
        int n = nValue;
        int r = rValue;
        r = 10;
        char[] e = Origin(r);//数组e储存式子
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
                e = Origin(r);//再次生成式子
            }
        }
        System.out.println(Arrays.toString(e));
    }

    public static char[] Origin(int r) {
        Random random = new Random();

        char[] operator = new char[3];//数组存储运算符

        int operatorNumber = random.nextInt(3)+1;//随机生成1到3个运算符

        for (int i = 0; i < operatorNumber; i++) {//随机生成运算符
            int randomnumber = random.nextInt(4) + 1;
            switch (randomnumber){
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
                default:
                    break;
            }
        }

        int calnumber = 0;
        int[] decide = new int[operatorNumber+1];//数组记录决定生成自然数（记为0）或真分数（记为1）
        for (int i = 0; i <= operatorNumber; i++) {//决定生成自然数或者真分数
            int randomnumber = random.nextInt(2);
            if(randomnumber==0){
                decide[i]=0;
                calnumber++;//自然数在数组中需要一位储存
            }
            else {
                decide[i]=1;
                calnumber = calnumber +3;//分数在数组中需要三位储存
            }
        }

        char[] calNumber = new char[calnumber];//数组储存要进行运算的数字
        int j = 0;
        for (int i = 0; i < calnumber; ) {//随机生成数字
            if(decide[j]==0){//生成自然数
                int randomnumber = random.nextInt(r);
                calNumber[i] = (char)(randomnumber+'0');
                j++;
                i++;
            }else{//生成分数
                char[] faction = ProperFaction(r);
                for (int i1 = i; i1 < i+3; i1++) {
                    calNumber[i1]=faction[i1-i];
                }
                j++;
                i = i + 3;
            }
        }

        int operationnumber = calnumber+operatorNumber*3+2;//储存生成的式子的式子长度(包括括号)
        char[] operation = new char[operationnumber];
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
            if(hesis==0)break;//hesis值为0则不添加括号
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

        return operation;
    }

    public static char[] ProperFaction(int r) {
        Random random = new Random();
        char[] faction = new char[3];
        int denominator = random.nextInt(r-1)+1;//生成分母
        int numerator = random.nextInt(denominator);//生成分子

        faction[0] = (char)(numerator+'0');
        faction[1] = '/';
        faction[2] = (char)(denominator+'0');
        return faction;
    }
}
