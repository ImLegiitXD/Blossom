package moshi.blossom.event.impl.player;

import moshi.blossom.event.Event;

public class SafeWalkEvent

extends Event {
    private boolean forceSafeWalk;

    public SafeWalkEvent(boolean forceSafeWalk) {
        this.forceSafeWalk = forceSafeWalk;

    }

    public boolean isForceSafeWalk() {
        return this.forceSafeWalk;

    }

    public void setForceSafeWalk(boolean forceSafeWalk) {
        this.forceSafeWalk = forceSafeWalk;

    }

}
