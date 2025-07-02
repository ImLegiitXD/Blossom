package moshi.blossom.module.impl.player;

import javax.vecmath.Vector3d;
import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.MathUtil;
import moshi.blossom.util.network.PacketUtil;
import moshi.blossom.util.player.MoveUtil;
import moshi.blossom.util.player.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Scaffold extends Module {

    final NumberOption timerBoost = new NumberOption("TIMER_BOOST", "Timer", 1.0D, 1.0D, 5.0D, 0.05D);

    private double keepY;

    private int silentSlot;

    private int jumpTicks;

    private ItemStack silentStack;

    private BlockData placeData;

    private float[] rotations;

    @EventLink
    public Listener<PacketEvent> handlePacket;

    @EventLink(1)
    public Listener<MoveEvent> handleMove;

    @EventLink(1)
    public Listener<MotionEvent> handleMotion;

    @EventLink
    public Listener<TickEvent> handleTick;

    public Scaffold() {
        super("Scaffold", "Scaffold", Category.PLAYER);

        this.keepY = 0.0D;

        this.silentSlot = 0;

        this.jumpTicks = 0;

        this.silentStack = null;

        this.placeData = null;

        this.handlePacket = (event -> {
            if (event.getPacket() instanceof C09PacketHeldItemChange) {
                event.setCanceled(true);

            }

        });

        this.handleMove = (event -> {
            this.jumpTicks++;

            if (this.mc.theWorld != null && this.mc.thePlayer != null &&
            this.silentStack != null && this.silentStack.getItem() instanceof net.minecraft.item.ItemBlock) {
                if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindJump)) {
                    if (this.jumpTicks == 3) {
                        event.setY(0.0D);

                    } else if (getPlayer().onGround) {
                        this.jumpTicks = 0;

                        event.setY(0.41999998688697815D);

                    }

                } else {
                    this.jumpTicks = 0;

                }

            }

        });

        this.handleMotion = (event -> {
            TimerUtil.setTimer((float)this.timerBoost.getValue());

            if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindJump)) {
                this.keepY = event.getY();

            }

            BlockPos belowPos = new BlockPos(getPlayer().posX, this.keepY, getPlayer().posZ).add(0, -1, 0);

            if (this.mc.theWorld.isAirBlock(belowPos)) {
                this.placeData = getData(belowPos);

            }

            if (this.placeData != null) {
                Vec3 vec = getVecHit(this.placeData);

                float[] rots = MathUtil.getRotations(vec.xCoord, vec.yCoord, vec.zCoord);

                MathUtil.applyGCD(rots, this.rotations);

                this.rotations = rots;

            }

            if (event.isPre()) {
                event.setYaw(MoveUtil.getDirection() + 180.0F);

                event.setPitch(70.0F);

            }

        });

        this.handleTick = (event -> {
            if (this.placeData != null && this.mc.theWorld != null &&
            this.mc.thePlayer != null && this.silentStack != null &&
            this.silentStack.getItem() instanceof net.minecraft.item.ItemBlock) {
                if (this.mc.playerController.onPlayerRightClick(
                getPlayer(),
                this.mc.theWorld,
                this.silentStack,
                this.placeData.blockPos,
                this.placeData.facing,
                getVecHit(this.placeData))) {
                    PacketUtil.send(new C0APacketAnimation());

                }

                this.placeData = null;

            }

        });

    setupOptions(new Option[] { this.timerBoost });

    }

    public void onEnable() {
        super.onEnable();

        this.jumpTicks = 0;

        this.silentSlot = -1;

        this.silentStack = null;

        for (int i = 0; i < 9; i++) {
            if (getPlayer().inventoryContainer.getInventory().get(36 + i) != null &&
            ((ItemStack)getPlayer().inventoryContainer.getInventory().get(36 + i)).getItem() instanceof net.minecraft.item.ItemBlock) {
                this.silentSlot = i;

                this.silentStack = getPlayer().inventoryContainer.getInventory().get(36 + i);

                break;

            }

        }

        if (this.silentSlot != -1) {
            PacketUtil.sendSilent(new C09PacketHeldItemChange(this.silentSlot));

        }

        this.keepY = getPlayer().posY;

        this.placeData = null;

    this.rotations = new float[] { getPlayer().rotationYaw, 80.0F };

    }

    public Vec3 getVecHit(BlockData blockData) {
        Vector3d vec = new Vector3d(
        blockData.blockPos.getX(),
        blockData.blockPos.getY(),
        blockData.blockPos.getZ()
        );

        EnumFacing facing = blockData.facing;

        switch (facing) {
            case SOUTH:
            case NORTH:
            vec.x = MathHelper.clamp_double(
            getPlayer().posX + 0.45D,
            blockData.blockPos.getX() + 0.2D,
            blockData.blockPos.getX() + 0.8D
            );

            break;

            case EAST:
            case WEST:
            vec.z = MathHelper.clamp_double(
            getPlayer().posZ + 0.45D,
            blockData.blockPos.getZ() + 0.2D,
            blockData.blockPos.getZ() + 0.8D
            );

            break;

        }

        if (facing == EnumFacing.SOUTH) vec.z++;

        if (facing == EnumFacing.EAST) vec.x++;

        if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
            vec.x += 0.5D;

            vec.z += 0.5D;

            vec.y += (facing == EnumFacing.UP) ? 1.0D : 0.0D;

        } else {
            vec.y += MathUtil.randomDouble(0.9D, 0.91D);

        }

        return new Vec3(vec.x, vec.y, vec.z);

    }

    public void onDisable() {
        super.onDisable();

        TimerUtil.setTimer(1.0F);

        if (this.silentSlot != -1 && this.silentSlot != getPlayer().inventory.currentItem) {
            PacketUtil.sendSilent(new C09PacketHeldItemChange(getPlayer().inventory.currentItem));

        }

    }

    public BlockData getData(BlockPos fromBlock) {
        if (isValidPos(fromBlock.add(0, -1, 0)))
        return new BlockData(fromBlock.add(0, -1, 0), EnumFacing.UP);

        if (isValidPos(fromBlock.add(0, 1, 0)))
        return new BlockData(fromBlock.add(0, 1, 0), EnumFacing.DOWN);

        if (isValidPos(fromBlock.add(0, 0, -1)))
        return new BlockData(fromBlock.add(0, 0, -1), EnumFacing.SOUTH);

        if (isValidPos(fromBlock.add(0, 0, 1)))
        return new BlockData(fromBlock.add(0, 0, 1), EnumFacing.NORTH);

        if (isValidPos(fromBlock.add(-1, 0, 0)))
        return new BlockData(fromBlock.add(-1, 0, 0), EnumFacing.EAST);

        if (isValidPos(fromBlock.add(1, 0, 0)))
        return new BlockData(fromBlock.add(1, 0, 0), EnumFacing.WEST);

        if (isValidPos(fromBlock.add(-1, 0, -1)))
        return new BlockData(fromBlock.add(-1, 0, -1), EnumFacing.SOUTH);

        if (isValidPos(fromBlock.add(1, 0, 1)))
        return new BlockData(fromBlock.add(1, 0, 1), EnumFacing.NORTH);

        if (isValidPos(fromBlock.add(-1, 0, 1)))
        return new BlockData(fromBlock.add(-1, 0, 1), EnumFacing.EAST);

        if (isValidPos(fromBlock.add(1, 0, -1)))
        return new BlockData(fromBlock.add(1, 0, -1), EnumFacing.WEST);

        if (isValidPos(fromBlock.add(0, 0, -1)))
        return new BlockData(fromBlock.add(0, 0, -1), EnumFacing.SOUTH);

        if (isValidPos(fromBlock.add(0, -1, -1)))
        return new BlockData(fromBlock.add(0, -1, -1), EnumFacing.SOUTH);

        if (isValidPos(fromBlock.add(0, -1, 1)))
        return new BlockData(fromBlock.add(0, -1, 1), EnumFacing.NORTH);

        if (isValidPos(fromBlock.add(-1, -1, 0)))
        return new BlockData(fromBlock.add(-1, -1, 0), EnumFacing.EAST);

        if (isValidPos(fromBlock.add(1, -1, 0)))
        return new BlockData(fromBlock.add(1, -1, 0), EnumFacing.WEST);

        if (isValidPos(fromBlock.add(-1, -1, -1)))
        return new BlockData(fromBlock.add(-1, -1, -1), EnumFacing.SOUTH);

        if (isValidPos(fromBlock.add(1, -1, 1)))
        return new BlockData(fromBlock.add(1, -1, 1), EnumFacing.NORTH);

        if (isValidPos(fromBlock.add(-1, -1, 1)))
        return new BlockData(fromBlock.add(-1, -1, 1), EnumFacing.EAST);

        if (isValidPos(fromBlock.add(1, -1, -1)))
        return new BlockData(fromBlock.add(1, -1, -1), EnumFacing.WEST);

        if (isValidPos(fromBlock.add(0, -1, -1)))
        return new BlockData(fromBlock.add(0, -1, -1), EnumFacing.SOUTH);

        return null;

    }

    public boolean isValidPos(BlockPos block) {
        Block blockState = this.mc.theWorld.getBlockState(block).getBlock();

        if (blockState == Blocks.sand) return true;

        if (blockState instanceof net.minecraft.block.BlockContainer ||
        !blockState.isFullBlock() ||
        !blockState.isFullCube() ||
        blockState instanceof net.minecraft.block.BlockFalling) {
            return false;

        }

        return (!blockState.getMaterial().isLiquid() && blockState.getMaterial().isSolid());

    }

    private static class BlockData {
        private final BlockPos blockPos;

        private final EnumFacing facing;

        public BlockData(BlockPos blockPos, EnumFacing facing) {
            this.blockPos = blockPos;

            this.facing = facing;

        }

    }

}
