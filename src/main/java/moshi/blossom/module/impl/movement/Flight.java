package moshi.blossom.module.impl.movement;

import java.util.Objects;
import moshi.blossom.event.impl.player.CollideEvent;
import moshi.blossom.event.impl.player.MotionEvent;
import moshi.blossom.event.impl.player.MoveEvent;
import moshi.blossom.event.impl.player.PreJumpEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.ModeHandler;
import moshi.blossom.module.Module;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.module.impl.movement.flights.BoostFlight;
import moshi.blossom.module.impl.movement.flights.VulcanFlight;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ContOption;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.player.TimerUtil;
import net.minecraft.util.AxisAlignedBB;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Flight extends Module {

    final ModeHandler modeHandler = new ModeHandler();

    final ModeOption modeOption = new ModeOption("MODE", "Mode", "Vanilla",
    "Vanilla", "Boost", "NCP", "NCP Latest", "Verus", "Vulcan", "Spoof", "Collide", "Buffer Abuse") {
        public void setMode(String newMode) {
            super.setMode(newMode);

            if (Objects.requireNonNull(ModManager.getMod("Flight")).isToggled()) {
                Flight.this.modeHandler.handle(newMode);

            }

        }

    };

    final NumberOption speedOpt = new NumberOption("SPEED", "Speed", 1.0D, 0.3D, 5.0D, 0.05D);

    final ContOption boostCont = new ContOption("BOOST_CONT", "Boost");

    final ContOption vulcanCont = new ContOption("VULCAN_CONT", "Vulcan");

    double startY;

    @EventLink
    public Listener<MoveEvent> handleMove;

    @EventLink
    public Listener<MotionEvent> handleMotion;

    @EventLink
    public Listener<CollideEvent> handleCollide;

    @EventLink
    public Listener<PreJumpEvent> handleJump;

    public Flight() {
        super("Flight", "Flight", Category.MOVEMENT);

        BoostFlight boostFlight = new BoostFlight();

        VulcanFlight vulcanFlight = new VulcanFlight();

        this.handleMove = (event -> {
            if (this.modeOption.is("vanilla")) {
                event.setSpeed(this.speedOpt.getValue());

                event.setY(0.0D);

                if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                    event.setY(0.5D);

                }

                if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    event.setY(-0.5D);

                }

            } else if (this.modeOption.is("ncp") || this.modeOption.is("spoof")) {
                event.setY(0.0D);

            } else if (this.modeOption.is("verus") && event.getPlayer().onGround) {
                event.jump();

            }

        });

        this.handleMotion = (event -> {
            if (event.isPre()) {
                if (this.modeOption.is("vanilla") || this.modeOption.is("spoof")) {
                    event.setGround(true);

                }

                return;

            }

            if (this.modeOption.is("ncp")) {
                getPlayer().setPosition(getPlayer().posX, getPlayer().posY - 1.0E-6D, getPlayer().posZ);

            }

        });

        this.handleCollide = (event -> {
            if ((this.modeOption.is("collide") || this.modeOption.is("verus")) &&
            event.getState().getBlock() instanceof net.minecraft.block.BlockAir &&
            event.getPos().getY() <= this.startY) {
                event.setAlignedBB(AxisAlignedBB.fromBounds(
                event.getPos().getX(),
                event.getPos().getY(),
                event.getPos().getZ(),
                (event.getPos().getX() + 1),
                this.startY,
                (event.getPos().getZ() + 1)
                ));

            }

        });

        this.handleJump = (event -> event.setCanceled(true));

        this.boostCont.setupOptions(boostFlight.getOptions().toArray(new Option[0]));

        this.vulcanCont.setupOptions(vulcanFlight.getOptions().toArray(new Option[0]));

    setupOptions(new Option[] { this.modeOption, this.speedOpt, this.boostCont, this.vulcanCont });

    this.modeHandler.setupModes(new ModuleMode[] { boostFlight, vulcanFlight });

    }

    public String getSuffix() {
        return this.modeOption.getMode();

    }

    public void onEnable() {
        super.onEnable();

        this.startY = getPlayer().posY;

        this.modeHandler.onEnable(this.modeOption.getMode());

        this.modeHandler.handle(this.modeOption.getMode());

    }

    public void onDisable() {
        super.onDisable();

        TimerUtil.setTimer(1.0F);

        this.modeHandler.onDisable(this.modeOption.getMode());

    }

}
