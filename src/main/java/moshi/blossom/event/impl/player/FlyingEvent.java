package moshi.blossom.event.impl.player;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.event.Event;

@Setter
@Getter
public class FlyingEvent extends Event {
    private float yaw;

    public FlyingEvent(float yaw, float forward, float strafe) {
        this.yaw = yaw;
        this.forward = forward;
        this.strafe = strafe;
    }

    private float forward; private float strafe;
}
