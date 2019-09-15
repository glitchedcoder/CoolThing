package com.glitchedcode.ct.logger;

import com.glitchedcode.ct.CoolThing;
import org.fusesource.jansi.Ansi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class Logger {

    private final File dir, file;
    private final BetterPrintWriter writer;
    private final AtomicInteger errCount, warnCount;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm:ss");

    public Logger(File dir) {
        this(dir, "logger");
    }

    public Logger(File dir, String name) {
        this(dir, name.endsWith(".txt") ? name : (name + ".txt"), true);
    }

    public Logger(File dir, String name, boolean append) {
        this.dir = dir;
        if (!dir.isDirectory())
            throw new IllegalArgumentException("Given variable dir is not a valid directory/folder.");
        this.file = new File(dir, name);
        BetterPrintWriter a = null;
        if (!this.file.exists()) {
            try {
                if (this.file.createNewFile())
                    System.out.println(Ansi.ansi().fgGreen().a("Created new file: " + name).reset());
            } catch (IOException e) {
                System.out.println(Ansi.ansi().fgRed().a("Failed to create file " + name).reset());
                e.printStackTrace();
            }
        }
        try {
            a = new BetterPrintWriter(new BufferedWriter(new FileWriter(this.file, append)));
        } catch (IOException e) {
            System.out.println(Ansi.ansi().fgRed().a("Failed to create print writer.").reset());
            e.printStackTrace();
        }
        this.writer = a;
        if (this.file.length() != 0L && this.writer != null) {
            SimpleDateFormat d = new SimpleDateFormat("MMMM dd, yyyy");
            Stream.of("", "Date: " + d.format(new Date()), "").forEach(this.writer::println);
        }
        this.errCount = new AtomicInteger(0);
        this.warnCount = new AtomicInteger(0);
    }

    public void print(LogType type, String message) {
        if (isOpen()) {
            file(type, message);
            console(type, message);
        }
    }

    public void print(LogType type, Ansi.Color color, String message) {
        if (isOpen()) {
            file(type, message);
            console(type, color, message);
        }
    }

    public void file(LogType type, String message) {
        if (isOpen()) {
            if (type == LogType.DEBUG && !CoolThing.isDebugEnabled())
                return;
            this.writer.println(time() + type.getPrefix() + message);
            save();
        }
    }

    public void console(LogType type, String message) {
        if (isOpen())
            console(type, Ansi.Color.WHITE, message);
    }

    public void console(LogType type, Ansi.Color color, String message) {
        if (isOpen()) {
            if (type == LogType.DEBUG && !CoolThing.isDebugEnabled())
                return;
            if (CoolThing.hasInputListener()) {
                if (CoolThing.getInputListener() != null) {
                    if (CoolThing.getInputListener().isRunning())
                        System.out.print("\b\b");
                }
            }
            if (type == LogType.ERROR)
                errCount.incrementAndGet();
            else if (type == LogType.WARN)
                warnCount.incrementAndGet();
            if (CoolThing.hasNoColor()) {
                System.out.println(time() + type.getPrefix() + message);
            } else {
                System.out.print(Ansi.ansi().fgBrightBlack().a(time()));
                System.out.println(type.getColor().fg(color).a(message).reset());
            }
            if (CoolThing.hasInputListener()) {
                if (CoolThing.getInputListener() != null) {
                    if (CoolThing.getInputListener().isRunning())
                        System.out.print("> ");
                }
            }
        }
    }

    public void info(String message) {
        file(LogType.INFO, message);
        console(LogType.INFO, message);
    }

    public void info(Ansi.Color color, String message) {
        file(LogType.INFO, message);
        console(LogType.INFO, color, message);
    }

    public void command(String message) {
        file(LogType.COMMAND, message);
        console(LogType.COMMAND, message);
    }

    public void command(Ansi.Color color, String message) {
        file(LogType.COMMAND, message);
        console(LogType.COMMAND, color, message);
    }

    public void debug(String message) {
        file(LogType.DEBUG, message);
        console(LogType.DEBUG, message);
    }

    public void debug(Ansi.Color color, String message) {
        file(LogType.DEBUG, message);
        console(LogType.DEBUG, color, message);
    }

    public void warn(String message) {
        file(LogType.WARN, message);
        console(LogType.WARN, message);
    }

    public void warn(Ansi.Color color, String message) {
        file(LogType.WARN, message);
        console(LogType.WARN, color, message);
    }

    public void handleError(Thread thread, Throwable throwable) {
        new ErrorLog(thread, throwable);
    }

    public void error(String message) {
        file(LogType.ERROR, message);
        console(LogType.ERROR, message);
    }

    public void error(Ansi.Color color, String message) {
        file(LogType.ERROR, message);
        console(LogType.ERROR, color, message);
    }

    public void fatal(String message) {
        file(LogType.FATAL, message);
        console(LogType.FATAL, message);
    }

    public void fatal(Ansi.Color color, String message) {
        file(LogType.FATAL, message);
        console(LogType.FATAL, color, message);
    }

    public void save() {
        if (isOpen())
            this.writer.flush();
    }

    public void close() {
        if (isOpen()) {
            if (errCount.get() > 0 || warnCount.get() > 0)
                warn(Ansi.Color.YELLOW, "Finished with " + errCount.get() + " error(s) and " + warnCount.get() + " warning(s).");
            else
                info(Ansi.Color.GREEN, "Finished with " + errCount.get() + " error(s) and " + warnCount.get() + " warning(s).");
            this.writer.flush();
            this.writer.close();
        }
    }

    public boolean isOpen() {
        return this.writer.isOpen();
    }

    public File getDir() {
        return this.dir;
    }

    public File getFile() {
        return this.file;
    }

    public BetterPrintWriter getWriter() {
        return this.writer;
    }

    public int getErrorCount() {
        return errCount.get();
    }

    public int getWarnCount() {
        return warnCount.get();
    }

    private String time() {
        return "[" + FORMAT.format(new Date()) + "] ";
    }
}