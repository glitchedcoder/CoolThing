package com.glitchedcode.ct.font;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.logger.Logger;
import org.fusesource.jansi.Ansi;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Transparency.TRANSLUCENT;

public final class TextBuilder {

    private Color c;
    private double scale;
    private final boolean bg;
    private BufferedImage image;
    private final StringBuilder stringBuilder;
    private static final Logger logger = CoolThing.getLogger();
    private static final Color PRIMARY, SECONDARY, TERTIARY, QUATERNARY, BACKGROUND;

    static {
        PRIMARY = new Color(255, 255, 255);
        SECONDARY = new Color(192, 192, 192);
        TERTIARY = new Color(128, 128, 128);
        QUATERNARY = new Color(64, 64, 64);
        BACKGROUND = new Color(0, 0, 0);
    }

    private TextBuilder(String s, Color c, boolean bg) {
        this.c = c;
        this.bg = bg;
        this.stringBuilder = new StringBuilder();
        a(s);
    }

    public boolean hasBg() {
        return bg;
    }

    public TextBuilder c(Color c) {
        this.c = c;
        return this;
    }

    public TextBuilder a(String s) {
        if (s.isEmpty())
            return this;
        stringBuilder.append(s);
        BufferedImage i = toImage(CharImage.fromString(s));
        if (i == null)
            throw new IllegalStateException("Failed to get stitched image of text " + s);
        if (!bg) {
            int l = BACKGROUND.getRGB();
            for (int j = 0; j < i.getHeight(); j++) {
                for (int k = 0; k < i.getWidth(); k++) {
                    if (i.getRGB(k, j) == l)
                        i.setRGB(k, j, TRANSLUCENT);
                }
            }
        }
        color(i, this.c);
        if (this.image != null)
            this.image = stitch(this.image, i);
        else
            this.image = i;
        return this;
    }

    public TextBuilder scale(double d) {
        this.scale = d;
        return this;
    }

    public static TextBuilder create(String s, boolean bg) {
        return new TextBuilder(s, Color.WHITE, bg);
    }

    public static TextBuilder create(String s, boolean bg, Color c) {
        return new TextBuilder(s, c, bg);
    }

    public BufferedImage build() {
        int w, h;
        if (scale > 0) {
            w = Math.round((float) (image.getWidth() * scale));
            h = Math.round((float) (image.getHeight() * scale));
        } else {
            w = image.getWidth();
            h = image.getHeight();
        }
        BufferedImage i = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = i.getGraphics();
        graphics.drawImage(this.image, 0, 0, w, h, null);
        graphics.dispose();
        return i;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{text=" + stringBuilder.toString() + ", background=" + bg
                + ", scale=" + scale + ", currentColor={" + c.toString() + "}}";
    }

    private void color(BufferedImage i, Color c) {
        Colour p = Colour.asColour(PRIMARY).adjustGrayColor(c);
        Colour s = Colour.asColour(SECONDARY).adjustGrayColor(c);
        Colour t = Colour.asColour(TERTIARY).adjustGrayColor(c);
        Colour q = Colour.asColour(QUATERNARY).adjustGrayColor(c);
        for (int j = 0; j < i.getWidth(); j++) {
            for (int k = 0; k < i.getHeight(); k++) {
                int l = i.getRGB(j, k);
                if (l == PRIMARY.getRGB())
                    i.setRGB(j, k, p.getRGB());
                else if (l == SECONDARY.getRGB())
                    i.setRGB(j, k, s.getRGB());
                else if (l == TERTIARY.getRGB())
                    i.setRGB(j, k, t.getRGB());
                else if (l == QUATERNARY.getRGB())
                    i.setRGB(j, k, q.getRGB());
            }
        }
    }

    private BufferedImage toImage(CharImage... images) {
        if (images.length > 0) {
            BufferedImage i = null;
            for (CharImage m : images) {
                if (i == null) {
                    i = m.toImage();
                    continue;
                }
                BufferedImage n = m.toImage();
                if (n != null)
                    i = stitch(i, n);
                else
                    logger.warn(Ansi.Color.YELLOW, "CharImage#toImage() returned null for char " + m.getId() + " (" + m.name() + ")");
            }
            return i;
        }
        return CharImage.UNKNOWN_CHAR.toImage();
    }

    private BufferedImage stitch(BufferedImage b1, BufferedImage b2) {
        int w = b1.getWidth() + b2.getWidth(), h = Math.max(b1.getHeight(), b2.getHeight());
        BufferedImage b3 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = b3.createGraphics();
        graphics2D.drawImage(b1, 0, 0, null);
        graphics2D.drawImage(b2, b1.getWidth(), 0, null);
        graphics2D.dispose();
        return b3;
    }
}