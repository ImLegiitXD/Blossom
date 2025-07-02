package moshi.blossom.ui.guis;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import moshi.blossom.module.ModManager;
import moshi.blossom.module.Module;
import moshi.blossom.option.Option;
import moshi.blossom.option.impl.ContOption;
import moshi.skidded.font.Fonts;
import moshi.skidded.font.MCFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class AstolfoClickGui extends GuiScreen {
    public static Option selectedOpt = null;

    public void initGui() {
        super.initGui();
        selectedOpt = null;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        handleGui(HandleType.DRAW, mouseX, mouseY, partialTicks, 0, 0);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        handleGui(HandleType.CLICK, mouseX, mouseY, 0.0F, mouseButton, 0);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        handleGui(HandleType.RELEASE, mouseX, mouseY, 0.0F, 0, state);
    }

    public void handleGui(HandleType type, int mouseX, int mouseY, float partialTicks, int mouseButton, int state) {
        if (type == HandleType.RELEASE) {
            selectedOpt = null;
            return;
        }
        if (type == HandleType.CLICK)
            selectedOpt = null;
        FontRenderer vfr = Minecraft.getMinecraft().fontRendererObj;
        MCFontRenderer fr = Fonts.lt15;
        for (Module.Category category : Module.Category.values()) {
            float posY = category.posY;
            float height = 17.0F;
            float width = 112.0F;
            Gui.drawRect((int) category.posX, (int) posY, (int) (category.posX + width), (int) (posY + height), (new Color(1579032)).getRGB());
            vfr.drawStringWithShadow(category.name().toLowerCase(), category.posX + 3.5F, category.posY + 6.5F, -1);

            float ol = 0.5F;

            if (!ModManager.getMods(category).isEmpty()) {
                posY += height;
            }
            List<Module> mods = ModManager.getMods(category);
            for (int i = 0; i < mods.size(); i++) {
                Module module = mods.get(i);
                height = 16.0F;
                if (type == HandleType.CLICK && isHovered(mouseX, mouseY, category.posX, posY, (category.posX + width), (posY + height)))
                    if (mouseButton == 0) {
                        module.toggle();
                    } else if (mouseButton == 1 && !module.getOptions().isEmpty()) {
                        module.setExpanded(!module.isExpanded());
                    }
                Gui.drawRect((int) category.posX, (int) posY, (int) (category.posX + width), (int) (posY + height), (module
                        .isExpanded() && !module.getOptions().isEmpty()) ? (new Color(1447446)).getRGB() : (module.isToggled() ? category.color.getRGB() : (new Color(2434344)).getRGB()));
                Gui.drawRect((int) category.posX, (int) posY, (int) (category.posX + 2.0F), (int) (posY + height), (new Color(1447446)).getRGB());
                Gui.drawRect((int) (category.posX + width - 2.0F), (int) posY, (int) (category.posX + width), (int) (posY + height), (new Color(1447446)).getRGB());
                fr.drawString(module.getName().toLowerCase(), category.posX + 4.0F, posY + 8.0F, (module
                        .isExpanded() && !module.getOptions().isEmpty()) ? category.color.getRGB() : (
                        module.isToggled() ? (new Color(13882323))
                                .getRGB() : (new Color(13750737)).getRGB()));

                if (i != mods.size() - 1) {
                    posY += height;
                } else if (module.isExpanded()) {
                    posY += height;
                }
                if (module.isExpanded()) {
                    height = 11.0F;
                    List<Option> options = module.getOptions();
                    for (int j = 0; j < options.size(); j++) {
                        Option option = options.get(j);
                        Gui.drawRect((int) category.posX, (int) posY, (int) (category.posX + width), (int) (posY + height), (new Color(1447446))
                                .getRGB());
                        if (isHovered(mouseX, mouseY, category.posX, posY, (category.posX + width), (posY + height)) && type == HandleType.CLICK) {
                            selectedOpt = option;
                            option.click(mouseX, mouseY, category.posX, posY, category.posX + width, posY + height, mouseButton);
                            return;
                        }
                        if (selectedOpt == option && type == HandleType.DRAW)
                            option.update(mouseX, mouseY, category.posX + 4.0F, posY, category.posX + width - 4.0F, posY + height);
                        option.draw(mouseX, mouseY, category.posX + 4.0F, posY, category.posX + width - 4.0F, posY + height, module);

                        if (j != module.getOptions().size() - 1 || i != mods.size() - 1) {
                            posY += height;
                        }
                        if (option instanceof ContOption && ((ContOption)option).isExpanded()) {
                            for (int k = 0; k < ((ContOption)option).getOptionList().size(); k++) {
                                Option option1 = ((ContOption)option).getOptionList().get(k);
                                Gui.drawRect((int) category.posX, (int) posY, (int) (category.posX + width), (int) (posY + height), (new Color(1447446))
                                        .getRGB());
                                Gui.drawRect((int) (category.posX + 4.0F), (int) posY, (int) (category.posX + width - 4.0F), (int) (posY + height), (new Color(987152))
                                        .getRGB());
                                if (isHovered(mouseX, mouseY, (category.posX + 7.0F), posY, (category.posX + width - 7.0F), (posY + height)) && type == HandleType.CLICK) {
                                    selectedOpt = option1;
                                    option1.click(mouseX, mouseY, category.posX + 7.0F, posY, category.posX + width - 7.0F, posY + height, mouseButton);
                                    return;
                                }
                                if (selectedOpt == option1 && type == HandleType.DRAW)
                                    option1.update(mouseX, mouseY, category.posX + 7.0F, posY, category.posX + width - 7.0F, posY + height);
                                option1.draw(mouseX, mouseY, category.posX + 7.0F, posY, category.posX + width - 7.0F, posY + height, module);
                                posY += height;
                            }
                        }
                    }
                }
            }

            Gui.drawRect((int) (category.posX - ol), (int) (category.posY - ol), (int) (category.posX + width + ol), (int) category.posY, category.color.getRGB());
            Gui.drawRect((int) (category.posX - ol), (int) category.posY, (int) category.posX, (int) (posY + height + 2.0F), category.color.getRGB());
            Gui.drawRect((int) (category.posX + width), (int) category.posY, (int) (category.posX + width + ol), (int) (posY + height + 2.0F), category.color.getRGB());

            Gui.drawRect((int) category.posX, (int) (posY + height), (int) (category.posX + width), (int) (posY + height + 2.0F), (new Color(1447446)).getRGB());
            Gui.drawRect((int) (category.posX - ol), (int) (posY + height + 2.0F), (int) (category.posX + width + ol), (int) (posY + height + 2.0F + ol), category.color.getRGB());
        }
    }

    public enum HandleType {
        DRAW,
        CLICK,
        RELEASE;
    }

    public boolean isHovered(int mouseX, int mouseY, double x, double y, double x2, double y2) {
        return (mouseX > x && mouseY > y && mouseX < x2 && mouseY < y2);
    }
}