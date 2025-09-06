package moshi.blossom.event.impl.player;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.event.Event;
import net.minecraft.client.entity.EntityPlayerSP;

@Getter
@Setter
public class MotionEvent extends Event {
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

    public void setRotations(float[] rotations) {
        setYaw(rotations[0]);
        setPitch(rotations[1]);
    }
}
