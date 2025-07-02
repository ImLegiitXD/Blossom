package moshi.blossom.module.impl.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector4d;
import moshi.blossom.Blossom;
import moshi.blossom.event.impl.render.DrawEvent;
import moshi.blossom.module.Module;
import moshi.skidded.SkiddedRenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;
import org.lwjgl.opengl.GL11;

public class ESP extends Module {

    @EventLink
    public Listener<DrawEvent> handleDraw;

    public ESP() {
        super("ESP", "ESP", Category.RENDER);

        this.handleDraw = (event -> {
            List<Entity> entities = new ArrayList<>(this.mc.theWorld.getLoadedEntityList());

            //entities.removeIf(());

            for (Entity entity : entities) {
                if (!(entity instanceof EntityLivingBase)) continue;

                if (!shouldDraw(entity)) continue;

                EntityLivingBase asLiving = (EntityLivingBase) entity;

                Vector4d pos = SkiddedRenderUtil.get2DVector(asLiving);

                this.mc.entityRenderer.setupOverlayRendering();

                if (pos != null) {
                    drawESP(pos, asLiving);

                }

            }

        });

    }

    public void drawESP(Vector4d pos, EntityLivingBase entity) {
        double height = pos.w - pos.y;

        float max = entity.getMaxHealth();

        float cur = entity.getHealth();

        float healthf = cur / max;

        int color = (new Color(13750737)).getRGB();

        int bg = (new Color(2063597568, true)).getRGB();

        int col = HUD.fadeBetween((new Color(7583578)).getRGB(), (new Color(13388362)).getRGB(), 1.0F - healthf);

        Gui.drawRect((int) (pos.x - 2.5D - 0.5D), (int) (pos.w - height * healthf - 0.5D), (int) (pos.x - 1.5D + 0.5D), (int) (pos.w + 0.5D), bg);

        Gui.drawRect((int) (pos.x - 2.5D), (int) (pos.w - height * healthf), (int) (pos.x - 1.5D), (int) pos.w, col);

        String entName = entity.getName();

        if (Blossom.INSTANCE.getUserHelper().isTarget(entity)) {
            entName = EnumChatFormatting.GRAY + "(" + EnumChatFormatting.RED + "target" + EnumChatFormatting.GRAY + ") " + EnumChatFormatting.RESET + entName;

        }

        GL11.glPushMatrix();

        GL11.glScaled(0.5D, 0.5D, 1.0D);

        Gui.drawRect((int) ((float)((pos.x + (pos.z - pos.x) / 2.0D - ((Minecraft.getMinecraft()).fontRendererObj.getStringWidth(entName) / 4)) * 2.0D) - 2.0F), (int) ((float)(pos.y - 7.0D) * 2.0F - 2.0F),
        (int) ((pos.x + (pos.z - pos.x) / 2.0D + ((Minecraft.getMinecraft()).fontRendererObj.getStringWidth(entName) / 4) + 1.5D) * 2.0D), (int) ((float)(pos.y - 2.0D) * 2.0F), (new Color(1728053248, true))
        .getRGB());

        (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow(entName,
        (float)((pos.x + (pos.z - pos.x) / 2.0D - ((Minecraft.getMinecraft()).fontRendererObj.getStringWidth(entName) / 4)) * 2.0D), (float)(pos.y - 7.0D) * 2.0F, color);

        GL11.glPopMatrix();

    }

    public boolean shouldDraw(Entity entity) {
        if (entity == getPlayer())
        return false;

        if (entity instanceof net.minecraft.entity.player.EntityPlayer)
        return true;

        return false;

    }

}
