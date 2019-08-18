package com.glitchedcode.ct.window;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.UUID;

public interface Renderable {

    /**
     * Gets the id of the {@link Renderable}.
     *
     * @return The id of the {@link Renderable}.
     */
    @Nonnull
    UUID getId();

    /**
     * Called when the {@link Renderable} is ticked.
     *
     * @param count The tick count.
     */
    void tick(int count);

    /**
     * Called when the {@link Renderable} needs to be drawn.
     *
     * @param graphics The graphics.
     */
    void draw(Graphics2D graphics);

    /**
     * Gets whether the {@link Renderable} should be {@link #draw(Graphics2D) drawn}.
     *
     * @return True if the {@link Renderable} should be {@link #draw(Graphics2D) drawn}.
     */
    boolean shouldDraw();

    /**
     * Gets whether the {@link Renderable} should be {@link #remove() removed}.
     *
     * @return True if the {@link Renderable} should be removed.
     */
    boolean shouldRemove();

    /**
     * Called when the {@link Renderable} is to be removed.
     */
    void remove();

    /**
     * Called when the {@link Renderable} should be spawned/made visible.
     */
    void spawn();

}