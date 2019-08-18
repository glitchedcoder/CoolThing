package com.glitchedcode.ct.math;

/**
 * Used for speed and angle measurements.
 * Always assumes that the initial point is (0, 0) on a graph.
 */
public class Vector implements Cloneable {

    private final double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public boolean isUnitVector() {
        return getMagnitude() == 1;
    }

    public boolean isZeroVector() {
        return x == 0 && y == 0;
    }

    public Vector add(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y);
    }

    public Vector add(double x, double y) {
        return new Vector(this.x + x, this.y + y);
    }

    public Vector multiply(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public Vector getUnitVector() {
        double m = 1 / getMagnitude();
        return new Vector(x * m, y * m);
    }

    public String toComponentForm() {
        return "〈" + x + ", " + y + "〉";
    }

    public String toAlternateComponentForm() {
        return x + "i " + (y >= 0 ? "+ " : "- ") + y + "j";
    }

    public Angle getAngle() {
        return new Angle(Math.atan2(this.y, this.x));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this)
                return true;
            if (obj instanceof Vector) {
                Vector vector = (Vector) obj;
                return vector.x == this.x && vector.y == this.y;
            }
        }
        return false;
    }

    @Override
    public Vector clone() {
        Vector v = null;
        try {
            v = (Vector) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{x=" + x + ", y=" + y + ", magnitude=" + getMagnitude() + "}";
    }

    public static Vector toVector(double magnitude, Angle angle) {
        double m = Math.abs(magnitude);
        return new Vector(m * Math.cos(angle.getAngle()), m * Math.sin(angle.getAngle()));
    }
}