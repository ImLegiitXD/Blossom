package moshi.blossom.module.impl.player;

import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.player.PlayerUtil;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class NoFall extends Module {

    final NumberOption maxUses = new NumberOption("MAX_USES", "Max Repeats", 2.0D, 1.0D, 10.0D, 1.0D);

    int fallTicks;

    @EventLink
    public final Listener<MotionEvent> handleMotion;

    public NoFall() {
        super("NoFall", "No Fall", Category.PLAYER);

        this.fallTicks = 0;

        this.handleMotion = (event -> {
            if (getPlayer().onGround) {
                this.fallTicks = 0;

            }

            if (ModManager.getMod("AntiVoid").isToggled() &&
            PlayerUtil.isOverVoid() &&
            getPlayer().motionY < 0.0D) {
                return;

            }

            if (getPlayer().motionY < -0.2D &&
            getPlayer().fallDistance >= 3.0D &&
            (this.fallTicks <= this.maxUses.getValue() - 1.0D ||
            getPlayer().isCollidedHorizontally)) {
                event.setGround(true);

                getPlayer().fallDistance = 0.0F;

                this.fallTicks++;

            }

        });

    setupOptions(new Option[] { this.maxUses });

    }

    public String getSuffix() {
        return "Spoof " + Math.round(this.maxUses.getValue());

    }

    public void onEnable() {
        super.onEnable();

        this.fallTicks = 0;

    }

}
