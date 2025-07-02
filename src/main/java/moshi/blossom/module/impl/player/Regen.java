package moshi.blossom.module.impl.player;

import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.ChatUtil;
import moshi.blossom.util.network.PacketUtil;
import moshi.blossom.util.player.MoveUtil;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Regen extends Module {

    @EventLink(1)
    final Listener<MotionEvent> handleMotion;

    final NumberOption packets = new NumberOption("PACKETS", "Packets", 30.0D, 1.0D, 50.0D, 1.0D);

    public Regen() {
        super("Regen", "Regen", Category.PLAYER);

        this.handleMotion = (event -> {
            if (event.isPost())
            return;

            if (ModManager.getMod("FastBow").isToggled() && GameSettings.isKeyDown(this.mc.gameSettings.keyBindUseItem))
            return;

            if (ModManager.getMod("Timer").isToggled() && this.mc.timer.timerSpeed > 1.35D)
            return;

            if (getPlayer().onGround && !MoveUtil.isMoving() && !getPlayer().isUsingItem() &&
            getPlayer().getHealth() < getPlayer().getMaxHealth()) {
                ChatUtil.printDebug("sent");

                sendPackets(event.getYaw(), event.getPitch(), (int)this.packets.getValue());

            }

        });

    setupOptions(new Option[] { this.packets });

    }

    private void sendPackets(float yaw, float pitch, int packets) {
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
