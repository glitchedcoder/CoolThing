package com.glitchedcode.ct.entity;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.entity.stat.ScreenFadeIn;
import com.glitchedcode.ct.entity.stat.ScreenFadeOut;
import com.glitchedcode.ct.entity.stat.StaticImage;
import com.glitchedcode.ct.entity.stat.StaticText;
import com.glitchedcode.ct.font.TextBuilder;
import com.glitchedcode.ct.logger.Logger;
import com.glitchedcode.ct.window.View;
import org.fusesource.jansi.Ansi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.function.Function;

public enum EntityType {

    STATIC_IMAGE("Static Image", true, view -> {
        return new StaticImage(view, ImageType.MISSING_TEXTURE);
    }),
    STATIC_TEXT("Static Text", true, view -> {
        return new StaticText(view, TextBuilder.create("text", false));
    }),
    SCREEN_FADE_IN("Screen Fade", true, view -> {
        return new ScreenFadeIn(view, Color.black, 1);
    }),
    SCREEN_FADE_OUT("Screen Fade Out", true, view -> {
        return new ScreenFadeOut(view, Color.BLACK, 1);
    });

    private final String name;
    private final boolean isStatic;
    private final Function<View, ? extends Entity> function;

    EntityType(String name, boolean isStatic, Function<View, ? extends Entity> function) {
        this.name = name;
        this.isStatic = isStatic;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public Entity spawn(Location location) {
        if (CoolThing.getApplication() != null) {
            View view = CoolThing.getApplication().getWindow().getView();
            Entity e = function.apply(CoolThing.getApplication().getWindow().getView());
            view.addRenderable(e);
            e.spawn();
            return e;
        } else {
            Logger logger = CoolThing.getLogger();
            logger.error(Ansi.Color.RED, "Tried spawning an entity when GameApplication was null.");
            return null;
        }
    }

    @Nullable
    public static EntityType fromName(String s) {
        for (EntityType t : values()) {
            if (t.getName().equalsIgnoreCase(s))
                return t;
        }
        return null;
    }

    @Nonnull
    public static EntityType fromName(String s, EntityType defaultValue) {
        for (EntityType t : values()) {
            if (t.getName().equalsIgnoreCase(s))
                return t;
        }
        return defaultValue;
    }
}