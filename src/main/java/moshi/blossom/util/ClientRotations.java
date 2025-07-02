package moshi.blossom.util;

import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.render.RenderEvent;
import net.minecraft.client.Minecraft;

public class ClientRotations {
    private static float oldRenderYaw;
    private static float renderYaw;
    private static float oldRenderPitch;
    private static float renderPitch;
    private static boolean rotateNextPartialTicks;

    public static void update(MotionEvent e) {
        if (e.isPost()) {
            rotateNextPartialTicks = true;
            oldRenderYaw = renderYaw;
            oldRenderPitch = renderPitch;
            renderYaw = e.getYaw();
            renderPitch = e.getPitch();

            if (e.getYaw() == Minecraft.getMinecraft().thePlayer.rotationYaw &&
                    e.getPitch() == Minecraft.getMinecraft().thePlayer.rotationPitch) {
                rotateNextPartialTicks = false;
            } else {
                renderRotations();
            }
        }
    }

    public static void update(RenderEvent e) {
        if (rotateNextPartialTicks) {
            renderRotations();
        }
    }

    private static void renderRotations() {
        Minecraft mc = Minecraft.getMinecraft();
        float pitch = oldRenderPitch + (renderPitch - oldRenderPitch) * mc.timer.renderPartialTicks;
        float yaw = oldRenderYaw + (renderYaw - oldRenderYaw) * mc.timer.renderPartialTicks;

        mc.thePlayer.rotationYawHead = yaw;
        mc.thePlayer.renderYawOffset = yaw;
        mc.thePlayer.prevRenderYawOffset = yaw;
        mc.thePlayer.rotationPitchHead = pitch;
    }
}