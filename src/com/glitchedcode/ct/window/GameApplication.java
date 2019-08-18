package com.glitchedcode.ct.window;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.logger.Logger;
import org.fusesource.jansi.Ansi;

import javax.annotation.concurrent.ThreadSafe;
import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public final class GameApplication extends JFrame implements Runnable, FocusListener {

    private final int w, h;
    private final GameWindow window;
    private final AtomicBoolean running;
    private final AtomicInteger fps, tps;

    public GameApplication(GameWindow window, int w, int h) {
        super();
        this.w = w;
        this.h = h;
        this.window = window;
        this.fps = new AtomicInteger(0);
        this.tps = new AtomicInteger(0);
        this.running = new AtomicBoolean(false);
        setTitle("Cool Thing");
        setExtendedState(MAXIMIZED_BOTH);
        add(window);
        pack();
        setResizable(false);
        window.rs(getWidth(), getHeight());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addFocusListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CoolThing.stop(0);
            }
        });
        setLocationRelativeTo(null);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public GameWindow getWindow() {
        return window;
    }

    public int getTicksPerSecond() {
        return tps.get();
    }

    public int getFramesPerSecond() {
        return fps.get();
    }

    @Override
    public void focusGained(FocusEvent e) {
        window.focusGained(e);
    }

    @Override
    public void focusLost(FocusEvent e) {
        window.focusLost(e);
    }

    public void stop() {
        running.set(false);
        window.close();
    }

    @Override
    public void run() {
        this.running.set(true);
        setVisible(true);
        requestFocus();
        long lastTime = System.nanoTime();
        double unprocessed = 0.0;
        double nanoPerTick = 1000000000 / 30;
        int frames = 0, ticks = 0;
        long lastTimer = System.currentTimeMillis();
        Logger logger = CoolThing.getLogger();
        while (running.get()) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nanoPerTick;
            lastTime = now;
            boolean shouldRender = false;
            while (unprocessed >= 1) {
                ticks++;
                try {
                    if (running.get())
                        getWindow().tick(ticks);
                    else
                        return;
                } catch (Exception e) {
                    logger.error(Ansi.Color.RED, "Uncaught exception whilst ticking window: " + e.getMessage());
                    logger.handleError(Thread.currentThread(), e);
                }
                unprocessed -= 1;
                shouldRender = true;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (shouldRender) {
                frames++;
                try {
                    if (running.get())
                        getWindow().draw();
                    else
                        return;
                } catch (Exception e) {
                    logger.error(Ansi.Color.RED, "Uncaught exception whilst drawing window: " + e.getMessage());
                    logger.handleError(Thread.currentThread(), e);
                }
            }
            if ((System.currentTimeMillis() - lastTimer) > 1000) {
                lastTimer += 1000;
                this.fps.set(frames);
                this.tps.set(ticks);
                logger.debug("TPS: " + ticks + ", FPS: " + frames);
                frames = 0;
                ticks = 0;
            }
        }
    }
}