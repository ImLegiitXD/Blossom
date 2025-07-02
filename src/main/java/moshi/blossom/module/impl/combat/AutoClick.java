package moshi.blossom.module.impl.combat;

import moshi.blossom.event.impl.TickEvent;
import moshi.blossom.event.impl.player.MouseTickEvent;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.Clock;
import moshi.blossom.util.MathUtil;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class AutoClick extends Module {

    private final NumberOption speed = new NumberOption("SPEED", "Speed", 13.0, 1.0, 20.0, 0.05);

    private final NumberOption randomness = new NumberOption("RANDOMNESS", "Randomness", 2.0, 0.0, 10.0, 0.025);

    private final Clock clock = new Clock();

    private long nextDelay;

    @EventLink
    public Listener<MouseTickEvent> handleMouse;

    @EventLink
    public Listener<TickEvent> handleTick;

    public AutoClick() {
        super("AutoClick", "Auto Click", Category.COMBAT);

        initializeEventHandlers();

        setupOptions(speed, randomness);

    }

    private void initializeEventHandlers() {
        this.handleMouse = event -> {
            if (event.getType() == MouseTickEvent.Type.LEFT && shouldAutoClick()) {
                event.setCanceled(true);

            }

        };

        this.handleTick = event -> {
            if (mc.currentScreen != null) return;

            if (shouldAutoClick() && clock.elapsed(nextDelay - 7L)) {
                clickMouse();

                nextDelay = calculateNextDelay();

                clock.reset();

            }

        };

    }

    private long calculateNextDelay() {
        double clicksPerSecond = (randomness.getValue() == 0.0)
        ? speed.getValue()
        : Math.max(1.0, MathUtil.randomDouble(
        speed.getValue() - randomness.getValue(),
        speed.getValue()));

        return (long)(1000.0 / clicksPerSecond);

    }

    private boolean shouldAutoClick() {
        return GameSettings.isKeyDown(mc.gameSettings.keyBindAttack);

    }

    private void clickMouse() {
        getPlayer().swingItem();

        MovingObjectPosition target = mc.objectMouseOver;

        if (target == null) return;

        if (target.typeOfHit == MovingObjectType.ENTITY) {
            mc.playerController.attackEntity((EntityPlayer)getPlayer(), target.entityHit);

        }

    }

    @Override
    public void onEnable() {
        super.onEnable();

        clock.reset();

        nextDelay = 0L;

    }

}
