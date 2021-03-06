package com.glitchedcode.ct.window;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.entity.Entity;
import com.glitchedcode.ct.logger.Logger;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.image.BufferStrategy;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class GameWindow extends Canvas {

    private final AtomicReference<View> view;
    private static final RenderingHints hints;
    private static final Logger logger = CoolThing.getLogger();
    private static final KeyboardFocusManager MANAGER = KeyboardFocusManager.getCurrentKeyboardFocusManager();

    static {
        hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    public GameWindow(@Nonnull View view) {
        super();
        this.view = new AtomicReference<>(view);
        MANAGER.addKeyEventDispatcher(view);
        setFocusable(true);
    }

    public View getView() {
        return view.get();
    }

    void rs(int w, int h) {
        view.get().size(w, h);
        view.get().onLoad();
    }

    protected synchronized void tick(int count) {
        getView().tick(count);
        getView().getRenderables().forEach(r -> {
           if (r.shouldRemove()) {
               if (r instanceof Entity) {
                   Entity entity = (Entity) r;
                   if (entity.isDead())
                       logger.debug("Removing dead entity " + entity.toString());
                   else
                       logger.debug("Removing (not dead) entity " + entity.toString() + " for unknown reason but 'true' Renderable#shouldRemove()");
               }
               r.remove();
               getView().removeRenderable(r);
           } else
               r.tick(count);
        });
    }

    protected synchronized void draw() {
        BufferStrategy strategy = getBufferStrategy();
        if (strategy == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
        graphics.setRenderingHints(hints);
        graphics.setColor(getView().getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());
        getView().getRenderables().forEach(r -> {
            if (r.shouldDraw())
                r.draw(graphics);
        });
        strategy.show();
        graphics.dispose();
    }

    protected void focusLost(FocusEvent event) {
        getView().focusLost(event);
    }

    protected void focusGained(FocusEvent event) {
        getView().focusGained(event);
    }

    protected void close() {
        logger.debug("Closing GameWindow...");
        getView().onUnload();
        this.view.set(null);
    }

    public synchronized void setView(@Nonnull View view) throws IllegalArgumentException {
        if (!this.getView().equals(view)) {
            logger.debug("Setting new view (id: " + view.getId() + ", old view id: " + (getView() != null ? getView().getId() : "null") + ")");
            if (getView() != null) {
                getView().onUnload();
                MANAGER.removeKeyEventDispatcher(getView());
            }
            view.onLoad();
            MANAGER.addKeyEventDispatcher(view);
            this.view.set(view);
        } else
            throw new IllegalArgumentException("Given View (id " + view.getId() + ") is same as current View (id " + getView().getId() + ").");
    }
}