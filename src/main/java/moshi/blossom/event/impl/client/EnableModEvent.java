package moshi.blossom.event.impl.client;

import moshi.blossom.event.Event;
import moshi.blossom.module.Module;

public class EnableModEvent

extends Event {
    private final Module moduleEnabled;

    public EnableModEvent(Module moduleEnabled) {
        this.moduleEnabled = moduleEnabled;

    }

    public Module getModuleEnabled() {
        return this.moduleEnabled;

    }

}
