package moshi.blossom.event.impl.player;

import moshi.blossom.event.Event;

public class JumpEvent

extends Event {
    private float yaw;

    public JumpEvent(float yaw) {
        this.yaw = yaw;

    }

    public float getYaw() {
        return this.yaw;

    }

    public void setYaw(float yaw) {
        this.yaw = yaw;

    }

}
