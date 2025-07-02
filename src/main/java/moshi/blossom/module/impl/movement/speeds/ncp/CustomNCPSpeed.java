package moshi.blossom.module.impl.movement.speeds.ncp;

import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.module.impl.combat.KillAura;
import moshi.blossom.option.impl.BoolOption;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.ChatUtil;
import moshi.blossom.util.player.MoveUtil;
import moshi.blossom.util.player.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class CustomNCPSpeed extends ModuleMode {

    // Configuration options
    public final BoolOption ncpcDirectionFix = new BoolOption("DIR_FIX", "Dir Fix", true);

    public final BoolOption ncpcMotionReset = new BoolOption("MOTION_RESET", "Motion Reset", true);

    public final NumberOption ncpcGroundBoost = new NumberOption("GROUND_BOOST", "Ground Boost", 1.02D, 1.0D, 1.5D, 0.001D);

    public final NumberOption ncpcEffectMult = new NumberOption("EFFECT_MULT", "Effect Mult", 1.7D, 1.0D, 2.5D, 0.01D);

    public final NumberOption ncpcGroundFriction = new NumberOption("GROUND_FRICTION", "Ground Friction", 0.6D, 0.5D, 0.8D, 0.001D);

    public final NumberOption ncpcScaffoldFactor = new NumberOption("SCAFFOLD_FACTOR", "Scaffold Factor", 0.78D, 0.7D, 1.0D, 0.01D);

    // Movement state variables
    private int airTicks;

    private double lastMotion;

    // Event listeners
    @EventLink(1)
    public final Listener<MotionEvent> handleMotion;

    @EventLink
    public final Listener<MoveEvent> handleMove;

    public CustomNCPSpeed() {
        super("NCP Custom");

        // Initialize movement state
        this.airTicks = 0;

        this.lastMotion = 0.0D;

        // Motion event handler
        this.handleMotion = (event -> {
            if (event.isPre() && this.ncpcDirectionFix.isEnabled()) {
                KillAura killAura = (KillAura)ModManager.getMod("KillAura");

                if (!killAura.isToggled() || killAura.target == null) {
                    event.setYaw(MoveUtil.getDirection());

                }

            }

        });

        // Move event handler
        this.handleMove = (event -> {
            EntityPlayerSP player = event.getPlayer();

            if (player.onGround) {
                handleGroundMovement(event, player);

            } else if (this.airTicks != -1) {
                handleAirMovement(event, player);

            }

            handleFinalMovement(event, player);

            this.lastMotion *= 0.98D;

        });

    }

    private void handleGroundMovement(MoveEvent event, EntityPlayerSP player) {
        event.setY(0.41999998688697815D);

        this.lastMotion = 0.5659408820955987D * this.ncpcGroundBoost.getValue();

        if (player.isPotionActive(Potion.moveSpeed)) {
            this.lastMotion *= this.ncpcEffectMult.getValue();

        }

        this.airTicks = 0;

        ChatUtil.printDebug("" + this.ncpcGroundBoost.getValue());

    }

    private void handleAirMovement(MoveEvent event, EntityPlayerSP player) {
        if (this.airTicks == 0) {
            this.lastMotion *= this.ncpcGroundFriction.getValue();

        }

        if (player.hurtTime >= 9 && this.ncpcMotionReset.isEnabled()) {
            this.lastMotion = player.isPotionActive(Potion.moveSpeed) ?
            0.6173360727629288D : 0.4403020062703758D;

        }

        this.airTicks++;

    }

    private void handleFinalMovement(MoveEvent event, EntityPlayerSP player) {
        float yaw = player.rotationYaw;

        KillAura killAura = (KillAura)ModManager.getMod("KillAura");

        if (killAura.isToggled() && killAura.target != null &&
        GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump)) {
            yaw = killAura.rotations[0];

        }

        double speed = this.lastMotion;

        if (ModManager.getMod("Scaffold").isToggled() && player.isPotionActive(Potion.moveSpeed)) {
            speed *= this.ncpcScaffoldFactor.getValue();

        }

        MoveUtil.setSpeed(event, speed, MoveUtil.getDirection(yaw, player.moveForward, player.moveStrafing));

    }

    public void onEnable() {
        super.onEnable();

        this.airTicks = -1;

        this.lastMotion = 0.271D;

    }

    public void onDisable() {
        super.onDisable();

        TimerUtil.setTimer(1.0F);

    }

}
