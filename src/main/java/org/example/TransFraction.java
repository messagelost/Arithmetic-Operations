package org.example;




public class TransFraction  {
private char[]operater;
private int[]calNumber;
private  int []decide;

    public TransFraction(char[] operater, int[] calNumber,int[]decide) {
        this.operater=operater;
        this.calNumber=calNumber;
        this.decide=decide;
    }

    public Fraction[] tr() {
       // System.out.println(decide);
        Fraction fra[]=new Fraction[decide.length-1];
        int k=0;
        for (int i = 0; i < decide.length-1; i++) {
            if(decide[i]==0) {
                fra[i]=new Fraction(calNumber[k++],0,1);
            } else {
                fra[i]=new Fraction(calNumber[k],calNumber[k+1],calNumber[k+2]);
                k+=3;
            }
        }
        for (int i = 0; i < decide.length-1; i++) {
            System.out.printf("%d,%d,%d\n",fra[i].getWholeNumber(),fra[i].getNumerator(),fra[i].getDenominator());

        }
        return fra;
    }

    public static void main(String[] args) {
        int []a={10,9,7,5,6,4,5};
        int []b={0,0,1,0,0,32};
        char []c={'a'};
        TransFraction trs=new TransFraction(c,a,b);
        trs.tr();
    }
}
