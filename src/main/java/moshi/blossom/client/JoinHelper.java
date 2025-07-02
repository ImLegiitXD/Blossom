package moshi.blossom.client;

import java.util.List;
import moshi.blossom.Blossom;
import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.util.network.PacketUtil;
import moshi.blossom.util.player.MoveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class JoinHelper

{
    public boolean joining;

    public Item item;

    public int number;

    public int delay;

    public int stage;

    public boolean foundItem;

    public boolean foundGame;

    public boolean foundLobby;

    @EventLink
    public Listener<PacketEvent> handlePacket;

    @EventLink
    public Listener<TickEvent> handleMotion;

    public JoinHelper() {
        this.handlePacket = (event -> {
            if (event.getPacket() instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
                this.joining = false;

            }

            if (this.stage == 2 && event.getPacket() instanceof net.minecraft.network.play.server.S2DPacketOpenWindow) {
                this.stage = 3;

            }

            if (this.stage >= 3 && event.getPacket() instanceof net.minecraft.network.play.server.S2EPacketCloseWindow)
            this.stage = 0;

        });

        this.handleMotion = (event -> {
            if ((Minecraft.getMinecraft()).theWorld == null || (Minecraft.getMinecraft()).thePlayer == null)
            return;

            if ((Minecraft.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.GuiChat || MoveUtil.isMoving()) {
                this.joining = false;

                return;

            }

            if (!this.joining)
            return;

            Minecraft mc = Minecraft.getMinecraft();

            EntityPlayerSP player = (Minecraft.getMinecraft()).thePlayer;

            switch (this.stage) {
                case 0:
                if (!this.foundItem && player.inventoryContainer.getSlot(36).getHasStack()) {
                    PacketUtil.send((Packet)new C08PacketPlayerBlockPlacement(player.getHeldItem()));

                    this.stage++;

                }

                break;

                case 1:
                if (mc.currentScreen instanceof GuiContainer) {
                    GuiContainer container = (GuiContainer)mc.currentScreen;

                    List<ItemStack> inventory = container.inventorySlots.getInventory();

                    for (int i = 0; i < inventory.size(); i++) {
                        ItemStack slot = inventory.get(i);

                        if (slot != null)
                        if (slot.getItem() == this.item) {
                            PacketUtil.send((Packet)new C0EPacketClickWindow(container.inventorySlots.windowId, i, 0, 0, slot, (short)1));

                            this.stage++;

                            break;

                        }

                    }

                }

                break;

                case 3:
                if (mc.currentScreen instanceof GuiContainer) {
                    GuiContainer container = (GuiContainer)mc.currentScreen;

                    List<ItemStack> inventory = container.inventorySlots.getInventory();

                    for (int i = 0; i < inventory.size(); i++) {
                        ItemStack slot = inventory.get(i);

                        if (slot != null)
                        if (slot.stackSize == this.number) {
                            PacketUtil.send((Packet)new C0EPacketClickWindow(container.inventorySlots.windowId, i, 0, 0, slot, (short)1));

                            this.stage++;

                            break;

                        }

                    }

                }

                break;

                case 4:
                if (player.ticksExisted % 11 == 0)
                this.stage = 3;

                break;

            }

        });

    }

    public static void startJoining(Item name, int lobby) {
        JoinHelper helper = Blossom.INSTANCE.getJoinHelper();

        helper.joining = true;

        helper.item = name;

        helper.number = lobby;

        helper.delay = 0;

        helper.stage = 0;

        helper.foundItem = helper.foundGame = helper.foundLobby = false;

    }

}
