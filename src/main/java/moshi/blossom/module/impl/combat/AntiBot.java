package moshi.blossom.module.impl.combat;

import moshi.blossom.event.impl.player.UpdateEvent;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ModeOption;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;
import java.util.ArrayList;
import java.util.List;

public class AntiBot extends Module {

    public final ModeOption modeOption = new ModeOption("MODE", "Mode", "Basic", "Basic", "Tab");

    @EventLink
    public Listener<UpdateEvent> handleUpdate;

    public AntiBot() {
        super("AntiBot", "Anti Bot", Category.COMBAT);

        initializeEventHandlers();

        setupOptions(modeOption);

    }

    private void initializeEventHandlers() {
        this.handleUpdate = event -> {
            switch (modeOption.getMode()) {
                case "Basic":
                handleBasicMode();

                break;

                case "Tab":
                handleTabMode();

                break;

            }

        };

    }

    private void handleBasicMode() {
        List<EntityPlayer> potentialBots = new ArrayList<>(mc.theWorld.playerEntities);

        potentialBots.removeIf(player ->
        player == getPlayer() ||
        !isBot(player)
        );

        potentialBots.forEach(bot ->
        mc.theWorld.removeEntity(bot)
        );

    }

    private void handleTabMode() {
        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer &&
            entity != getPlayer() &&
            !isInTabList((EntityPlayer)entity)) {
                mc.theWorld.removeEntity(entity);

            }

        }

    }

    private boolean isBot(EntityPlayer player) {
        return false;

    }

    private boolean isInTabList(EntityPlayer player) {
        return KillAura.getTabList().contains(player);

    }

    @Override
    public String getSuffix() {
        return modeOption.getMode();

    }

}
