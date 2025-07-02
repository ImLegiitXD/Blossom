package moshi.blossom.module.impl.combat;

import com.google.common.base.Predicates;
import de.florianmichael.viamcp.fixes.AttackOrder;
import moshi.blossom.Blossom;
import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.*;
import moshi.blossom.event.impl.render.DrawEvent;
import moshi.blossom.event.impl.render.RenderEvent;
import moshi.blossom.event.impl.render.RenderItemEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.module.impl.render.HUD;
import moshi.blossom.option.*;
import moshi.blossom.option.impl.*;
import moshi.blossom.util.*;
import moshi.blossom.util.network.PacketUtil;
import moshi.blossom.util.render.RenderUtil;
import moshi.skidded.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.world.WorldSettings;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import moshi.blossom.client.AuraHelper;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class KillAura extends Module {

    // Configuration options
    public final ModeOption modeOption = new ModeOption("MODE", "Mode", "Single",
new String[]{"Single", "Switch", "Multi"});

    public final ContOption targetCont = new ContOption("TARGET_CONT", "Target");

    public final ModeOption teamsMode = new ModeOption("TEAMS_MODE", "Teams", "Disabled",
new String[]{"Disabled", "Color"});

    public final ModeOption antiBotMode = new ModeOption("ANTIBOT_MODE", "Anti Bot", "Disabled",
new String[]{"Disabled", "Basic"});

    public final BoolOption playersEnabled = new BoolOption("TARGET_PLAYERS", "Players", true);

    public final BoolOption mobsEnabled = new BoolOption("TARGET_MOBS", "Mobs", true);

    public final BoolOption villagersEnabled = new BoolOption("TARGET_VILLAGERS", "Villagers", false);

    public final BoolOption animalsEnabled = new BoolOption("TARGET_ANIMALS", "Animals", false);

    public final ContOption speedCont = new ContOption("SPEED_CONT", "Speed");

    public final NumberOption hitDelayVal = new NumberOption("HIT_DELAY", "Hit Delay", 2.0D, 0.0D, 10.0D, 1.0D);

    public final NumberOption apsValue = new NumberOption("ATTACK_SPEED", "Speed", 15.0D, 1.0D, 20.0D, 0.1D);

    public final NumberOption apsRandomness = new NumberOption("ATTACK_RAND", "Randomness", 5.0D, 0.0D, 10.0D, 0.05D);

    public final ContOption rangeCont = new ContOption("RANGE_CONT", "Range");

    public final NumberOption attackRange = new NumberOption("ATTACK_RANGE", "Attack", 3.0D, 3.0D, 6.0D, 0.01D);

    public final NumberOption targetRange = new NumberOption("TARGET_RANGE", "Target", 3.75D, 3.0D, 8.0D, 0.01D);

    public final NumberOption blockRange = new NumberOption("BLOCK_RANGE", "Block", 3.75D, 3.0D, 8.0D, 0.01D);

    public final ContOption rotationCont = new ContOption("ROTATION_CONT", "Rotations");

    public final BoolOption moveFixEnabled = new BoolOption("MOVE_FIX", "Move Fix", false);

    public final ModeOption rotationMode = new ModeOption("ROTATION_MODE", "Mode", "Center",
new String[]{"Center", "Head", "Head 2", "Head 3", "Down", "Disabled"});

    public final ModeOption rotationSpeedMode = new ModeOption("ROTATION_SPEED_MODE", "Speed", "Bypass",
new String[]{"Bypass", "Smooth", "Smooth 2", "Instant", "Tick", "Flick"});

    public final NumberOption randomnessValue = new NumberOption("ROTATION_RANDOMNESS", "Randomness", 0.0D, 0.0D, 25.0D, 0.05D);

    public final ContOption disableOnCont = new ContOption("DISABLE_ON_CONT", "Disable On");

    public final BoolOption deathDisable = new BoolOption("DEATH_DISABLE", "Death", true);

    public final BoolOption lagBackDisable = new BoolOption("LAGBACK_DISABLE", "Lag-Back", true);

    public final NumberOption lagBackMaxDistance = new NumberOption("LAGBACK_DISTANCE", "Distance", 10.0D, 0.0D, 50.0D, 1.0D,
    this.lagBackDisable::isEnabled);

    public final ContOption renderCont = new ContOption("RENDER_CONT", "Render");

    public final ModeOption renderRangeMode = new ModeOption("RENDER_RANGE_MODE", "Range Mode", "Attack",
new String[]{"Attack", "Target", "None"});

    public final NumberOption renderAlpha = new NumberOption("RENDER_ALPHA", "Alpha", 155.0D, 30.0D, 255.0D, 1.0D);

    public final ContOption slyAuraCont = new ContOption("SLY_AURA_CONT", "Sly");

    public final ModeOption slyMode = new ModeOption("SLY_MODE", "Mode", "None",
new String[]{"None", "Hurt Tick", "Hurt Full"});

    public final NumberOption slyRange = new NumberOption("SLY_RANGE", "Range", 2.5D, 1.0D, 4.0D, 0.1D);

    public final BoolOption slyVoidCheck = new BoolOption("SLY_VOID_CHECK", "Void Check", true);

    public final ModeOption priorityMode = new ModeOption("PRIORITY_MODE", "Priority", "Distance",
new String[]{"Distance", "Health"});

    public final ModeOption autoBlockMode = new ModeOption("AUTOBLOCK_MODE", "Auto Block", "Fake",
new String[]{"Fake", "NCP", "Tick", "C08 Spam", "Vanilla", "Reverse"});

    public final ModeOption raycastMode = new ModeOption("RAYCAST_MODE", "Ray Cast", "Equal",
new String[]{"Equal", "Legit", "Disabled"});

    public final ModeOption targetHUDMode = new ModeOption("TARGETHUD_MODE", "Target HUD", "Blossom",
new String[]{"Blossom", "Astolfo"});

    public final ModeOption swingMode = new ModeOption("SWING_MODE", "Swing", "Client",
new String[]{"Client", "Server", "Sword", "Disabled"});

    public final BoolOption targetSwingOpt = new BoolOption("TARGET_SWING", "Target Swing", true);

    public final NumberOption switchDelayVal = new NumberOption("SWITCH_DELAY", "Switch Delay", 5.0D, 3.0D, 40.0D, 1.0D,
    () -> this.modeOption.is("switch"));

    public final NumberOption maxTargetsVal = new NumberOption("MAX_TARGETS", "Max Targets", 5.0D, 2.0D, 20.0D, 1.0D,
    () -> this.modeOption.is("switch", "multi"));

    public final BoolOption keepSprintOpt = new BoolOption("KEEP_SPRINT", "Keep Sprint", true);

    public final NumberOption ticksExistedVal = new NumberOption("TICKS_EXISTED", "Ticks Existed", 15.0D, 0.0D, 50.0D, 1.0D);

    public final NumberOption particlesVal = new NumberOption("PARTICLES", "Particles", 1.0D, 0.0D, 5.0D, 1.0D);

    public final BoolOption noScaffold = new BoolOption("NO_SCAFFOLD", "No Scaffold", true);

    public final BoolOption noInvOpt = new BoolOption("NO_INV", "No Inv", true);

    public final BoolOption noUseOpt = new BoolOption("NO_USE", "No Use", true);

    // State variables
    public Entity target;

    public Entity rayCasted;

    public int targetIndex;

    public boolean blocking;

    public float[] rotations;

    public boolean attacking;

    public final AttackPattern pattern = new AttackPattern();

    public int hitDelay;

    public int attackTicks;

    private float anim;

    private float smoothHealth;

    // Event listeners
    @EventLink public Listener<FlyingEvent> handleFlying;

    @EventLink public Listener<JumpEvent> handleJump;

    @EventLink public Listener<MotionEvent> handleMotion;

    @EventLink public Listener<TickEvent> handleTick;

    @EventLink public Listener<RenderItemEvent> handleItem;

    @EventLink public Listener<RenderEvent> handleRender;

    @EventLink public Listener<PacketEvent> handlePacket;

    @EventLink public Listener<DrawEvent> handleDraw;

    public KillAura() {
        super("KillAura", "Kill Aura", Module.Category.COMBAT);

        setupOptions();

        initializeEventHandlers();

    }

    private void setupOptions() {
        setupOptions(new Option[]{
            modeOption, targetCont, speedCont, rangeCont, rotationCont, disableOnCont,
            renderCont, slyAuraCont, priorityMode, autoBlockMode, raycastMode, targetHUDMode,
            swingMode, targetSwingOpt, switchDelayVal, maxTargetsVal, keepSprintOpt,
            ticksExistedVal, particlesVal, noScaffold, noInvOpt, noUseOpt
        });

        targetCont.setupOptions(new Option[]{
            teamsMode, antiBotMode, playersEnabled, mobsEnabled, villagersEnabled, animalsEnabled
        });

        speedCont.setupOptions(new Option[]{
            hitDelayVal, apsValue, apsRandomness
        });

        rangeCont.setupOptions(new Option[]{
            attackRange, targetRange, blockRange
        });

        rotationCont.setupOptions(new Option[]{
            moveFixEnabled, rotationMode, rotationSpeedMode, randomnessValue
        });

        disableOnCont.setupOptions(new Option[]{
            deathDisable, lagBackDisable, lagBackMaxDistance
        });

        renderCont.setupOptions(new Option[]{
            renderRangeMode, renderAlpha
        });

        slyAuraCont.setupOptions(new Option[]{
            slyMode, slyRange, slyVoidCheck
        });

    }

    private void initializeEventHandlers() {
        handleFlying = event -> handleFlyingEvent(event);

        handleJump = event -> handleJumpEvent(event);

        handleMotion = event -> handleMotionEvent(event);

        handleTick = event -> handleTickEvent(event);

        handleItem = event -> handleItemEvent(event);

        handleRender = event -> handleRenderEvent(event);

        handlePacket = event -> handlePacketEvent(event);

        handleDraw = event -> handleDrawEvent(event);

    }

    // Event handler methods
    private void handleFlyingEvent(FlyingEvent event) {
        if (shouldSkipRotation()) {
            resetTargetAndRotation();

            return;

        }

        updateAttackPattern();

        updateTargetSelection();

        updateRotations();

        updateHitDelay();

        if (moveFixEnabled.isEnabled() && !rotationMode.is("disabled")) {
            event.setYaw(rotations[0]);

        }

    }

    private void handleJumpEvent(JumpEvent event) {
        if (moveFixEnabled.isEnabled() && !rotationMode.is("disabled") && shouldRotate()) {
            event.setYaw(rotations[0]);

        }

    }

    private void handleMotionEvent(MotionEvent event) {
        if (event.isPre()) {
            handlePreAutoBlock();

        }

        if (event.isPost()) {
            handleSly();

        }

        if (!event.isPost() && !rotationMode.is("disabled") && shouldRotate()) {
            event.setRotations(rotations);

        }

    }

    private void handleTickEvent(TickEvent event) {
        if (getPlayer() == null || shouldSkipTick()) {
            return;

        }

        if (target != null && target.ticksExisted > ticksExistedVal.getValue()) {
            handleAttackLogic();

        }

        handlePostAttackAutoBlock();

    }

    private void handleItemEvent(RenderItemEvent event) {
        if (shouldBlock()) {
            event.setForceUse(true);

            event.setAction(EnumAction.BLOCK);

        }

    }

    private void handleRenderEvent(RenderEvent event) {
        if (!renderRangeMode.is("none")) {
            drawRangeCircle(event);

        }

    }

    private void handlePacketEvent(PacketEvent event) {
        if (getPlayer() == null) return;

        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            handlePositionPacket(event);

        } else if (event.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer &&
        getPlayer().getHealth() <= 0.0F) {
            toggle();

        }

    }

    private void handleDrawEvent(DrawEvent event) {
        if (target == null) {
            anim = 0.0F;

            smoothHealth = 0.0F;

            return;

        }

        updateHealthAnimation(event);

        drawTargetHUD(event);

    }

    // Core functionality methods
    private void updateAttackPattern() {
        if (pattern.check()) {
            pattern.setup(delay());

        }

        attacking = pattern.nextAttack();

    }

    private void updateTargetSelection() {
        if (entities().isEmpty() || modeOption.is("single")) {
            targetIndex = 0;

        } else {
            if (getPlayer().ticksExisted % (modeOption.is("Multi") ? 2.0D : switchDelayVal.getValue()) == 0.0D) {
                targetIndex++;

            }

            if (targetIndex >= Math.min(entities().size(), maxTargetsVal.getValue())) {
                targetIndex = 0;

            }

        }

        rayCasted = rayCast(attackRange.getValue(), rotations);

        target = entities().isEmpty() ? null : entities().get(targetIndex);

    }

    private void updateRotations() {
    rotations = target != null ? calculate(target) : new float[]{getPlayer().rotationYaw, getPlayer().rotationPitch};

    }

    private void updateHitDelay() {
        hitDelay = target == null ? 0 : hitDelay + 1;

        attacking = attacking && hitDelay >= hitDelayVal.getValue();

    }

    private void handleAttackLogic() {
        switch (raycastMode.get()) {
            case "legit":
            if (attacking) {
                if (rayCasted != null) {
                    attack(rayCasted);

                } else if (targetSwingOpt.isEnabled()) {
                    swing();

                }

            }

            break;

            case "equal":
            if (attacking) {
                if (rayCasted == target) {
                    attack(target);

                } else if (targetSwingOpt.isEnabled()) {
                    swing();

                }

            }

            break;

            case "disabled":
            attackTicks++;

            if (attacking) {
                if (shouldAttack(target)) {
                    attack(target);

                } else {
                    swing();

                }

            }

            break;

        }

    }

    // Helper methods
    private boolean shouldSkipRotation() {
        return (noInvOpt.isEnabled() && mc.currentScreen != null) ||
        (noUseOpt.isEnabled() && getPlayer().isUsingItem()) ||
        !shouldRotate();

    }

    private boolean shouldSkipTick() {
        return (noInvOpt.isEnabled() && mc.currentScreen != null) ||
        (noUseOpt.isEnabled() && getPlayer().isUsingItem());

    }

    private void resetTargetAndRotation() {
    rotations = new float[]{getPlayer().rotationYaw, getPlayer().rotationPitch};

        target = null;

    }

    private void drawRangeCircle(RenderEvent event) {
        Color color = HUD.hudColor.apply(0);

        RenderUtil.drawCircleAt(
        0.0D,
        (mc.gameSettings.thirdPersonView == 0) ? 0.2D : 0.0D,
        0.0D,
        renderRangeMode.is("attack") ? attackRange.getValue() : targetRange.getValue(),
        1.5F,
        new Color(
        color.getRed() / 255.0F,
        color.getGreen() / 255.0F,
        color.getBlue() / 255.0F,
        (float)renderAlpha.getValue() / 255.0F
        )
        );

    }

    private void handlePositionPacket(PacketEvent event) {
        S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.getPacket();

        double dx = getPlayer().posX - packet.getX();

        double dz = getPlayer().posZ - packet.getZ();

        if (lagBackDisable.isEnabled() && Math.hypot(dx, dz) > lagBackMaxDistance.getValue()) {
            toggle();

        }

    }

    private void updateHealthAnimation(DrawEvent event) {
        if (smoothHealth != ((EntityLivingBase)target).getHealth()) {
            double delta = ((EntityLivingBase)target).getHealth() - smoothHealth;

            smoothHealth = (float)(smoothHealth + delta / (1.0F / event.deltaNs));

        }

        anim += event.deltaNs / 3.0F;

        anim = Math.min(anim, 1.0F);

    }

    private void drawTargetHUD(DrawEvent event) {
        double width = 135.0D;

        double height = 35.0D;

        double x = (event.getWidth() / 2.0F) - width / 2.0D;

        double y = (event.getHeight() / 2.0F + 76.0F - 3.0F * anim);

        float max = ((EntityLivingBase)target).getMaxHealth();

        float cur = smoothHealth;

        float healthf = cur / max;

        int col = HUD.fadeBetween(new Color(12255134).getRGB(), new Color(16740721).getRGB(), 1.0F - healthf);

        Gui.drawRect((int) x,(int) y, (int) (x + width), (int) (y + height), new Color(19, 19, 19, (int)(131.0F * anim)).getRGB());

        Fonts.ps16.drawStringWithShadow(EnumChatFormatting.BOLD + target.getName(), x + 4.0D, y + 5.0D, -1);

        Fonts.ps15.drawStringWithShadow((Math.round(cur * 10.0F) / 10.0F + " / " + max), x + 4.0D, y + 15.0D, new Color(14671839).getRGB());

        Gui.drawRect((int) (x + 2.0D), (int) (y + height - 4.0D), (int) (x + 2.0D + (width - 4.0D) * healthf), (int) (y + height - 2.0D), col);

        Gui.drawRect((int) (x + 2.0D), (int) (y + height - 6.5D), (int) (x + 2.0D + (width - 4.0D) * 1.0D), (int) (y + height - 5.0D), new Color(6121432).getRGB());

        Gui.drawRect((int) (x + 3.0D), (int) (y + height - 11.0D), (int) (x + 2.0D + width - 6.0D), (int) (y + height - 10.0D), new Color(1056964608, true).getRGB());

    }

    // Attack and block related methods
    private void attack(Entity entity) {
        attackTicks = 0;

        AttackEvent conditionalEvent = new AttackEvent(entity);

        conditionalEvent.setPre(true);

        conditionalEvent.call();

        swing();

        if (keepSprintOpt.isEnabled()) {
            PacketUtil.send(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));

        } else {
            AttackOrder.sendFixedAttack(getPlayer(), entity);

        }

        for (int i = 0; i < particlesVal.getValue(); i++) {
            getPlayer().onEnchantmentCritical(entity);

            getPlayer().onCriticalHit(entity);

        }

        conditionalEvent.setPre(false);

        conditionalEvent.call();

    }

    private void swing() {
        switch (swingMode.get()) {
            case "client":
            getPlayer().swingItem();

            break;

            case "server":
            PacketUtil.send(new C0APacketAnimation());

            break;

            case "sword":
            if (getPlayer().getCurrentEquippedItem() != null &&
            getPlayer().getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemSword) {
                getPlayer().swingItem();

            } else {
                PacketUtil.send(new C0APacketAnimation());

            }

            break;

        }

    }

    private void handlePreAutoBlock() {
        switch (autoBlockMode.get()) {
            case "tick":
            if (!blocking && shouldBlock()) {
                packetBlock();

                blocking = true;

            } else if (blocking) {
                packetUnblock();

                blocking = false;

            }

            break;

            case "ncp":
            if (blocking) {
                packetUnblock();

                blocking = false;

            }

            break;

        }

    }

    private void handlePostAttackAutoBlock() {
        switch (autoBlockMode.get()) {
            case "reverse":
            if (shouldBlock()) {
                packetUnblock();

                packetBlock();

                blocking = true;

            } else if (blocking) {
                packetUnblock();

                blocking = false;

            }

            break;

            case "vanilla":
            if (shouldBlock() && attackTicks == 1) {
                packetBlock();

            }

            break;

            case "c08 spam":
            if (shouldBlock()) {
                packetBlock();

                blocking = true;

            } else if (blocking) {
                packetUnblock();

                blocking = false;

            }

            break;

            case "ncp":
            if (shouldBlock() && !blocking) {
                packetBlock();

                blocking = true;

            }

            break;

        }

    }

    private void handleSly() {
        if (target == null || !slyMode.is("hurt tick", "hurt full")) return;

        Vec3 look = target.getLook(1.0F);

        double nx = target.posX - look.xCoord * slyRange.getValue();

        double nz = target.posZ - look.zCoord * slyRange.getValue();

        if (((slyMode.is("hurt tick") && getPlayer().hurtTime >= 9) ||
        (slyMode.is("hurt full") && getPlayer().hurtTime > 2))) {
            if (mc.theWorld.getBlockState(new BlockPos(nx, getPlayer().posY, nz)).getBlock() instanceof net.minecraft.block.BlockAir) {
                boolean isVoid = true;

                for (int i = 0; i < getPlayer().posY + 1.0D; i++) {
                    if (!(mc.theWorld.getBlockState(new BlockPos(nx, i, nz)).getBlock() instanceof net.minecraft.block.BlockAir)) {
                        isVoid = false;

                        break;

                    }

                }

                if (!isVoid || !slyVoidCheck.isEnabled()) {
                    getPlayer().setPosition(nx, getPlayer().posY, nz);

                }

            }

        }

    }

    // Utility methods
    public void packetBlockNoInteract() {
        PacketUtil.send(new C08PacketPlayerBlockPlacement(getPlayer().getHeldItem()));

    }

    public void packetBlock() {
        if (target != null) {
            PacketUtil.send(new C02PacketUseEntity(target, C02PacketUseEntity.Action.INTERACT));

        }

        PacketUtil.send(new C08PacketPlayerBlockPlacement(getPlayer().getHeldItem()));

    }

    public void packetUnblock() {
        PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

    }

    private boolean shouldRotate() {
        return !noScaffold.isEnabled() || !ModManager.getMod("Scaffold").isToggled();

    }

    public boolean shouldBlock() {
        return getPlayer() != null &&
        getPlayer().getCurrentEquippedItem() != null &&
        getPlayer().getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemSword &&
        target != null &&
        getPlayer().getDistanceToEntity(target) < blockRange.getValue();

    }

    private boolean shouldAttack(Entity entity) {
        return getPlayer().getDistanceToEntity(entity) < attackRange.getValue() + 0.1D;

    }

    private int delay() {
        return apsRandomness.getValue() == 0.0D ?
        (int)apsValue.getValue() :
        (int)MathUtil.randomDouble(apsValue.getValue() - apsRandomness.getValue(), apsValue.getValue());

    }

    // Entity and rotation calculation methods
    public EntityLivingBase rayCast(double range, float[] rotations) {
        Vec3 eyes = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);

        Vec3 look = mc.thePlayer.getVectorForRotation(rotations[1], rotations[0]);

        Vec3 vec = eyes.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);

        List<Entity> entities = mc.theWorld.getEntitiesInAABBexcluding(
        mc.thePlayer,
        mc.thePlayer.getEntityBoundingBox()
        .addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range)
        .expand(1.0D, 1.0D, 1.0D),
        Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith)
        );

        EntityLivingBase raycastedEntity = null;

        for (Entity ent : entities) {
            if (!(ent instanceof EntityLivingBase)) continue;

            EntityLivingBase entity = (EntityLivingBase)ent;

            if (entity == mc.thePlayer) continue;

            float borderSize = entity.getCollisionBorderSize();

            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);

            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyes, vec);

            if (axisalignedbb.isVecInside(eyes)) {
                if (range >= 0.0D) {
                    raycastedEntity = entity;

                    range = 0.0D;

                }

                continue;

            }

            if (movingobjectposition != null) {
                double distance = eyes.distanceTo(movingobjectposition.hitVec);

                if (distance < range || range == 0.0D) {
                    if (entity == entity.ridingEntity && range == 0.0D) {
                        raycastedEntity = entity;

                        continue;

                    }

                    raycastedEntity = entity;

                    range = distance;

                }

            }

        }

        return raycastedEntity;

    }

    private float[] calculate(Entity entity) {
        AxisAlignedBB bb = entity.getEntityBoundingBox();

        double x = bb.minX + (bb.maxX - bb.minX) / 2.0D;

        double z = bb.minZ + (bb.maxZ - bb.minZ) / 2.0D;

        float[] newRots = MathUtil.getRotations(x, bb.minY + (bb.maxY - bb.minY) / 2.0D, z);

        switch (rotationMode.get()) {
            case "head":
            double i = (getPlayer().ticksExisted % 6 + 1 > 3) ? 0.3D : -0.3D;

            newRots = MathUtil.getRotations(x + i, bb.minY + entity.getEyeHeight() * 0.9D, z + i);

            break;

            case "head 2":
            newRots = MathUtil.getRotations(x, bb.minY + entity.getEyeHeight(), z);

            break;

            case "head 3":
            i = (getPlayer().ticksExisted % 4 + 1 > 2) ? 0.4D : -0.4D;

            newRots = MathUtil.getRotations(
            x + i + target.posX - target.lastTickPosX,
            bb.minY + entity.getEyeHeight() * 0.9D,
            z + i + target.posZ - target.lastTickPosZ
            );

            break;

            case "down":
            newRots = MathUtil.getRotations(x, bb.minY + 0.1D, z);

            break;

        }

        float speed = calculateRotationSpeed(newRots);

        newRots[0] = MathUtil.updateRots(rotations[0], newRots[0], speed);

        newRots[1] = MathUtil.updateRots(rotations[1], newRots[1], speed);

        if (randomnessValue.getValue() != 0.0D) {
            newRots[0] = (float)(newRots[0] + MathUtil.randomDouble(-randomnessValue.getValue(), randomnessValue.getValue()));

            newRots[1] = (float)(newRots[1] + MathUtil.randomDouble(-randomnessValue.getValue(), randomnessValue.getValue()));

        }

        applyGCD(newRots, rotations);

        newRots[1] = MathHelper.clamp_float(newRots[1], -90.0F, 90.0F);

        return newRots;

    }

    private float calculateRotationSpeed(float[] newRots) {
        float speed = 1000.0F;

        switch (rotationSpeedMode.get()) {
            case "bypass":
            float dr = Math.abs(MathHelper.wrapAngleTo180_float(newRots[0] - rotations[0]));

            speed = (float)(Math.min(dr * dr / 180.0F * 1.1F, 80.0F) * MathUtil.randomDouble(0.825D, 1.0D));

            speed *= (speed < 50.0F) ? 3.0F : 1.0F;

            break;

            case "smooth":
            speed = (float)MathUtil.randomDouble(15.0D, 80.0D);

            break;

            case "smooth 2":
            float delta = Math.abs(MathHelper.wrapAngleTo180_float(newRots[0] - rotations[0]));

            speed = delta / 4.0F;

            break;

            case "tick":
            speed = (getPlayer().ticksExisted % 3 == 0) ? 130.0F : 0.0F;

            break;

            case "flick":
            speed = (rayCast(targetRange.getValue(), rotations) != target) ?
            (float)MathUtil.randomDouble(100.0D, 300.0D) :
            (float)MathUtil.randomDouble(19.0D, 31.0D);

            break;

        }

        return speed;

    }

    private List<Entity> entities() {
        List<Entity> entityList = new ArrayList<>(mc.theWorld.getLoadedEntityList());

        entityList.removeIf(entity ->
        entity == getPlayer() ||
        !isValid(entity) ||
        getPlayer().getDistanceToEntity(entity) > targetRange.getValue() ||
        (entity instanceof EntityLivingBase && Blossom.INSTANCE.getUserHelper().isFriend((EntityLivingBase)entity))
        );

        switch (priorityMode.get()) {
            case "distance":
            entityList.sort(Comparator.comparingDouble(value -> getPlayer().getDistanceToEntity(value)));

            break;

            case "health":
            entityList.sort(Comparator.<Entity>comparingDouble(value -> ((EntityLivingBase)value).getHealth()).reversed());

            break;

        }

        entityList.sort((o2, o1) ->
        Boolean.compare(
        Blossom.INSTANCE.getUserHelper().isTarget((EntityLivingBase)o1),
        Blossom.INSTANCE.getUserHelper().isTarget((EntityLivingBase)o2)
        )
        );

        return entityList;

    }

    private boolean isValid(Entity entity) {
        if (entity instanceof EntityLivingBase && isBot((EntityLivingBase)entity)) return false;

        if (isTeam(entity)) return false;

        return (playersEnabled.isEnabled() && entity instanceof EntityPlayer) ||
        (mobsEnabled.isEnabled() && entity instanceof EntityMob) ||
        (villagersEnabled.isEnabled() && entity instanceof EntityVillager) ||
        (animalsEnabled.isEnabled() && entity instanceof EntityAnimal);

    }

    private boolean isBot(EntityLivingBase entity) {
        return antiBotMode.is("basic") &&
        entity != getPlayer() &&
        !getTabList().contains(entity);

    }

    public static List<EntityPlayer> getTabList() {
        Minecraft mc = Minecraft.getMinecraft();

        return GuiPlayerTabOverlay.field_175252_a.sortedCopy(mc.thePlayer.sendQueue.getPlayerInfoMap())
        .stream()
        .map(info -> mc.theWorld.getPlayerEntityByName(info.getGameProfile().getName()))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    }

    private boolean isTeam(Entity entity) {
        return entity instanceof EntityLivingBase &&
        teamsMode.is("color") &&
        entity.getDisplayName().getFormattedText().charAt(0) == getPlayer().getDisplayName().getFormattedText().charAt(0) &&
        entity.getDisplayName().getFormattedText().charAt(1) == getPlayer().getDisplayName().getFormattedText().charAt(1);

    }

    private double getMouseGCD() {
        float sens = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;

        float pow = sens * sens * sens * 8.0F;

        return pow * 0.15D;

    }

    private void applyGCD(float[] rotations, float[] prevRots) {
        float yawDif = rotations[0] - prevRots[0];

        float pitchDif = rotations[1] - prevRots[1];

        double gcd = getMouseGCD();

        rotations[0] = (float)(rotations[0] - yawDif % gcd);

        rotations[1] = (float)(rotations[1] - pitchDif % gcd);

    }

    // Override methods
    @Override
    public String getSuffix() {
        return modeOption.getMode();

    }

    @Override
    public void onEnable() {
        super.onEnable();

        pattern.setup(delay());

        attacking = false;

        attackTicks = 0;

        hitDelay = 0;

        anim = 0.0F;

        smoothHealth = 0.0F;

        blocking = false;

        target = null;

        targetIndex = 0;

    rotations = new float[]{getPlayer().rotationYaw, getPlayer().rotationPitch};

    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (blocking) {
            AuraHelper.unblock();

        }

        target = null;

    }

    // Inner classes
    private static class AttackPattern {
        public ArrayList<Boolean> attackList;

        public int index = 0;

        public void setup(int aps) {
            index = 0;

            attackList = new ArrayList<>();

            for (int i = 0; i < 20; i++) {
                attackList.add(i, i < aps);

            }

            Collections.shuffle(attackList);

        }

        public boolean check() {
            return index >= attackList.size();

        }

        public boolean nextAttack() {
            boolean b = attackList.get(index);

            index++;

            return b;

        }

    }

}
