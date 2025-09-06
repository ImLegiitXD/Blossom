package moshi.blossom.event.impl.player;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.event.Event;

@Setter
@Getter
public class JumpEvent extends Event {
    private float yaw;

    public JumpEvent(float yaw) {
        this.yaw = yaw;
    }
}
