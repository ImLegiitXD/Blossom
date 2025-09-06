package moshi.blossom.option.impl;

import java.awt.Color;
import java.util.function.Supplier;

import lombok.Getter;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.util.ChatUtil;
import moshi.skidded.font.Fonts;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

@Getter
public class NumberOption extends Option {

    private double value;
    private final double min;
    private final double max;
    private final double increment;

    public NumberOption(String name, String displayName, double value, double min, double max, double increment) {
        super(name, displayName);
        this.value = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public NumberOption(String name, String displayName, double value, double min, double max, double increment, Supplier<Boolean> display) {
        super(name, displayName, display);
        this.value = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public void setValue(double value) {
        double prec = 1.0D / this.increment;
        this.value = Math.round(Math.max(this.min, Math.min(this.max, value)) * prec) / prec;
    }

    public void draw(int mouseX, int mouseY, float x, float y, float x2, float y2, Module parent) {
        double delta = (x2 - x);
        double deltaVal = (this.value - this.min) / (this.max - this.min);

        Gui.drawRect((int) x, (int) y, (int) (x + delta * deltaVal), (int) y2, (parent.getCategory()).color.getRGB());

        Gui.drawRect((int) Math.max(x, x + delta * deltaVal - 2.0D), (int) y, (int) (x + delta * deltaVal), (int) y2, (new Color(
        (int)((parent.getCategory()).color.getRed() * 0.7F),
        (int)((parent.getCategory()).color.getGreen() * 0.7F),
        (int)((parent.getCategory()).color.getBlue() * 0.7F))).getRGB());

        Fonts.lt15.drawString(getDisplayName(), (x + 3.0F), y + 4.5D, (new Color(15527148)).getRGB());

        Fonts.lt15.drawString(
        String.valueOf(getValue()).toUpperCase().replaceAll(" ", "_"), (x2 - Fonts.lt15
        .getStringWidth(String.valueOf(getValue()).toUpperCase().replaceAll(" ", "_")) - 1.0F), y + 4.5D, (new Color(15527148)).getRGB());

    }

public void click(int mouseX, int mouseY, float x, float y, float x2, float y2, int button) {}

    public void update(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        double deltaW = (x2 - x);
        double delta = deltaW - (x2 - mouseX);
        double f = delta / deltaW;
        f = MathHelper.clamp_double(f, 0.0D, 1.0D);
        double deltaV = this.max - this.min;
        double val = deltaV * f + this.min;
        setValue(val);
    }

    public String pureStrValue() {
        return String.valueOf(getValue());
    }

    public void setByStr(String string) {
        setValue(Double.parseDouble(string));
    }

    public String getChatStatus() {
        return "Val: " + this.value + " - " + this.min + " / " + this.max;
    }

    public void setByChatString(String string) {
        try {
            setValue(Double.parseDouble(string));
        } catch (Exception e) {
            ChatUtil.printWarning("Something went wrong");
        }
    }
}
