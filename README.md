## 使用方法
### 生成题目与答案
在命令行输入ArithmeticOperation.exe -n<题目数量> -r<数字范围>
结果可在exercisefile.txt与answer.txt查看
### 比对输入答案与正确答案
创建Userfile.txt文件，在文件中输入答案
在命令行输入ArithmeticOperation.exe -e<exercisefile.txt> -a<answer.txt>
得分正误可在Grade.txt查看

## 类与接口
- 1.**MathExerciseGenerator类：**：
    - **该类主要实现<span style="color:red">参数命令行程序、将生成的四则运算表达式和答案存入对应文件，生成答案正确错误的显示文件、对命令行的检索的功能</span>。**
- 2.**CreateOperation类：**：
    - **该类主要实现<span style="color:red">随机运算符、自然数、分数、自然数、括号的随机生成，还包括了生成有无括号的四则运算表达式的字符串</span>。**
- 3.**Fraction类：**：
    - **该类实现了<span style="color:red">真分数的结构以及运算函数</span>，有对真分数的加减乘除以及化简操作。**
- 4.**TransFraction类：**：
    - **该类实现了对随机数的转换，将随机生成的自然数，分数，真分数转化成包括><span style="color:red"> wholeNumber，numerator，denominato</span>的真分数。**
- 5.**ArithmeticOperation类：**：
    - **该类主要实现对有括号四则运算表达式中运算操作符的提取，并利用<span style="color:red">栈与Fraction类</span>计算出最终运算结果。**
- 6.**ArithmeticExpressionEquivalence类：**：
    - **该类主要利用<span style="color:red">逆波兰算法将中缀表达式转换为后缀表达式，用来观察各个式子运算顺序是否相同。</span>**
[参考链接](https://www.cnblogs.com/lanhaicode/p/10776166.html)
# <span style="color:black">

## 核心代码
<details>
  <summary>CreateOperation</summary>
  
```public class CreateOperation {
    public static int nValue;//生成题目个数
    public static int rValue;//题目中数值范围
     public static void main(String[] args) {
        int n = nValue;
        int r = rValue;
        r =10;
        Random random = new Random();
         char []operater=Operator(r);
         //System.out.println(operater);
         int []decide=Decide(operater);
         //System.out.println(Arrays.toString(decide));
         int []calNumber=CalNumber(decide,r);
         //System.out.println(Arrays.toString(calNumber));
        char[]e = Create(decide,operater,calNumber);
        System.out.println(e);

    }

    public static char[] Create(int[] decide,char[]operater,int[]calNumber){
        Random random = new Random();
        int[]hesis=new int[1];
        hesis[0]= random.nextInt(2);//选择是否添加括号
        char[] e = My_string(decide,operater,calNumber).toCharArray();//数组e储存初始式子
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
        if(operater.length!=1&&hesis[0]==1){
            e = Final_Expresion(String.valueOf(e),decide,operater,calNumber,hesis);
            //System.out.println(Arrays.toString(e1));
        }
        return e;
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

        int operatorNumber=operater.length + 1;
        Random random = new Random();
        int calnumber = 0;
        int[] decide = new int[operatorNumber +1];//数组记录决定生成自然数（记为0）或真分数（记为1）带分数（记为2）
        for (int i = 0; i < operatorNumber; i++) {//决定生成自然数或者真分数
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
                   int randomnumber = random.nextInt(r-1)+1;
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
           return calNumber;
       }
       public static String My_string(int[]decide,char []operator,int []calNumber){
           String fomula = new String();
           int count = 0;
           for (int i = 0; i < decide.length - 2; i++) {
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
           if (decide[decide.length - 2] == 0) {
               fomula = fomula + String.valueOf(calNumber[count++]);
           } else if (decide[decide.length - 2] == 1) {
               fomula = fomula + String.valueOf(calNumber[count]) + "/" + String.valueOf(calNumber[count + 1]);
               count += 2;
           } else {
               fomula = fomula + String.valueOf(calNumber[count]) + "'" + String.valueOf(calNumber[count + 1]) + "/" + String.valueOf(calNumber[count + 2]) ;
               count += 3;
           }
           //System.out.println(fomula);
        return fomula;
       }

    public  static char[] Final_Expresion(String fomula,int[]decide,char []operator,int []calNumber,int[] hesis){
         Random random = new Random();
        int chooseNumber1;//前括号的后一个数值
        int chooseNumber2;//后括号的前一个数值
        char[] operation = new char[0];
        int j = 0;
        int count = 0;
        while (true) {
            if (hesis[0] == 0 ) {
                operation = fomula.toCharArray();
                break;//hesis值为0则不添加括号
            }
            if(operator.length == 1){
                hesis[0] = 0;
                operation = fomula.toCharArray();
                break;
            }
            else {
                chooseNumber2 = random.nextInt(decide.length - 2) + 2;
                chooseNumber1 = random.nextInt(chooseNumber2 - 1) + 1;//随机选择
                //System.out.println(chooseNumber1);
                //System.out.println(chooseNumber2);

                if(chooseNumber1==1&&chooseNumber2== decide.length-1){
                    operation = fomula.toCharArray();
                    hesis[0]=0;
                    break;
                }else{
                    fomula = "";//清空字符串重新添加
                    for (int i = 0; i < decide.length - 2; i++) {
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
                    if(chooseNumber2== decide.length-1){
                        if (decide[decide.length - 2] == 0) {
                            fomula = fomula + String.valueOf(calNumber[count++])+ ')';
                        } else if (decide[decide.length - 2] == 1) {
                            fomula = fomula + String.valueOf(calNumber[count]) + "/" + String.valueOf(calNumber[count + 1])+ ')';
                            count += 2;
                        } else {
                            fomula = fomula + String.valueOf(calNumber[count]) + "'" + String.valueOf(calNumber[count + 1]) + "/" + String.valueOf(calNumber[count + 2])+ ')';
                            count += 3;
                        }
                    }else{
                        if (decide[decide.length - 2] == 0) {
                            fomula = fomula + String.valueOf(calNumber[count++]);
                        } else if (decide[decide.length - 2] == 1) {
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

        //System.out.println(operation);

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
            int numerator = random.nextInt(denominator)+1;//生成分子
            faction[0] = numerator;
            faction[1] =  denominator;
        }
        return faction;
    }
    public static void Answer(String s){


    }
}
