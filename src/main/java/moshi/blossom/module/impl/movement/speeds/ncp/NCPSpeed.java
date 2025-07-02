package moshi.blossom.module.impl.movement.speeds.ncp;

import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.module.impl.combat.KillAura;
import moshi.blossom.module.impl.movement.Speed;
import moshi.blossom.util.ChatUtil;
import moshi.blossom.util.player.MoveUtil;
import moshi.blossom.util.player.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class NCPSpeed extends ModuleMode {

    private int airTicks;

    private double lastMotion;

    private boolean nextGround;

    @EventLink(1)
    public final Listener<MotionEvent> handleMotion;

    @EventLink
    public final Listener<MoveEvent> handleMove;

    public NCPSpeed() {
        super("NCP");

        this.handleMotion = (event -> {
            if (event.isPost()) return;

            KillAura aura = (KillAura)ModManager.getMod("KillAura");

            boolean shouldFix = (!aura.isToggled() || aura.target == null ||
            getPlayer().getDistanceToEntity(aura.target) >= 3.25D) &&
            !ModManager.getMod("Scaffold").isToggled();

            if (shouldFix) {
                event.setYaw(MoveUtil.getDirection());

            }

        });

        this.handleMove = (event -> {
            EntityPlayerSP player = event.getPlayer();

            if (MoveUtil.isMoving()) {
                getPlayer().setSprinting(!ModManager.getMod("Scaffold").isToggled());

            }

            if (!ModManager.getMod("Timer").isToggled()) {
                TimerUtil.setTimer((float)((Speed)ModManager.getMod("Speed")).ncpBoost.getValue());

            }

            if (player.onGround) {
                handleGroundMovement(event);

            } else if (this.airTicks != -1) {
                handleAirMovement(player);

            }

            handleFinalMovement(event, player);

            this.lastMotion *= 0.981D;

        });

    }

    private void handleGroundMovement(MoveEvent event) {
        event.setY(0.41999998688697815D);

        this.lastMotion = 0.5885785173794227D;

        EntityPlayerSP player = event.getPlayer();

        if (player.isPotionActive(Potion.moveSpeed)) {
            this.lastMotion = 0.8277915521139273D;

            if (player.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 3) {
                ChatUtil.printDebug("gotta go fast");

                this.lastMotion = 1.0663077620450587D;

            }

        }

        this.airTicks = 0;

    }

    private void handleAirMovement(EntityPlayerSP player) {
        if (this.airTicks == 0) {
            this.lastMotion *= 0.6D;

        }

        if (player.hurtTime >= 9) {
            this.lastMotion = 0.5659408820955987D;

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

        double speedModifier = ModManager.getMod("Scaffold").isToggled() &&
        player.isPotionActive(Potion.moveSpeed) ?
        0.875D : 1.0D;

        MoveUtil.setSpeed(event, this.lastMotion * speedModifier,
        MoveUtil.getDirection(yaw, player.moveForward, player.moveStrafing));

    }

    public void onEnable() {
        super.onEnable();

        this.airTicks = -1;

        this.lastMotion = 0.271D;

        this.nextGround = false;

    }

    public void onDisable() {
        super.onDisable();

        TimerUtil.setTimer(1.0F);

    }

}
