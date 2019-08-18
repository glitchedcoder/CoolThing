package com.glitchedcode.ct.entity.stat;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.entity.Entity;
import com.glitchedcode.ct.entity.EntityType;
import com.glitchedcode.ct.entity.Location;
import com.glitchedcode.ct.window.GameWindow;
import com.glitchedcode.ct.window.View;

import java.awt.*;

public class ScreenFadeIn extends Entity {

    private Color color;
    private final int skip;
    private Rectangle bounds;

    public ScreenFadeIn(View view, Color color, int skip) {
        super(view, EntityType.SCREEN_FADE_IN, new Location(0, 0));
        this.color = color;
        this.skip = Math.abs(skip);
    }

    @Override
    public void tick(int count) {
        if (color.getAlpha() < 255) {
            int sum = color.getAlpha() + skip;
            if (sum >= 255) {
                color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
            } else
                color = new Color(color.getRed(), color.getGreen(), color.getBlue(), sum);
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect(0, 0, bounds.width, bounds.height);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    protected void onLoad() {
        GameWindow window = CoolThing.getApplication().getWindow();
        bounds = new Rectangle(0, 0, window.getWidth(), window.getHeight());
    }

    @Override
    protected void onUnload() {
        this.color = null;
        this.bounds = null;
    }
}