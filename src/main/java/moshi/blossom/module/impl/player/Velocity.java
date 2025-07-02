package moshi.blossom.module.impl.player;

import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ContOption;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.player.MoveUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Velocity extends Module {

    public ContOption regularCont = new ContOption("REGULAR_CONT", "Regular");

    public NumberOption hVal = new NumberOption("H_VAL", "H-Value", 0.0D, 0.0D, 100.0D, 1.0D);

    public NumberOption vVal = new NumberOption("V_VAL", "V-Value", 0.0D, 0.0D, 100.0D, 1.0D);

    public final ModeOption modeOption;

    int ticksSinceVelo;

    @EventLink
    public final Listener<MotionEvent> handleMotion;

    @EventLink
    public final Listener<PacketEvent> handlePacket;

    public Velocity() {
        super("Velocity", "Velocity", Category.PLAYER);

        this.modeOption = new ModeOption("MODE", "Mode", "Regular",
    new String[] { "Regular", "Packet", "Hypixel", "Conditional", "Intave", "Sparky" });

        this.ticksSinceVelo = 0;

        this.handleMotion = (event -> {
            switch (this.modeOption.get()) {
                case "sparky":
                if (this.ticksSinceVelo == -1) {
                    return;

                }

                if (this.ticksSinceVelo == 3) {
                    MoveUtil.setSpeed(0.0D);

                }

                this.ticksSinceVelo++;

                break;

            }

        });

        this.handlePacket = (event -> {
            switch (this.modeOption.get()) {
                case "regular":
                double hfactor = this.hVal.getValue() / 100.0D;

                double vfactor = this.vVal.getValue() / 100.0D;

                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity velocity = (S12PacketEntityVelocity)event.getPacket();

                    if (velocity.getEntityID() == getPlayer().getEntityId()) {
                        event.setCanceled(true);

                        double x = velocity.getMotionX() / 8000.0D;

                        double y = velocity.getMotionY() / 8000.0D;

                        double z = velocity.getMotionZ() / 8000.0D;

                        getPlayer().setVelocity(x * hfactor, y * vfactor, z * hfactor);

                    }

                }

                if (event.getPacket() instanceof S27PacketExplosion) {
                    S27PacketExplosion explosion = (S27PacketExplosion)event.getPacket();

                    event.setCanceled(true);

                    getPlayer().motionX += explosion.getX() * hfactor;

                    getPlayer().motionZ += explosion.getZ() * hfactor;

                    getPlayer().motionY += explosion.getY() * vfactor;

                }

                break;

                case "hypixel":
                if (event.getPacket() instanceof S12PacketEntityVelocity)
                event.setCanceled(((S12PacketEntityVelocity)event.getPacket()).getEntityID() == getPlayer().getEntityId());

                if (event.getPacket() instanceof S27PacketExplosion)
                event.setCanceled(true);

                break;

                case "packet":
                if (event.getPacket() instanceof S12PacketEntityVelocity)
                event.setCanceled(((S12PacketEntityVelocity)event.getPacket()).getEntityID() == getPlayer().getEntityId());

                break;

                case "sparky":
                if (event.getPacket() instanceof S12PacketEntityVelocity &&
                ((S12PacketEntityVelocity)event.getPacket()).getEntityID() == getPlayer().getEntityId())
                this.ticksSinceVelo = 0;

                break;

                case "conditional":
                if (event.getPacket() instanceof S12PacketEntityVelocity &&
                ((S12PacketEntityVelocity)event.getPacket()).getEntityID() == getPlayer().getEntityId()) {
                    event.cancelEvent();

                    double y = ((S12PacketEntityVelocity)event.getPacket()).getMotionY() / 8000.0D;

                    if (y >= 0.0D)
                    getPlayer().motionY = y;

                }

                break;

            }

        });

    setupOptions(new Option[] { this.modeOption, this.regularCont });

    this.regularCont.setupOptions(new Option[] { this.hVal, this.vVal });

    }

    public String getSuffix() {
        if (this.modeOption.is("regular"))
        return this.hVal.getValue() + "% " + this.vVal.getValue() + "%";

        return this.modeOption.getMode();

    }

    public void onEnable() {
        super.onEnable();

        this.ticksSinceVelo = -1;

    }

}
