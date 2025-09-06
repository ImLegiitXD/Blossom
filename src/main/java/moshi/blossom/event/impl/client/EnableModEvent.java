package moshi.blossom.event.impl.client;

import lombok.Getter;
import moshi.blossom.event.Event;
import moshi.blossom.module.Module;

@Getter
public class EnableModEvent extends Event {
    private final Module moduleEnabled;

    public EnableModEvent(Module moduleEnabled) {
        this.moduleEnabled = moduleEnabled;
    }
}
