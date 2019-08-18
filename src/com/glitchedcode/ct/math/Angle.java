package com.glitchedcode.ct.math;

public class Angle implements Cloneable {

    private int angle, rotations;

    public Angle() {
        this(0);
    }

    public Angle(double angle) {
        this(Math.round((float) angle));
    }

    public Angle(int angle) {
        if (angle > 360 || angle < -360) {
            this.rotations = Math.floorDiv(angle, 360);
            this.angle = angle % 360;
        } else {
            this.rotations = 0;
            this.angle = angle;
        }
    }

    public void add(double angle) {
        int newAngle = Math.round((float) angle) + this.angle;
        if (newAngle > 360 || newAngle < -360) {
            this.rotations += Math.floorDiv(newAngle, 360);
            this.angle += newAngle % 360;
        } else
            this.angle = newAngle;
    }

    public void add(Angle angle) {
        if (angle.getRotations() != 0)
            rotations += angle.getRotations();
        int newAngle = this.angle + angle.getAngle();
        if (newAngle > 360 || newAngle < -360) {
            this.rotations += Math.floorDiv(newAngle, 360);
            this.angle += newAngle % 360;
        } else
            this.angle = newAngle;
    }

    public void subtract(double angle) {
        int newAngle = this.angle - Math.round((float) angle);
        if (newAngle > 360 || newAngle < -360) {

        } else
            this.angle = newAngle;
    }

    public void subtract(Angle angle) {
        if (angle.getRotations() != 0)
            rotations -= angle.getRotations();
        int newAngle = this.angle - angle.getAngle();
        if (newAngle > 360 || newAngle < -360) {
            this.rotations -= Math.floorDiv(newAngle, 360);
            this.angle -= newAngle % 360;
        } else
            this.angle = newAngle;
    }

    public void setAngle(int angle) {
        if (angle > 360 || angle < -360) {
            this.rotations = Math.floorDiv(angle, 360);
            this.angle = angle % 360;
        } else {
            this.rotations = 0;
            this.angle = angle;
        }
    }

    public void setRotations(int rotations) {
        this.rotations = rotations;
    }

    public int getAngle() {
        return angle;
    }

    public int getRotations() {
        return rotations;
    }

    public int getFullAngle() {
        return (360 * rotations) + angle;
    }

    public double toRadian() {
        return Math.toRadians(angle);
    }

    @Override
    public Angle clone() {
        Angle a = null;
        try {
            a = (Angle) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{degrees=" + getAngle() + ", radians=" + new Fraction(toRadian() / Math.PI).toFraction() + "π}";
    }
}