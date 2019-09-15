package com.glitchedcode.ct.entity.stat;

import com.glitchedcode.ct.entity.Entity;
import com.glitchedcode.ct.entity.EntityType;
import com.glitchedcode.ct.entity.Location;
import com.glitchedcode.ct.font.TextBuilder;
import com.glitchedcode.ct.window.View;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;

public class StaticText extends Entity {

    private Rectangle bounds;
    private AtomicReference<BufferedImage> image;
    private AtomicReference<TextBuilder> builder;

    public StaticText(View view, TextBuilder builder) {
        this(view, builder, new Location(0, 0));
    }

    public StaticText(View view, TextBuilder builder, Location location) {
        super(view, EntityType.STATIC_TEXT, location);
        this.builder = new AtomicReference<>(builder);
    }

    @Override
    public void tick(int count) {
        // we're static so we can ignore this
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(image.get(), getLocation().getX(), getLocation().getY(), null);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    protected void onLoad() {
        if (this.image != null)
            this.image.set(getTextBuilder().build());
        else
            this.image = new AtomicReference<>(getTextBuilder().build());
        this.bounds = new Rectangle(asImage().getWidth(), asImage().getHeight());
    }

    @Override
    protected void onUnload() {
        this.builder.set(null);
        this.image.set(null);
    }

    public BufferedImage asImage() {
        if (this.image != null && this.image.get() != null)
            return image.get();
        else
            return getTextBuilder().build();
    }

    public TextBuilder getTextBuilder() {
        return builder.get();
    }

    @Override
    public String toString() {
        return getClass().getName() + "{id=" + getId().toString()
                + ", entity_type=" + getType().name() + ", location=" + getLocation().toString()
                + ", loaded=" + isLoaded() + ", dead=" + isDead() + ", visible=" + isVisible()
                + ", view=" + (getView() != null ? getView().toString() : "null")
                + ", text_builder=" + getTextBuilder().toString() + "}";
    }
}
