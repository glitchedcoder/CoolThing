package com.glitchedcode.ct.entity.stat;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.entity.Entity;
import com.glitchedcode.ct.entity.EntityType;
import com.glitchedcode.ct.entity.Location;
import com.glitchedcode.ct.window.GameWindow;
import com.glitchedcode.ct.window.View;
import lombok.EqualsAndHashCode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Used as a way to introduce a reversal fade.
 * <br />
 * Starts out as fully transparent (a: 255) and fades to translucent (a: 0).
 */
@EqualsAndHashCode(callSuper = true)
public class ScreenFadeOut extends Entity {

    private Color color;
    private final int skip;
    private Rectangle bounds;

    public ScreenFadeOut(View view, Color color, int skip) {
        super(view, EntityType.SCREEN_FADE_OUT, new Location(0, 0));
        this.color = color;
        this.skip = Math.abs(skip);
    }

    @Override
    public void tick(int count) {
        if (color.getAlpha() > 0) {
            int sum = color.getAlpha() - skip;
            color = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(sum, 0));
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
        color = null;
        bounds = null;
    }
}