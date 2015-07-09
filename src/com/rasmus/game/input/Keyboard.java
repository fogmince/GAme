package com.rasmus.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private boolean[] keys = new boolean[128];

    public int pressed1, pressed2, pressed3, pressed4, pressed5, pressedEsc;

    public boolean up, down, left, right;
    public boolean zero, one, two, three, four, five, six, seven, eight, nine;
    public boolean q, e, space, enter, tab, shift, backspace, esc;

    public void update() {
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];

        zero = keys[KeyEvent.VK_0];
        one = keys[KeyEvent.VK_1];
        two = keys[KeyEvent.VK_2];
        three = keys[KeyEvent.VK_3];
        four = keys[KeyEvent.VK_4];
        five = keys[KeyEvent.VK_5];
        six = keys[KeyEvent.VK_6];
        seven = keys[KeyEvent.VK_7];
        eight = keys[KeyEvent.VK_8];
        nine = keys[KeyEvent.VK_9];

        q = keys[KeyEvent.VK_Q];
        e  = keys[KeyEvent.VK_E];
        space = keys[KeyEvent.VK_SPACE];
        enter = keys[KeyEvent.VK_ENTER];
        tab = keys[KeyEvent.VK_TAB];
        shift = keys[KeyEvent.VK_SHIFT];
        backspace = keys[KeyEvent.VK_BACK_SPACE];
        esc = keys[KeyEvent.VK_ESCAPE];

        if(pressed1 == 1) one = false;
        if(one) pressed1 = 1;

        if(pressed2 == 1) two = false;
        if(two) pressed2 = 1;

        if(pressed3 == 1) three = false;
        if(three) pressed3 = 1;

        if(pressed4 == 1) four = false;
        if(four) pressed4 = 1;

        if(pressed5 == 1) five = false;
        if(five) pressed5 = 1;

        if(pressedEsc == 1) esc = false;
        if(esc) pressedEsc = 1;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        pressed1 = 0;
        pressed2 = 0;
        pressed3 = 0;
        pressed4 = 0;
        pressed5 = 0;
        pressedEsc = 0;
    }
}
