package moshi.blossom.event.impl;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.event.Event;

@Setter
@Getter
public class KeyEvent extends Event {
    private int keyPressed;

    public KeyEvent(int keyPressed) {
        this.keyPressed = keyPressed;
    }

}
