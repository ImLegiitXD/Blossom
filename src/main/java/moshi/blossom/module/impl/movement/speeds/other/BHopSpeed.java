package moshi.blossom.module.impl.movement.speeds.other;

import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.AttackEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.module.impl.combat.KillAura;
import moshi.blossom.util.player.MoveUtil;
import moshi.blossom.util.network.Packets;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class BHopSpeed extends ModuleMode {

    private int airTicks;

    private int hops;

    private int veloTicks;

    private double motion;

    private double veloMotion;

    private float lastDir;

    @EventLink
    public final Listener<MoveEvent> handleMove;

    @EventLink(0)
    public final Listener<MotionEvent> handleMotion;

    @EventLink
    public final Listener<AttackEvent> handleAttack;

    @EventLink
    public final Listener<PacketEvent> handlePacket;

    public BHopSpeed() {
        super("BHop");

        this.handleMove = (event -> {
            if (this.veloTicks != -1) this.veloTicks++;

            double base = getMotion();

            if (!MoveUtil.isMoving()) {
                MoveUtil.setSpeed(0.0D);

                this.motion = base;

                return;

            }

            getPlayer().setSprinting(true);

            if (this.motion == 0.0D) this.motion = base;

            if (event.getPlayer().onGround) {
                event.setY(jumpY());

                getPlayer().triggerAchievement(StatList.jumpStat);

                this.motion = base * 2.0D;

                this.airTicks = 0;

                this.hops++;

            } else {
                if (event.getPlayer().isCollidedHorizontally) {
                    this.motion = Math.max(0.2D, this.motion * 0.8D);

                }

                if (this.airTicks == 0) {
                    this.motion *= 0.6D;

                } else {
                    this.motion -= this.motion / 156.0D;

                }

                this.airTicks++;

            }

            float dirf = 1.0F - Math.abs(MoveUtil.getDirection() - this.lastDir) / 360.0F;

            if (dirf <= 0.5D) dirf = 1.0F - dirf;

            this.motion *= getPlayer().onGround ? 1.0D : Math.min(dirf * 1.1D, 1.0D);

            if (this.veloTicks == 1) {
                this.motion = handleVelo(this.veloMotion);

            }

            if (this.veloTicks == -1 || this.veloTicks > 2) {
                event.setSpeed(this.motion * modifier());

            }

            this.lastDir = MoveUtil.getDirection();

        });

        this.handleMotion = (event -> {
            if (event.isPost()) return;

            if (shouldFix()) {
                event.setYaw(MoveUtil.getDirection());

            }

        });

        this.handleAttack = (event -> {
            // Attack event handling can be added here
        });

        this.handlePacket = (event -> {
        if (event.is(new Packets[] { Packets.S_RESPAWN, Packets.S_PLAYER_POS_LOOK })) {
            this.motion = 1.5E-5D;

        }

        if (!event.is(Packets.S_ENTITY_VELOCITY)) return;

        S12PacketEntityVelocity velo = (S12PacketEntityVelocity)event.getPacket();

        if (velo.getEntityID() != getPlayer().getEntityId()) return;

        double motionX = velo.getMotionX() / 8000.0D;

        double motionZ = velo.getMotionZ() / 8000.0D;

        this.veloMotion = Math.hypot(motionX, motionZ);

        if (this.veloMotion > 0.1D) {
            this.veloTicks = 0;

        } else {
            event.cancelEvent();

        }

    });

}

public void onEnable() {
    super.onEnable();

    this.veloTicks = -1;

    this.motion = 0.0D;

    this.hops = 0;

    this.veloMotion = 0.0D;

    this.airTicks = 0;

    this.lastDir = MoveUtil.getDirection();

}

public void onDisable() {
    super.onDisable();

    MoveUtil.setSpeed(MoveUtil.getSpeed() * 0.75D);

}

private double modifier() {
    return ModManager.getMod("Scaffold").isToggled() ? 0.99D : 1.0D;

}

private double handleVelo(double motion) {
    if (motion > 0.35D) motion *= 0.7D;

    return Math.max(motion, this.motion);

}

private boolean shouldFix() {
    KillAura aura = (KillAura)ModManager.getMod("KillAura");

    return (MoveUtil.isMoving() &&
    (!aura.isToggled() || aura.target == null ||
    getPlayer().getDistanceToEntity(aura.target) >= 3.5D) &&
    !ModManager.getMod("Scaffold").isToggled());

}

private double jumpY() {
    double y = 0.41999998688697815D;

    if (getPlayer().isPotionActive(Potion.jump)) {
        y += ((getPlayer().getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);

    }

    return y;

}

private double getMotion() {
    double motion = (this.hops < 1) ? 0.2783D : 0.2898D;

    if (getPlayer().isPotionActive(Potion.moveSpeed)) {
        motion *= 1.0D + (getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) * 0.15D;

    }

    return motion;

}

}
