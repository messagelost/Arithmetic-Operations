package org.example;

import java.util.Arrays;
import java.util.Random;

public class CreateOperation {
    public static int nValue;//生成题目个数
    public static int rValue;//题目中数值范围
    public static void main(String[] args) {
        int n = nValue;
        int r = rValue;
        r =20;
        char[] e = Origin(r);//数组e储存式子
        boolean CorreatOperation = true;
        while(true) {//判读式子是否正确
            for (int i = 0; i < e.length - 2; i++) {
                if (e[i] == '\u00F7' && e[i + 2] == '0') {//如果式子有除以0
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
        System.out.print("式子：");
        for (char c : e) {
            System.out.print(c);
        }
    }

    public static char[] Origin(int r) {
        Random random = new Random();
        char[] operator = new char[3];//数组存储运算符
        int operatorNumber = random.nextInt(3) + 1;//随机生成1到3个运算符
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
                default:
                    break;
            }
        }

        int calnumber = 0;
        int[] decide = new int[operatorNumber + 1];//数组记录决定生成自然数（记为0）或真分数（记为1）
        for (int i = 0; i <= operatorNumber; i++) {//决定生成自然数或者真分数
            int randomnumber = random.nextInt(3);
            if (randomnumber == 0) {
                decide[i] = 0;
                calnumber++;//自然数在数组中需要一位储存
            } else if (randomnumber == 1) {
                decide[i] = 1;
                calnumber = calnumber + 2;//真分数需要两位储存
            } else {
                decide[i] = 2;
                calnumber = calnumber + 3;//带分数在数组中需要三位储存
            }
        }
        int[] calNumber = new int[calnumber];//数组储存要进行运算的数字
        int j = 0;
        for (int i = 0; i < calnumber; ) {//随机生成数字
            if (decide[j] == 0) {//生成自然数
                int randomnumber = random.nextInt(r);
                calNumber[i] = randomnumber;
                j++;
                i++;
            } else if (decide[j] == 1) {//生成真分数
                int[] faction = ProperFaction(r, 0);
                for (int i1 = i; i1 < i + 2; i1++) {
                    calNumber[i1] = faction[i1 - i];
                }
                j++;
                i = i + 2;
            } else {//生成带分数
                int[] faction = ProperFaction(r, 1);
                for (int i1 = i; i1 < i + 3; i1++) {
                    calNumber[i1] = faction[i1 - i];
                }
                j++;
                i = i + 3;
            }
        }

        String fomula = new String();
        int count = 0;
        for (int i = 0; i < decide.length - 1; i++) {
            if (decide[i] == 0) {
                fomula = fomula + String.valueOf(calNumber[count++]) + ' ' + operator[i] + ' ';
            } else if (decide[i] == 1) {
                fomula = fomula + String.valueOf(calNumber[count]) + "/" + String.valueOf(calNumber[count + 1]) + ' ' + operator[i] + ' ';
                count += 2;
            } else {
                fomula = fomula + String.valueOf(calNumber[count]) + "'" + String.valueOf(calNumber[count + 1]) + "/" + String.valueOf(calNumber[count + 2]) + ' ' + operator[i] + ' ';
                count += 3;
            }
        }
        //添加最后一个数值
        if (decide[decide.length - 1] == 0) {
            fomula = fomula + String.valueOf(calNumber[count++]);
        } else if (decide[decide.length - 1] == 1) {
            fomula = fomula + String.valueOf(calNumber[count]) + "/" + String.valueOf(calNumber[count + 1]);
            count += 2;
        } else {
            fomula = fomula + String.valueOf(calNumber[count]) + "'" + String.valueOf(calNumber[count + 1]) + "/" + String.valueOf(calNumber[count + 2]) ;
            count += 3;
        }
        System.out.println(fomula);//不加括号的原式子



        int hesis = random.nextInt(2);//选择是否添加括号
        int chooseNumber1;//前括号的后一个数值
        int chooseNumber2;//后括号的前一个数值
        char[] operation = new char[0];
        j = 0;
        count = 0;
        while (true) {
            if (hesis == 0 || operatorNumber == 1) {
                operation = fomula.toCharArray();
                break;//hesis值为0则不添加括号
            }
            else {
                chooseNumber2 = random.nextInt(decide.length - 1) + 2;
                chooseNumber1 = random.nextInt(chooseNumber2 - 1) + 1;//随机选择
                System.out.println(chooseNumber1);
                System.out.println(chooseNumber2);

                if(chooseNumber1==1&&chooseNumber2== decide.length){
                    operation = fomula.toCharArray();
                    break;
                }else{
                    fomula = "";//清空字符串重新添加
                    for (int i = 0; i < decide.length - 1; i++) {
                        if(i==chooseNumber1-1){//添加前括号
                            if (decide[i] == 0) {
                                fomula = fomula + '(' + String.valueOf(calNumber[count++]) + ' ' + operator[i] + ' ';
                            } else if (decide[i] == 1) {
                                fomula = fomula + '(' + String.valueOf(calNumber[count]) + "/" + String.valueOf(calNumber[count + 1]) + ' ' + operator[i] + ' ';
                                count += 2;
                            } else {
                                fomula = fomula + '(' + String.valueOf(calNumber[count]) + "'" + String.valueOf(calNumber[count + 1]) + "/" + String.valueOf(calNumber[count + 2]) + ' ' + operator[i] + ' ';
                                count += 3;
                            }
                        } else if (i == chooseNumber2-1) {//添加后括号
                            if (decide[i] == 0) {
                                fomula = fomula + String.valueOf(calNumber[count++])+ ')' + ' ' + operator[i] + ' ' ;
                            } else if (decide[i] == 1) {
                                fomula = fomula + String.valueOf(calNumber[count]) + "/" + String.valueOf(calNumber[count + 1])+ ')' + ' ' + operator[i] + ' ' ;
                                count += 2;
                            } else {
                                fomula = fomula + String.valueOf(calNumber[count]) + "'" + String.valueOf(calNumber[count + 1]) + "/" + String.valueOf(calNumber[count + 2])+ ')' + ' ' + operator[i] + ' ' ;
                                count += 3;
                            }
                        }else{//添加数值
                            if (decide[i] == 0) {
                                fomula = fomula + String.valueOf(calNumber[count++]) + ' ' + operator[i] + ' ';
                            } else if (decide[i] == 1) {
                                fomula = fomula + String.valueOf(calNumber[count]) + "/" + String.valueOf(calNumber[count + 1]) + ' ' + operator[i] + ' ';
                                count += 2;
                            } else {
                                fomula = fomula + String.valueOf(calNumber[count]) + "'" + String.valueOf(calNumber[count + 1]) + "/" + String.valueOf(calNumber[count + 2]) + ' ' + operator[i] + ' ';
                                count += 3;
                            }
                        }
                    }
                    //添加最后一个数
                    if(chooseNumber2== decide.length){
                        if (decide[decide.length - 1] == 0) {
                            fomula = fomula + String.valueOf(calNumber[count++])+ ')';
                        } else if (decide[decide.length - 1] == 1) {
                            fomula = fomula + String.valueOf(calNumber[count]) + "/" + String.valueOf(calNumber[count + 1])+ ')';
                            count += 2;
                        } else {
                            fomula = fomula + String.valueOf(calNumber[count]) + "'" + String.valueOf(calNumber[count + 1]) + "/" + String.valueOf(calNumber[count + 2])+ ')';
                            count += 3;
                        }
                    }else{
                        if (decide[decide.length - 1] == 0) {
                            fomula = fomula + String.valueOf(calNumber[count++]);
                        } else if (decide[decide.length - 1] == 1) {
                            fomula = fomula + String.valueOf(calNumber[count]) + "/" + String.valueOf(calNumber[count + 1]);
                            count += 2;
                        } else {
                            fomula = fomula + String.valueOf(calNumber[count]) + "'" + String.valueOf(calNumber[count + 1]) + "/" + String.valueOf(calNumber[count + 2]) ;
                            count += 3;
                        }
                    }
                }
                operation = fomula.toCharArray();
                break;
            }
        }

        System.out.println(operation);

        return operation;
    }

    public static int[] ProperFaction(int r,int t) {
        Random random = new Random();
        int[] faction = new int[2+t];
        if(t==1){//带分数
            int real=random.nextInt(r-1)+1;
            int denominator = random.nextInt(r-1)+1;//生成分母
            int numerator = random.nextInt(denominator);//生成分子
            faction[0] = real;
            faction[1] = numerator;
            faction[2] =  denominator;
        }else{//真分数
            int denominator = random.nextInt(r-1)+1;//生成分母
            int numerator = random.nextInt(denominator);//生成分子
            faction[0] = numerator;
            faction[1] =  denominator;
        }


        return faction;
    }
}
