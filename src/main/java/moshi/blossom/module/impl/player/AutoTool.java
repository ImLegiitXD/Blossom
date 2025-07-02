package moshi.blossom.module.impl.player;

import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.module.Module;
import moshi.blossom.util.player.InvUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class AutoTool extends Module {

    @EventLink
    public Listener<PacketEvent> handlePacket;

    public AutoTool() {
        super("AutoTool", "Auto Tool", Category.PLAYER);

        this.handlePacket = (event -> {
            if (!(event.getPacket() instanceof C07PacketPlayerDigging)) return;

            C07PacketPlayerDigging packet = (C07PacketPlayerDigging)event.getPacket();

            if (packet.getStatus() != C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) return;

            BlockPos pos = packet.getPosition();

            int bestSlot = -1;

            ItemStack bestTool = null;

            for (int i = 1; i < 9; i++) {
                ItemStack current = getPlayer().inventoryContainer.getInventory().get(36 + i);

                if (current != null &&
                current.getItem() instanceof ItemTool &&
                (bestTool == null ||
                InvUtil.isToolBetter(current, bestTool, this.mc.theWorld.getBlockState(pos).getBlock()))) {
                    bestSlot = i;

                    bestTool = current;

                }

            }

            if (bestSlot != -1 &&
            getPlayer().inventory.currentItem != bestSlot &&
            InvUtil.getToolDigEfficiency(bestTool, this.mc.theWorld.getBlockState(pos).getBlock()) != 1.0D) {
                getPlayer().inventory.currentItem = bestSlot;

                this.mc.playerController.syncCurrentPlayItem();

            }

        });

    }

}
