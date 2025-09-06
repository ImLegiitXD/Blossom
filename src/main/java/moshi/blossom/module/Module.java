package moshi.blossom.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import moshi.blossom.Blossom;
import moshi.blossom.event.impl.client.DisableModEvent;
import moshi.blossom.event.impl.client.EnableModEvent;
import moshi.blossom.option.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

@Getter
@Setter
public class Module {

    private final String name;
    private final String displayName;
    private final Category category;

    private int toggleKey;
    private boolean toggled;
    private boolean expanded;
    public float smoothX;
    public float smoothY;
    public boolean shouldDraw;

    protected Minecraft mc = Minecraft.getMinecraft();
    private final List<Option> options = new ArrayList<>();

    public Module(String name, String displayName, Category category) {
        this.name = name;
        this.displayName = displayName;
        this.category = category;
    }

    public void onEnable() {
        (new EnableModEvent(this)).call();
        Blossom.INSTANCE.getEventBus().subscribe(this);
    }

    public void onDisable() {
        (new DisableModEvent(this)).call();
        Blossom.INSTANCE.getEventBus().unsubscribe(this);
    }

    public void toggle() {
        setToggled(!isToggled());
    }

    public void setupOptions(Option... options) {
        this.options.addAll(Arrays.asList(options));
    }

    public void setToggled(boolean toggled) {
        if (this.toggled == toggled) return;

        this.toggled = toggled;

        if (toggled) {
            onEnable();

        } else {
            onDisable();

        }

    }

    public String getSuffix() {
        return null;

    }

    public String getDescription() {
        return null;

    }

    protected EntityPlayerSP getPlayer() {
        return (Minecraft.getMinecraft()).thePlayer;

    }

    public enum Category {

        COMBAT(new Color(233, 76, 61)),
        MOVEMENT(new Color(47, 204, 114)),
        PLAYER(new Color(142, 67, 174)),
        RENDER(new Color(56, 0, 207)),
        MISC(new Color(244, 155, 19)),
        EXPLOIT(new Color(52, 151, 218)),
        SCRIPT(new Color(252, 218, 95));

        public float posX = 20.0F;
        public float posY = 20.0F;
        public final Color color;

        Category(Color color) {
            this.color = color;
        }

        public static void setup() {
            for (int i = 0; i < values().length; i++) {
                values()[i].posX = (20 + 134 * i);
            }
        }
    }
}
