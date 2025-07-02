package moshi.blossom.util.player;

import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.util.Util;

public class MoveUtil extends Util {

    public static void setSpeed(MoveEvent event, double speed) {
        setSpeed(event, speed, getDirection());
    }

    public static void setSpeed(double speed) {
        if (speed == 0.0D) {
            getPlayer().motionX = 0.0D;
            getPlayer().motionZ = 0.0D;
            return;
        }

        if (!isMoving()) return;

        double dir = Math.toRadians(getDirection());
        getPlayer().motionX = -Math.sin(dir) * speed;
        getPlayer().motionZ = Math.cos(dir) * speed;
    }

    public static void setSpeed(MoveEvent event, double speed, float yawDegrees) {
        if (speed == 0.0D) {
            event.setZeroXZ();
            return;
        }

        if (!isMoving()) return;

        double dir = Math.toRadians(yawDegrees);
        event.setX(-Math.sin(dir) * speed);
        event.setZ(Math.cos(dir) * speed);
    }

    public static double getSpeed() {
        return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
    }

    public static double getSpeed(MoveEvent event) {
        return Math.hypot(event.getX(), event.getZ());
    }

    public static boolean isMoving() {
        return mc.thePlayer != null &&
                (mc.thePlayer.moveForward != 0.0F ||
                        mc.thePlayer.moveStrafing != 0.0F);
    }

    public static float getDirection() {
        return getDirection(
                mc.thePlayer.rotationYaw,
                mc.thePlayer.moveForward,
                mc.thePlayer.moveStrafing
        );
    }

    public static float getDirection(float rotationYaw, float moveForward, float moveStrafing) {
        if (moveForward < 0.0F) {
            rotationYaw += 180.0F;
        }

        float forwardFactor = 1.0F;
        if (moveForward < 0.0F) {
            forwardFactor = -0.5F;
        } else if (moveForward > 0.0F) {
            forwardFactor = 0.5F;
        }

        if (moveStrafing > 0.0F) {
            rotationYaw -= 90.0F * forwardFactor;
        }
        if (moveStrafing < 0.0F) {
            rotationYaw += 90.0F * forwardFactor;
        }

        return rotationYaw;
    }
}