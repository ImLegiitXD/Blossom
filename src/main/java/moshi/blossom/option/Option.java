package moshi.blossom.option;

import java.util.function.Supplier;
import moshi.blossom.module.Module;

public abstract class Option {
    private final String name;

    private final String displayName;

    private Supplier<Boolean> visibilityCondition = () -> true;

    public Option(String name, String displayName) {
        this.name = name;

        this.displayName = displayName;

    }

    public Option(String name, String displayName, Supplier<Boolean> visibilityCondition) {
        this(name, displayName);

        this.visibilityCondition = visibilityCondition;

    }

    public String getName() {
        return name;

    }

    public String getDisplayName() {
        return displayName;

    }

    public String getDescription() {
        return null;

    }

    public Supplier<Boolean> getVisibilityCondition() {
        return visibilityCondition;

    }

    public abstract String getChatStatus();

    public abstract void setByChatString(String input);

    public abstract String pureStrValue();

    public abstract void setByStr(String value);

    public abstract void draw(int mouseX, int mouseY, float x, float y, float width, float height, Module parent);

    public abstract void click(int mouseX, int mouseY, float x, float y, float width, float height, int mouseButton);

    public abstract void update(int mouseX, int mouseY, float x, float y, float width, float height);

}
