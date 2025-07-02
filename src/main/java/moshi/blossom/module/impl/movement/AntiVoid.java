package moshi.blossom.module.impl.movement;

import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.util.MathUtil;
import moshi.blossom.util.network.Packets;
import moshi.blossom.util.player.PlayerUtil;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class AntiVoid extends Module {

final ModeOption modeOption = new ModeOption("MODE", "Mode", "Flag", new String[] { "Flag", "Jump" });

    public boolean nextGround;

    public int lagbackTicks;

    public static double lastGroundY;

    @EventLink
    public final Listener<MoveEvent> handleMove;

    @EventLink
    public final Listener<PacketEvent> handlePackeet;

    @EventLink
    public final Listener<MotionEvent> handleMotion;

    public AntiVoid() {
        super("AntiVoid", "Anti Void", Category.MOVEMENT);

        this.nextGround = false;

        this.lagbackTicks = 0;

        this.handleMove = (event -> {
            if (getPlayer().isSpectator()) {
                this.lagbackTicks = 0;

                this.nextGround = false;

                return;

            }

            this.lagbackTicks++;

            switch (this.modeOption.get()) {
                case "flag":
                if (this.lagbackTicks < 15) return;

                if (getPlayer().onGround) {
                    this.nextGround = false;

                    return;

                }

                if (event.getPlayer().fallDistance > 5.0F && PlayerUtil.isOverVoid()) {
                    this.nextGround = true;

                    event.setZero();

                }

                break;

                case "jump":
                if (this.lagbackTicks < 5 || getPlayer().ticksExisted < 20) return;

                if (getPlayer().onGround) {
                    lastGroundY = getPlayer().posY;

                }

                double delta = getPlayer().posY - lastGroundY;

                if (delta < -5.0D && PlayerUtil.isOverVoid()) {
                    event.setY(1.1D);

                    this.nextGround = true;

                }

                break;

            }

        });

        this.handlePackeet = (event -> {
            if (event.getPacket() instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
                this.lagbackTicks = 0;

            }

            if (this.modeOption.is("jump") && getPlayer() != null &&
        event.is(new Packets[] { Packets.S_EXPLOSION, Packets.S_ENTITY_VELOCITY }) &&
            getPlayer().offGroundTicks > 13 && PlayerUtil.isOverVoid()) {
                event.cancelEvent();

            }

        });

        this.handleMotion = (event -> {
            if (this.nextGround) {
                if (this.modeOption.is("jump")) {
                    event.setGround(true);

                    double mathGround = 0.015625D;

                    event.setY(Math.round(event.getY() / mathGround) * mathGround);

                } else {
                    event.setY(event.getY() + MathUtil.randomDouble(0.1D, 1.0D));

                }

                this.nextGround = false;

            }

        });

    setupOptions(new Option[] { this.modeOption });

    }

    public void onEnable() {
        super.onEnable();

        lastGroundY = getPlayer().posY;

        this.lagbackTicks = 0;

        this.nextGround = false;

    }

}
