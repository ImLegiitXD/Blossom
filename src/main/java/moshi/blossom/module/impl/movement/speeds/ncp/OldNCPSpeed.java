package moshi.blossom.module.impl.movement.speeds.ncp;

import java.util.ArrayList;
import java.util.List;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.event.impl.player.UpdateEvent;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.util.player.MoveUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class OldNCPSpeed extends ModuleMode {

    private int ticks;

    private int stage;

    private double motion;

    private final List<Packet<?>> packets;

    @EventLink
    public Listener<UpdateEvent> handleUpdate;

    @EventLink
    public Listener<MotionEvent> handleMotion;

    @EventLink
    public Listener<MoveEvent> handleMove;

    public OldNCPSpeed() {
        super("Old NCP");

        this.packets = new ArrayList<>();

    this.handleUpdate = (event -> {});

    this.handleMotion = (event -> {});

        this.handleMove = (event -> {
            EntityPlayerSP player = event.getPlayer();

            if (player.onGround) {
                this.motion = 0.6D;

                event.setY(0.41999998688697815D);

                this.ticks = 0;

                this.stage++;

            } else {
                if (this.ticks == 0) {
                    this.motion *= 0.6D * (1.0D + this.stage * 0.02D);

                    if (this.stage > 8) {
                        this.stage = 0;

                    }

                }

                if (event.getY() < 0.0D && event.getY() > -0.8D) {
                    event.setY(event.getY() * 1.05D);

                }

                this.motion *= 0.9800000190734863D;

                this.ticks++;

            }

            MoveUtil.setSpeed(event, this.motion);

        });

    }

    public void onEnable() {
        super.onEnable();

        this.stage = 0;

    }

    public void onDisable() {
        super.onDisable();

    }

}
