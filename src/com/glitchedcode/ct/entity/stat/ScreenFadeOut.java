package com.glitchedcode.ct.entity.stat;

import com.glitchedcode.ct.entity.Entity;
import com.glitchedcode.ct.entity.EntityType;
import com.glitchedcode.ct.entity.Location;
import com.glitchedcode.ct.window.View;

import java.awt.*;

public class ScreenFadeOut extends Entity {

    public ScreenFadeOut(View view, Color color, int skip) {
        super(view, EntityType.SCREEN_FADE_OUT, new Location(0, 0));
    }

    @Override
    public void tick(int count) {

    }

    @Override
    public void draw(Graphics2D graphics) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected void onUnload() {

    }
}
