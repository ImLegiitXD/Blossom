package moshi.blossom.util.player;

import com.google.common.collect.Lists;
import java.util.List;
import moshi.blossom.util.Util;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class InvUtil extends Util {
    public static final List<Item> helmetParts = Lists.newArrayList(
            Items.golden_helmet, Items.leather_helmet,
            Items.chainmail_helmet, Items.iron_helmet,
            Items.diamond_helmet);

    public static final List<Item> chestParts = Lists.newArrayList(
            Items.golden_chestplate, Items.leather_chestplate,
            Items.chainmail_chestplate, Items.iron_chestplate,
            Items.diamond_chestplate);

    public static final List<Item> leggingParts = Lists.newArrayList(
            Items.golden_leggings, Items.leather_leggings,
            Items.chainmail_leggings, Items.iron_leggings,
            Items.diamond_leggings);

    public static final List<Item> bootParts = Lists.newArrayList(
            Items.golden_boots, Items.leather_boots,
            Items.chainmail_boots, Items.iron_boots,
            Items.diamond_boots);

    public static ItemStack equipedHelmet() {
        return getPlayer().inventoryContainer.getInventory().get(5);
    }

    public static ItemStack equipedChestplate() {
        return getPlayer().inventoryContainer.getInventory().get(6);
    }

    public static ItemStack equipedLeggings() {
        return getPlayer().inventoryContainer.getInventory().get(7);
    }

    public static ItemStack equipedBoots() {
        return getPlayer().inventoryContainer.getInventory().get(8);
    }

    public static ItemStack getBestArmorPart(List<Item> filter) {
        int size = getPlayer().inventoryContainer.getInventory().size();
        ItemStack lastPart = null;

        for (int i = 0; i < size; i++) {
            ItemStack stack = getPlayer().inventoryContainer.getInventory().get(i);
            if (stack != null && stack.getItem() instanceof ItemArmor && filter.contains(stack.getItem())) {
                if (lastPart == null || isArmorBetter(stack, lastPart)) {
                    lastPart = stack;
                }
            }
        }
        return lastPart;
    }

    public static int blockCount() {
        int count = 0;
        for (ItemStack stack : getPlayer().inventoryContainer.getInventory()) {
            if (stack != null && stack.getItem() instanceof net.minecraft.item.ItemBlock) {
                count += stack.stackSize;
            }
        }
        return count;
    }

    public static int foodCount() {
        int count = 0;
        for (ItemStack stack : getPlayer().inventoryContainer.getInventory()) {
            if (stack != null && stack.getItem() instanceof net.minecraft.item.ItemFood) {
                count += stack.stackSize;
            }
        }
        return count;
    }

    public static boolean isArmorBetter(ItemStack better, ItemStack than) {
        return getArmorValue(better) > getArmorValue(than);
    }

    public static float getArmorValue(ItemStack stack) {
        return ((ItemArmor)stack.getItem()).damageReduceAmount +
                EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
    }

    public static ItemStack getBestSword() {
        int size = getPlayer().inventoryContainer.getInventory().size();
        ItemStack lastSword = null;

        for (int i = 0; i < size; i++) {
            ItemStack stack = getPlayer().inventoryContainer.getInventory().get(i);
            if (stack != null && stack.getItem() instanceof ItemSword) {
                if (lastSword == null || isSwordBetter(stack, lastSword)) {
                    lastSword = stack;
                }
            }
        }
        return lastSword;
    }

    public static ItemStack getBestAxe() {
        int size = getPlayer().inventoryContainer.getInventory().size();
        ItemStack lastAxe = null;

        for (int i = 0; i < size; i++) {
            ItemStack stack = getPlayer().inventoryContainer.getInventory().get(i);
            if (stack != null && stack.getItem() instanceof net.minecraft.item.ItemAxe) {
                if (lastAxe == null || isToolBetter(stack, lastAxe, Blocks.planks)) {
                    lastAxe = stack;
                }
            }
        }
        return lastAxe;
    }

    public static ItemStack getBestPickaxe() {
        int size = getPlayer().inventoryContainer.getInventory().size();
        ItemStack lastPickaxe = null;

        for (int i = 0; i < size; i++) {
            ItemStack stack = getPlayer().inventoryContainer.getInventory().get(i);
            if (stack != null && stack.getItem() instanceof net.minecraft.item.ItemPickaxe) {
                if (lastPickaxe == null || isToolBetter(stack, lastPickaxe, Blocks.stone)) {
                    lastPickaxe = stack;
                }
            }
        }
        return lastPickaxe;
    }

    public static boolean isToolBetter(ItemStack better, ItemStack than, Block versus) {
        return getToolDigEfficiency(better, versus) > getToolDigEfficiency(than, versus);
    }

    public static boolean isSwordBetter(ItemStack better, ItemStack than) {
        return getSwordDamage((ItemSword)better.getItem(), better) >
                getSwordDamage((ItemSword)than.getItem(), than);
    }

    public static float getSwordDamage(ItemSword sword, ItemStack stack) {
        return sword.getAttackDamage() +
                EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F;
    }

    public static float getToolDigEfficiency(ItemStack stack, Block block) {
        float efficiency = stack.getStrVsBlock(block);
        if (efficiency > 1.0F) {
            int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
            if (level > 0) {
                efficiency += (level * level + 1);
            }
        }
        return efficiency;
    }
}