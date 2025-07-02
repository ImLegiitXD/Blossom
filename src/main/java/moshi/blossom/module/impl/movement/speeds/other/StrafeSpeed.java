package moshi.blossom.module.impl.movement.speeds.other;

import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.util.player.MoveUtil;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class StrafeSpeed extends ModuleMode {

    @EventLink
    public Listener<MoveEvent> handleMove;

    public StrafeSpeed() {
        super("Strafe");

        this.handleMove = (event -> {
            if (event.getPlayer().onGround) {
                event.setY(0.41999998688697815D);

                if (getPlayer().isPotionActive(Potion.jump)) {
                    event.setY(event.getY() +
                    ((getPlayer().getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F));

                }

                float f = MoveUtil.getDirection() * 0.017453292F;

                event.setX(event.getX() - (MathHelper.sin(f) * 0.2F));

                event.setZ(event.getZ() + (MathHelper.cos(f) * 0.2F));

            }

        });

    }

}
