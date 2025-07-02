package moshi.blossom.util.player;

import moshi.blossom.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class PlayerUtil extends Util {
    // Movement constants
    public static final double BASE_MOTION = 0.21585904623731839D;
    public static final double BASE_MOTION_SPRINT = 0.28060730580133125D;
    public static final double BASE_MOTION_WATER = 0.09989148404308008D;
    public static final double MAX_MOTION_SPRINT = 0.28623662093593094D;
    public static final double BASE_GROUND_BOOST = 1.9561839658913562D;
    public static final double BASE_GROUND_FRICTION = 0.587619839258055D;
    public static final double SPEED_GROUND_BOOST = 2.016843005849186D;
    public static final double MOVE_FRICTION = 0.9800000190734863D;

    /**
     * Checks if the player is standing over void (no blocks beneath)
     * @return true if there are no solid blocks below the player
     */
    public static boolean isOverVoid() {
        double playerX = getPlayer().posX;
        double playerZ = getPlayer().posZ;

        for (int y = 0; y < getPlayer().posY + 1.0D; y++) {
            BlockPos pos = new BlockPos(playerX, y, playerZ);
            if (mc.theWorld.getBlockState(pos).getBlock() != Blocks.air) {
                return false;
            }
        }
        return true;
    }

}