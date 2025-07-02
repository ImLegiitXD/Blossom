package moshi.blossom.module.impl.player;

import com.google.common.collect.Lists;
import java.util.List;
import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ContOption;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.Clock;
import moshi.blossom.util.player.InvUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class InvManager extends Module {

    public final ModeOption modeOption = new ModeOption("MODE", "Mode", "UHC",
new String[] { "UHC", "CTW" });

    public final NumberOption delayOpt = new NumberOption("DELAY_OPT", "Delay", 50.0D, 0.0D, 300.0D, 25.0D);

    public final ContOption slotsOpt = new ContOption("SLOTS_CONT", "Slots");

    public final NumberOption swordSlotOpt = new NumberOption("SWORD_SLOT", "Sword", 1.0D, 1.0D, 9.0D, 1.0D);

    public final NumberOption pickaxeSlotOpt = new NumberOption("PICKAXE_SLOT", "Pickaxe", 2.0D, 1.0D, 9.0D, 1.0D);

    public final NumberOption axeSlotOpt = new NumberOption("AXE_SLOT", "Axe", 3.0D, 1.0D, 9.0D, 1.0D);

    public final NumberOption gapsSlotOpt = new NumberOption("GAPS_SLOT", "Gaps", 4.0D, 1.0D, 9.0D, 1.0D);

    public final NumberOption blocksSlotOpt = new NumberOption("BLOCKS_SLOT", "Blocks", 8.0D, 1.0D, 9.0D, 1.0D);

    public final Clock dropClock;

    private final Clock swapClock;

    private final List<String> ctwItems;

    private final List<String> uhcItems;

    @EventLink
    public Listener<TickEvent> handleTick;

    public InvManager() {
        super("InvManager", "Inventory Manager", Category.PLAYER);

        this.dropClock = new Clock();

        this.swapClock = new Clock();

        this.ctwItems = Lists.newArrayList(
        "item.bow",
        "item.arrow",
        "item.ingotIron",
        "item.diamond",
        "item.beefCooked",
        "item.appleGold",
        "item.boat"
        );

        this.uhcItems = Lists.newArrayList(
        "item.appleGold",
        "item.apple",
        "item.diamond",
        "item.ingotGold",
        "item.ingotIron",
        "item.dyePowder.blue",
        "item.book",
        "item.bucketLava",
        "item.bucketWater",
        "item.reeds",
        "item.leather",
        "tile.enchantmentTable",
        "item.stick",
        "item.expBottle",
        "item.boat",
        "item.bucket",
        "item.potion"
        );

        this.handleTick = (event -> {
            if (getPlayer() == null || this.mc.theWorld == null) return;

            if (!(this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)) return;

            int swordSlot = (int)(this.swordSlotOpt.getValue() - 1.0D);

            int pickaxeSlot = (int)(this.pickaxeSlotOpt.getValue() - 1.0D);

            int axeSlot = (int)(this.axeSlotOpt.getValue() - 1.0D);

            int gapSlot = (int)(this.gapsSlotOpt.getValue() - 1.0D);

            int blockSlot = (int)(this.blocksSlotOpt.getValue() - 1.0D);

            List<ItemStack> inventory = getPlayer().inventoryContainer.getInventory();

            int size = inventory.size();

            // Drop unwanted items
            if (this.dropClock.elapsed((long)this.delayOpt.getValue())) {
                for (int i = 9; i < size; i++) {
                    ItemStack stack = inventory.get(i);

                    if (stack == null) continue;

                    if (stack.getItem() instanceof net.minecraft.item.ItemSword) {
                        if (InvUtil.getBestSword() != stack) {
                            this.dropClock.reset();

                            drop(i);

                            break;

                        }

                    }

                    else if (stack.getItem() instanceof net.minecraft.item.ItemPickaxe) {
                        if (InvUtil.getBestPickaxe() != stack) {
                            this.dropClock.reset();

                            drop(i);

                            break;

                        }

                    }

                    else if (stack.getItem() instanceof net.minecraft.item.ItemAxe) {
                        if (InvUtil.getBestAxe() != stack) {
                            this.dropClock.reset();

                            drop(i);

                            break;

                        }

                    }

                    else if (stack.getItem() instanceof net.minecraft.item.ItemBlock) {
                        if (InvUtil.blockCount() > 192) {
                            this.dropClock.reset();

                            drop(i);

                            break;

                        }

                    }

                    else if (stack.getItem() instanceof net.minecraft.item.ItemFood) {
                        if (InvUtil.foodCount() > 128) {
                            this.dropClock.reset();

                            drop(i);

                            break;

                        }

                    }

                    else if (!(stack.getItem() instanceof net.minecraft.item.ItemPotion)) {
                        if (stack.getItem() instanceof net.minecraft.item.ItemArmor) {
                            if ((InvUtil.helmetParts.contains(stack.getItem()) && stack != InvUtil.getBestArmorPart(InvUtil.helmetParts)) ||
                            (InvUtil.chestParts.contains(stack.getItem()) && stack != InvUtil.getBestArmorPart(InvUtil.chestParts)) ||
                            (InvUtil.leggingParts.contains(stack.getItem()) && stack != InvUtil.getBestArmorPart(InvUtil.leggingParts)) ||
                            (InvUtil.bootParts.contains(stack.getItem()) && stack != InvUtil.getBestArmorPart(InvUtil.bootParts))) {
                                this.dropClock.reset();

                                drop(i);

                                break;

                            }

                        }

                        else if (!whiteList().contains(stack.getItem().getUnlocalizedName())) {
                            this.dropClock.reset();

                            drop(i);

                            break;

                        }

                    }

                }

            }

            if (!this.dropClock.elapsed((long)(this.delayOpt.getValue() + 100.0D))) return;

            // Organize hotbar
            if (this.swapClock.elapsed((long)this.delayOpt.getValue())) {
                this.swapClock.reset();

                for (int i = 8; i < size; i++) {
                    ItemStack stack = inventory.get(i);

                    if (stack == null) continue;

                    if (stack.getItem() instanceof net.minecraft.item.ItemSword &&
                    stack == InvUtil.getBestSword() &&
                    (inventory.get(36 + swordSlot) == null ||
                    inventory.get(36 + swordSlot) != InvUtil.getBestSword())) {
                        swap(i, swordSlot);

                        break;

                    }

                    if (stack.getItem() instanceof net.minecraft.item.ItemPickaxe &&
                    stack == InvUtil.getBestPickaxe() &&
                    (inventory.get(36 + pickaxeSlot) == null ||
                    inventory.get(36 + pickaxeSlot) != InvUtil.getBestPickaxe())) {
                        swap(i, pickaxeSlot);

                        break;

                    }

                    if (stack.getItem() instanceof net.minecraft.item.ItemAxe &&
                    stack == InvUtil.getBestAxe() &&
                    (inventory.get(36 + axeSlot) == null ||
                    inventory.get(36 + axeSlot) != InvUtil.getBestAxe())) {
                        swap(i, axeSlot);

                        break;

                    }

                    ItemStack currentBlockSlot = inventory.get(36 + blockSlot);

                    if (stack.getItem() instanceof net.minecraft.item.ItemBlock &&
                    (currentBlockSlot == null ||
                    !(currentBlockSlot.getItem() instanceof net.minecraft.item.ItemBlock))) {
                        swap(i, blockSlot);

                        break;

                    }

                    ItemStack currentGappleSlot = inventory.get(36 + gapSlot);

                    if (stack.getItem() == Items.golden_apple &&
                    (currentGappleSlot == null ||
                    currentGappleSlot.getItem() != Items.golden_apple)) {
                        swap(i, gapSlot);

                        break;

                    }

                }

            }

        });

    setupOptions(new Option[] { this.modeOption, this.delayOpt, this.slotsOpt });

        this.slotsOpt.setupOptions(new Option[] {
            this.swordSlotOpt,
            this.pickaxeSlotOpt,
            this.axeSlotOpt,
            this.gapsSlotOpt,
            this.blocksSlotOpt
        });

    }

    private List<String> whiteList() {
        return this.modeOption.is("UHC") ? this.uhcItems : this.ctwItems;

    }

    public void onEnable() {
        super.onEnable();

        this.dropClock.reset();

        this.swapClock.reset();

    }

    private void swap(int from, int toHotbarSlot) {
        this.mc.playerController.windowClick(
        getPlayer().openContainer.windowId,
        from,
        toHotbarSlot,
        2,
        getPlayer()
        );

    }

    private void drop(int slot) {
        this.mc.playerController.windowClick(
        getPlayer().openContainer.windowId,
        slot,
        1,
        4,
        getPlayer()
        );

    }

}
