package moshi.blossom.module.impl.render;

import java.awt.Color;
import moshi.blossom.event.impl.render.DrawEvent;
import moshi.blossom.module.Module;
import net.minecraft.client.gui.Gui;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class Crosshair extends Module {

    @EventLink
    final Listener<DrawEvent> handleDraw;

    public Crosshair() {
        super("Crosshair", "Crosshair", Category.RENDER);

        this.handleDraw = (event -> {
            double centerX = (event.getWidth() / 2.0F);

            double centerY = (event.getHeight() / 2.0F);

            int col = ((Color)HUD.hudColor.apply(Integer.valueOf(0))).getRGB();

            int bg = (new Color(1946157056, true)).getRGB();

            Gui.drawRect((int) (centerX - 0.5D - 0.5D), (int) (centerY - 5.0D - 0.5D), (int) (centerX + 0.5D + 0.5D), (int) (centerY - 2.0D + 0.5D), bg);

            Gui.drawRect((int) (centerX - 0.5D), (int) (centerY - 5.0D), (int) (centerX + 0.5D), (int) (centerY - 2.0D), col);

            Gui.drawRect((int) (centerX - 0.5D - 0.5D), (int) (centerY + 2.0D - 0.5D), (int) (centerX + 0.5D + 0.5D), (int) (centerY + 5.0D + 0.5D), bg);

            Gui.drawRect((int) (centerX - 0.5D), (int) (centerY + 2.0D), (int) (centerX + 0.5D), (int) (centerY + 5.0D), col);

            Gui.drawRect((int) (centerX - 5.0D - 0.5D), (int) (centerY - 0.5D - 0.5D), (int) (centerX - 2.0D + 0.5D), (int) (centerY + 0.5D + 0.5D), bg);

            Gui.drawRect((int) (centerX - 5.0D), (int) (centerY - 0.5D), (int) (centerX - 2.0D), (int) (centerY + 0.5D), col);

            Gui.drawRect((int) (centerX + 2.0D - 0.5D), (int) (centerY - 0.5D - 0.5D), (int) (centerX + 5.0D + 0.5D), (int) (centerY + 0.5D + 0.5D), bg);

            Gui.drawRect((int) (centerX + 2.0D), (int) (centerY - 0.5D), (int) (centerX + 5.0D), (int) (centerY + 0.5D), col);

        });

        setToggled(true);

    }

}
