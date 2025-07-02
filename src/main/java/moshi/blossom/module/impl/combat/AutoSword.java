package moshi.blossom.module.impl.combat;

import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.event.impl.player.AttackEvent;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.player.InvUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class AutoSword extends Module {

    public final ModeOption modeOption = new ModeOption("MODE", "Mode", "Safe", "Safe", "Instant");

    public final NumberOption safeTicks = new NumberOption("SAFE_TICKS", "Safe Ticks", 2.0, 1.0, 3.0, 1.0);

    private int nextSlot = -1;

    @EventLink
    public Listener<TickEvent> handleTick;

    @EventLink
    public Listener<AttackEvent> handleAttack;

    public AutoSword() {
        super("AutoSword", "Auto Sword", Category.COMBAT);

        initializeEventHandlers();

        setupOptions(modeOption, safeTicks);

    }

    private void initializeEventHandlers() {
        this.handleTick = event -> {
            if (getPlayer() == null) return;

            if (shouldSwitchSword()) {
                getPlayer().inventory.currentItem = nextSlot;

                mc.playerController.syncCurrentPlayItem();

                nextSlot = -1;

            }

        };

        this.handleAttack = event -> {
            if (event.isPost()) return;

            int bestSwordSlot = findBestSwordSlot();

            if (shouldUpdateSlot(bestSwordSlot)) {
                nextSlot = bestSwordSlot;

                if (modeOption.is("instant")) {
                    getPlayer().inventory.currentItem = nextSlot;

                    mc.playerController.syncCurrentPlayItem();

                }

            }

        };

    }

    private boolean shouldSwitchSword() {
        return nextSlot != -1 &&
        modeOption.is("safe") &&
        getPlayer().ticksExisted % safeTicks.getValue() == 0;

    }

    private int findBestSwordSlot() {
        int bestSlot = -1;

        ItemStack bestSword = null;

        for (int i = 0; i < 9; i++) {
            ItemStack current = getPlayer().inventoryContainer.getInventory().get(36 + i);

            if (current != null && current.getItem() instanceof ItemSword) {
                if (bestSword == null || InvUtil.isSwordBetter(current, bestSword)) {
                    bestSlot = i;

                    bestSword = current;

                }

            }

        }

        return bestSlot;

    }

    private boolean shouldUpdateSlot(int slot) {
        return slot != -1 && getPlayer().inventory.currentItem != slot;

    }

    @Override
    public void onEnable() {
        super.onEnable();

        nextSlot = -1;

    }

}
