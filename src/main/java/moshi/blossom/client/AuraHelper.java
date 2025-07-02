package moshi.blossom.client;

import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.impl.combat.KillAura;
import moshi.blossom.util.ChatUtil;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class AuraHelper

{
    public static boolean nextUnblock;

    private boolean sending;

    @EventLink
    public final Listener<TickEvent> onTick;

    @EventLink
    public final Listener<PacketEvent> onPacket;

    public AuraHelper() {
        this.onTick = (event -> {
            if (nextUnblock) {
                this.sending = true;

                aura().packetUnblock();

                ChatUtil.printInfo("unblocked");

                nextUnblock = false;

            }

            this.sending = false;

        });

        this.onPacket = (event -> {
            if (nextUnblock && !this.sending && (event.getPacket() instanceof net.minecraft.network.play.client.C07PacketPlayerDigging || event.getPacket() instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement)) {
                event.cancelEvent();

            }

        });

    }

    public static void unblock() {
        nextUnblock = true;

    }

    private KillAura aura() {
        return (KillAura)ModManager.getMod("KillAura");

    }

}
