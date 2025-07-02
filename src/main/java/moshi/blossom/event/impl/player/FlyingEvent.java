package moshi.blossom.event.impl.player;

import moshi.blossom.event.Event;

public class FlyingEvent

extends Event {
    private float yaw;

    public FlyingEvent(float yaw, float forward, float strafe) {
        this.yaw = yaw;

        this.forward = forward;

        this.strafe = strafe;

    }

    private float forward; private float strafe;

    public float getYaw() {
        return this.yaw;

    }

    public void setYaw(float yaw) {
        this.yaw = yaw;

    }

    public float getForward() {
        return this.forward;

    }

    public void setForward(float forward) {
        this.forward = forward;

    }

    public float getStrafe() {
        return this.strafe;

    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;

    }

}
