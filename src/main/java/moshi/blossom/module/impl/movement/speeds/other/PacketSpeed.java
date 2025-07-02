package moshi.blossom.module.impl.movement.speeds.other;

import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.util.network.PacketUtil;
import moshi.blossom.util.network.Packets;
import moshi.blossom.util.player.MoveUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class PacketSpeed extends ModuleMode {

    double mx;

    double mz;

    double lx;

    double ly;

    double lz;

    float lyaw;

    float lpitch;

    boolean sending;

    @EventLink
    public Listener<MoveEvent> handleMove;

    @EventLink
    public Listener<MotionEvent> handleMotion;

    @EventLink
    public Listener<PacketEvent> handleSend;

    public PacketSpeed() {
        super("Packet");

        this.handleMove = (event -> {
            if (MoveUtil.isMoving()) {
                if (getPlayer().onGround) {
                    double val = 0.2783D;

                    event.setSpeed(val * 5.0D);

                    this.mx = event.getX();

                    this.mz = event.getZ();

                } else if (getPlayer().offGroundTicks == 1) {
                    event.setSpeed(0.25D);

                }

            } else {
                event.setSpeed(0.0D);

            }

        });

        this.handleMotion = (event -> {
            if (!getPlayer().onGround || event.isPost()) return;

            int packets = 20;

            double dx = getPlayer().posX - getPlayer().lastTickPosX;

            double dz = getPlayer().posZ - getPlayer().lastTickPosZ;

            this.sending = true;

            for (float i = 0.0F; i <= packets; i++) {
                double xPart = dx / packets * i;

                double zPart = dz / packets * i;

                PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(
                this.lx + xPart,
                this.ly,
                this.lz + zPart,
                this.lyaw,
                this.lpitch,
                event.isGround()
                ));

            }

            this.sending = false;

        });

        this.handleSend = (event -> {
            if (!event.is(Packets.C_PLAYER) || this.sending) return;

            C03PacketPlayer player = (C03PacketPlayer)event.getPacket();

            if (player.isMoving()) {
                this.lx = player.getPositionX();

                this.ly = player.getPositionY();

                this.lz = player.getPositionZ();

            }

            if (player.getRotating()) {
                this.lyaw = player.getYaw();

                this.lpitch = player.getPitch();

            }

        });

    }

    public void onEnable() {
        super.onEnable();

        this.lx = getPlayer().posX;

        this.ly = getPlayer().posY;

        this.lz = getPlayer().posZ;

        this.lyaw = getPlayer().rotationYaw;

        this.lpitch = getPlayer().rotationPitch;

        getPlayer().motionX = 0.0D;

        getPlayer().motionZ = 0.0D;

        this.sending = false;

    }

    public void onDisable() {
        super.onDisable();

        getPlayer().motionX = 0.0D;

        getPlayer().motionZ = 0.0D;

    }

}
