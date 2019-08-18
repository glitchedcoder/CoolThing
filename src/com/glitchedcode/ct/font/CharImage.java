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
    a('a', "/lang/_A.png"),
    b('b', "/lang/_B.png"),
    c('c', "/lang/_C.png"),
    d('d', "/lang/_D.png"),
    e('e', "/lang/_E.png"),
    f('f', "/lang/_F.png"),
    g('g', "/lang/_G.png"),
    h('h', "/lang/_H.png"),
    i('i', "/lang/_I.png"),
    j('j', "/lang/_J.png"),
    k('k', "/lang/_K.png"),
    l('l', "/lang/_L.png"),
    m('m', "/lang/_M.png"),
    n('n', "/lang/_N.png"),
    o('o', "/lang/_O.png"),
    p('p', "/lang/_P.png"),
    q('q', "/lang/_Q.png"),
    r('r', "/lang/_R.png"),
    s('s', "/lang/_S.png"),
    t('t', "/lang/_T.png"),
    u('u', "/lang/_U.png"),
    v('v', "/lang/_V.png"),
    w('w', "/lang/_W.png"),
    x('x', "/lang/_X.png"),
    y('y', "/lang/_Y.png"),
    z('z', "/lang/_Z.png"),
    _1('1', "/lang/1.png"),
    _2('2', "/lang/2.png"),
    _3('3', "/lang/3.png"),
    _4('4', "/lang/4.png"),
    _5('5', "/lang/5.png"),
    _6('6', "/lang/6.png"),
    _7('7', "/lang/7.png"),
    _8('8', "/lang/8.png"),
    _9('9', "/lang/9.png"),
    _0('0', "/lang/0.png"),
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
    public static final CharImage[] NUMBERS = new CharImage[] {_0, _1, _2, _3, _4, _5, _6, _7, _8, _9};
    public static final CharImage[] LOWER_CASE = new CharImage[] {
            a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
    };
    public static final CharImage[] UPPER_CASE = new CharImage[] {
            A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
    };
    public static final CharImage[] MISC_CHARS = new CharImage[] {
            EXCLAMATION_POINT, PERIOD, QUESTION_MARK, COMMA, COLON, SEMICOLON, OPEN_PARENTHESIS,
            CLOSED_PARENTHESIS, OPEN_SQUARE_BRACKET, CLOSED_SQUARE_BRACKET, DOLLAR_SIGN, PERCENT_SIGN,
            AMPERSAND, STAR, MINUS, UNDERSCORE, FORWARD_SLASH, BACKWARD_SLASH, EQUALS_SIGN, PLUS_SIGN,
            GREATER_THAN, LESS_THAN, APOSTROPHE, QUOTE, OPEN_CURLY_BRACKET, CLOSED_CURLY_BRACKET
    };

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
        for (CharImage i : values()) {
            if (i.id == c)
                return i;
        }
        Logger logger = CoolThing.getLogger();
        logger.warn(Ansi.Color.YELLOW, "Unknown character " + c + " substituted for CharImage#UNKNOWN_CHAR.");
        return UNKNOWN_CHAR;
    }
}