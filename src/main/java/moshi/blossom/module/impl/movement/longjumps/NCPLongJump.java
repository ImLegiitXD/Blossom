package moshi.blossom.module.impl.movement.longjumps;

import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.util.cpackets.CPacket;
import moshi.blossom.util.cpackets.impl.MoveOffsetPacket;
import moshi.blossom.util.network.PacketUtil;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class NCPLongJump extends ModuleMode {

    private int stage;

    private int ticks;

    private double motion;

    @EventLink
    public Listener<PacketEvent> handlePacket;

    @EventLink
    public Listener<MoveEvent> handleMove;

    public NCPLongJump() {
        super("NCP");

        this.handlePacket = (event -> {
            if (event.is(Packets.S_PLAYER_POS_LOOK) && this.stage == 1) {
                handlePositionPacket(event);

            }

        });

        this.handleMove = (event -> {
            switch (this.stage) {
                case 0:
                handleInitialStage(event);

                break;

                case 1:
                handlePositioningStage(event);

                break;

                case 2:
                handleJumpStage(event);

                break;

            }

        });

    }

    private void handlePositionPacket(PacketEvent event) {
        S08PacketPlayerPosLook posLook = (S08PacketPlayerPosLook)event.getPacket();

        PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(
        posLook.getX(),
        posLook.getY(),
        posLook.getZ(),
        posLook.getYaw(),
        posLook.getPitch(),
        false
        ));

        event.cancelEvent();

        this.stage = 2;

    }

    private void handleInitialStage(MoveEvent event) {
        event.setZeroXZ();

        PacketUtil.send(new MoveOffsetPacket(0.0D, -0.25D, 0.0D, false));

        this.stage++;

    }

    private void handlePositioningStage(MoveEvent event) {
        event.setZeroXZ();

    }

    private void handleJumpStage(MoveEvent event) {
        switch (this.ticks) {
            case 0:
            this.motion = 0.6D;

            event.setY(0.41999998688697815D);

            break;

            case 1:
            this.motion *= 1.65D;

            break;

            case 10:
            this.motion *= 0.0D;

            break;

            default:
            this.motion -= this.motion / 156.0D;

            break;

        }

        if (this.ticks > 4 && getPlayer().onGround) {
            ModManager.getMod("LongJump").toggle();

            this.motion = 0.0D;

        }

        event.setSpeed(this.motion);

        this.ticks++;

    }

    public void onEnable() {
        super.onEnable();

        this.stage = 0;

        this.ticks = 0;

        this.motion = 0.0D;

    }

    public void onDisable() {
        super.onDisable();

        getPlayer().motionX = getPlayer().motionZ = 0.0D;

    }

}
