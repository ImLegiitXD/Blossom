package moshi.blossom.module.impl.player;

import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.module.Module;
import net.minecraft.potion.Potion;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class AutoPot extends Module {

    @EventLink
    final Listener<TickEvent> handleTick;

    public AutoPot() {
        super("AutoPot", "Auto Pot", Category.PLAYER);

        this.handleTick = (event -> {
        });

    }

    boolean isGood(Potion potion) {
        return false;

    }

}
