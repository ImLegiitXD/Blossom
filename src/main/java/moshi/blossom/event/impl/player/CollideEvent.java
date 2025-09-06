package moshi.blossom.event.impl.player;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.event.Event;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@Setter
@Getter
public class CollideEvent extends Event {
    private BlockPos pos;
    private IBlockState state;
    private AxisAlignedBB alignedBB;
    private List<AxisAlignedBB> axisList;

    public CollideEvent(BlockPos pos, IBlockState state, AxisAlignedBB alignedBB, List<AxisAlignedBB> axisList) {
        this.pos = pos;
        this.state = state;
        this.alignedBB = alignedBB;
        this.axisList = axisList;
    }
}
