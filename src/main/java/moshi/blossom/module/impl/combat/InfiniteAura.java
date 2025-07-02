package moshi.blossom.module.impl.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.florianmichael.viamcp.fixes.AttackOrder;
import moshi.blossom.Blossom;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.module.Module;
import moshi.blossom.util.ChatUtil;
import moshi.blossom.util.network.PacketUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class InfiniteAura extends Module {

    @EventLink
    public Listener<MotionEvent> handleTick;

    public InfiniteAura() {
        super("InfiniteAura", "Infinite Aura", Category.COMBAT);

        this.handleTick = event -> {
            if (event.isPost()) return;

            if (getPlayer() == null || mc.theWorld == null) return;

            List<Entity> entities = getNearbyEntities(20.0);

            if (entities.isEmpty()) return;

            Entity target = entities.get(0);

            if (target == null || target.isDead) return;

            double verticalDistance = Math.abs(getPlayer().posY - target.posY);

            if (verticalDistance > 1.0 || !getPlayer().onGround || mc.thePlayer.ticksExisted % 10 != 0) {
                return;

            }

            performAttack(target);

        };

    }

    private void performAttack(Entity target) {
        // Send packets to target position
        sendMovementPackets(
        getPlayer().posX, getPlayer().posY, getPlayer().posZ,
        target.posX, getPlayer().posY, target.posZ,
        100
        );

        // Attack sequence
        getPlayer().swingItem();

        AttackOrder.sendFixedAttack(getPlayer(), target);

        ChatUtil.print("Attacked " + target.getName());

        // Return packets to original position
        sendMovementPackets(
        target.posX, getPlayer().posY, target.posZ,
        getPlayer().posX, getPlayer().posY, getPlayer().posZ,
        100
        );

    }

    private void sendMovementPackets(double startX, double startY, double startZ,
    double endX, double endY, double endZ,
    int packets) {
        double deltaX = endX - startX;

        double deltaZ = endZ - startZ;

        for (int i = 0; i <= packets; i++) {
            double xProgress = deltaX / packets * i;

            double zProgress = deltaZ / packets * i;

            PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(
            startX + xProgress,
            startY,
            startZ + zProgress,
            getPlayer().rotationYaw,
            getPlayer().rotationPitch,
            getPlayer().onGround
            ));

        }

    }

    private List<Entity> getNearbyEntities(double range) {
        List<Entity> entities = new ArrayList<>(mc.theWorld.getLoadedEntityList());

        entities.removeIf(entity ->
        entity == getPlayer() ||
        !(entity instanceof EntityLivingBase) ||
        getPlayer().getDistanceToEntity(entity) > range ||
        Blossom.INSTANCE.getUserHelper().isFriend((EntityLivingBase)entity)
        );

        entities.sort(Comparator.comparingDouble(entity -> getPlayer().getDistanceToEntity(entity)));

        entities.sort((e1, e2) -> Boolean.compare(
        Blossom.INSTANCE.getUserHelper().isTarget((EntityLivingBase)e2),
        Blossom.INSTANCE.getUserHelper().isTarget((EntityLivingBase)e1)
        ));

        return entities;

    }

}
