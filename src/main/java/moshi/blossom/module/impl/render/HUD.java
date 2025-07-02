package moshi.blossom.module.impl.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import moshi.blossom.Blossom;
import moshi.blossom.client.BypassHelper;
import moshi.blossom.event.impl.render.DrawEvent;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ModeOption;
import moshi.blossom.ui.Palette;
import moshi.skidded.font.Fonts;
import moshi.skidded.font.MCFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import nevalackin.homoBus.Listener;
import nevalackin.homoBus.annotations.EventLink;

public class HUD extends Module {

public final ModeOption hudMode = new ModeOption("HUD_MODE", "Mode", "Blossom", new String[] { "Blossom", "Blossom 2", "Blossom Old", "Astolfo" });

public final ModeOption modListMode = new ModeOption("MOD_LIST_MODE", "Mod List", "Blossom", new String[] { "Blossom", "Blossom 2", "Blossom Old", "Astolfo", "Astolfo 2", "Astolfo 3", "Nowoline" });

public final ModeOption modListColor = new ModeOption("MOD_LIST_COLOR", "Mod List Color", "Astolfo", new String[] { "Blossom", "Blossom 2", "White", "Black&White", "Astolfo", "Rose", "Red", "Pink", "Purple", "Sky" });

    @EventLink
    public final Listener<DrawEvent> handleDraw;

    public HUD() {
        super("HUD", "HUD", Category.RENDER);

        this.handleDraw = (event -> {
            FontRenderer fr;

            String text;

            String pingText;

            switch (this.hudMode.get()) {
                case "blossom":
                fr = (Minecraft.getMinecraft()).fontRendererObj;

                fr.drawStringWithShadow(EnumChatFormatting.RED + "❤ " + EnumChatFormatting.GRAY + "/" + EnumChatFormatting.RESET + " Blossom" + EnumChatFormatting.GRAY + " !", 3.0F, 4.0F, -1);

                fr.drawStringWithShadow(Math.round((getPlayer()).posX) + ", " + Math.round((getPlayer()).posY) + ", " + Math.round((getPlayer()).posZ), 3.0F, event.getHeight() - 9.0F, -1);

                fr.drawStringWithShadow("BPS: " + ((float)Math.round(Math.sqrt((this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX) * (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX) + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ) * (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ)) * 20.0D * this.mc.timer.timerSpeed * 10.0D) / 10.0F), 3.0F, event.getHeight() - 20.0F, -1);

                fr.drawStringWithShadow("LT: " + BypassHelper.lastUID, event.getWidth() - fr.getStringWidth("LT: " + BypassHelper.lastUID) - 3.0F, event.getHeight() - 31.0F, -1);

                fr.drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), event.getWidth() - fr.getStringWidth("FPS: " + Minecraft.getDebugFPS()) - 3.0F, event.getHeight() - 20.0F, -1);

                text = "\"Me baneó NCP\" " + EnumChatFormatting.GRAY + "- " + (Blossom.INSTANCE.getClientInfo()).build;

                fr.drawStringWithShadow(text, event.getWidth() - fr.getStringWidth(text) - 3.0F, event.getHeight() - 9.0F, -1);

                break;

                case "blossom 2":
                fr = (Minecraft.getMinecraft()).fontRendererObj;

                fr.drawStringWithShadow("Blossom", 3.0F, 4.0F, -1);

                fr.drawStringWithShadow(Math.round((getPlayer()).posX) + ", " + Math.round((getPlayer()).posY) + ", " + Math.round((getPlayer()).posZ), 3.0F, event.getHeight() - 9.0F, -1);

                text = "\"Ah es blink?\" " + EnumChatFormatting.GRAY + "/ b" + (Blossom.INSTANCE.getClientInfo()).build;

                fr.drawStringWithShadow(text, event.getWidth() - fr.getStringWidth(text) - 2.0F, event.getHeight() - 9.0F, -1);

                break;

                case "blossom old":
                Fonts.bl28.drawStringWithShadow("A", 3.0F, 8.0F, -1);

                Fonts.ps16.drawStringWithShadow(EnumChatFormatting.BOLD + "Blossom", 21.0F, 5.0F, -1);

                Fonts.ps15.drawStringWithShadow("dev v" + (Blossom.INSTANCE.getClientInfo()).version, 21.0F, 14.0F, (new Color(14803425)).getRGB());

                Fonts.ps17.drawStringWithShadow(EnumChatFormatting.BOLD + "" + EnumChatFormatting.RESET + Math.round((getPlayer()).posX) + EnumChatFormatting.BOLD + ", " + EnumChatFormatting.RESET + Math.round((getPlayer()).posY) + EnumChatFormatting.BOLD + ", " + EnumChatFormatting.RESET + Math.round((getPlayer()).posZ), 4.0F, event.getHeight() - 10.0F, -1);

                break;

                case "astolfo":
                Fonts.sf18.drawStringWithShadow("h", 1.5D, 3.5D, ((Color)hudColor.apply(Integer.valueOf(0))).getRGB());

                Fonts.sf18.drawStringWithShadow("ilarious packets", 7.0D, 3.5D, (new Color(14935011)).getRGB());

                Fonts.sf18.drawStringWithShadow(Math.round((getPlayer()).posX) + ", " + Math.round((getPlayer()).posY) + ", " + Math.round((getPlayer()).posZ), 1.0D, event.getHeight() - 7.5D, (new Color(14935011)).getRGB());

                pingText = "Ping: " + ((this.mc.getCurrentServerData() == null) ? "0" : Long.valueOf((this.mc.getCurrentServerData()).pingToServer));

                Fonts.sf18.drawStringWithShadow(pingText, (event.getWidth() - Fonts.sf18.getStringWidth(pingText) - 1.0F), event.getHeight() - 7.5D, (new Color(14935011)).getRGB());

                break;

            }

