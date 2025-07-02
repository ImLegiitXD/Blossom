package moshi.blossom.event.impl.player;

import java.util.List;
import moshi.blossom.event.Event;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class CollideEvent

extends Event
{
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

    public BlockPos getPos() {
        return this.pos;

    }

    public void setPos(BlockPos pos) {
        this.pos = pos;

    }

    public IBlockState getState() {
        return this.state;

    }

    public void setState(IBlockState state) {
        this.state = state;

    }

    public AxisAlignedBB getAlignedBB() {
        return this.alignedBB;

    }

    public void setAlignedBB(AxisAlignedBB alignedBB) {
        this.alignedBB = alignedBB;

    }

    public List<AxisAlignedBB> getAxisList() {
        return this.axisList;

    }

    public void setAxisList(List<AxisAlignedBB> axisList) {
        this.axisList = axisList;

    }

}
