package moshi.blossom.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import moshi.blossom.Blossom;

public class ModeHandler {

    private final List<ModuleMode> modeList = new ArrayList<>();

    public void setupModes(ModuleMode... moduleModes) {
        this.modeList.addAll(Arrays.asList(moduleModes));

    }

    public void handle(String mode) {
        for (ModuleMode moduleMode : this.modeList) {
            Blossom.INSTANCE.getEventBus().unsubscribe(moduleMode);

            if (moduleMode.getName().equals(mode)) {
                Blossom.INSTANCE.getEventBus().subscribe(moduleMode);

            }

        }

    }

    public void onEnable(String mode) {
        for (ModuleMode moduleMode : this.modeList) {
            Blossom.INSTANCE.getEventBus().unsubscribe(moduleMode);

            if (moduleMode.getName().equals(mode)) {
                moduleMode.onEnable();

            }

        }

    }

    public void onDisable(String mode) {
        for (ModuleMode moduleMode : this.modeList) {
            Blossom.INSTANCE.getEventBus().unsubscribe(moduleMode);

            if (moduleMode.getName().equals(mode)) {
                moduleMode.onDisable();

            }

        }

    }

}
