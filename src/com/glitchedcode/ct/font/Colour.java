package com.glitchedcode.ct.font;

import java.awt.*;
import java.awt.color.ColorSpace;

/**
 * Not British, just don't want to have to use the full classpath to use {@link Color}.
 */
public class Colour extends Color {

    public Colour(int r, int g, int b) {
        super(r, g, b);
    }

    public Colour(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public Colour(int rgb) {
        super(rgb);
    }

    public Colour(int rgba, boolean hasalpha) {
        super(rgba, hasalpha);
    }

    public Colour(float r, float g, float b) {
        super(r, g, b);
    }

    public Colour(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public Colour(ColorSpace cspace, float[] components, float alpha) {
        super(cspace, components, alpha);
    }

    public double getRelativeLuminance() {
        return (0.2126 * getRed()) + (0.7152 * getGreen()) + (0.0722 * getBlue());
    }

    public Colour adjustGrayColor(Color c) {
        double d = getRelativeLuminance();
        return new Colour((int) (c.getRed() * d/255), (int) (c.getGreen() * d/255), (int) (c.getBlue() * d/255));
    }

    public static Colour asColour(Color color) {
        return new Colour(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
}