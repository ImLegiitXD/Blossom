package moshi.blossom.event.impl.client;

import moshi.blossom.event.Event;
import moshi.blossom.module.Module;

public class DisableModEvent

extends Event {
    private final Module moduleDisabled;

    public DisableModEvent(Module moduleDisabled) {
        this.moduleDisabled = moduleDisabled;

    }

    public Module getModuleDisabled() {
        return this.moduleDisabled;

    }

}
