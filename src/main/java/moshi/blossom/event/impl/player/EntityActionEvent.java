package moshi.blossom.event.impl.player;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.event.Event;

@Setter
@Getter
public class EntityActionEvent extends Event {

    private boolean sprinting;

    private boolean sneaking;

    public EntityActionEvent(boolean sprinting, boolean sneaking) {
        this.sprinting = sprinting;
        this.sneaking = sneaking;
    }
}
