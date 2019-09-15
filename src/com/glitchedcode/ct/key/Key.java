package com.glitchedcode.ct.key;

import javax.annotation.Nullable;
import java.awt.event.KeyEvent;

/**
 * Used as a wrapper for {@link KeyEvent}'s keys.
 */
public enum Key {

    A(KeyEvent.VK_A),
    B(KeyEvent.VK_B),
    C(KeyEvent.VK_C),
    D(KeyEvent.VK_D),
    E(KeyEvent.VK_E),
    F(KeyEvent.VK_F),
    G(KeyEvent.VK_G),
    H(KeyEvent.VK_H),
    I(KeyEvent.VK_I),
    J(KeyEvent.VK_J),
    K(KeyEvent.VK_K),
    L(KeyEvent.VK_L),
    M(KeyEvent.VK_M),
    N(KeyEvent.VK_N),
    O(KeyEvent.VK_O),
    P(KeyEvent.VK_P),
    Q(KeyEvent.VK_Q),
    R(KeyEvent.VK_R),
    S(KeyEvent.VK_S),
    T(KeyEvent.VK_T),
    U(KeyEvent.VK_U),
    V(KeyEvent.VK_V),
    W(KeyEvent.VK_W),
    X(KeyEvent.VK_X),
    Y(KeyEvent.VK_Y),
    Z(KeyEvent.VK_Z),
    N0(KeyEvent.VK_0),
    N1(KeyEvent.VK_1),
    N2(KeyEvent.VK_2),
    N3(KeyEvent.VK_3),
    N4(KeyEvent.VK_4),
    N5(KeyEvent.VK_5),
    N6(KeyEvent.VK_6),
    N7(KeyEvent.VK_7),
    N8(KeyEvent.VK_8),
    N9(KeyEvent.VK_9),
    ESCAPE(KeyEvent.VK_ESCAPE),
    TAB(KeyEvent.VK_TAB),
    CAPS_LOCK(KeyEvent.VK_CAPS_LOCK),
    SHIFT(KeyEvent.VK_SHIFT),
    CONTROL(KeyEvent.VK_CONTROL),
    MINUS(KeyEvent.VK_MINUS),
    EQUALS(KeyEvent.VK_EQUALS),
    SEMICOLON(KeyEvent.VK_SEMICOLON),
    LEFT_SQUARE_BRACKET(KeyEvent.VK_OPEN_BRACKET),
    RIGHT_SQUARE_BRACKET(KeyEvent.VK_CLOSE_BRACKET),
    BACKSPACE(KeyEvent.VK_BACK_SPACE),
    ARROW_UP(KeyEvent.VK_UP),
    ARROW_DOWN(KeyEvent.VK_DOWN),
    ARROW_LEFT(KeyEvent.VK_LEFT),
    ARROW_RIGHT(KeyEvent.VK_RIGHT),
    ENTER(KeyEvent.VK_ENTER),
    FORWARD_SLASH(KeyEvent.VK_SLASH),
    BACKWARD_SLASH(KeyEvent.VK_BACK_SLASH),
    APOSTROPHE(KeyEvent.VK_QUOTE),
    COMMA(KeyEvent.VK_COMMA),
    PERIOD(KeyEvent.VK_PERIOD),
    ALT(KeyEvent.VK_ALT),
    F1(KeyEvent.VK_F1),
    F2(KeyEvent.VK_F2),
    F3(KeyEvent.VK_F3),
    F4(KeyEvent.VK_F4),
    F5(KeyEvent.VK_F5),
    F6(KeyEvent.VK_F6),
    F7(KeyEvent.VK_F7),
    F8(KeyEvent.VK_F8),
    F9(KeyEvent.VK_F9),
    F10(KeyEvent.VK_F10),
    F11(KeyEvent.VK_F11),
    F12(KeyEvent.VK_F12),
    F13(KeyEvent.VK_F13),
    F14(KeyEvent.VK_F14),
    F15(KeyEvent.VK_F15),
    F16(KeyEvent.VK_F16),
    F17(KeyEvent.VK_F17),
    F18(KeyEvent.VK_F18),
    F19(KeyEvent.VK_F19),
    F20(KeyEvent.VK_F20),
    F21(KeyEvent.VK_F21),
    F22(KeyEvent.VK_F22),
    F23(KeyEvent.VK_F23),
    F24(KeyEvent.VK_F24),
    PRINT_SCREEN(KeyEvent.VK_PRINTSCREEN),
    INSERT(KeyEvent.VK_INSERT),
    DELETE(KeyEvent.VK_DELETE),
    HOME(KeyEvent.VK_HOME),
    END(KeyEvent.VK_END),
    PAGE_UP(KeyEvent.VK_PAGE_UP),
    PAGE_DOWN(KeyEvent.VK_PAGE_DOWN),
    NUMBER_LOCK(KeyEvent.VK_NUM_LOCK),
    ASTERISK(KeyEvent.VK_ASTERISK),
    PLUS(KeyEvent.VK_PLUS);

    private final int id;

    Key(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public static Key toKey(int id) {
        for (Key k : values()) {
            if (k.id == id)
                return k;
        }
        return null;
    }
}