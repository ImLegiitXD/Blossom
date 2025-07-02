package moshi.blossom.module.impl.movement;

import moshi.blossom.event.Event;
import moshi.blossom.event.impl.player.EntityActionEvent;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.SlowDownEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.module.impl.combat.KillAura;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.util.network.PacketUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class NoSlow extends Module {

    final ModeOption modeOption = new ModeOption("MODE", "Mode", "Vanilla",
new String[] { "Vanilla", "NCP", "NCP 2" });

    @EventLink
    public Listener<EntityActionEvent> handleEntity;

    @EventLink
    public Listener<MotionEvent> handleMotion;

    @EventLink
    public Listener<SlowDownEvent> handleSlowDown;

    public NoSlow() {
        super("NoSlow", "No Slow", Category.MOVEMENT);

        this.handleEntity = (event -> {
            if (shouldNoSlow() && !isSword() && this.modeOption.is("ncp 2")) {
                event.setSneaking(true);

            }

        });

        this.handleMotion = (event -> {
            if (this.modeOption.is("ncp") && shouldNoSlow() && isSword()) {
                if (event.isPre()) {
                    packetUnblock();

                } else {
                    packetBlock();

                }

            }

        });

        this.handleSlowDown = Event::cancelEvent;

    setupOptions(new Option[] { this.modeOption });

    }

    private boolean shouldNoSlow() {
        return (getPlayer().isUsingItem() &&
        !ModManager.getMod("Scaffold").isToggled() &&
        (!ModManager.getMod("KillAura").isToggled() ||
        ((KillAura)ModManager.getMod("KillAura")).target == null));

    }

    public String getSuffix() {
        return this.modeOption.getMode();

    }

    private boolean isSword() {
        return (getPlayer().getHeldItem() != null &&
        getPlayer().getHeldItem().getItem() instanceof net.minecraft.item.ItemSword);

    }

    public void packetBlock() {
        PacketUtil.send(new C08PacketPlayerBlockPlacement(getPlayer().getHeldItem()));

    }

    public void packetUnblock() {
        PacketUtil.send(new C07PacketPlayerDigging(
        C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
        BlockPos.ORIGIN,
        EnumFacing.DOWN
        ));

    }

}
