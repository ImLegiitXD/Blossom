package moshi.blossom.module.impl.movement;

import java.util.ArrayList;
import java.util.List;
import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.FlyingEvent;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.BoolOption;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.Clock;
import moshi.blossom.util.network.PacketUtil;
import moshi.blossom.util.network.Packets;
import moshi.blossom.util.player.TimerUtil;
import net.minecraft.network.Packet;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Timer extends Module {

    final NumberOption timerOption = new NumberOption("SPEED", "Speed", 1.5D, 1.0D, 10.0D, 0.05D);

    final BoolOption blinkOption = new BoolOption("BLINK_ENABLED", "Blink", false);

    final NumberOption blinkPulseOption = new NumberOption("BLINK_PULSE", "Pulse", 350.0D, 0.0D, 2000.0D, 50.0D);

    final Clock clock = new Clock();

    final List<Packet<?>> packets = new ArrayList<>();

    @EventLink
    public Listener<FlyingEvent> handleFlying;

    @EventLink(0)
    public Listener<PacketEvent> handleSend;

    public Timer() {
        super("Timer", "Timer", Category.MOVEMENT);

        this.handleFlying = (event -> {
            TimerUtil.setTimer((float)this.timerOption.getValue());

        });

        this.handleSend = (event -> {
            if (!shouldProcessPacket(event)) return;

            if (this.clock.elapsed((long)this.blinkPulseOption.getValue())) {
                resend();

                this.clock.reset();

            } else {
                this.packets.add(event.getPacket());

                event.cancelEvent();

            }

        });

    setupOptions(new Option[] { this.timerOption, this.blinkOption, this.blinkPulseOption });

    }

    private boolean shouldProcessPacket(PacketEvent event) {
        if (event.isCanceled() || !this.blinkOption.isEnabled()) return false;

        Packets type = event.getPacket().pType();

        return type == Packets.C_PLAYER ||
        type == Packets.C_USE_ENTITY ||
        type == Packets.C_ANIMATION ||
        type == Packets.C_PLAYER_BLOCK_PLACEMENT ||
        type == Packets.C_PLAYER_DIGGING;

    }

    private void resend() {
        for (Packet<?> packet : this.packets) {
            PacketUtil.sendSilent(packet);

        }

        this.packets.clear();

    }

    public void onEnable() {
        super.onEnable();

        this.clock.reset();

    }

    public void onDisable() {
        super.onDisable();

        TimerUtil.setTimer(1.0F);

        if (this.blinkOption.isEnabled()) {
            resend();

        }

    }

}
