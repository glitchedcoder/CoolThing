package com.glitchedcode.ct.math;

import com.glitchedcode.ct.CoolThing;

public class Fraction implements Cloneable {

    private int numerator, denominator;

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction(float floatingPoint) throws IllegalArgumentException {
        this(Double.valueOf(String.valueOf(floatingPoint)));
    }

    public Fraction(double decimal) throws IllegalArgumentException {
        if (Double.isFinite(decimal)) {
            if (decimal != Math.floor(decimal)) {
                String s = String.valueOf(decimal);
                String[] n = s.split("\\."); // get the numbers after the decimal point
                CoolThing.getLogger().info(getGCF() + "");
                this.numerator = (int) (decimal * 10 * n[1].length());
                this.denominator = 10 * n[1].length();

            } else {
                this.numerator = (int) decimal;
                this.denominator = 1;
            }
        } else
            throw new IllegalArgumentException("The decimal given was infinite.");
    }

    /**
     * Sets the numerator of the fraction.
     *
     * @param numerator The numerator of the fraction.
     */
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public void setDenominator(int denominator) throws IllegalArgumentException {
        if (denominator != 0)
            this.denominator = denominator;
        else
            throw new IllegalArgumentException("Denominator cannot be 0.");
    }

    public Fraction add(Fraction fraction) {
        if (this.denominator == fraction.denominator)
            return new Fraction(this.numerator + fraction.numerator, this.denominator);
        else
            return new Fraction((this.numerator * fraction.denominator) + (fraction.numerator * this.denominator), this.denominator * fraction.denominator);
    }

    public Fraction subtract(Fraction fraction) {
        if (this.denominator == fraction.denominator)
            return new Fraction(this.numerator - fraction.numerator, this.denominator);
        else
            return new Fraction((this.numerator * fraction.denominator) - (fraction.numerator * this.denominator), this.denominator * fraction.denominator);
    }

    public Fraction multiply(Fraction fraction) {
        return new Fraction(this.numerator * fraction.numerator, this.denominator * fraction.denominator);
    }

    public Fraction divide(Fraction fraction) {
        return multiply(new Fraction(fraction.denominator, fraction.numerator));
    }

    public Fraction simplify() {
        return new Fraction(numerator / getGCF(), denominator / getGCF());
    }

    public int getGCF() {
        int a = numerator, b = denominator;
        while (a != 0 && b != 0) {
            int c = b;
            b = a % b;
            a = c;
        }
        return a + b;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public String toFraction() {
        return numerator + "/" + denominator;
    }

    public float toDecimal() {
        return numerator / denominator;
    }

    public boolean greaterThan(Fraction fraction) {
        return fraction.toDecimal() < this.toDecimal();
    }

    @Override
    public boolean equals(Object object) {
        if (object != null) {
            if (object == this)
                return true;
            if (object instanceof Fraction) {
                Fraction fraction = (Fraction) object;
                return fraction.toDecimal() == this.toDecimal();
            }
        }
        return false;
    }

    @Override
    public Fraction clone() {
        Fraction clone = null;
        try {
            clone = (Fraction) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{numerator=" + numerator + ", denominator=" + denominator + "}";
    }
}