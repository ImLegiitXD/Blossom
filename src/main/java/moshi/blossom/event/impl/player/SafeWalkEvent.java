package moshi.blossom.event.impl.player;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.event.Event;

@Setter
@Getter
public class SafeWalkEvent extends Event {
    private boolean forceSafeWalk;

    public SafeWalkEvent(boolean forceSafeWalk) {
        this.forceSafeWalk = forceSafeWalk;
    }
}
