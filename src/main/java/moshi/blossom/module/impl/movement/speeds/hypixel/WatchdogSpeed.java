package moshi.blossom.module.impl.movement.speeds.hypixel;

import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.util.player.MoveUtil;
import net.minecraft.potion.Potion;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class WatchdogSpeed extends ModuleMode {

    private enum SpeedState {
        IDLING,
        GROUND,
        BOOSTING,
        AIR,
        DAMAGED
    }

    private SpeedState state;

    private int hops;

    private int airTicks;

    private double motion;

    @EventLink
    public Listener<MoveEvent> handleMove;

    @EventLink
    public Listener<MotionEvent> handleMotion;

    public WatchdogSpeed() {
        super("Watchdog");

        this.handleMove = (event -> {
            if (mc.currentScreen != null) return;

            double boost = calculateBoost();

            handleStateManagement(boost);

            switch (state) {
                case GROUND:
                handleGroundMovement(event, boost);

                break;

                case BOOSTING:
                handleBoostingMovement(event);

                break;

                case AIR:
                handleAirMovement(event, boost);

                break;

                case IDLING:
                handleIdlingMovement(event);

                break;

            }

        });

    this.handleMotion = (event -> {});

    }

    private double calculateBoost() {
        double boost = 0.6013831405863909D;

        if (getPlayer().isPotionActive(Potion.moveSpeed)) {
            int amplifier = getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier();

            boost *= 1.0D + (amplifier + 1) * 0.24D;

        }

        return boost;

    }

    private void handleStateManagement(double boost) {
        if (getPlayer().onGround) {
            state = SpeedState.GROUND;

            airTicks = 0;

        } else {
            if (state == SpeedState.BOOSTING) {
                state = SpeedState.AIR;

            } else if (state == SpeedState.GROUND) {
                state = SpeedState.BOOSTING;

            }

            airTicks++;

        }

        if (state == SpeedState.IDLING && getPlayer().hurtTime > 0 && !getPlayer().onGround) {
            state = SpeedState.AIR;

        }

    }

    private void handleGroundMovement(MoveEvent event, double boost) {
        double delta = boost - motion;

        motion += delta / 5.0D;

        event.setY(0.41999998688697815D);

        MoveUtil.setSpeed(event, motion);

        hops++;

        if (hops >= 7) {
            motion = boost * 0.98D;

            hops = 0;

        }

    }

    private void handleBoostingMovement(MoveEvent event) {
        event.setX(event.getX() * 1.025D);

        event.setZ(event.getZ() * 1.025D);

    }

    private void handleAirMovement(MoveEvent event, double boost) {
        if (getPlayer().hurtTime >= 9) {
            MoveUtil.setSpeed(event, boost * 0.7D);

        } else if (getPlayer().hurtTime > 0) {
            MoveUtil.setSpeed(event, MoveUtil.getSpeed(event));

        } else if (airTicks == 4 || airTicks == 6) {
            event.setX(event.getX() * 1.012D);

            event.setZ(event.getZ() * 1.012D);

        }

    }

    private void handleIdlingMovement(MoveEvent event) {
        event.setX(event.getX() * 0.5D);

        event.setZ(event.getZ() * 0.5D);

        motion = calculateBoost() * 0.98D;

    }

    public void onEnable() {
        state = SpeedState.IDLING;

        hops = 0;

        airTicks = 0;

    }

}
