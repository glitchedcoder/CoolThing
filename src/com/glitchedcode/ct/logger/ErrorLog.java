package com.glitchedcode.ct.logger;

import com.glitchedcode.ct.CoolThing;
import org.fusesource.jansi.Ansi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

public class ErrorLog {

    private static final Logger logger = CoolThing.getLogger();
    private static final File DIR = new File(CoolThing.getDataFolder(), "error logs");

    public ErrorLog(Thread thread, Throwable throwable) {
        logger.error(Ansi.Color.RED, "Uncaught exception " + throwable.getClass().getName()
                + " in thread \"" + thread.getName() + "\": " + throwable.getMessage());
        logger.debug(Ansi.Color.RED, "Stack trace:\n" + formatStackTrace(throwable));
        if (!DIR.exists()) {
            logger.debug("Error logs folder doesn't exist, creating...");
            if (DIR.mkdir())
                logger.debug("Created error log folder located at " + DIR.getAbsolutePath());
        }
        File log = new File(DIR, "error_log_" + timeLog() + ".txt");
        try {
            if (log.createNewFile())
                logger.debug("Created error log \"" + log.getName() + "\".");
            BetterPrintWriter writer = new BetterPrintWriter(new BufferedWriter(new FileWriter(log, false)));
            Stream.of("Error log of CoolThing v" + CoolThing.getVersion(),
                    "// Here we go again :(",
                    "",
                    "Occurrence: " + (new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss z")).format(new Date()),
                    "",
                    "Thread: " + thread.getName(),
                    "",
                    "Exception type: " + throwable.getClass().getSimpleName(),
                    "",
                    "Message: " + throwable.getMessage(),
                    "",
                    "------------------------------",
                    "BEGINNING OF STACK TRACE",
                    "------------------------------",
                    formatStackTrace(throwable),
                    "------------------------------",
                    "END OF STACK TRACE",
                    "------------------------------"
            ).forEach(writer::write);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.warn(Ansi.Color.RED, "Somehow managed to run into an error while creating the error log.");
            logger.warn(Ansi.Color.RED, "Report the following error to the developer:\n" + formatStackTrace(e));
        }
    }

    private String timeLog() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        return f.format(new Date());
    }

    private String formatStackTrace(Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        builder.append(throwable.getClass().getName());
        builder.append(": ");
        builder.append(throwable.getMessage());
        builder.append("\n");
        for (StackTraceElement element : throwable.getStackTrace()) {
            builder.append("\t");
            builder.append(element.getClassName());
            builder.append("#");
            builder.append(element.getMethodName());
            builder.append(":");
            builder.append(element.getLineNumber());
            builder.append(element.isNativeMethod() ? " (native method)\n" : "\n");
        }
        return builder.toString();
    }
}