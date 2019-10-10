package com.glitchedcode.ct.font;

import com.glitchedcode.ct.CoolThing;
import com.glitchedcode.ct.logger.Logger;
import org.fusesource.jansi.Ansi;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public enum CharImage {

    A('A', "/lang/A.png"),
    B('B', "/lang/B.png"),
    C('C', "/lang/C.png"),
    D('D', "/lang/D.png"),
    E('E', "/lang/E.png"),
    F('F', "/lang/F.png"),
    G('G', "/lang/G.png"),
    H('H', "/lang/H.png"),
    I('I', "/lang/I.png"),
    J('J', "/lang/J.png"),
    K('K', "/lang/K.png"),
    L('L', "/lang/L.png"),
    M('M', "/lang/M.png"),
    N('N', "/lang/N.png"),
    O('O', "/lang/O.png"),
    P('P', "/lang/P.png"),
    Q('Q', "/lang/Q.png"),
    R('R', "/lang/R.png"),
    S('S', "/lang/S.png"),
    T('T', "/lang/T.png"),
    U('U', "/lang/U.png"),
    V('V', "/lang/V.png"),
    W('W', "/lang/W.png"),
    X('X', "/lang/X.png"),
    Y('Y', "/lang/Y.png"),
    Z('Z', "/lang/Z.png"),
    L_A('a', "/lang/_A.png"),
    L_B('b', "/lang/_B.png"),
    L_C('c', "/lang/_C.png"),
    L_D('d', "/lang/_D.png"),
    L_E('e', "/lang/_E.png"),
    L_F('f', "/lang/_F.png"),
    L_G('g', "/lang/_G.png"),
    L_H('h', "/lang/_H.png"),
    L_I('i', "/lang/_I.png"),
    L_J('j', "/lang/_J.png"),
    L_K('k', "/lang/_K.png"),
    L_L('l', "/lang/_L.png"),
    L_M('m', "/lang/_M.png"),
    L_N('n', "/lang/_N.png"),
    L_O('o', "/lang/_O.png"),
    L_P('p', "/lang/_P.png"),
    L_Q('q', "/lang/_Q.png"),
    L_R('r', "/lang/_R.png"),
    L_S('s', "/lang/_S.png"),
    L_T('t', "/lang/_T.png"),
    L_U('u', "/lang/_U.png"),
    L_V('v', "/lang/_V.png"),
    L_W('w', "/lang/_W.png"),
    L_X('x', "/lang/_X.png"),
    L_Y('y', "/lang/_Y.png"),
    L_Z('z', "/lang/_Z.png"),
    N1('1', "/lang/1.png"),
    N2('2', "/lang/2.png"),
    N3('3', "/lang/3.png"),
    N4('4', "/lang/4.png"),
    N5('5', "/lang/5.png"),
    N6('6', "/lang/6.png"),
    N7('7', "/lang/7.png"),
    N8('8', "/lang/8.png"),
    N9('9', "/lang/9.png"),
    N0('0', "/lang/0.png"),
    EXCLAMATION_POINT('!', "/lang/EXCLAMATION_POINT.png"),
    PERIOD('.', "/lang/PERIOD.png"),
    QUESTION_MARK('?', "/lang/QUESTION_MARK.png"),
    COMMA(',', "/lang/COMMA.png"),
    COLON(':', "/lang/COLON.png"),
    SEMICOLON(';', "/lang/SEMICOLON.png"),
    OPEN_PARENTHESIS('(', "/lang/OPEN_PARENTHESIS.png"),
    CLOSED_PARENTHESIS(')', "/lang/CLOSED_PARENTHESIS.png"),
    OPEN_SQUARE_BRACKET('[', "/lang/OPEN_SQUARE_BRACKET.png"),
    CLOSED_SQUARE_BRACKET('[', "/lang/CLOSED_SQUARE_BRACKET.png"),
    DOLLAR_SIGN('$', "/lang/DOLLAR_SIGN.png"),
    PERCENT_SIGN('%', "/lang/PERCENT_SIGN.png"),
    AMPERSAND('&', "/lang/AMPERSAND.png"),
    STAR('*', "/lang/STAR.png"),
    MINUS('-', "/lang/MINUS.png"),
    UNDERSCORE('_', "/lang/UNDERSCORE.png"),
    FORWARD_SLASH('/', "/lang/FORWARD_SLASH.png"),
    BACKWARD_SLASH('\\', "/lang/BACKWARD_SLASH.png"),
    EQUALS_SIGN('=', "/lang/EQUALS_SIGN.png"),
    PLUS_SIGN('+', "/lang/PLUS_SIGN.png"),
    GREATER_THAN('>', "/lang/GREATER_THAN.png"),
    LESS_THAN('<', "/lang/LESS_THAN.png"),
    APOSTROPHE('\'', "/lang/APOSTROPHE.png"),
    QUOTE('"', "/lang/QUOTE.png"),
    OPEN_CURLY_BRACKET('{', "/lang/OPEN_CURLY_BRACKET.png"),
    CLOSED_CURLY_BRACKET('}', "/lang/CLOSED_CURLY_BRACKET.png"),
    SPACE(' ', "/lang/SPACE.png"),
    UNKNOWN_CHAR('@', "/lang/UNKNOWN_CHAR.png");

    private final char id;
    private final String fileName;
    private static final CharImage[] values = values();

    CharImage(char id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public char getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public BufferedImage toImage() {
        try {
            return ImageIO.read(CoolThing.class.getResourceAsStream(getFileName()));
        } catch (Exception e) {
            Logger logger = CoolThing.getLogger();
            logger.error(Ansi.Color.RED, "Failed to read image \"" + getFileName() + "\": " + e.getMessage());
            logger.handleError(Thread.currentThread(), e);
        }
        return UNKNOWN_CHAR.toImage();
    }

    public static CharImage[] fromString(String s) {
        char[] c = s.toCharArray();
        List<CharImage> l = new ArrayList<>();
        for (char d : c)
            l.add(fromChar(d));
        return l.toArray(new CharImage[0]);
    }

    public static CharImage fromChar(char c) {
        for (CharImage i : values) {
            if (i.id == c)
                return i;
        }
        Logger logger = CoolThing.getLogger();
        logger.warn(Ansi.Color.YELLOW, "Unknown character " + c + " substituted for CharImage#UNKNOWN_CHAR.");
        return UNKNOWN_CHAR;
    }
}