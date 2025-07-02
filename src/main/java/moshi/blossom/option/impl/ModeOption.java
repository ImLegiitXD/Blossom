package moshi.blossom.option.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.util.ChatUtil;
import moshi.skidded.font.Fonts;

public class ModeOption

extends Option
{
    private String mode;

    private final List<String> modeList = new ArrayList<>();

    public ModeOption(String name, String displayName, String defaultMode, String... modes) {
        super(name, displayName);

        this.modeList.addAll(Arrays.asList(modes));

        this.mode = this.modeList.get(this.modeList.indexOf(defaultMode));

    }

    public ModeOption(String name, String displayName, String defaultMode, String[] modes, Supplier<Boolean> display) {
        super(name, displayName, display);

        this.modeList.addAll(Arrays.asList(modes));

        this.mode = this.modeList.get(this.modeList.indexOf(defaultMode));

    }

    public boolean is(String mode) {
        return get().equalsIgnoreCase(mode);

    }

    public boolean is(String... modes) {
        for (String s : modes) {
            if (getMode().equalsIgnoreCase(s))
            return true;

        }

        return false;

    }

    public String get() {
        return getMode().toLowerCase();

    }

    public String getMode() {
        return this.mode;

    }

    public void setMode(String newMode) {
        this.mode = this.modeList.get(this.modeList.indexOf(newMode));

    }

    public void draw(int mouseX, int mouseY, float x, float y, float x2, float y2, Module parent) {
        Fonts.lt15.drawString(getDisplayName(), (x + 3.0F), y + 4.5D, (new Color(14474460)).getRGB());

        Fonts.lt15.drawString(
        getMode().toUpperCase().replaceAll(" ", "_"), (x2 - Fonts.lt15
        .getStringWidth(getMode().toUpperCase().replaceAll(" ", "_")) - 1.0F), y + 4.5D, (new Color(14474460)).getRGB());

    }

    public void click(int mouseX, int mouseY, float x, float y, float x2, float y2, int button) {
        if (button == 0) {
            int index = this.modeList.indexOf(this.mode) + 1;

            if (index > this.modeList.size() - 1)
            index = 0;

            setMode(this.modeList.get(index));

        } else if (button == 1) {
            int index = this.modeList.indexOf(this.mode) - 1;

            if (index < 0)
            index = this.modeList.size() - 1;

            setMode(this.modeList.get(index));

        }

    }

public void update(int mouseX, int mouseY, float x, float y, float x2, float y2) {}

    public String pureStrValue() {
        return getMode();

    }

    public void setByStr(String string) {
        if (this.modeList.contains(string)) {
            setMode(string);

        }

    }

    public String getChatStatus() {
        return "Mode: " + getMode() + " Modes: " + Arrays.toString(this.modeList.toArray());

    }

    public void setByChatString(String string) {
        string = string.replaceAll("_", " ");

        if (!this.modeList.contains(string)) {
            ChatUtil.printWarning("Mode \"" + string + "\" does not exist");

            return;

        }

        setMode(string);

    }

}
