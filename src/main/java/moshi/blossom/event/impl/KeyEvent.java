package moshi.blossom.event.impl;

import moshi.blossom.event.Event;

public class KeyEvent

extends Event {
    private int keyPressed;

    public KeyEvent(int keyPressed) {
        this.keyPressed = keyPressed;

    }

    public int getKeyPressed() {
        return this.keyPressed;

    }

    public void setKeyPressed(int keyPressed) {
        this.keyPressed = keyPressed;

    }

}
