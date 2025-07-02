package moshi.blossom.module.impl.movement.flights;

import com.google.common.collect.Lists;
import moshi.blossom.event.Event;
import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.event.impl.player.PreJumpEvent;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.BoolOption;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.cpackets.CPacket;
import moshi.blossom.util.cpackets.impl.MoveOffsetPacket;
import moshi.blossom.util.network.PacketUtil;
import moshi.blossom.util.network.Packets;
import moshi.blossom.util.player.MoveUtil;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;
import java.util.List;

public class VulcanFlight extends ModuleMode {

    // Configuration options
    public final BoolOption damageOpt = new BoolOption("DAMAGE", "Damage", true);

    public final NumberOption flySpeed = new NumberOption("FLY_SPEED", "Fly Speed", 2.0D, 0.5D, 5.0D, 0.05D);

    public final NumberOption flyTicking = new NumberOption("TICKING", "Ticking", 4.0D, 2.0D, 10.0D, 1.0D);

    // Flight state
    private int ticks;

    private int stage;

    private double y;

    // Event listeners
    @EventLink
    public final Listener<MoveEvent> handleMove;

    @EventLink
    public final Listener<PacketEvent> handlePacket;

    @EventLink
    public final Listener<PreJumpEvent> handleJump;

    public VulcanFlight() {
        super("Vulcan");

        this.handleMove = (event -> {
            if (stage <= 2) {
                event.setZeroXZ();

            }

            switch (stage) {
                case 0:
                handleInitialDamage();

                break;

                case 1:
                handleDamageCheck();

                break;

                case 3:
                handleFlightMovement(event);

                break;

            }

        });

        this.handlePacket = (event -> {
            if (stage == 3 && event.is(Packets.C_PLAYER)) {
                handlePlayerPacket();

                if (ticks % flyTicking.getValue() != 0.0D) {
                    event.cancelEvent();

                }

            }

            if (event.is(Packets.S_ENTITY_VELOCITY, Packets.S_EXPLOSION)) {
                event.cancelEvent();

            }

            if (stage == 2 && event.is(Packets.S_PLAYER_POS_LOOK)) {
                ticks = 0;

                stage = 3;

            }

        });

        this.handleJump = Event::cancelEvent;

    }

    private void handleInitialDamage() {
        if (damageOpt.isEnabled()) {
            PacketUtil.send(new MoveOffsetPacket(0.0D, 3.2D, 0.0D, false));

            PacketUtil.send(new MoveOffsetPacket(0.0D, 0.0D, 0.0D, false));

            PacketUtil.send(new MoveOffsetPacket(0.0D, 0.0D, 0.0D, true));

        }

        stage++;

    }

    private void handleDamageCheck() {
        if (getPlayer().hurtTime > 0) {
            PacketUtil.send(new MoveOffsetPacket(0.0D, -0.5D, 0.0D, false));

            stage++;

        }

    }

    private void handleFlightMovement(MoveEvent event) {
        event.setY(0.0D);

        event.setSpeed(flySpeed.getValue());

        if (!MoveUtil.isMoving()) {
            event.setZeroXZ();

        }

        ticks++;

    }

    private void handlePlayerPacket() {
        if (getPlayer() != null) {
            getPlayer().posY = y + 0.175D;

        }

    }

    public List<Option> getOptions() {
        return Lists.newArrayList(damageOpt, flySpeed, flyTicking);

    }

    public void onEnable() {
        ticks = stage = 0;

        y = getPlayer().posY;

    }

    public void onDisable() {
        getPlayer().motionX = getPlayer().motionZ = 0.0D;

    }

}
