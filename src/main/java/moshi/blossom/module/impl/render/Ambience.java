package moshi.blossom.module.impl.render;

import moshi.blossom.event.impl.network.PacketEvent;
import moshi.blossom.event.impl.player.UpdateEvent;
import moshi.blossom.module.Module;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Ambience extends Module {

    @EventLink
    public Listener<UpdateEvent> handleUpdate;

    @EventLink
    public Listener<PacketEvent> handlePacket;

    public Ambience() {
        super("Ambience", "Ambience", Category.RENDER);

        this.handleUpdate = (event -> this.mc.theWorld.setWorldTime(18000L));

        this.handlePacket = (event -> {
            if (event.getPacket() instanceof net.minecraft.network.play.server.S03PacketTimeUpdate)
            event.setCanceled(true);

        });

        setToggled(true);

    }

}
