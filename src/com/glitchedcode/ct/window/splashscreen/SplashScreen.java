package com.glitchedcode.ct.window.splashscreen;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.entity.ImageType;
import com.glitchedcode.ct.entity.Location;
import com.glitchedcode.ct.entity.stat.ScreenFadeIn;
import com.glitchedcode.ct.entity.stat.ScreenFadeOut;
import com.glitchedcode.ct.entity.stat.StaticImage;
import com.glitchedcode.ct.entity.stat.StaticText;
import com.glitchedcode.ct.font.TextBuilder;
import com.glitchedcode.ct.key.Key;
import com.glitchedcode.ct.sound.Sound;
import com.glitchedcode.ct.window.View;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashScreen extends View {

    private StaticImage logo;
    private StaticText loading;
    private ScreenFadeIn screenFadeIn;
    private ScreenFadeOut screenFadeOut;

    public SplashScreen() {
        super("Splash Screen");
    }

    @Override
    protected void onLoad() {
        requestFocus();
        logo = new StaticImage(this, ImageType.LOGO, new Location(0, 0));
        logo.spawn();
        logger.debug(logo.toString());
        addRenderable(logo);
        Rectangle r = logo.getBounds();
        logo.setLocation(new Location((WIDTH / 2) - (r.width / 2), (HEIGHT / 2) - (r.height / 2)));
        loading = new StaticText(this, TextBuilder.create("GlitchedCode", false, Color.DARK_GRAY).scale(5));
        loading.spawn();
        addRenderable(loading);
        Rectangle r2 = loading.getBounds();
        loading.setLocation(new Location((WIDTH / 2) - (r2.width / 2), (HEIGHT / 2) - (r2.height / 2) + (r.height / 2) + 100));
        logger.debug(loading.toString());
        screenFadeOut = new ScreenFadeOut(this, new Color(255, 255, 255, 255), 6);
        screenFadeOut.spawn();
        addRenderable(screenFadeOut);
        screenFadeIn = new ScreenFadeIn(this, new Color(0, 0, 0, 0), 3);
        ScheduledExecutorService service = CoolThing.getExecutorService();
        service.schedule((Runnable) Sound.UPGRADE_PURCHASE::play, 2, TimeUnit.SECONDS);
        service.schedule(() -> {
            screenFadeIn.spawn();
            addRenderable(screenFadeIn);
        }, 4, TimeUnit.SECONDS);
        service.schedule((Runnable) Sound.THEME_SONG::play, 8, TimeUnit.SECONDS);
    }

    @Override
    protected void onUnload() {
        logo.remove();
        loading.remove();
        screenFadeOut.remove();
        screenFadeIn.remove();
        logo = null;
        loading = null;
        screenFadeOut = null;
        screenFadeIn = null;
    }

    @Override
    protected void tick(int count) {
    }

    @Override
    public Key[] getKeyListeners() {
        return new Key[0];
    }

    @Override
    protected void onKeyPress(Key key) {
        // do nothing
    }

    @Override
    protected void onKeyRelease(Key key) {
        // do nothing
    }

    @Override
    protected void focusGained(FocusEvent event) {
        // do nothing
    }

    @Override
    protected void focusLost(FocusEvent event) {
        // do nothing
    }
}