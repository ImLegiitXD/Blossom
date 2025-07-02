package moshi.blossom.module.impl.player;

import java.util.List;
import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.Clock;
import moshi.blossom.util.player.InvUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class AutoArmor extends Module {

    public final NumberOption delayOpt = new NumberOption("DELAY_OPT", "Delay", 50.0D, 0.0D, 300.0D, 25.0D);

    private long delay;

    private final Clock putClock;

    @EventLink
    public Listener<TickEvent> handleTick;

    @EventLink
    public Listener<PacketEvent> handlePacket;

    public AutoArmor() {
        super("AutoArmor", "Auto Armor", Category.PLAYER);

        this.delay = 0L;

        this.putClock = new Clock();

        this.handleTick = (event -> {
            if (getPlayer() == null || this.mc.theWorld == null) return;

            InvManager invManager = (InvManager)ModManager.getMod("InvManager");

            if (invManager.isToggled() && !invManager.dropClock.elapsed((long)(invManager.delayOpt.getValue() + 50.0D))) {
                return;

            }

            if (!(this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)) return;

            List<ItemStack> inventory = getPlayer().inventoryContainer.getInventory();

            int size = inventory.size();

            if (this.putClock.elapsed(this.delay)) {
                for (int i = 8; i < size; i++) {
                    ItemStack stack = inventory.get(i);

                    if (stack == null) continue;

                    if (stack != InvUtil.equipedHelmet() && stack == InvUtil.getBestArmorPart(InvUtil.helmetParts)) {
                        handleArmorPiece(i, 5, InvUtil.equipedHelmet());

                        break;

                    }

                    if (stack != InvUtil.equipedChestplate() && stack == InvUtil.getBestArmorPart(InvUtil.chestParts)) {
                        handleArmorPiece(i, 6, InvUtil.equipedChestplate());

                        break;

                    }

                    if (stack != InvUtil.equipedLeggings() && stack == InvUtil.getBestArmorPart(InvUtil.leggingParts)) {
                        handleArmorPiece(i, 7, InvUtil.equipedLeggings());

                        break;

                    }

                    if (stack != InvUtil.equipedBoots() && stack == InvUtil.getBestArmorPart(InvUtil.bootParts)) {
                        handleArmorPiece(i, 8, InvUtil.equipedBoots());

                        break;

                    }

                }

            }

        });

        this.handlePacket = (event -> {
            if (event.getPacket() instanceof C16PacketClientStatus &&
            ((C16PacketClientStatus)event.getPacket()).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                resetTimers();

            }

            if (event.getPacket() instanceof net.minecraft.network.play.server.S2DPacketOpenWindow) {
                resetTimers();

            }

        });

    setupOptions(new Option[] { this.delayOpt });

    }

    private void handleArmorPiece(int inventorySlot, int armorSlot, ItemStack equipped) {
        if (equipped == null) {
            put(inventorySlot);

        } else {
            swap(armorSlot, inventorySlot);

        }

        this.putClock.reset();

        this.delay = nextDelay();

    }

    private long nextDelay() {
        return (long)this.delayOpt.getValue();

    }

    private void resetTimers() {
        this.putClock.reset();

        this.delay = 100L;

    }

    public void onEnable() {
        super.onEnable();

        this.delay = 0L;

        this.putClock.reset();

    }

    private void put(int slot) {
        this.mc.playerController.windowClick(
        getPlayer().openContainer.windowId,
        slot,
        0,
        1,
        getPlayer()
        );

    }

    private void swap(int slot, int to) {
        this.mc.playerController.windowClick(
        getPlayer().openContainer.windowId,
        slot,
        0,
        4,
        getPlayer()
        );

        put(to);

    }

}
