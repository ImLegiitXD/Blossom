package moshi.blossom.module.impl.player;

import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.module.Module;
import moshi.blossom.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class AntiLava extends Module {

    private boolean cancelNext;

    @EventLink
    public Listener<MotionEvent> handleEvent;

    public AntiLava() {
        super("AntiLava", "Anti Lava", Category.PLAYER);

        this.cancelNext = false;

        this.handleEvent = (event -> {
            if (event.isPost()) return;

            BlockPos nextPos = new BlockPos(
            getPlayer().posX + getPlayer().motionX,
            getPlayer().posY + (getPlayer().onGround ? Math.max(0.0D, getPlayer().motionY) : getPlayer().motionY),
            getPlayer().posZ + getPlayer().motionZ
            );

            Block block = this.mc.theWorld.getBlockState(nextPos).getBlock();

            if (block == Blocks.lava || block == Blocks.flowing_lava) {
                event.setY(event.getY() + MathUtil.randomDouble(0.5D, 0.85D));

            }

        });

    }

    public String getSuffix() {
        return "Flag";

    }

    public void onEnable() {
        super.onEnable();

        this.cancelNext = false;

    }

}
