package moshi.blossom.module.impl.player;

import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.module.Module;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class FastMine extends Module {

    @EventLink
    public Listener<MotionEvent> handleMotion;

    public FastMine() {
        super("FastMine", "Fast Mine", Category.PLAYER);

        this.handleMotion = (event -> {
            if (!event.isPre()) return;

            // Instantly finish mining when block damage reaches 72.5%
            if (this.mc.playerController.curBlockDamageMP > 0.725D) {
                this.mc.playerController.curBlockDamageMP = 1.0F;

            }

        });

    }

    public String getSuffix() {
        return "NCP"; // NoCheatPlus bypass mode
    }

}
