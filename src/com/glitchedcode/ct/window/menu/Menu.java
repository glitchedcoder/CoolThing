package com.glitchedcode.ct.window.menu;

import com.glitchedcode.ct.entity.Location;
import com.glitchedcode.ct.entity.stat.StaticImage;
import com.glitchedcode.ct.font.TextBuilder;
import com.glitchedcode.ct.key.Key;
import com.glitchedcode.ct.sound.Sound;
import com.glitchedcode.ct.window.View;
import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.Color;

@EqualsAndHashCode(callSuper = true)
public abstract class Menu extends View {

    private final StaticImage title;

    public Menu(@Nonnull TextBuilder title) {
        this(title, 0);
    }

    public Menu(@Nonnull TextBuilder title, int yOffset) {
        super(title.getText());
        this.title = new StaticImage(this, title.build());
        this.title.setLocation(new Location());
    }

    @Nullable
    protected abstract Menu getParent();

    protected abstract MenuComponent[] getComponents();

    protected abstract void select(MenuComponent component);

    protected final MenuComponent getFocusedComponent() {
        for (MenuComponent c : getComponents()) {
            if (c.isFocusable() && c.isFocused())
                return c;
        }
        throw new IllegalStateException("Couldn't find a component that was focusable & focused.");
    }

    protected final MenuComponent getNextComponent() {
        int i = getIndex();
        int l = getComponents().length;
        return (i + 1) < l ? getComponents()[i + 1] : getComponents()[0];
    }

    protected final MenuComponent getLastComponent() {
        int i = getIndex();
        int j = getComponents().length;
        return (i - 1) > -1 ? getComponents()[i - 1] : getComponents()[j - 1];
    }

    @Nullable
    protected final MenuComponent getNextFocusableComponent() {
        int j = getIndex();
        for (int i = j; i < getComponents().length; i++) {
            MenuComponent component = getComponents()[i];
            if (component.isFocusable())
                return component;
        }
        for (int i = 0; i < j; i++) {
            MenuComponent component = getComponents()[i];
            if (component.isFocusable())
                return component;
        }
        return null;
    }

    @Nullable
    protected final MenuComponent getLastFocusableComponent() {
        int j = getIndex();
        for (int i = j; i > 0; i--) {
            MenuComponent component = getComponents()[i];
            if (component.isFocusable())
                return component;
        }
        for (int i = getComponents().length - 1; i > j; i--) {
            MenuComponent component = getComponents()[i];
            if (component.isFocusable())
                return component;
        }
        return null;
    }

    protected final int getIndex() {
        MenuComponent component = getFocusedComponent();
        for (int i = 0; i < getComponents().length; i++) {
            MenuComponent c = getComponents()[i];
            if (c.equals(component))
                return i;
        }
        return -1;
    }

    @Override
    protected void onLoad() {
        setBackground(Color.BLACK);
        title.spawn();
        addRenderable(title);
        if (getComponents().length == 0)
            throw new IllegalStateException("Menu#getComponents() is empty.");
        for (MenuComponent component : getComponents()) {
            component.spawn();
            addRenderable(component);
        }
        for (MenuComponent component : getComponents()) {
            if (component.isFocusable()) {
                component.setFocused(true);
                break;
            }
        }
    }

    @Override
    public Key[] getKeyListeners() {
        return new Key[] {
                Key.W,
                Key.ARROW_UP,
                Key.S,
                Key.ARROW_DOWN,
                Key.ENTER
        };
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case W:
            case ARROW_UP:
                MenuComponent f = getFocusedComponent();
                MenuComponent l = getLastFocusableComponent();
                if (l != null) {
                    f.setFocused(false);
                    l.setFocused(true);
                    Sound.MENU_MOVE.play();
                } else
                    Sound.NEGATIVE_NOISE.play();
                break;
            case S:
            case ARROW_DOWN:
                MenuComponent c = getFocusedComponent();
                MenuComponent n = getNextFocusableComponent();
                if (n != null) {
                    c.setFocused(false);
                    n.setFocused(true);
                    Sound.MENU_MOVE.play();
                } else
                    Sound.NEGATIVE_NOISE.play();
                break;
            case ENTER:
                Sound.MENU_SELECT.play();
                select(getFocusedComponent());
                break;
            case ESCAPE:
                if (getParent() != null) {
                    Sound.PAUSE.play();
                    setView(getParent());
                }
                break;
            default:
                break;
        }
    }
}