package moshi.blossom.module.impl.player;

import com.google.common.collect.Lists;
import java.util.List;
import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.Clock;
import moshi.blossom.util.player.InvUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Stealer extends Module {

    public final NumberOption firstDelayOpt = new NumberOption("FIRST_DELAY", "First Delay", 150.0D, 0.0D, 300.0D, 25.0D);

    public final NumberOption delayOpt = new NumberOption("DELAY_OPT", "Delay", 50.0D, 0.0D, 300.0D, 25.0D);

    private long delay;

    private final Clock stealClock;

    private final List<Item> whiteListedItems;

    @EventLink
    public Listener<TickEvent> handleTick;

    @EventLink
    public Listener<PacketEvent> handlePacket;

    public Stealer() {
        super("Stealer", "Stealer", Category.PLAYER);

        this.delay = 0L;

        this.stealClock = new Clock();

        this.whiteListedItems = Lists.newArrayList(
        Items.golden_apple,
        Items.potionitem,
        Items.ender_pearl
        );

        this.handleTick = (event -> {
            if (getPlayer() == null || this.mc.theWorld == null) {
                return;

            }

            if (getPlayer().openContainer == null) {
                return;

            }

            if (getPlayer().openContainer instanceof ContainerChest) {
                int size = ((ContainerChest)getPlayer().openContainer).getLowerChestInventory().getSizeInventory();

                int i = 0;

                if (this.stealClock.elapsed(this.delay)) {
                    while (i < size) {
                        if (steal(i)) {
                            this.delay = nextDelay();

                            this.stealClock.reset();

                            break;

                        }

                        i++;

                    }

                }

            }

        });

        this.handlePacket = (event -> {
            if (event.getPacket() instanceof net.minecraft.network.play.server.S2DPacketOpenWindow) {
                this.stealClock.reset();

                this.delay = (long)this.firstDelayOpt.getValue();

            }

        });

    setupOptions(new Option[] { this.firstDelayOpt, this.delayOpt });

    }

    public void onEnable() {
        super.onEnable();

        this.stealClock.reset();

        this.delay = nextDelay();

    }

    public boolean steal(int slotNum) {
        Slot slot = getPlayer().openContainer.getSlot(slotNum);

        if (!slot.getHasStack()) {
            return false;

        }

        ItemStack stack = slot.getStack();

        if (stack.getItem() instanceof net.minecraft.item.ItemSword) {
            if (InvUtil.getBestSword() == null || InvUtil.isSwordBetter(stack, InvUtil.getBestSword())) {
                take(slotNum);

                return true;

            }

        }

        else if (stack.getItem() instanceof net.minecraft.item.ItemAxe) {
            if (InvUtil.getBestAxe() == null || InvUtil.isToolBetter(stack, InvUtil.getBestAxe(), Blocks.planks)) {
                take(slotNum);

                return true;

            }

        }

        else if (stack.getItem() instanceof net.minecraft.item.ItemPickaxe) {
            if (InvUtil.getBestAxe() == null || InvUtil.isToolBetter(stack, InvUtil.getBestAxe(), Blocks.stone)) {
                take(slotNum);

                return true;

            }

        }

        else {
            if (stack.getItem() instanceof net.minecraft.item.ItemBlock || stack.getItem() instanceof net.minecraft.item.ItemArmor) {
                take(slotNum);

                return true;

            }

            if (this.whiteListedItems.contains(stack.getItem())) {
                take(slotNum);

                return true;

            }

        }

        return false;

    }

    public void onDisable() {
        super.onDisable();

    }

    private void take(int slot) {
        this.mc.playerController.windowClick(
        getPlayer().openContainer.windowId,
        slot,
        0,
        1,
        getPlayer()
        );

    }

    private long nextDelay() {
        return (long)this.delayOpt.getValue();

    }

}
