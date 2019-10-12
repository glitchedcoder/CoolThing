package com.glitchedcode.ct.window.menu;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.entity.Location;
import com.glitchedcode.ct.entity.stat.StaticText;
import com.glitchedcode.ct.font.TextBuilder;
import com.glitchedcode.ct.key.Key;
import com.glitchedcode.ct.sound.Sound;
import com.glitchedcode.ct.window.GameApplication;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;

import static java.awt.Color.WHITE;

@EqualsAndHashCode(callSuper = true)
public class MainMenu extends Menu {

    private StaticText gameInfo;
    private MenuComponent playComponent;
    private MenuComponent exitComponent;
    private MenuComponent optionsComponent;

    public MainMenu() {
        super(TextBuilder.create("B", false, Color.RED).c(Color.LIGHT_GRAY).a("ONG").scale(10));
        playComponent = new MenuComponent(this, "PLAY", 7D);
        optionsComponent = new MenuComponent(this, "OPTIONS", 7D);
        exitComponent = new MenuComponent(this, "EXIT", 7D);
    }

    @Nullable
    @Override
    protected Menu getParent() {
        return null;
    }

    @Override
    protected MenuComponent[] getComponents() {
        return new MenuComponent[]
                {
                        playComponent,
                        optionsComponent,
                        exitComponent
                };
    }

    @Override
    protected void select(MenuComponent component) {
        switch (component.getText()) {
            case "PLAY":
                Sound.PLAYER_SCORE.play();
                break;
            case "OPTIONS":
                Sound.BALL_HIT_1.play();
                break;
            case "EXIT":
                CoolThing.stop(0);
                break;
            default:
                Sound.NEGATIVE_NOISE.play();
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        TextBuilder builder = TextBuilder.create("FPS: -- TPS: --", false, WHITE).scale(2D);
        gameInfo = new StaticText(this, builder);
        gameInfo.spawn();
        gameInfo.setLocation(null);
        addRenderable(gameInfo);
    }

    @Override
    protected void onUnload() {
        // ignore for now
    }

    @Override
    protected void tick(int count) {
        GameApplication app = CoolThing.getApplication();
        if (count % 30 == 0) {
            TextBuilder builder = TextBuilder
                    .create("FPS: " + app.getFramesPerSecond() + " TPS: " + app.getTicksPerSecond(), false, WHITE)
                    .scale(2D);
            gameInfo.setText(builder);
        }
    }

    @Override
    protected void onKeyRelease(Key key) {
        // ignore
    }

    @Override
    protected void focusGained(FocusEvent event) {
        // ignore
    }

    @Override
    protected void focusLost(FocusEvent event) {
        // ignore
    }
}
