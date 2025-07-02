package moshi.blossom.module.impl.movement;

import moshi.blossom.event.impl.player.FlyingEvent;
import moshi.blossom.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Sprint extends Module {

    @EventLink
    public Listener<FlyingEvent> handleFlying;

    public Sprint() {
        super("Sprint", "Sprint", Category.MOVEMENT);

        this.handleFlying = (event -> {
            EntityPlayerSP player = getPlayer();

            boolean canSprint = !player.isCollidedHorizontally &&
            !player.isUsingItem() &&
            !player.isSneaking() &&
            player.moveForward > 0.0F;

            if (canSprint) {
                player.setSprinting(true);

            }

        });

    }

}
