package moshi.blossom.event.impl.player;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.event.Event;
import net.minecraft.entity.Entity;

@Getter
@Setter
public class AttackEvent extends Event {
    private final Entity entity;
    private boolean pre;

    public AttackEvent(Entity entity) {
        this.entity = entity;
    }

    public boolean isPost() {
        return !this.pre;
    }
}
