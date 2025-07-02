package moshi.blossom.module.impl.movement;

import java.util.Objects;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.ModeHandler;
import moshi.blossom.module.Module;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.module.impl.movement.speeds.hypixel.WatchdogSpeed;
import moshi.blossom.module.impl.movement.speeds.ncp.CustomNCPSpeed;
import moshi.blossom.module.impl.movement.speeds.ncp.NCPSpeed;
import moshi.blossom.module.impl.movement.speeds.other.*;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ContOption;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.option.impl.NumberOption;
import moshi.blossom.util.player.TimerUtil;

public class Speed extends Module {

    final ModeHandler modeHandler = new ModeHandler();

    public final ModeOption modeOption = new ModeOption("MODE", "Mode", "BHop",
    "BHop", "BHop 2", "BHop 3", "Watchdog", "Packet", "NCP", "NCP Custom", "No Rules", "Vulcan") {
        public void setMode(String newMode) {
            super.setMode(newMode);

            if (Objects.requireNonNull(ModManager.getMod("Speed")).isToggled()) {
                Speed.this.modeHandler.handle(newMode);

            }

        }

    };

    public final NumberOption ncpBoost = new NumberOption("NCP_BOOST", "NCP Boost", 1.0D, 1.0D, 2.0D, 0.01D);

    public final ContOption customNCPCont = new ContOption("CUSTOM_NCP_CONT", "Custom NCP");

    public Speed() {
        super("Speed", "Speed", Category.MOVEMENT);

        CustomNCPSpeed customNCPSpeed = new CustomNCPSpeed();

        setupOptions(this.modeOption, this.ncpBoost, this.customNCPCont);

        this.customNCPCont.setupOptions(new Option[] {
            customNCPSpeed.ncpcDirectionFix,
            customNCPSpeed.ncpcMotionReset,
            customNCPSpeed.ncpcGroundBoost,
            customNCPSpeed.ncpcEffectMult,
            customNCPSpeed.ncpcGroundFriction,
            customNCPSpeed.ncpcScaffoldFactor
        });

        this.modeHandler.setupModes(new ModuleMode[] {
            new NCPSpeed(),
            new WatchdogSpeed(),
            new BHopSpeed(),
            new BHop2Speed(),
            new BHop3Speed(),
            new NoRulesSpeed(),
            new PacketSpeed(),
            new VulcanSpeed(),
            customNCPSpeed
        });

    }

    public String getSuffix() {
        return this.modeOption.getMode();

    }

    public void onEnable() {
        super.onEnable();

        this.modeHandler.onEnable(this.modeOption.getMode());

        this.modeHandler.handle(this.modeOption.getMode());

    }

    public void onDisable() {
        super.onDisable();

        TimerUtil.setTimer(1.0F);

        this.modeHandler.onDisable(this.modeOption.getMode());

    }

}
