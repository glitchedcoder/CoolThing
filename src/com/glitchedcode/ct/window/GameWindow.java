package com.glitchedcode.ct.window;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.logger.Logger;
import org.fusesource.jansi.Ansi;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.image.BufferStrategy;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class GameWindow extends Canvas {

    private int correctedHeight;
    private final AtomicReference<View> view;
    private static final RenderingHints hints;
    private static final Logger logger = CoolThing.getLogger();
    private static final KeyboardFocusManager MANAGER = KeyboardFocusManager.getCurrentKeyboardFocusManager();

    static {
        hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    public GameWindow() {
        super();
        this.view = new AtomicReference<>(null);
        setFocusable(true);
    }

    void adjustSize(GameApplication window, int w, int h) {
        Insets insets = window.getInsets();
        int b = h - insets.top - insets.bottom;
        correctedHeight = b;
        Dimension d = new Dimension(w, b);
        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
    }

    public synchronized View getView() {
        return view.get();
    }

    protected synchronized void tick(int count) {
        View view = getView();
        view.tick(count);
        view.getRenderables().forEach(r -> {
           if (r.shouldRemove()) {
               logger.debug("Removing renderable marked for disposal: " + r.toString());
               r.remove();
               view.removeRenderable(r);
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
        View view = getView();
        Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
        graphics.setRenderingHints(hints);
        graphics.setColor(view.getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());
        view.getRenderables().forEach(r -> {
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

    public synchronized void setView(@Nonnull View view) {
        View v = getView();
        if (v != null) {
            if (v.getId() == view.getId()) {
                logger.warn(Ansi.Color.RED, "Given new view is the same as the old view!");
                logger.warn(Ansi.Color.RED, "Old view: " + v.toString());
                logger.warn(Ansi.Color.RED, "New view: " + view.toString());
                return;
            }
            logger.debug("Unloading old view '" + v.getName() + "' (id " + v.getId() + ").");
            MANAGER.removeKeyEventDispatcher(v);
            v.onUnload();
        } else
            view.size(getWidth(), correctedHeight);
        logger.debug("Loading new view '" + view.getName() + "' (id: " + view.getId() + ").");
        this.view.set(view);
        MANAGER.addKeyEventDispatcher(view);
        view.onLoad();
    }
}