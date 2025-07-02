package moshi.blossom.user.login;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.opengl.GL11;

public final class PasswordField extends Gui {
    private final FontRenderer fontRenderer;
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;

    private String text = "";
    private int maxStringLength = Integer.MAX_VALUE;
    private int cursorCounter;
    private boolean enableBackgroundDrawing = true;
    private boolean canLoseFocus = true;
    private boolean isFocused = false;
    private final boolean isEnabled = true;
    private int i = 0;
    private int cursorPosition = 0;
    private int selectionEnd = 0;
    private int enabledColor = 14737632;
    private final int disabledColor = 7368816;
    private boolean b = true;

    public PasswordField(FontRenderer fontRenderer, int x, int y, int width, int height) {
        this.fontRenderer = fontRenderer;
        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
    }

    public void updateCursorCounter() {
        this.cursorCounter++;
    }

    public String getText() {
        return this.text.replaceAll(" ", "");
    }

    public void setText(String text) {
        this.text = text;
        setCursorPositionEnd();
    }

    public String getSelectedtext() {
        int start = Math.min(this.cursorPosition, this.selectionEnd);
        int end = Math.max(this.cursorPosition, this.selectionEnd);
        return this.text.substring(start, end);
    }

    public void writeText(String text) {
        String filtered = ChatAllowedCharacters.filterAllowedCharacters(text);
        int start = Math.min(this.cursorPosition, this.selectionEnd);
        int end = Math.max(this.cursorPosition, this.selectionEnd);
        int availableSpace = this.maxStringLength - this.text.length() - (this.text.length() - end);

        StringBuilder newText = new StringBuilder();
        if (this.text.length() > 0) {
            newText.append(this.text.substring(0, start));
        }

        if (availableSpace < filtered.length()) {
            newText.append(filtered.substring(0, availableSpace));
        } else {
            newText.append(filtered);
        }

        if (this.text.length() > 0 && end < this.text.length()) {
            newText.append(this.text.substring(end));
        }

        this.text = newText.toString().replaceAll(" ", "");
        cursorPos(start - this.selectionEnd + Math.min(availableSpace, filtered.length()));
    }

    public void setCursorPosition(int pos) {
        this.cursorPosition = Math.max(0, Math.min(pos, this.text.length()));
        func_73800_i(this.cursorPosition);
    }

    public void setCursorPositionZero() {
        setCursorPosition(0);
    }

    public void setCursorPositionEnd() {
        setCursorPosition(this.text.length());
    }

    public void cursorPos(int offset) {
        setCursorPosition(this.selectionEnd + offset);
    }

