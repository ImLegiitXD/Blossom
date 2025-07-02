package moshi.blossom.event.impl.player;

import moshi.blossom.event.Event;
import net.minecraft.entity.Entity;

public class AttackEvent

extends Event {
    private final Entity entity;

    private boolean pre;

    public AttackEvent(Entity entity) {
        this.entity = entity;

    }

    public Entity getEntity() {
        return this.entity;

    }

    public boolean isPost() {
        return !this.pre;

    }

    public boolean isPre() {
        return this.pre;

    }

    public void setPre(boolean pre) {
        this.pre = pre;

    }

}
