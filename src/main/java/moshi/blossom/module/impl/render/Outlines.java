package moshi.blossom.module.impl.render;

import moshi.blossom.Blossom;
import moshi.blossom.event.impl.render.EntityRenderEvent;
import moshi.blossom.module.Module;
import moshi.skidded.SkiddedRenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;
import java.awt.Color;

public class Outlines extends Module {

    private static final Color OUTLINE_COLOR = new Color(11550270);

    @EventLink
    public final Listener<EntityRenderEvent> handleRenderer = this::onEntityRender;

    public Outlines() {
        super("Outlines", "Outlines", Module.Category.RENDER);

    }

    private void onEntityRender(EntityRenderEvent event) {
        if (!(event.entity instanceof EntityLivingBase)) return;

        if (!Blossom.INSTANCE.getUserHelper().isTarget((EntityLivingBase)event.entity)) return;

        EntityLivingBase entity = (EntityLivingBase)event.entity;

        event.rendererLivingEntity.renderModel(entity, event.f6, event.f5, event.f7, event.f2, event.f8, 0.0625F);

        GlStateManager.depthMask(true);

        if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
            event.rendererLivingEntity.renderLayers(entity, event.f6, event.f5,
            event.partialTicks, event.f7, event.f2, event.f8, 0.0625F);

        }

        SkiddedRenderUtil.renderOne();

        event.rendererLivingEntity.renderModel(entity, event.f6, event.f5, event.f7, event.f2, event.f8, 0.0625F);

        SkiddedRenderUtil.renderTwo();

        event.rendererLivingEntity.renderModel(entity, event.f6, event.f5, event.f7, event.f2, event.f8, 0.0625F);

        SkiddedRenderUtil.renderThree();

        SkiddedRenderUtil.renderFour(OUTLINE_COLOR.getRGB());

        event.rendererLivingEntity.renderModel(entity, event.f6, event.f5, event.f7, event.f2, event.f8, 0.0625F);

        SkiddedRenderUtil.renderFive();

        event.cancelEvent();

    }

}
