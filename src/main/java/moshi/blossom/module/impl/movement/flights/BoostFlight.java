package moshi.blossom.module.impl.movement.flights;

import com.google.common.collect.Lists;
import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.event.impl.player.PreJumpEvent;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.ChatUtil;
import moshi.blossom.util.Clock;
import moshi.blossom.util.network.PacketUtil;
import moshi.blossom.util.network.Packets;
import moshi.blossom.util.player.TimerUtil;
import net.minecraft.network.Packet;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;
import java.util.ArrayList;
import java.util.List;

public class BoostFlight extends ModuleMode {

    // Configuration options
    public final ModeOption damageMode = new ModeOption("DAMAGE_MODE", "Damage", "Spoof",
new String[]{"Spoof", "Fast", "Wait", "None"});

    public final NumberOption boostSpeed = new NumberOption("BOOST_SPEED", "Boost Speed",
    0.525D, 0.3D, 3.0D, 0.001D);

    public final NumberOption boostFriction = new NumberOption("BOOST_FRICTION", "Boost Friction",
    6.0D, 0.6D, 10.0D, 0.01D);

    public final ModeOption boostMode = new ModeOption("BOOST_MODE", "Boost", "Verus",
new String[]{"Verus", "Verus 2", "Verus 3", "NCP", "Static"});

    public final NumberOption timerTicks = new NumberOption("TIMER_TICKS", "Timer Ticks",
    35.0D, 0.0D, 80.0D, 1.0D);

    public final NumberOption timerSpeed = new NumberOption("TIMER_SPEED", "Timer Speed",
    0.6D, 0.1D, 5.0D, 0.05D);

    public final ModeOption blinkMode = new ModeOption("BLINK_MODE", "Blink", "Pulse",
new String[]{"Pulse", "None", "Always"});

    public final NumberOption pulseDelay = new NumberOption("PULSE_DELAY", "Pulse Delay",
    300.0D, 50.0D, 1000.0D, 25.0D);

    public final NumberOption maxPulsesOpt = new NumberOption("MAX_PULSES", "Max Pulses",
    2.0D, 0.0D, 20.0D, 1.0D);

    // Flight state
    private final List<Packet<?>> packets = new ArrayList<>();

    private final Clock pulseClock = new Clock();

    private int ticks;

    private int hops;

    private int stage;

    private int pulses;

    private double motion;

    private double y;

    // Event listeners
    @EventLink
    public Listener<MoveEvent> handleMove;

    @EventLink
    public Listener<MotionEvent> handleMotion;

    @EventLink
    public Listener<PacketEvent> handlePacket;

    @EventLink
    public Listener<PreJumpEvent> handleJump;

    public BoostFlight() {
        super("Boost");

        this.handleMove = event -> {
            if (stage <= 1) {
                event.setZeroXZ();

            }

            switch (stage) {
                case 0:
                handleDamageStage(event);

                break;

                case 1:
                handleWaitStage();

                break;

                case 2:
                handleFlightStage(event);

                break;

            }

        };

        this.handleMotion = event -> {
            if (event.isPre() && damageMode.is("spoof", "fast") && stage == 0) {
                event.setGround(false);

            }

        };

        this.handlePacket = event -> {
            if (shouldHandlePacket(event)) {
                handlePacketEvent(event);

            }

        };

        this.handleJump = event -> event.setCanceled(true);

    }

    private void handleDamageStage(MoveEvent event) {
        switch (damageMode.get()) {
            case "spoof":
            if (event.getPlayer().onGround && hops >= 3) {
                stage++;

            } else if (event.getPlayer().onGround) {
                event.setY(0.41999998688697815D);

                hops++;

            }

            break;

            case "fast":
            if (event.getPlayer().onGround && hops >= 3) {
                stage++;

            } else if (event.getPlayer().onGround) {
                TimerUtil.setTimer(2.0F);

                event.setY(0.41999998688697815D);

                hops++;

            }

            break;

        }

    }

    private void handleWaitStage() {
        ticks++;

        if (getPlayer().hurtTime > 0) {
            TimerUtil.setTimer(1.0F);

            pulseClock.reset();

            ticks = 0;

            stage++;

        }

    }

    private void handleFlightStage(MoveEvent event) {
        switch (ticks) {
            case 0:
            motion = boostSpeed.getValue();

            event.setY(0.41999998688697815D);

            break;

            case 1:
            motion *= boostFriction.getValue();

            break;

            default:
            handleBoostMode();

            event.setY(0.0D);

            break;

        }

        TimerUtil.setTimer(ticks < timerTicks.getValue() ? (float)timerSpeed.getValue() : 1.0F);

        event.setSpeed(motion);

        ticks++;

    }

    private void handleBoostMode() {
        switch (boostMode.get()) {
            case "verus":
            if (ticks == 20) motion *= 0.5D;

            motion -= motion / 156.0D;

            break;

            case "verus 2":
            if (ticks < 15) {
                motion -= motion / 156.0D;

            } else if (ticks == 15) {
                motion *= 0.7D;

            } else {
                motion -= motion / 85.0D;

            }

            break;

            case "verus 3":
            if (ticks == 12) motion *= 0.3D;

            motion -= motion / 156.0D;

            break;

            case "ncp":
            motion -= motion / 156.0D;

            break;

        }

    }

    private boolean shouldHandlePacket(PacketEvent event) {
        return event.getDir() == PacketEvent.Dir.SEND &&
        getPlayer() != null &&
        mc.theWorld != null &&
        (stage == 2 || (stage == 1 && ticks >= 2)) &&
        !blinkMode.is("none");

    }

    private void handlePacketEvent(PacketEvent event) {
        packets.add(event.getPacket());

        event.cancelEvent();

        if (event.is(Packets.C_PLAYER) &&
        stage == 2 &&
        blinkMode.is("pulse") &&
        pulseClock.elapsed((long)pulseDelay.getValue()) &&
        (pulses < maxPulsesOpt.getValue() || maxPulsesOpt.getValue() == 0.0D)) {
            resend();

            pulses++;

            pulseClock.reset();

        }

    }

    private void resend() {
        ChatUtil.printDebug("sent");

        if (!mc.isIntegratedServerRunning()) {
            packets.forEach(PacketUtil::sendSilent);

            packets.clear();

        }

    }

    public List<Option> getOptions() {
        return Lists.newArrayList(
        damageMode, boostSpeed, boostFriction, boostMode,
        timerTicks, timerSpeed, blinkMode, pulseDelay, maxPulsesOpt
        );

    }

    public void onEnable() {
        packets.clear();

        pulseClock.reset();

        ticks = hops = stage = pulses = 0;

        if (damageMode.is("wait")) stage = 1;

        if (damageMode.is("none")) stage = 2;

        y = getPlayer().posY;

    }

    public void onDisable() {
        TimerUtil.setTimer(1.0F);

        getPlayer().motionZ = getPlayer().motionX = 0.0D;

        resend();

    }

}
