package com.glitchedcode.ct.entity.dynamic;

import com.glitchedcode.ct.entity.Entity;
import com.glitchedcode.ct.entity.EntityType;
import com.glitchedcode.ct.entity.Location;
import com.glitchedcode.ct.math.Vector;
import com.glitchedcode.ct.window.View;
import lombok.EqualsAndHashCode;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
@EqualsAndHashCode(callSuper = true)
public abstract class DynamicEntity extends Entity {

    private final AtomicBoolean canCollide;
    private final AtomicReference<Vector> vector;

    public DynamicEntity(View view, EntityType type, Location location, boolean canCollide) {
        super(view, type, location);
        this.vector = new AtomicReference<>(new Vector(0, 0));
        this.canCollide = new AtomicBoolean(canCollide);
    }

    /**
     * Gets whether the {@link DynamicEntity} can collide with other {@link DynamicEntity}s.
     *
     * @return True if the {@link DynamicEntity} can collide with other {@link DynamicEntity}s.
     */
    public final boolean canCollide() {
        return canCollide.get();
    }

    /**
     * Sets whether the {@link DynamicEntity} can collide with other {@link DynamicEntity}s.
     *
     * @param canCollide True if the dynamic entity should collide with other dynamic entities.
     */
    public final void setCanCollide(boolean canCollide) {
        this.canCollide.set(canCollide);
    }

    @Override
    public synchronized void tick(int count) {
        // Update our location
        if (!getVector().isZeroVector()) {
            Location newLoc = getLocation().add((int) getVector().getX(), (int) getVector().getY());

        }
    }

    public final Vector getVector() {
        return vector.get();
    }

    public final void setVector(Vector vector) {
        if (vector != null)
            this.vector.set(vector);
        else
            this.vector.set(new Vector(0, 0));
    }
}
