package com.glitchedcode.ct.entity;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.logger.Logger;
import org.fusesource.jansi.Ansi;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum ImageType {

    LOGO("/texture/pixel_logo.png"),
    MISSING_TEXTURE("/texture/missing_texture.png"),
    UNKNOWN_IMAGE("");

    private final String name;

    ImageType(String name) {
        this.name = name;
    }

    public File asFile() {
        return new File(getClass().getResource(name).getFile());
    }

    public String getName() {
        return name;
    }

    public BufferedImage asImage() {
        try {
            return ImageIO.read(CoolThing.class.getResourceAsStream(getName()));
        } catch (Exception e) {
            Logger logger = CoolThing.getLogger();
            logger.error(Ansi.Color.RED, "Failed to read image \"" + getName() + "\": " + e.getMessage());
            logger.handleError(Thread.currentThread(), e);
        }
        return null;
    }
}