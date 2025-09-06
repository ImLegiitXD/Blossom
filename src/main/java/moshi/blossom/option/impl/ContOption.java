package moshi.blossom.option.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.ui.guis.AstolfoClickGui;
import moshi.skidded.font.Fonts;
import net.minecraft.client.gui.Gui;

@Setter
@Getter
public class ContOption extends Option
{
    private final List<Option> optionList = new ArrayList<>();

    private boolean expanded;

    public ContOption(String name, String displayName) {
        super(name, displayName);

    }

    public ContOption(String name, String displayName, Supplier<Boolean> display) {
        super(name, displayName, display);

    }

    public void setupOptions(Option... options) {
        this.optionList.addAll(Arrays.asList(options));

    }

    public void draw(int mouseX, int mouseY, float x, float y, float x2, float y2, Module parent) {
        if (isExpanded()) {
            Gui.drawRect((int) x, (int) y, (int) x2, (int) y2, (new Color(987152)).getRGB());
        }

        Fonts.lt15.drawString(getDisplayName() + "...", (x + (x2 - x) / 2.0F - Fonts.lt15.getStringWidth(getDisplayName() + "...") / 2.0F), y + 4.5D,
        isExpanded() ? (parent.getCategory()).color.getRGB() : (new Color(14474460)).getRGB());
    }

    public void click(int mouseX, int mouseY, float x, float y, float x2, float y2, int button) {
        if (button == 0) {
            setExpanded(!isExpanded());
            AstolfoClickGui.selectedOpt = null;
        }
    }

public void update(int mouseX, int mouseY, float x, float y, float x2, float y2) {}

    public String pureStrValue() {
        return "";
    }

public void setByStr(String string) {}

    public String getChatStatus() {
        return "Size: " + this.optionList.size();
    }

public void setByChatString(String string) {}
}
