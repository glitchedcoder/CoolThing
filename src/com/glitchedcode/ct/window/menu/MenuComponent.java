package com.glitchedcode.ct.window.menu;

import com.glitchedcode.ct.entity.Entity;
import com.glitchedcode.ct.entity.EntityType;
import com.glitchedcode.ct.entity.Location;
import com.glitchedcode.ct.font.TextBuilder;
import com.glitchedcode.ct.window.View;
import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@EqualsAndHashCode(callSuper = true)
public class MenuComponent extends Entity {

    private Rectangle bounds;
    private Color focus, unfocus, unfocusable;
    private final AtomicReference<String> text;
    private final AtomicBoolean focused, focusable;
    private final AtomicReference<BufferedImage> image;
    private final AtomicReference<TextBuilder> builder;

    public MenuComponent(View view, String text) {
        this(view, text, 1.0);
    }

    public MenuComponent(View view, String text, double scale) {
        super(view, EntityType.MENU_COMPONENT, new Location(0, 0));
        this.focus = Color.WHITE;
        this.unfocus = Color.LIGHT_GRAY;
        this.unfocusable = Color.DARK_GRAY;
        this.text = new AtomicReference<>(text);
        TextBuilder builder = TextBuilder.create(text, false, unfocus).scale(scale);
        this.image = new AtomicReference<>(builder.build());
        this.builder = new AtomicReference<>(builder);
        this.focusable = new AtomicBoolean(true);
        this.focused = new AtomicBoolean(false);
    }

    @Override
    public void tick(int count) {
        // we're static so we can ignore this
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(asImage(), getLocation().getX(), getLocation().getY(), null);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    protected void onLoad() {
        Location l = getLocation();
        BufferedImage i = asImage();
        int x = l.getX(), y = l.getY();
        this.bounds = new Rectangle(x, y, x + i.getWidth(), y + i.getHeight());
    }

    @Override
    protected void onUnload() {
        this.bounds = null;
        this.focus = null;
        this.unfocus = null;
        this.unfocusable = null;
        this.image.set(null);
        this.builder.set(null);
    }

    public String getText() {
        return text.get();
    }

    public boolean isFocused() {
        return focused.get();
    }

    public boolean isFocusable() {
        return focusable.get();
    }

    public BufferedImage asImage() {
        return image.get();
    }

    public TextBuilder getBuilder() {
        return builder.get();
    }

    public void setText(@Nonnull String text) {
        this.text.set(text);
        updateText();
    }

    public void setFocused(boolean focused) {
        if (!isFocusable() && focused)
            throw new IllegalStateException("Tried to set Menu Component (" + toString() + ") to focused while unfocusable.");
        this.focused.set(focused);
    }

    public void setFocusable(boolean focusable) {
        this.focusable.set(focusable);
        updateText();
    }

    public void setFocusedColor(Color color) {
        this.focus = color;
        updateText();
    }

    public void setUnfocusedColor(Color color) {
        this.unfocus = color;
        updateText();
    }

    public void setUnfocusableColor(Color color) {
        this.unfocusable = color;
        updateText();
    }

    @Override
    public String toString() {
        return getClass().getName() + "{id=" + getId().toString()
                + ", entity_type=" + getType().name() + ", location=" + getLocation().toString()
                + ", loaded=" + isLoaded() + ", dead=" + isDead() + ", visible=" + isVisible()
                + ", view=" + (getView() != null ? getView().toString() : "null")
                + ", text=" + getText() + "focusable=" + isFocusable() + ", focused=" + isFocused()
                + ", builder=" + (getBuilder() != null ? getBuilder().toString() : "null")
                + ", image=" + (asImage() != null ? asImage().toString() : "null") + "}";
    }

    private void updateText() {
        if (isFocusable()) {
            TextBuilder b = TextBuilder.create(text.get(), false, isFocused() ? focus : unfocus);
            image.set(b.build());
            builder.set(b);
        } else {
            TextBuilder b = TextBuilder.create(text.get(), false, unfocusable);
            image.set(b.build());
            builder.set(b);
        }
    }
}
