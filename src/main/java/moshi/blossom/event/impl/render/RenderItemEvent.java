package moshi.blossom.event.impl.render;

import moshi.blossom.event.Event;
import net.minecraft.item.EnumAction;

public class RenderItemEvent

extends Event {
    private boolean forceUse;

    private EnumAction action;

    public RenderItemEvent(boolean forceUse, EnumAction action) {
        this.forceUse = forceUse;

        this.action = action;

    }

    public boolean isForceUse() {
        return this.forceUse;

    }

    public void setForceUse(boolean forceUse) {
        this.forceUse = forceUse;

    }

    public EnumAction getAction() {
        return this.action;

    }

    public void setAction(EnumAction action) {
        this.action = action;

    }

}
