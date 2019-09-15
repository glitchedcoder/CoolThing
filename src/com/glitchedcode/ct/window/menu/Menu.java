package com.glitchedcode.ct.window.menu;

import com.glitchedcode.ct.font.TextBuilder;
import com.glitchedcode.ct.key.Key;
import com.glitchedcode.ct.sound.Sound;
import com.glitchedcode.ct.window.View;

import javax.annotation.Nonnull;

public abstract class Menu extends View {

    private TextBuilder title;


    public Menu(@Nonnull TextBuilder title) {
        this(title, 0);
    }

    public Menu(@Nonnull TextBuilder title, int yOffset) {
        this.title = title;

    }

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
        if (i + 1 == getComponents().length)
            return getComponents()[0];
        else
            return getComponents()[i + 1];
    }

    protected final MenuComponent getLastComponent() {
        return null;
    }

    protected final MenuComponent getNextFocusableComponent() {
        return null;
    }

    protected final MenuComponent getLastFocusableComponent() {
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
    public void onLoad() {
        if (getComponents().length == 0)
            throw new IllegalStateException("Menu#getComponents() is empty.");
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
                f.setFocused(false);
                l.setFocused(true);
                Sound.MENU_MOVE.play();
                break;
            case S:
            case ARROW_DOWN:
                MenuComponent c = getFocusedComponent();
                MenuComponent n = getNextFocusableComponent();
                c.setFocused(false);
                n.setFocused(true);
                Sound.MENU_MOVE.play();
                break;
            case ENTER:
                Sound.MENU_SELECT.play();
                select(getFocusedComponent());
            default:
                break;
        }
    }
}