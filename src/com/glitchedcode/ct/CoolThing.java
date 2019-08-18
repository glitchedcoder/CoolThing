package com.glitchedcode.ct;

import com.glitchedcode.ct.command.CommandManager;
import com.glitchedcode.ct.command.Help;
import com.glitchedcode.ct.command.Stop;
import com.glitchedcode.ct.logger.Logger;
import com.glitchedcode.ct.thread.InputListener;
import com.glitchedcode.ct.window.GameApplication;
import com.glitchedcode.ct.window.GameWindow;
import com.glitchedcode.ct.window.splashscreen.SplashScreen;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public final class CoolThing {

    private static Logger logger;
    private static int WIDTH, HEIGHT;
    private static boolean noColor, debug, iL;
    private static GameApplication application;
    private static InputListener inputListener;
    private static final String VERSION = "1.0.0";
    private static ScheduledExecutorService executorService;
    private static final File dataFolder = new File(System.getProperty("user.dir"));

    private CoolThing() {}

    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        assert (dataFolder.isDirectory());
        executorService = Executors.newScheduledThreadPool(2);
        logger = new Logger(dataFolder);
        if (args.length > 0) {
            noColor = hasArg("-nocolor", args);
            debug = hasArg("-debug", args);
            iL = hasArg("-input", args);
        }
        if (debug)
            logger.debug("Debug has been enabled for this session. Expect excessive spam.");
        if (noColor)
            logger.info("No-color has been enabled for this session.");
        WIDTH = getWidth(args);
        HEIGHT = getHeight(args);
        logger.debug("Width: " + WIDTH + ", Height: " + HEIGHT);
        SplashScreen splashScreen = new SplashScreen();
        GameWindow window = new GameWindow(splashScreen);
        application = new GameApplication(window, WIDTH, HEIGHT);
        executorService.execute(application);
        if (iL) {
            Stream.of(
                    new Help(),
                    new Stop()
            ).forEach(CommandManager::register);
            inputListener = new InputListener();
            logger.debug("Starting input listener...");
            inputListener.start();
        }
    }

    public static void stop(int code) {
        logger.debug("Received exit code " + code + " (" + (code == -1 ? "error" : "normal") + "), closing...");
        if (iL)
            inputListener.close();
        application.stop();
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(500, TimeUnit.MILLISECONDS))
                executorService.shutdownNow();
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        logger.save();
        logger.close();
        AnsiConsole.systemUninstall();
        System.exit(code);
    }

    private static boolean hasArg(String arg, String[] args) {
        for (String s : args) {
            if (s.equalsIgnoreCase(arg))
                return true;
        }
        return false;
    }

    private static int getIndex(String arg, String[] args) {
        if (hasArg(arg, args)) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equalsIgnoreCase(arg))
                    return i;
            }
        }
        return -1;
    }

    private static String[] getInput(char c, String arg, String[] args) {
        if (hasArg(c + arg, args)) {
            List<String> list = new ArrayList<>();
            int i = getIndex(c + arg, args);
            if (i != -1) {
                int k = 0;
                for (int j = i + 1; j < args.length && !args[j].startsWith(c + ""); j++) {
                    list.add(args[j]);
                    k++;
                }
                return list.toArray(new String[k]);
            }
        }
        return new String[0];
    }

    private static int getWidth(String[] args) {
        if (hasArg("-width", args)) {
            String[] a = getInput('-', "width", args);
            if (a.length == 1) {
                String s = a[0];
                try {
                    int w = Integer.valueOf(s);
                    if (w > 0)
                        WIDTH = w;
                    else
                        logger.warn(Ansi.Color.YELLOW, "The given width " + w + " needs to be positive.");
                } catch (NumberFormatException e) {
                    logger.warn(Ansi.Color.YELLOW, "Couldn't format the given width \"" + s + "\" to an integer.");
                }
            } else
                logger.warn(Ansi.Color.YELLOW, "Unable to read width: arg length given: " + a.length + ", expected: 1");
        } else {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            return dimension.width;
        }
        return 1920;
    }

    private static int getHeight(String[] args) {
        if (hasArg("-height", args)) {
            String[] a = getInput('-', "height", args);
            if (a.length == 1) {
                String s = a[0];
                try {
                    int h = Integer.valueOf(s);
                    if (h > 0)
                        HEIGHT = h;
                    else
                        logger.warn(Ansi.Color.YELLOW, "The given height " + h + " needs to be positivie.");
                } catch (NumberFormatException e) {
                    logger.warn(Ansi.Color.YELLOW, "Couldn't format the given height \"" + s + "\" to an integer.");
                }
            } else
                logger.warn(Ansi.Color.YELLOW, "Unable to read height: arg length given: " + a.length + ", expected: 1");
        } else {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = ge.getDefaultScreenDevice();
            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(device.getDefaultConfiguration());
            return dimension.height - insets.bottom;
        }
        return 1080;
    }

    public static GameApplication getApplication() {
        return application;
    }

    public static ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public static Thread.UncaughtExceptionHandler getExceptionHandler() {
        return logger::handleError;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static boolean isDebugEnabled() {
        return debug;
    }

    public static boolean hasNoColor() {
        return noColor;
    }

    public static File getDataFolder() {
        return dataFolder;
    }

    public static String getVersion() {
        return VERSION;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() {
        return WIDTH;
    }
}