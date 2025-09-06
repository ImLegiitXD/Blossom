package moshi.blossom.event.impl.render;

import moshi.blossom.event.Event;
import moshi.blossom.event.impl.player.MoveEvent;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;

public class EntityRenderEvent extends Event {
    public Entity entity;
    public float f6;
    public float f5;
    public float f7;

    public EntityRenderEvent(Entity entity, float f6, float f5, float f7, float f2, float f8, RendererLivingEntity rendererLivingEntity, float partialTicks) {
        this.entity = entity;
        this.f6 = f6;
        this.f5 = f5;
        this.f7 = f7;
        this.f2 = f2;
        this.f8 = f8;
        this.rendererLivingEntity = rendererLivingEntity;
        this.partialTicks = partialTicks;
    }

    public float f2;
    public float f8;
    public RendererLivingEntity rendererLivingEntity;
    public float partialTicks;
}
