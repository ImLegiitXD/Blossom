package net.minecraft.network.play.client;

import java.io.IOException;
import moshi.blossom.util.network.PacketDir;
import moshi.blossom.util.network.Packets;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C13PacketPlayerAbilities implements Packet<INetHandlerPlayServer> {

    private boolean invulnerable;

    private boolean flying;

    private boolean allowFlying;

    private boolean creativeMode;

    private float flySpeed;

    private float walkSpeed;

    public PacketDir pDir() {
        return PacketDir.CLIENT;

    }

    public Packets pType() {
        return Packets.C_PLAYER_ABILITIES;

    }

public C13PacketPlayerAbilities() {}

    public C13PacketPlayerAbilities(PlayerCapabilities capabilities) {
        this.invulnerable = capabilities.disableDamage;

        this.flying = capabilities.isFlying;

        this.allowFlying = capabilities.allowFlying;

        this.creativeMode = capabilities.isCreativeMode;

        this.flySpeed = capabilities.getFlySpeed();

        this.walkSpeed = capabilities.getWalkSpeed();

    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        byte flags = buf.readByte();

        this.invulnerable = (flags & 0x1) > 0;

        this.flying = (flags & 0x2) > 0;

        this.allowFlying = (flags & 0x4) > 0;

        this.creativeMode = (flags & 0x8) > 0;

        this.flySpeed = buf.readFloat();

        this.walkSpeed = buf.readFloat();

    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        byte flags = 0;

        if (this.invulnerable) flags |= 0x1;

        if (this.flying) flags |= 0x2;

        if (this.allowFlying) flags |= 0x4;

        if (this.creativeMode) flags |= 0x8;

        buf.writeByte(flags);

        buf.writeFloat(this.flySpeed);

        buf.writeFloat(this.walkSpeed);

    }

    public void processPacket(INetHandlerPlayServer handler) {
        handler.processPlayerAbilities(this);

    }

    // Getters
    public boolean isInvulnerable() {
        return this.invulnerable;

    }

    public boolean isFlying() {
        return this.flying;

    }

    public boolean isAllowFlying() {
        return this.allowFlying;

    }

    public boolean isCreativeMode() {
        return this.creativeMode;

    }

    public float getFlySpeed() {
        return this.flySpeed;

    }

    public float getWalkSpeed() {
        return this.walkSpeed;

    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;

    }

    public void setFlying(boolean flying) {
        this.flying = flying;

    }

    public void setAllowFlying(boolean allowFlying) {
        this.allowFlying = allowFlying;

    }

    public void setCreativeMode(boolean creativeMode) {
        this.creativeMode = creativeMode;

    }

    public void setFlySpeed(float flySpeed) {
        this.flySpeed = flySpeed;

    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;

    }

}
