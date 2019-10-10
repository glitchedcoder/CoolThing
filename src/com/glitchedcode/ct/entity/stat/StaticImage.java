package com.glitchedcode.ct.entity.stat;

import com.glitchedcode.ct.entity.Entity;
import com.glitchedcode.ct.entity.EntityType;
import com.glitchedcode.ct.entity.ImageType;
import com.glitchedcode.ct.entity.Location;
import com.glitchedcode.ct.window.View;
import lombok.EqualsAndHashCode;
import org.fusesource.jansi.Ansi;

import javax.annotation.concurrent.ThreadSafe;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
@EqualsAndHashCode(callSuper = true)
public class StaticImage extends Entity {

    private Rectangle rectangle;
    private final AtomicReference<ImageType> type;
    private final AtomicReference<BufferedImage> image;

    public StaticImage(View view, BufferedImage image) {
        super(view, EntityType.STATIC_IMAGE, new Location(0, 0));
        this.image = new AtomicReference<>(image);
        this.type = new AtomicReference<>(ImageType.UNKNOWN_IMAGE);
    }

    public StaticImage(View view, BufferedImage image, Location location) {
        super(view, EntityType.STATIC_IMAGE, location);
        this.image = new AtomicReference<>(image);
        this.type = new AtomicReference<>(ImageType.UNKNOWN_IMAGE);
    }

    public StaticImage(View view, ImageType type) {
        super(view, EntityType.STATIC_IMAGE, new Location(0, 0));
        this.image = new AtomicReference<>(type.asImage());
        this.type = new AtomicReference<>(type);
    }

    public StaticImage(View view, ImageType type, Location location) {
        super(view, EntityType.STATIC_IMAGE, location);
        this.image = new AtomicReference<>(type.asImage());
        this.type = new AtomicReference<>(type);
    }

    @Override
    public void tick(int count) {
        // do nothing
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(getImage(), getLocation().getX(), getLocation().getY(), null);
    }

    @Override
    public Rectangle getBounds() {
        return rectangle;
    }

    @Override
    protected void onLoad() {
        rectangle = new Rectangle(getImage().getWidth(), getImage().getHeight());
    }

    @Override
    protected void onUnload() {
        rectangle = null;
        this.image.set(null);
    }

    public final void setImage(ImageType type) {
        this.type.set(type);
        BufferedImage image = type.asImage();
        if (image != null) {
            this.image.set(image);
            this.rectangle = new Rectangle(image.getWidth(), image.getHeight());
        } else
            logger.warn(Ansi.Color.YELLOW, "ImageType#asImage() returned null when calling #setImage(ImageType)");
    }

    public final void setImage(BufferedImage image) {
        this.type.set(ImageType.UNKNOWN_IMAGE);
        this.image.set(image);
        this.rectangle = new Rectangle(image.getWidth(), image.getHeight());
    }

    public ImageType getImageType() {
        return type.get();
    }

    public final BufferedImage getImage() {
        return image.get();
    }

    @Override
    public String toString() {
        return getClass().getName() + "{id=" + getId().toString()
                + ", entity_type=" + getType().name() + ", location=" + getLocation().toString()
                + ", loaded=" + isLoaded() + ", dead=" + isDead() + ", visible=" + isVisible()
                + ", view=" + (getView() != null ? getView().toString() : "null")
                + ", image={" + (getImage() != null ? getImage().toString() : "null") + "}, image_type=" + getImageType().name() + "}";
    }
}