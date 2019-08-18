package com.glitchedcode.ct.entity;

public class Location implements Cloneable {

    private final int x, y;

    public Location() {
        this(0, 0);
    }

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location add(int x, int y) {
        return new Location(this.x + x, this.y + y);
    }

    public Location subtract(int x, int y) {
        return new Location(this.x - x, this.y - y);
    }

    public Location multiply(int scalar) {
        return new Location(this.x * scalar, this.y * scalar);
    }

    public boolean similarTo(Location location) {
        return (this.x == location.x && this.y == location.y);
    }

    public Location getMidpoint(Location location) {
        int newX = Math.round(((float) x + location.x) / 2);
        int newY = Math.round(((float) y + location.y) / 2);
        return new Location(newX, newY);
    }

    public int distance(Location location) {
        return Math.round((float) Math.sqrt(distanceSquared(location)));
    }

    public int distanceX(Location location) {
        return Math.abs(x - location.x);
    }

    public int distanceY(Location location) {
        return Math.abs(y - location.y);
    }

    public int distanceSquared(Location location) {
        int a = x - location.x;
        int b = y - location.y;
        return Math.round((float) (Math.pow(a, 2) + Math.pow(b, 2)));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public Location clone() {
        Location loc = null;
        try {
            loc = (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return loc;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{x=" + x + ", y=" + y + "}";
    }
}