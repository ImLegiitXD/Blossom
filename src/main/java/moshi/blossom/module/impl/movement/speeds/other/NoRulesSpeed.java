package moshi.blossom.module.impl.movement.speeds.other;

import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.module.ModuleMode;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class NoRulesSpeed extends ModuleMode {

    int stage;

    int ticks;

    double motion;

    @EventLink
    public final Listener<MoveEvent> handleMove;

    public NoRulesSpeed() {
        super("No Rules");

        this.handleMove = (event -> {
            if (getPlayer().onGround) {
                this.stage = 0;

                this.ticks = 0;

            } else {
                this.stage = (this.ticks == 0) ? 1 : 2;

                this.ticks++;

            }

            switch (this.stage) {
                case 0:
                double boost = 0.7012797212930308D;

                if (getPlayer().isPotionActive(Potion.moveSpeed)) {
                    boost *= 1.0D + (getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) * 0.22D;

                }

                this.motion = boost;

                event.setY(0.41999998688697815D);

                break;

                case 1:
                if (!GameSettings.isKeyDown(this.mc.gameSettings.keyBindJump)) {
                    event.setY(0.0D);

                }

                this.motion *= 0.71D;

                break;

            }

            event.setSpeed(this.motion);

        });

    }

    public void onEnable() {
        super.onEnable();

        this.ticks = 0;

        this.stage = -1;

        this.motion = 0.0D;

    }

}
