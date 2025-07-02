package moshi.skidded.font;

import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Fonts {
    public static final MCFontRenderer sf18 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/astolfo.ttf"), 18.0F), true, false);

    public static final MCFontRenderer ps14 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/poppins.ttf"), 14.0F), true, false);

    public static final MCFontRenderer ps15 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/poppins.ttf"), 15.0F), true, false);

    public static final MCFontRenderer ps16 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/poppins.ttf"), 16.0F), true, false);

    public static final MCFontRenderer ps17 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/poppins.ttf"), 17.0F), true, false);

    public static final MCFontRenderer ps18 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/poppins.ttf"), 18.0F), true, false);

    public static final MCFontRenderer ps19 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/poppins.ttf"), 19.0F), true, false);

    public static final MCFontRenderer ps20 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/poppins.ttf"), 20.0F), true, false);

    public static final MCFontRenderer ps21 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/poppins.ttf"), 21.0F), true, false);

    public static final MCFontRenderer lt15 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/lato.ttf"), 15.0F), true, false);

    public static final MCFontRenderer lt16 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/lato.ttf"), 16.0F), true, false);

    public static final MCFontRenderer lt17 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/lato.ttf"), 17.0F), true, false);

    public static final MCFontRenderer bl28 = new MCFontRenderer(
            fontFromTTF(new ResourceLocation("blossom/fonts/blossom.ttf"), 28.0F), true, false);

    public static Font fontFromTTF(ResourceLocation fontLocation, float fontSize) {
        Font output = null;
        try {
            output = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(fontLocation).getInputStream());
            output = output.deriveFont(fontSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}