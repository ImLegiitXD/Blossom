package moshi.blossom.module.impl.player;

import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MouseTickEvent;
import moshi.blossom.module.Module;
import moshi.blossom.util.ChatUtil;
import moshi.blossom.util.network.PacketUtil;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class FastUse extends Module {

    @EventLink
    public Listener<MotionEvent> handleMotion;

    @EventLink
    public Listener<MouseTickEvent> handleMouse;

    public FastUse() {
        super("FastUse", "Fast Use", Category.PLAYER);

        this.handleMotion = (event -> {
            // Motion event handling can be added here if needed
        });

        this.handleMouse = (event -> {
            if (!shouldFastUse()) return;

            if (event.getType() == MouseTickEvent.Type.RIGHT_HOLD) {
                event.cancelEvent();

            }

            if (event.getType() != MouseTickEvent.Type.RIGHT) return;

            event.cancelEvent();

            fastUse(getPlayer().getHeldItem());

        });

    }

    private void fastUse(ItemStack stack) {
        PacketUtil.send(new C08PacketPlayerBlockPlacement(stack));

        sendPackets(stack.getMaxItemUseDuration());

    }

    private boolean shouldFastUse() {
        ItemStack stack = getPlayer().getHeldItem();

        if (stack == null) return false;

        return (stack.getItem() instanceof net.minecraft.item.ItemFood && getPlayer().getFoodStats().needFood()) ||
        stack.getItem() instanceof net.minecraft.item.ItemAppleGold ||
        (stack.getItem() instanceof ItemPotion && !ItemPotion.isSplash(stack.getMetadata()));

    }

    private void sendPackets(int packets) {
        ChatUtil.printDebug("Sent " + packets + " packets");

        for (int i = 0; i < packets; i++) {
            PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(
            getPlayer().posX,
            getPlayer().posY,
            getPlayer().posZ,
            getPlayer().rotationYaw,
            getPlayer().rotationPitch,
            getPlayer().onGround
            ));

        }

    }

}
