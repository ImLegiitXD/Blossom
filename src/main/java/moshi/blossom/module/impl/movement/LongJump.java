package moshi.blossom.module.impl.movement;

import java.util.Objects;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.ModeHandler;
import moshi.blossom.module.Module;
import moshi.blossom.module.ModuleMode;
import moshi.blossom.module.impl.movement.longjumps.NCPLongJump;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.util.player.TimerUtil;

public class LongJump extends Module {

    final ModeHandler modeHandler = new ModeHandler();

    final ModeOption modeOption = new ModeOption("MODE", "Mode", "Vanilla",
    "Vanilla", "NCP", "NCP 2") {
        public void setMode(String newMode) {
            super.setMode(newMode);

            if (((Module)Objects.<Module>requireNonNull(ModManager.getMod("LongJump"))).isToggled()) {
                LongJump.this.modeHandler.handle(newMode);

            }

        }

    };

    public LongJump() {
        super("LongJump", "Long Jump", Category.MOVEMENT);

        setupOptions(this.modeOption);

        this.modeHandler.setupModes(new NCPLongJump());

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
