package moshi.blossom.module.impl.movement.speeds.other;

import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.util.player.TimerUtil;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class VulcanSpeed extends ModuleMode {

    private double speed;

    private int ticks;

    @EventLink
    public final Listener<MoveEvent> handleEvent;

    @EventLink
    public final Listener<MotionEvent> handleMotion;

    public VulcanSpeed() {
        super("Vulcan");

        this.handleEvent = (event -> {
            if (event.getPlayer().onGround) {
                TimerUtil.setTimer(1.0F);

                event.setY(0.41999998688697815D);

                this.speed = 0.6061590433001639D;

                this.ticks = 0;

            } else {
                if (this.ticks == 0) {
                    this.speed *= 0.5934960376506356D;

                } else {
                    this.speed *= 0.989D;

                }

                if (this.ticks == 7) {
                    TimerUtil.setTimer(1.2F);

                }

                if (this.ticks == 9) {
                    TimerUtil.setTimer(0.9F);

                }

                this.ticks++;

            }

            event.setSpeed(this.speed);

        });

        this.handleMotion = (event -> {
        });

    }

}