            for (Module module : (Blossom.INSTANCE.getModManager()).moduleList) {
                float speed = event.deltaNs / 1.8F;

                if (module.isToggled()) {
                    module.smoothX = Math.min(1.0F, module.smoothX + speed);

                    module.smoothY = Math.min(1.0F, module.smoothY + speed);

                } else {
                    module.smoothX = Math.max(0.0F, module.smoothX - speed);

                    module.smoothY = Math.max(0.0F, module.smoothY - speed);

                }

                if (module.isToggled()) {
                    module.shouldDraw = true;

                    continue;

                }

                if (module.smoothX == 0.0F && module.smoothY == 0.0F) {
                    module.shouldDraw = false;

                }

            }

            List<Module> toggledMods = new ArrayList<>((Blossom.INSTANCE.getModManager()).moduleList);

            toggledMods.removeIf((module) -> !module.shouldDraw);

            switch (this.modListMode.get()) {
                case "blossom":
                renderModList(event, this.mc.fontRendererObj, toggledMods, 0.0F, 0.0F, -1.5F, 1.5F, 3.5F, 0.0F, 10.5F, (index) -> new Color(0, 0, 0, 120), hudColor, false, true, true);

                break;

                case "blossom 2":
                renderModList(event, this.mc.fontRendererObj, toggledMods, 0.0F, 0.0F, -1.0F, 1.5F, 3.0F, 0.0F, 9.5F, (index) -> new Color(0, 0, 0, 120), hudColor, false, false, false);

                break;

                case "blossom old":
                renderModList(event, Fonts.ps18, toggledMods, 0.0F, 0.0F, -2.5F, 2.0F, 4.5F, 0.0F, 10.0F, (index) -> new Color(0, 0, 0, 120), hudColor, false, true, true);

                break;

                case "astolfo":
                renderModList(event, Fonts.sf18, toggledMods, 1.0F, 0.0F, -2.0F, 2.5F, 3.5F, 0.0F, 11.0F, (index) -> new Color(0, 0, 0, 120), hudColor, true, false, false);

                break;

                case "astolfo 2":
                renderModList(event, Fonts.sf18, toggledMods, 0.0F, 0.0F, -1.5F, 2.5F, 3.5F, 0.0F, 11.0F, (index) -> new Color(0, 0, 0, 120), hudColor, false, false, false);

                break;

                case "nowoline":
                renderModList(event, Fonts.sf18, toggledMods, 1.0F, 0.0F, -2.5F, 2.0F, 4.5F, 0.0F, 10.0F, (index) -> new Color(0, 0, 0, 120), hudColor, true, false, false);

                break;

            }

        });

        setToggled(true);

    setupOptions(new Option[] { (Option)this.hudMode, (Option)this.modListMode, (Option)this.modListColor });

    }

    public static Function<Integer, Color> hudColor;

    private void renderModList(DrawEvent event, FontRenderer font, List<Module> modules, float offsetX, float offsetY, float textOffsetX, float textOffsetY, float additionalLeft, float additionalRight, float height, Function<Integer, Color> backgroundCol, Function<Integer, Color> textCol, boolean rightOutline, boolean leftOutline, boolean horzOutline) {
        modules.sort(Comparator.<Module>comparingDouble(value -> font.getStringWidth(renderName((Module)value))).reversed());

        float y = offsetY;

        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);

            float width = event.getWidth();

            if (rightOutline) {
                Gui.drawRect((int) (width - offsetX + additionalRight), (int) y, (int) (width - offsetX + additionalRight + 1.0F), (int) (y + height), ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

            }

            if (leftOutline) {
                Gui.drawRect((int) (width - offsetX - (font.getStringWidth(renderName(module)) + additionalLeft + 1.0F) * module.smoothX), (int) y, (int) (width - offsetX - (font.getStringWidth(renderName(module)) + additionalLeft) * module.smoothX), (int) (y + height), ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

            }

            if (horzOutline) {
                if (i == modules.size() - 1) {
                    Gui.drawRect((int) (width - offsetX - (font.getStringWidth(renderName(module)) + additionalLeft + 1.0F) * module.smoothX), (int) (y + height), (int) (width - offsetX + additionalRight), (int) (y + height + 1.0F), ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

                } else {
                    double delta = (font.getStringWidth(renderName(modules.get(i))) - font.getStringWidth(renderName(modules.get(i + 1))));

                    if (!((Module)modules.get(i + 1)).isToggled() && i <= modules.size() - 2) {
                        double delta1 = (font.getStringWidth(renderName(modules.get(i))) - font.getStringWidth(renderName(modules.get(i + 2))));

                        double ddelta = delta1 - delta;

                        Gui.drawRect((int) (width - offsetX - font.getStringWidth(renderName(module)) * module.smoothX - additionalLeft - 1.0F), (int) (y + height), (int) ((width - offsetX - font.getStringWidth(renderName(module)) * module.smoothX - additionalLeft) + delta + ddelta * (1.0F - ((Module)modules.get(i + 1)).smoothX)), (int) (y + height + 1.0F), ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

                    } else {
                        Gui.drawRect((int) (width - offsetX - font.getStringWidth(renderName(module)) * module.smoothX - additionalLeft - 1.0F), (int) (y + height), (int) ((width - offsetX - font.getStringWidth(renderName(module)) * module.smoothX - additionalLeft) + delta), (int) (y + height + 1.0F), ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

                    }

                }

            }

            Gui.drawRect((int) (width - offsetX - (font.getStringWidth(renderName(module)) + additionalLeft) * module.smoothX), (int) y, (int) (width - offsetX + additionalRight), (int) (y + height), ((Color)backgroundCol.apply(Integer.valueOf(i))).getRGB());

            font.drawStringWithShadow(renderName(module), width - offsetX - (font.getStringWidth(renderName(module)) - textOffsetX) * module.smoothX, y + textOffsetY, ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

            y += height * module.smoothY;

        }

    }

    private void renderModList(DrawEvent event, MCFontRenderer font, List<Module> modules, float offsetX, float offsetY, float textOffsetX, float textOffsetY, float additionalLeft, float additionalRight, float height, Function<Integer, Color> backgroundCol, Function<Integer, Color> textCol, boolean rightOutline, boolean leftOutline, boolean horzOutline) {
        modules.sort(Comparator.<Module>comparingDouble(value -> font.getStringWidth(renderName((Module)value))).reversed());

        float y = offsetY;

        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);

            float width = event.getWidth();

            if (rightOutline) {
                Gui.drawRect((int) (width - offsetX + additionalRight), (int) y, (int) (width - offsetX + additionalRight + 1.0F), (int) (y + height), ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

            }

            if (leftOutline) {
                Gui.drawRect((int) (width - offsetX - (font.getStringWidth(renderName(module)) + additionalLeft + 1.0F) * module.smoothX), (int) y, (int) (width - offsetX - (font.getStringWidth(renderName(module)) + additionalLeft) * module.smoothX), (int) (y + height), ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

            }

            if (horzOutline) {
                if (i == modules.size() - 1) {
                    Gui.drawRect((int) (width - offsetX - (font.getStringWidth(renderName(module)) + additionalLeft + 1.0F) * module.smoothX), (int) (y + height), (int) (width - offsetX + additionalRight), (int) (y + height + 1.0F), ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

                } else {
                    double delta = (font.getStringWidth(renderName(modules.get(i))) - font.getStringWidth(renderName(modules.get(i + 1))));

                    if (!((Module)modules.get(i + 1)).isToggled() && i <= modules.size() - 2) {
                        double delta1 = (font.getStringWidth(renderName(modules.get(i))) - font.getStringWidth(renderName(modules.get(i + 2))));

                        double ddelta = delta1 - delta;

                        Gui.drawRect((int) (width - offsetX - font.getStringWidth(renderName(module)) * module.smoothX - additionalLeft - 1.0F), (int) (y + height), (int) ((width - offsetX - font.getStringWidth(renderName(module)) * module.smoothX - additionalLeft) + delta + ddelta * (1.0F - ((Module)modules.get(i + 1)).smoothX)), (int) (y + height + 1.0F), ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

                    } else {
                        Gui.drawRect((int) (width - offsetX - font.getStringWidth(renderName(module)) * module.smoothX - additionalLeft - 1.0F), (int) (y + height), (int) ((width - offsetX - font.getStringWidth(renderName(module)) * module.smoothX - additionalLeft) + delta), (int) (y + height + 1.0F), ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

                    }

                }

            }

            Gui.drawRect((int) (width - offsetX - (font.getStringWidth(renderName(module)) + additionalLeft) * module.smoothX), (int) y, (int) (width - offsetX + additionalRight), (int) (y + height), ((Color)backgroundCol.apply(Integer.valueOf(i))).getRGB());

            font.drawStringWithShadow(renderName(module), width - offsetX - (font.getStringWidth(renderName(module)) - textOffsetX) * module.smoothX, y + textOffsetY, ((Color)textCol.apply(Integer.valueOf(i))).getRGB());

            y += height * module.smoothY;

        }

    }

    private String renderName(Module module) {
        return module.getDisplayName() + ((module.getSuffix() == null) ? "" : (EnumChatFormatting.GRAY + " " + module.getSuffix()));

    }

    public static float colorOffset(int index) {
        long currentMillis = System.currentTimeMillis();

        return (float)((currentMillis + index) % 2000L) / 1000.0F;

    }

    public static int fadeBetween(int color1, int color2, float offset) {
        if (offset > 1.0F) {
            offset = 1.0F - offset % 1.0F;

        }

        double invert = (1.0F - offset);

        int r = (int)((color1 >> 16 & 0xFF) * invert + ((color2 >> 16 & 0xFF) * offset));

        int g = (int)((color1 >> 8 & 0xFF) * invert + ((color2 >> 8 & 0xFF) * offset));

        int b = (int)((color1 & 0xFF) * invert + ((color2 & 0xFF) * offset));

        int a = (int)((color1 >> 24 & 0xFF) * invert + ((color2 >> 24 & 0xFF) * offset));

        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;

    }

    public static int fadeBetween(int color1, int color2, int color3, float offset) {
        int colorA, colorB;

        if (offset > 1.0F) {
            offset = 1.0F - offset % 1.0F;

        }

        if (offset < 0.5D) {
            colorA = color1;

            colorB = color2;

            offset *= 2.0F;

        } else {
            colorA = color2;

            colorB = color3;

            offset = (offset - 0.5F) * 2.0F;

        }

        double invert = (1.0F - offset);

        int r = (int)((colorA >> 16 & 0xFF) * invert + ((colorB >> 16 & 0xFF) * offset));

        int g = (int)((colorA >> 8 & 0xFF) * invert + ((colorB >> 8 & 0xFF) * offset));

        int b = (int)((colorA & 0xFF) * invert + ((colorB & 0xFF) * offset));

        int a = (int)((colorA >> 24 & 0xFF) * invert + ((colorB >> 24 & 0xFF) * offset));

        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;

    }

    public static int fadeBetween(int color1, int color2, int color3, int color4, float offset) {
        int colorA, colorB;

        if (offset > 1.0F) {
            offset = 1.0F - offset % 1.0F;

        }

        if (offset < 0.33333334F) {
            colorA = color1;

            colorB = color2;

            offset *= 3.0F;

        } else if (offset < 0.6666667F) {
            colorA = color2;

            colorB = color3;

            offset = (offset - 0.33333334F) * 3.0F;

        } else {
            colorA = color3;

            colorB = color4;

            offset = (offset - 0.6666667F) * 3.0F;

        }

        double invert = (1.0F - offset);

        int r = (int)((colorA >> 16 & 0xFF) * invert + ((colorB >> 16 & 0xFF) * offset));

        int g = (int)((colorA >> 8 & 0xFF) * invert + ((colorB >> 8 & 0xFF) * offset));

        int b = (int)((colorA & 0xFF) * invert + ((colorB & 0xFF) * offset));

        int a = (int)((colorA >> 24 & 0xFF) * invert + ((colorB >> 24 & 0xFF) * offset));

        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;

    }

    static {
        hudColor = (index -> {
            switch (((HUD)ModManager.getMod("HUD")).modListColor.get()) {
                case "blossom":
                return new Color(fadeBetween((new Color(14451967)).getRGB(), (new Color(7488646)).getRGB(), colorOffset(index.intValue() * -200)));

                case "blossom 2":
                return new Color(fadeBetween((new Color(16752880)).getRGB(), (new Color(12022444)).getRGB(), (new Color(12296447)).getRGB(), (new Color(9007799)).getRGB(), colorOffset(index.intValue() * -100)));

                case "astolfo":
                return new Color(fadeBetween((new Color(16748781)).getRGB(), (new Color(12751103)).getRGB(), (new Color(10457343)).getRGB(), (new Color(9479935)).getRGB(), colorOffset(index.intValue() * -100)));

                case "rose":
                return Palette.rosewater;

                case "red":
                return Palette.red;

                case "pink":
                return Palette.pink;

                case "purple":
                return Palette.mauve;

                case "sky":
                return Palette.blue;

                case "white":
                return new Color(14474460);

                case "black&white":
                return new Color(fadeBetween((new Color(15724527)).getRGB(), (new Color(3223857)).getRGB(), colorOffset(index.intValue() * -200)));

            }

            return null;

        });

    }

}
