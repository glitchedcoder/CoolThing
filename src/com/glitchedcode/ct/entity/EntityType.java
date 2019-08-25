package com.glitchedcode.ct.entity;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.entity.stat.ScreenFadeIn;
import com.glitchedcode.ct.entity.stat.ScreenFadeOut;
import com.glitchedcode.ct.entity.stat.StaticImage;
import com.glitchedcode.ct.entity.stat.StaticText;
import com.glitchedcode.ct.font.TextBuilder;
import com.glitchedcode.ct.logger.Logger;
import com.glitchedcode.ct.window.View;
import com.glitchedcode.ct.window.menu.MenuComponent;
import org.fusesource.jansi.Ansi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.function.Function;

public enum EntityType {

    STATIC_IMAGE("Static Image", "static_image", true, view -> {
        return new StaticImage(view, ImageType.MISSING_TEXTURE);
    }),
    STATIC_TEXT("Static Text", "static_text", true, view -> {
        return new StaticText(view, TextBuilder.create("text", false));
    }),
    SCREEN_FADE_IN("Screen Fade In", "fade_in", true, view -> {
        return new ScreenFadeIn(view, Color.black, 1);
    }),
    SCREEN_FADE_OUT("Screen Fade Out", "fade_out", true, view -> {
        return new ScreenFadeOut(view, Color.BLACK, 1);
    }),
    MENU_COMPONENT("Menu Component", "menu_component", true, view -> {
        return new MenuComponent(view, "text");
    });

    private final String name, id;
    private final boolean isStatic;
    private final Function<View, ? extends Entity> function;

    EntityType(String name, String id, boolean isStatic, Function<View, ? extends Entity> function) {
        this.name = name;
        this.id = id;
        this.isStatic = isStatic;
        this.function = function;
    }

    public String getId() {
        return id;
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
            e.spawn();
            e.setLocation(location);
            view.addRenderable(e);
            return e;
        } else {
            Logger logger = CoolThing.getLogger();
            logger.error(Ansi.Color.RED, "Tried spawning an entity when GameApplication was null.");
            return null;
        }
    }

    public Entity spawn(@Nonnull View view) {
        Entity e = function.apply(view);
        e.spawn();
        view.addRenderable(e);
        return e;
    }

    public Entity spawn(@Nonnull View view, Location location) {
        Entity e = function.apply(view);
        e.spawn();
        e.setLocation(location);
        view.addRenderable(e);
        return e;
    }

    @Nullable
    public static EntityType fromName(@Nonnull String s) {
        for (EntityType t : values()) {
            if (t.getName().equalsIgnoreCase(s))
                return t;
        }
        return null;
    }

    @Nonnull
    public static EntityType fromName(@Nonnull String s, EntityType defaultValue) {
        for (EntityType t : values()) {
            if (t.getName().equalsIgnoreCase(s))
                return t;
        }
        return defaultValue;
    }

    @Nullable
    public static EntityType fromId(@Nonnull String id) {
        for (EntityType t : values()) {
            if (t.id.equalsIgnoreCase(id))
                return t;
        }
        return null;
    }

    @Nonnull
    public static EntityType fromId(@Nonnull String id, EntityType defaultValue) {
        for (EntityType t : values()) {
            if (t.id.equalsIgnoreCase(id))
                return t;
        }
        return defaultValue;
    }
}