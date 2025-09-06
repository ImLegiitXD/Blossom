package moshi.blossom.event.impl.player;

import lombok.Getter;
import moshi.blossom.event.Event;
import moshi.blossom.util.player.MoveUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

@Getter
public class MoveEvent extends Event {
    private double x;
    private double y;

    public MoveEvent(double x, double y, double z, EntityPlayerSP player) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.player = player;
    }

    private double z; private final EntityPlayerSP player;

    public void setSpeed(double m) {
        MoveUtil.setSpeed(this, m);
    }

    public void jump() {
        setY(0.41999998688697815D);

        if (this.player.isPotionActive(Potion.jump)) setY(getY() + ((this.player.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F));

        if (this.player.isSprinting()) {
            float f = (getPlayer()).rotationYaw * 0.017453292F;
            setX(getX() - (MathHelper.sin(f) * 0.2F));
            setZ(getZ() + (MathHelper.cos(f) * 0.2F));
        }
    }

    public void setZero() {
        setX(0.0D);
        setZ(0.0D);
        setY(0.0D);
    }

    public void setZeroXZ() {
        setX(0.0D);
        setZ(0.0D);
    }

    public void setX(double x) {
        this.player.motionX = x;
        this.x = x;
    }

    public void setY(double y) {
        this.player.motionY = y;
        this.y = y;
    }

    public void setZ(double z) {
        this.player.motionZ = z;
        this.z = z;
    }
}
