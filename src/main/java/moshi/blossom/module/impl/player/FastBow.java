package moshi.blossom.module.impl.player;

import moshi.blossom.event.impl.player.MouseTickEvent;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.util.ChatUtil;
import moshi.blossom.util.network.PacketUtil;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class FastBow extends Module {

    public final ModeOption modeOption = new ModeOption("MODE", "Mode", "RPK",
new String[] { "RPK", "AK-47", "M97", "SPAS-12", "AR-15", "Uzi", "Tank" });

    @EventLink
    public Listener<MouseTickEvent> handleMouse;

    public FastBow() {
        super("FastBow", "Fast Bow", Category.PLAYER);

        this.handleMouse = (event -> {
            if (!shouldFastUse()) return;

            if (this.modeOption.is("ar-15")) {
                if (event.getType() == MouseTickEvent.Type.RIGHT_HOLD) {
                    event.cancelEvent();

                }

                if (event.getType() != MouseTickEvent.Type.RIGHT) return;

                event.cancelEvent();

                fastUse(getPlayer().getHeldItem(), getPlayer().rotationYaw, getPlayer().rotationPitch);

            } else {
                if (event.getType() != MouseTickEvent.Type.RIGHT &&
                event.getType() != MouseTickEvent.Type.RIGHT_HOLD) return;

                event.cancelEvent();

                handleWeaponModes();

            }

        });

    setupOptions(new Option[] { this.modeOption });

    }

    private void handleWeaponModes() {
        switch (this.modeOption.get()) {
            case "rpk":
            fastUse(getPlayer().getHeldItem(), getPlayer().rotationYaw, getPlayer().rotationPitch);

            break;

            case "ak-47":
            if (getPlayer().ticksExisted % 2 == 0) {
                fastUse(getPlayer().getHeldItem(), getPlayer().rotationYaw, getPlayer().rotationPitch);

            }

            break;

            case "m97":
            if (getPlayer().ticksExisted % 4 == 0) {
                fastUse(getPlayer().getHeldItem(), getPlayer().rotationYaw, getPlayer().rotationPitch);

                fastUse(getPlayer().getHeldItem(), getPlayer().rotationYaw + 4.0F, getPlayer().rotationPitch);

                fastUse(getPlayer().getHeldItem(), getPlayer().rotationYaw - 4.0F, getPlayer().rotationPitch);

            }

            break;

            case "spas-12":
            if (getPlayer().ticksExisted % 12 == 0) {
                fireShotgunSpread();

            }

            break;

            case "tank":
            if (getPlayer().ticksExisted % 2 == 0 && getPlayer().onGround) {
                float angle = getPlayer().ticksExisted % 90.0F * 4.0F;

                fastUse(getPlayer().getHeldItem(), getPlayer().rotationYaw + angle, getPlayer().rotationPitch);

                fastUse(getPlayer().getHeldItem(), getPlayer().rotationYaw + angle - 180.0F, getPlayer().rotationPitch);

            }

            break;

            case "uzi":
            int t = getPlayer().ticksExisted % 7 - 3;

            fastUse(getPlayer().getHeldItem(), getPlayer().rotationYaw + (t * 2), getPlayer().rotationPitch);

            break;

        }

    }

    private void fireShotgunSpread() {
        float baseYaw = getPlayer().rotationYaw;

        float basePitch = getPlayer().rotationPitch;

        fastUse(getPlayer().getHeldItem(), baseYaw, basePitch);

        fastUse(getPlayer().getHeldItem(), baseYaw + 2.0F, basePitch);

        fastUse(getPlayer().getHeldItem(), baseYaw - 2.0F, basePitch);

        fastUse(getPlayer().getHeldItem(), baseYaw, basePitch - 2.0F);

        fastUse(getPlayer().getHeldItem(), baseYaw + 2.0F, basePitch - 2.0F);

        fastUse(getPlayer().getHeldItem(), baseYaw - 2.0F, basePitch - 2.0F);

        fastUse(getPlayer().getHeldItem(), baseYaw, basePitch + 2.0F);

        fastUse(getPlayer().getHeldItem(), baseYaw + 2.0F, basePitch + 2.0F);

        fastUse(getPlayer().getHeldItem(), baseYaw - 2.0F, basePitch + 2.0F);

    }

    private void fastUse(ItemStack stack, float yaw, float pitch) {
        pitch = MathHelper.clamp_float(pitch, -90.0F, 90.0F);

        PacketUtil.send(new C08PacketPlayerBlockPlacement(stack));

        sendPackets(20, yaw, pitch);

        PacketUtil.send(new C07PacketPlayerDigging(
        C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
        BlockPos.ORIGIN,
        EnumFacing.DOWN
        ));

    }

    public String getSuffix() {
        return this.modeOption.getMode();

    }

    private boolean shouldFastUse() {
        ItemStack stack = getPlayer().getHeldItem();

        return stack != null && stack.getItem() instanceof ItemBow;

    }

    private void sendPackets(int packets, float yaw, float pitch) {
        ChatUtil.printDebug("Sent " + packets + " packets");

        for (int i = 0; i < packets; i++) {
            PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(
            getPlayer().posX,
            getPlayer().posY,
            getPlayer().posZ,
            yaw,
            pitch,
            getPlayer().onGround
            ));

        }

    }

}
