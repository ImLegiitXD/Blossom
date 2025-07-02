package moshi.blossom.module.impl.player;

import io.netty.util.internal.ThreadLocalRandom;
import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.event.impl.player.FlyingEvent;
import moshi.blossom.event.impl.player.JumpEvent;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.SafeWalkEvent;
import moshi.blossom.module.Module;
import moshi.blossom.util.network.PacketUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Scaffold2 extends Module {

    private float yaw;

    private float rany;

    public Scaffold2() {
        super("Scaffold2", "Scaffold2", Category.PLAYER);

        this.handleFlying = (event -> {
            double prec = 0.02222222276031971D;

            this.yaw = (float)(Math.round((getPlayer()).rotationYaw * prec) / prec) - this.rany + 45.0F;

            event.setForward((getPlayer()).moveForward * -1.0F);

            event.setStrafe((getPlayer()).moveForward * -1.0F);

            event.setYaw(this.yaw);

        });

        this.handleSafeWalk = (event -> event.setForceSafeWalk(true));

        this.handleJump = (event -> event.setYaw(this.yaw));

        this.handleEvent = (event -> {
            if (event.isPost()) {
                return;

            }

            if (this.mc.theWorld.getBlockState((new BlockPos((Entity)this.mc.thePlayer)).add(0, -1, 0)).getBlock() instanceof net.minecraft.block.BlockAir) {
                if (this.nextDelay != 0) {
                    PacketUtil.send((Packet)new C0BPacketEntityAction((Entity)getPlayer(), C0BPacketEntityAction.Action.START_SNEAKING));

                }

                (getPlayer()).motionX *= 0.8D;

                (getPlayer()).motionZ *= 0.8D;

                this.nextDelay = 0;

            } else {
                if (this.nextDelay == 0) {
                    PacketUtil.send((Packet)new C0BPacketEntityAction((Entity)getPlayer(), C0BPacketEntityAction.Action.STOP_SNEAKING));

                }

                this.nextDelay++;

            }

            event.setYaw(this.yaw);

            if (Math.round(this.yaw) % 90 != 0) {
                event.setPitch(80.19848F);

            } else {
                event.setPitch(76.6F);

            }

        });

        this.handleTick = (event -> {
            if (getPlayer() == null)
            return;

            if (this.mc.objectMouseOver == null || getPlayer().getCurrentEquippedItem() == null)
            return;

            if (!(getPlayer().getCurrentEquippedItem().getItem() instanceof net.minecraft.item.ItemBlock))
            return;

            if (this.mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
            return;

            if (this.mc.objectMouseOver.sideHit == EnumFacing.UP) {
                if (!this.mc.thePlayer.onGround && this.mc.playerController.onPlayerRightClick(getPlayer(), this.mc.theWorld, getPlayer().getCurrentEquippedItem(), this.mc.objectMouseOver.getBlockPos(), this.mc.objectMouseOver.sideHit, this.mc.objectMouseOver.hitVec))
                getPlayer().swingItem();

            } else if (this.mc.playerController.onPlayerRightClick(getPlayer(), this.mc.theWorld, getPlayer().getCurrentEquippedItem(), this.mc.objectMouseOver.getBlockPos(), this.mc.objectMouseOver.sideHit, this.mc.objectMouseOver.hitVec)) {
                getPlayer().swingItem();

            }

        });

    }

    private int nextDelay;

    @EventLink
    public Listener<FlyingEvent> handleFlying;

    @EventLink
    public Listener<SafeWalkEvent> handleSafeWalk;

    @EventLink
    public Listener<JumpEvent> handleJump;

    @EventLink
    public Listener<MotionEvent> handleEvent;

    @EventLink
    public Listener<TickEvent> handleTick;

    public void onEnable() {
        super.onEnable();

        this.rany = (float)ThreadLocalRandom.current().nextDouble(179.51D, 180.49D);

        getPlayer().setSprinting(false);

    }

    public void onDisable() {
        super.onDisable();

        if (this.nextDelay <= 1)
        PacketUtil.send(new C0BPacketEntityAction(getPlayer(), C0BPacketEntityAction.Action.STOP_SNEAKING));

    }

}
