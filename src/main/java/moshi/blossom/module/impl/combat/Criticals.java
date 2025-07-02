package moshi.blossom.module.impl.combat;

import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.AttackEvent;
import moshi.blossom.module.Module;
import moshi.blossom.util.ChatUtil;
import moshi.blossom.util.cpackets.CPacket;
import moshi.blossom.util.cpackets.impl.MoveOffsetPacket;
import moshi.blossom.util.network.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Criticals extends Module {

    private int delay;

    private double lastX;

    private double lastY;

    private double lastZ;

    private float lastYaw;

    private float lastPitch;

    @EventLink
    public Listener<AttackEvent> handleAttack;

    @EventLink
    public Listener<PacketEvent> handlePacket;

    public Criticals() {
        super("Criticals", "Criticals", Category.COMBAT);

        initializeEventHandlers();

    }

    private void initializeEventHandlers() {
        this.handleAttack = event -> {
            if (event.isPost()) return;

            if (!canPerformCritical()) return;

            ChatUtil.printDebug("crit");

            sendCriticalPackets();

            this.delay = 0;

        };

        this.handlePacket = event -> {
            if (!(event.getPacket() instanceof C03PacketPlayer)) return;

            C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();

            updatePositionData(packet);

            updateRotationData(packet);

            this.delay++;

        };

    }

    private boolean canPerformCritical() {
        return getPlayer().onGround &&
        !getPlayer().isInWater() &&
        this.delay >= 10;

    }

    private void sendCriticalPackets() {
        PacketUtil.send(new CPacket[] {
            new MoveOffsetPacket(0.0D, 0.03125D, 0.0D, true),
            new MoveOffsetPacket(0.0D, 0.03125D, 0.0D, false),
            new MoveOffsetPacket(0.0D, 0.011025D, 0.0D, false)
        });

    }

    private void updatePositionData(C03PacketPlayer packet) {
        if (packet.isMoving()) {
            this.lastX = packet.getPositionX();

            this.lastY = packet.getPositionY();

            this.lastZ = packet.getPositionZ();

        }

    }

    private void updateRotationData(C03PacketPlayer packet) {
        if (packet.getRotating()) {
            this.lastYaw = packet.getYaw();

            this.lastPitch = packet.getPitch();

        }

    }

    @Override
    public String getSuffix() {
        return "Packet";

    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.delay = 0;

    }

}
