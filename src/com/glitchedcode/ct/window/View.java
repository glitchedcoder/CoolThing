package com.glitchedcode.ct.window;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.immutable.ImmutableList;
import com.glitchedcode.ct.key.Key;
import com.glitchedcode.ct.logger.Logger;
import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@EqualsAndHashCode(of = {"id", "name"})
public abstract class View implements KeyEventDispatcher {

    private final int id;
    private final String name;
    protected int width, height;
    private final List<Renderable> renderables;
    private final AtomicReference<Color> background;
    protected final Logger logger = CoolThing.getLogger();
    private static final AtomicInteger counter = new AtomicInteger(0);

    public View() {
        this("");
    }

    public View(String name) {
        this.name = name;
        this.id = counter.incrementAndGet();
        this.renderables = new CopyOnWriteArrayList<>();
        this.background = new AtomicReference<>(Color.WHITE);
    }

    /**
     * Called when the {@link View} is being loaded.
     */
    protected abstract void onLoad();

    /**
     * Called when the {@link View} is being unloaded.
     */
    protected abstract void onUnload();

    /**
     * Called when the {@link View} is being ticked.
     *
     * @param count The tick count out of 30.
     */
    protected abstract void tick(int count);

    /**
     * Gets an array of {@link Key}s the {@link View} is listening for.
     *
     * @return An array of {@link Key}s the {@link View} is listening for.
     */
    public abstract Key[] getKeyListeners();

    /**
     * Called when a {@link #getKeyListeners() key listener} is pressed.
     *
     * @param key The key pressed.
     */
    protected abstract void onKeyPress(Key key);

    /**
     * Called when a {@link #getKeyListeners() key listener} is released.
     *
     * @param key The key released.
     */
    protected abstract void onKeyRelease(Key key);

    /**
     * Called when the focus is gained for the {@link GameApplication}.
     *
     * @param event The focus event.
     */
    protected abstract void focusGained(FocusEvent event);

    /**
     * Called when the focus is lost for the {@link GameApplication}.
     *
     * @param event The focus event.
     */
    protected abstract void focusLost(FocusEvent event);

    protected final void requestFocus() {
        if (CoolThing.getApplication() != null)
            CoolThing.getApplication().requestFocus();
        else
            logger.warn("Tried requesting focus on GameApplication when it was null.");
    }

    protected final void setView(@Nonnull View view) {
        if (CoolThing.getApplication() != null)
            CoolThing.getApplication().getWindow().setView(view);
        else
            logger.warn("Tried setting GameWindow's View when GameApplication was null.");
    }

    /**
     * Adds a {@link Renderable} object to the {@link View}.
     *
     * @param renderable The renderable object.
     */
    public void addRenderable(@Nonnull Renderable renderable) {
        if (!renderables.contains(renderable))
            renderables.add(renderable);
    }

    /**
     * Removes the given {@link Renderable} object from the {@link View}.
     *
     * @param renderable The renderable object.
     */
    public void removeRenderable(@Nonnull Renderable renderable) {
        renderables.remove(renderable);
    }

    public final String getName() {
        return name;
    }

    public final boolean hasName() {
        return !name.isEmpty();
    }

    public final Color getBackground() {
        return background.get();
    }

    public final void setBackground(Color color) {
        this.background.set(color);
    }

    public final int getId() {
        return id;
    }

    public final boolean isKeyListener() {
        return getKeyListeners().length != 0;
    }

    public final boolean hasKeyListener(Key key) {
        for (Key k : getKeyListeners()) {
            if (k.getId() == key.getId())
                return true;
        }
        return false;
    }

    /**
     * Gets a {@link Set} of {@link Renderable}s in the {@link View}.
     * <br />
     * <i>Note: This {@link Set} is immutable.</i>
     *
     * @return A {@link Set} of {@link Renderable} in the {@link View}.
     */
    public final List<Renderable> getRenderables() {
        return new ImmutableList<>(renderables);
    }

    void size(int w, int h) {
        width = w;
        height = h;
    }

    @Override
    public final boolean dispatchKeyEvent(KeyEvent event) {
        if (isKeyListener()) {
            if (event.getID() == KeyEvent.KEY_PRESSED) {
                Key k = Key.toKey(event.getKeyCode());
                if (k != null && hasKeyListener(k)) {
                    onKeyPress(k);
                    return true;
                }
            } else if (event.getID() == KeyEvent.KEY_RELEASED) {
                Key k = Key.toKey(event.getKeyCode());
                if (k != null && hasKeyListener(k)) {
                    onKeyRelease(k);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{id=" + id + ", name=" + (name.isEmpty() ? "(no name)" : name) + ", keyListener=" + isKeyListener() + "}";
    }
}