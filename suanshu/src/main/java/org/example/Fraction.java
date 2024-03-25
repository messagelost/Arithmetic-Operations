package org.example;

public class Fraction {



    public Fraction(int numerator, int denominator) {
        this(0, numerator, denominator);
    }

    // Getters and Setters for wholeNumber, numerator, and denominator
    private int wholeNumber=0;
    private int numerator=0;
    private int denominator=1;
    public Fraction(int wholeNumber, int numerator, int denominator) {
        this.wholeNumber = wholeNumber;
        this.numerator = numerator;
        this.denominator = denominator;
        simplify();
    }
    public int getWholeNumber() {
        return wholeNumber;
    }
    public int getNumerator() {
        return numerator;
    }
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public void setWholeNumber(int wholeNumber) {
        this.wholeNumber = wholeNumber;
    }

    private void simplify() {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }

//        if (numerator < 0 && wholeNumber == 0) {
//            numerator *= -1;
//        }
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }

        int gcd = gcd(Math.abs(numerator), denominator);
        numerator /= gcd;
        denominator /= gcd;

        if (numerator >= denominator) {
            wholeNumber += numerator / denominator;
            numerator %= denominator;
        }
    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public double toDecimal() {
        return wholeNumber + (double)numerator / denominator;
    }

    public Fraction add(Fraction other) {
        int newDenominator = this.denominator * other.denominator;
        int newNumerator = this.wholeNumber * newDenominator + this.numerator * other.denominator + other.wholeNumber * newDenominator + other.numerator * this.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction subtract(Fraction other) {
        int newDenominator = this.denominator * other.denominator;
        int newNumerator = this.wholeNumber * newDenominator + this.numerator * other.denominator - other.wholeNumber * newDenominator - other.numerator * this.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction multiply(Fraction other) {
        int newNumerator = (this.wholeNumber * this.denominator + this.numerator) * (other.wholeNumber * other.denominator + other.numerator);
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction divide(Fraction other) {
        int newNumerator = (this.wholeNumber * this.denominator + this.numerator) * other.denominator;
        int newDenominator = (other.wholeNumber * other.denominator + other.numerator) * this.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    // Override toString method to display the fraction in the format 1'1/2
}