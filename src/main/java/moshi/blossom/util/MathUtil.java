package moshi.blossom.util;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class MathUtil extends Util {
    public static float[] getRotations(double x, double y, double z) {
        Vec3 lookVec = getPlayer().getPositionEyes(1.0F);
        double dx = lookVec.xCoord - x;
        double dz = lookVec.zCoord - z;
        double dy = lookVec.yCoord - y;

        double dist = Math.sqrt(dx * dx + dz * dz);
        double yaw = Math.toDegrees(Math.atan2(dz, dx));
        double pitch = Math.toDegrees(Math.atan2(dy, dist));
        return new float[] { (float)yaw + 90.0F, (float)pitch };
    }

    public static float updateRots(float from, float to, float speed) {
        float f = MathHelper.wrapAngleTo180_float(to - from);
        if (f > speed) f = speed;
        if (f < -speed) f = -speed;
        return from + f;
    }

    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static int getRandomInRange(int min, int max) {
        return (int)(Math.random() * (max - min) + min);
    }

    public static double getMouseGCD() {
        float sens = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float pow = sens * sens * sens * 8.0F;
        return pow * 0.15D;
    }

    public static void applyGCD(float[] rotations, float[] prevRots) {
        float yawDif = rotations[0] - prevRots[0];
        float pitchDif = rotations[1] - prevRots[1];
        double gcd = getMouseGCD();

        rotations[0] = (float)(rotations[0] - yawDif % gcd);
        rotations[1] = (float)(rotations[1] - pitchDif % gcd);
    }
}