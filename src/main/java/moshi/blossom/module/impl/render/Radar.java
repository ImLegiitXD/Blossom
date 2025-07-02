package moshi.blossom.module.impl.render;

import moshi.blossom.Blossom;
import moshi.blossom.event.impl.render.DrawEvent;
import moshi.blossom.module.Module;
import moshi.blossom.util.MathUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;
import java.awt.Color;

public class Radar extends Module {

    private static final float RADAR_X = 5.0F;

    private static final float RADAR_Y = 28.0F;

    private static final float RADAR_WIDTH = 115.0F;

    private static final float RADAR_HEIGHT = 115.0F;

    private static final Color BACKGROUND_COLOR = new Color(0xBBBBBB40, true);

    private static final Color PLAYER_COLOR = new Color(0x6200FFFF, true);

    private static final Color TARGET_COLOR = new Color(0xFFB53B40, true);

    private static final Color FRIEND_COLOR = new Color(0x88BBBB40, true);

    @EventLink
    public final Listener<DrawEvent> handleDraw = this::onDraw;

    public Radar() {
        super("Radar", "Radar", Module.Category.RENDER);

    }

    private void onDraw(DrawEvent event) {
        // Draw radar background
        Gui.drawRect((int) RADAR_X, (int) RADAR_Y, (int) (RADAR_X + RADAR_WIDTH), (int) (RADAR_Y + RADAR_HEIGHT), BACKGROUND_COLOR.getRGB());

        Gui.drawRect((int) (RADAR_X + 1.0F), (int) (RADAR_Y + RADAR_HEIGHT - 1.5F),
        (int) (RADAR_X + RADAR_WIDTH - 1.0F), (int) (RADAR_Y + RADAR_HEIGHT - 1.0F),
        HUD.hudColor.apply(0).getRGB());

        final float centerX = RADAR_X + RADAR_WIDTH / 2.0F;

        final float centerY = RADAR_Y + RADAR_HEIGHT / 2.0F;

        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            if (entity == getPlayer() || !(entity instanceof net.minecraft.entity.player.EntityPlayer)) {
                continue;

            }

            float entYaw = MathUtil.getRotations(entity.posX, entity.posY, entity.posZ)[0];

            float angleDiff = Math.abs(entYaw - getPlayer().rotationYaw - 36000.0F) - 180.0F;

            double diffRadians = Math.toRadians(angleDiff);

            double dx = getPlayer().posX - entity.posX;

            double dy = getPlayer().posZ - entity.posZ;

            double dist = Math.hypot(dx, dy);

            double entX = MathHelper.clamp_double(centerX - Math.sin(diffRadians) * dist,
            RADAR_X + 2.0F, RADAR_X + RADAR_WIDTH - 3.0F);

            double entY = MathHelper.clamp_double(centerY + Math.cos(diffRadians) * dist,
            RADAR_Y + 2.0F, RADAR_Y + RADAR_HEIGHT - 3.0F);

            Gui.drawRect((int) entX, (int) entY, (int) (entX + 1.0D), (int) (entY + 1.0D), -1);

            if (Blossom.INSTANCE.getUserHelper().isTarget((EntityLivingBase) entity)) {
                drawCross(entX, entY, TARGET_COLOR);

            } else if (Blossom.INSTANCE.getUserHelper().isFriend((EntityLivingBase) entity)) {
                drawCross(entX, entY, FRIEND_COLOR);

            }

        }

        // Draw center player indicator
        drawCross(centerX, centerY, PLAYER_COLOR);

    }

    private void drawCross(double x, double y, Color color) {
        Gui.drawRect((int) (x - 0.5D), (int) (y - 2.0D), (int) (x + 0.5D), (int) (y + 2.0D), color.getRGB());

        Gui.drawRect((int) (x - 2.0D), (int) (y - 0.5D), (int) (x + 2.0D), (int) (y + 0.5D), color.getRGB());

    }

}
