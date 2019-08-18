package com.glitchedcode.ct.logger;

import java.io.*;

public class BetterPrintWriter extends PrintWriter {

    public BetterPrintWriter(Writer out) {
        super(out);
    }

    public BetterPrintWriter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public BetterPrintWriter(OutputStream out) {
        super(out);
    }

    public BetterPrintWriter(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public BetterPrintWriter(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    public BetterPrintWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, csn);
    }

    public BetterPrintWriter(File file) throws FileNotFoundException {
        super(file);
    }

    public BetterPrintWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, csn);
    }

    public boolean isOpen() {
        return out != null;
    }
}