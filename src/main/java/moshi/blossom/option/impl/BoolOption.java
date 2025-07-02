package moshi.blossom.option.impl;

import java.awt.Color;
import java.util.function.Supplier;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.skidded.font.Fonts;
import net.minecraft.client.gui.Gui;

public class BoolOption

extends Option
{
    private boolean enabled;

    public BoolOption(String name, String displayName, boolean enabled) {
        super(name, displayName);

        this.enabled = enabled;

    }

    public BoolOption(String name, String displayName, boolean enabled, Supplier<Boolean> display) {
        super(name, displayName, display);

        this.enabled = enabled;

    }

    public boolean isEnabled() {
        return this.enabled;

    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

    }

    public void draw(int mouseX, int mouseY, float x, float y, float x2, float y2, Module parent) {
        if (isEnabled())
        Gui.drawRect((int) x, (int) y, (int) x2, (int) y2, (parent.getCategory()).color.getRGB());

        Fonts.lt15.drawString(getDisplayName(), (x + 3.0F), y + 4.5D, (new Color(14474460)).getRGB());

    }

    public void click(int mouseX, int mouseY, float x, float y, float x2, float y2, int button) {
        setEnabled(!isEnabled());

    }

public void update(int mouseX, int mouseY, float x, float y, float x2, float y2) {}

    public String pureStrValue() {
        return String.valueOf(this.enabled);

    }

    public void setByStr(String string) {
        this.enabled = Boolean.parseBoolean(string);

    }

    public String getChatStatus() {
        return String.valueOf(this.enabled);

    }

    public void setByChatString(String string) {
        this.enabled = Boolean.parseBoolean(string);

    }

}
