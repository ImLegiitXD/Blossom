package moshi.blossom.event.impl.player;

import moshi.blossom.event.Event;
import net.minecraft.client.entity.EntityPlayerSP;

public class MotionEvent

extends Event
{
    private boolean pre = true;

    private double x;

    private double y;

    private double z;

    public MotionEvent(double x, double y, double z, float yaw, float pitch, boolean ground, EntityPlayerSP player) {
        this.x = x;

        this.y = y;

        this.z = z;

        this.yaw = yaw;

        this.pitch = pitch;

        this.ground = ground;

        this.player = player;

    }

    private float yaw; private float pitch; private boolean ground; private EntityPlayerSP player; public boolean force06;

    public boolean isPost() {
        return !isPre();

    }

    public boolean isPre() {
        return this.pre;

    }

    public void setPre(boolean pre) {
        this.pre = pre;

    }

    public double getX() {
        return this.x;

    }

    public void setX(double x) {
        this.x = x;

    }

    public double getY() {
        return this.y;

    }

    public void setY(double y) {
        this.y = y;

    }

    public double getZ() {
        return this.z;

    }

    public void setZ(double z) {
        this.z = z;

    }

    public float getYaw() {
        return this.yaw;

    }

    public void setYaw(float yaw) {
        this.yaw = yaw;

    }

    public float getPitch() {
        return this.pitch;

    }

    public void setPitch(float pitch) {
        this.pitch = pitch;

    }

    public boolean isGround() {
        return this.ground;

    }

    public void setGround(boolean ground) {
        this.ground = ground;

    }

    public void setRotations(float[] rotations) {
        setYaw(rotations[0]);

        setPitch(rotations[1]);

    }

    public EntityPlayerSP getPlayer() {
        return this.player;

    }

    public void setPlayer(EntityPlayerSP player) {
        this.player = player;

    }

}
