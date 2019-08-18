package com.glitchedcode.ct.entity;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.logger.Logger;
import com.glitchedcode.ct.window.Renderable;
import com.glitchedcode.ct.window.View;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Entity implements Renderable {

    private final UUID id;
    private final View view;
    private final EntityType type;
    private final AtomicReference<Location> location;
    private final AtomicBoolean dead, visible, loaded;
    protected static final Logger logger = CoolThing.getLogger();

    public Entity(@Nonnull View view, @Nonnull EntityType type, Location location) {
        this.view = view;
        this.type = type;
        if (location != null)
            this.location = new AtomicReference<>(location);
        else
            this.location = new AtomicReference<>(new Location(0, 0));
        this.id = UUID.randomUUID();
        this.dead = new AtomicBoolean(false);
        this.visible = new AtomicBoolean(false);
        this.loaded = new AtomicBoolean(false);
    }

    @Override
    public abstract void tick(int count);

    @Override
    public abstract void draw(Graphics2D graphics);

    public abstract Rectangle getBounds();

    protected abstract void onLoad();

    protected abstract void onUnload();

    @Override
    @Nonnull
    public final UUID getId() {
        return id;
    }

    @Override
    public final void spawn() {
        if (!isDead()) {
            this.onLoad();
            this.loaded.set(true);
            this.visible.set(true);
        } else
            logger.warn("Tried to spawn dead entity: " + toString());
    }

    @Override
    public final void remove() {
        if (!isDead() && isLoaded()) {
            this.onUnload();
            this.loaded.set(false);
            this.dead.set(true);
        } else
            logger.warn("Tried to remove dead & unloaded entity: " + toString());
    }

    @Override
    public boolean shouldDraw() {
        return (isVisible() && !isDead());
    }

    @Override
    public boolean shouldRemove() {
        return dead.get();
    }

    public final boolean isDead() {
        return dead.get();
    }

    public final boolean isLoaded() {
        return loaded.get();
    }

    public final boolean isVisible() {
        return visible.get();
    }

    public final View getView() {
        return view;
    }

    public final EntityType getType() {
        return type;
    }

    public final Location getLocation() {
        return location.get();
    }

    public final void setVisible(boolean visible) {
        this.visible.set(visible);
    }

    public final void setLocation(Location location) {
        if (location != null)
            this.location.set(location);
        else
            this.location.set(new Location(0, 0));
    }

    @Override
    public final boolean equals(Object object) {
        if (object != null) {
            if (object == this)
                return true;
            if (object instanceof Entity) {
                Entity e = (Entity) object;
                return e.id.equals(this.id);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{id=" + id.toString()
                + ", entity_type=" + type.name() + ", location=" + location.toString()
                + ", loaded=" + isLoaded() + ", dead=" + isDead() + ", visible=" + isVisible()
                + ", view=" + (view != null ? view.toString() : "null") + "}";
    }
}