    public boolean textboxKeyTyped(char typedChar, int keyCode) {
        if (!this.isEnabled || !this.isFocused) return false;

        switch (typedChar) {
            case 1: // Ctrl+A
                setCursorPositionEnd();
                func_73800_i(0);
                return true;
            case 3: // Ctrl+C
                GuiScreen.setClipboardString(getSelectedtext());
                return true;
            case 22: // Ctrl+V
                writeText(GuiScreen.getClipboardString());
                return true;
            case 24: // Ctrl+X
                GuiScreen.setClipboardString(getSelectedtext());
                writeText("");
                return true;
        }

        switch (keyCode) {
            case 14: // Backspace
                if (GuiScreen.isCtrlKeyDown()) {
                    func_73779_a(-1);
                } else {
                    deleteFromCursor(-1);
                }
                return true;
            case 199: // Home
                if (GuiScreen.isShiftKeyDown()) {
                    func_73800_i(0);
                } else {
                    setCursorPositionZero();
                }
                return true;
            case 203: // Left Arrow
                handleLeftArrowKey(GuiScreen.isShiftKeyDown(), GuiScreen.isCtrlKeyDown());
                return true;
            case 205: // Right Arrow
                handleRightArrowKey(GuiScreen.isShiftKeyDown(), GuiScreen.isCtrlKeyDown());
                return true;
            case 207: // End
                if (GuiScreen.isShiftKeyDown()) {
                    func_73800_i(this.text.length());
                } else {
                    setCursorPositionEnd();
                }
                return true;
            case 211: // Delete
                if (GuiScreen.isCtrlKeyDown()) {
                    func_73779_a(1);
                } else {
                    deleteFromCursor(1);
                }
                return true;
        }

        if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
            writeText(Character.toString(typedChar));
            return true;
        }
        return false;
    }

    private void handleLeftArrowKey(boolean shift, boolean ctrl) {
        if (shift) {
            if (ctrl) {
                func_73800_i(getNthWordFromPos(-1, getSelectionEnd()));
            } else {
                func_73800_i(getSelectionEnd() - 1);
            }
        } else if (ctrl) {
            setCursorPosition(getNthWordFromCursor(-1));
        } else {
            cursorPos(-1);
        }
    }

    private void handleRightArrowKey(boolean shift, boolean ctrl) {
        if (shift) {
            if (ctrl) {
                func_73800_i(getNthWordFromPos(1, getSelectionEnd()));
            } else {
                func_73800_i(getSelectionEnd() + 1);
            }
        } else if (ctrl) {
            setCursorPosition(getNthWordFromCursor(1));
        } else {
            cursorPos(1);
        }
    }

    public void drawTextBox() {
        if (!func_73778_q()) return;

        if (getEnableBackgroundDrawing()) {
            drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, new Color(0, 0, 0, 150).getRGB());
        }

        int textColor = this.isEnabled ? this.enabledColor : this.disabledColor;
        int cursorPos = this.cursorPosition - this.i;
        int selectionEnd = this.selectionEnd - this.i;
        String visibleText = this.fontRenderer.trimStringToWidth(this.text.substring(this.i), getWidth());

        boolean cursorVisible = (cursorPos >= 0 && cursorPos <= visibleText.length());
        boolean shouldBlink = (this.isFocused && this.cursorCounter / 6 % 2 == 0 && cursorVisible);

        int textX = this.enableBackgroundDrawing ? this.xPos + 4 : this.xPos;
        int textY = this.enableBackgroundDrawing ? this.yPos + (this.height - 8) / 2 : this.yPos;
        int cursorX = textX;

        if (selectionEnd > visibleText.length()) {
            selectionEnd = visibleText.length();
        }

        if (visibleText.length() > 0) {
            cursorX = Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(
                    this.text.replaceAll("(?s).", "*"), textX, textY, textColor);
        }

        boolean atEnd = (this.cursorPosition < this.text.length() || this.text.length() >= getMaxStringLength());
        int cursorDrawX = cursorX;

        if (!cursorVisible) {
            cursorDrawX = (cursorPos > 0) ? (textX + this.width) : textX;
        } else if (atEnd) {
            cursorDrawX = cursorX - 1;
            cursorX--;
        }

        if (shouldBlink) {
            if (atEnd) {
                drawRect(cursorDrawX, textY - 1, cursorDrawX + 1, textY + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
            } else {
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("_", cursorDrawX, textY, textColor);
            }
        }

        if (selectionEnd != cursorPos) {
            int selectionX = textX + this.fontRenderer.getStringWidth(visibleText.substring(0, selectionEnd));
            drawCursorVertical(cursorDrawX, textY - 1, selectionX - 1, textY + 1 + this.fontRenderer.FONT_HEIGHT);
        }
    }

    private void drawCursorVertical(int x1, int y1, int x2, int y2) {
        if (x1 < x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 < y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_COLOR_LOGIC_OP);
        GL11.glLogicOp(GL11.GL_OR_REVERSE);

        worldRenderer.begin(7, worldRenderer.getVertexFormat());
        worldRenderer.pos(x1, y2, 0.0D);
        worldRenderer.pos(x2, y2, 0.0D);
        worldRenderer.pos(x2, y1, 0.0D);
        worldRenderer.pos(x1, y1, 0.0D);
        worldRenderer.finishDrawing();

        GL11.glDisable(GL11.GL_COLOR_LOGIC_OP);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public int getMaxStringLength() { return this.maxStringLength; }
    public void setMaxStringLength(int length) {
        this.maxStringLength = length;
        if (this.text.length() > length) {
            this.text = this.text.substring(0, length);
        }
    }

    public int getCursorPosition() { return this.cursorPosition; }
    public boolean getEnableBackgroundDrawing() { return this.enableBackgroundDrawing; }
    public void setEnableBackgroundDrawing(boolean enable) { this.enableBackgroundDrawing = enable; }
    public void func_73794_g(int color) { this.enabledColor = color; }
    public boolean isFocused() { return this.isFocused; }
    public void setFocused(boolean focused) {
        if (focused && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = focused;
    }
    public int getSelectionEnd() { return this.selectionEnd; }
    public int getWidth() { return getEnableBackgroundDrawing() ? (this.width - 8) : this.width; }
    public void setCanLoseFocus(boolean canLoseFocus) { this.canLoseFocus = canLoseFocus; }
    public boolean func_73778_q() { return this.b; }
    public void func_73790_e(boolean b) { this.b = b; }

    // Helper methods
    public void func_73779_a(int direction) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                writeText("");
            } else {
                deleteFromCursor(getNthWordFromCursor(direction) - this.cursorPosition);
            }
        }
    }

    public void deleteFromCursor(int direction) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                writeText("");
            } else {
                boolean negative = (direction < 0);
                int start = negative ? (this.cursorPosition + direction) : this.cursorPosition;
                int end = negative ? this.cursorPosition : (this.cursorPosition + direction);

                String newText = "";
                if (start >= 0) {
                    newText = this.text.substring(0, start);
                }
                if (end < this.text.length()) {
                    newText += this.text.substring(end);
                }

                this.text = newText;
                if (negative) {
                    cursorPos(direction);
                }
            }
        }
    }

    public int getNthWordFromCursor(int words) {
        return getNthWordFromPos(words, getCursorPosition());
    }

    public int getNthWordFromPos(int words, int pos) {
        return type(words, pos, true);
    }

    public int type(int words, int pos, boolean skipWs) {
        int newPos = pos;
        boolean negative = (words < 0);

        for (int i = 0; i < Math.abs(words); i++) {
            if (!negative) {
                int length = this.text.length();
                newPos = this.text.indexOf(' ', newPos);
                if (newPos == -1) {
                    newPos = length;
                } else {
                    while (skipWs && newPos < length && this.text.charAt(newPos) == ' ') {
                        newPos++;
                    }
                }
            } else {
                while (skipWs && newPos > 0 && this.text.charAt(newPos - 1) == ' ') {
                    newPos--;
                }
                while (newPos > 0 && this.text.charAt(newPos - 1) != ' ') {
                    newPos--;
                }
            }
        }
        return newPos;
    }

    public void func_73800_i(int pos) {
        int length = this.text.length();
        if (pos > length) pos = length;
        if (pos < 0) pos = 0;

        this.selectionEnd = pos;

        if (this.fontRenderer != null) {
            if (this.i > length) this.i = length;

            int fieldWidth = getWidth();
            String visibleText = this.fontRenderer.trimStringToWidth(this.text.substring(this.i), fieldWidth);
            int visibleEnd = visibleText.length() + this.i;

            if (pos == this.i) {
                this.i -= this.fontRenderer.trimStringToWidth(this.text, fieldWidth, true).length();
            } else if (pos > visibleEnd) {
                this.i += pos - visibleEnd;
            } else if (pos <= this.i) {
                this.i -= this.i - pos;
            }

            this.i = Math.max(0, Math.min(this.i, length));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean clicked = (mouseX >= this.xPos && mouseX < this.xPos + this.width &&
                mouseY >= this.yPos && mouseY < this.yPos + this.height);

        if (this.canLoseFocus) {
            setFocused(this.isEnabled && clicked);
        }

        if (this.isFocused && mouseButton == 0) {
            int clickPos = mouseX - this.xPos;
            if (this.enableBackgroundDrawing) {
                clickPos -= 4;
            }

            String visibleText = this.fontRenderer.trimStringToWidth(
                    this.text.substring(this.i), getWidth());

            setCursorPosition(
                    this.fontRenderer.trimStringToWidth(visibleText, clickPos).length() + this.i);
        }
    }
}