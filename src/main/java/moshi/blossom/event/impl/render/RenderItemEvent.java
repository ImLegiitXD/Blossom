package moshi.blossom.event.impl.render;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.event.Event;
import net.minecraft.item.EnumAction;

@Setter
@Getter
public class RenderItemEvent extends Event {
    private boolean forceUse;

    private EnumAction action;

    public RenderItemEvent(boolean forceUse, EnumAction action) {
        this.forceUse = forceUse;
        this.action = action;
    }
}
