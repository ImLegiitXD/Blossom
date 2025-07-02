package moshi.blossom.util;

import moshi.blossom.module.impl.exploit.Debug;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ChatUtil extends Util {
    public static String print(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(
                EnumChatFormatting.LIGHT_PURPLE + "*> " +
                        EnumChatFormatting.RESET + message
        ));
        return message;
    }

    public static String printInfo(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(
                EnumChatFormatting.YELLOW + "?> " +
                        EnumChatFormatting.RESET + message
        ));
        return message;
    }

    public static String printWarning(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(
                EnumChatFormatting.RED + "!> " +
                        EnumChatFormatting.RESET + message
        ));
        return message;
    }

    public static String printDebug(String message) {
        if (Debug.enabled) {
            mc.thePlayer.addChatMessage(new ChatComponentText(
                    EnumChatFormatting.LIGHT_PURPLE + "/> (" +
                            getPlayer().ticksExisted + ") " +
                            EnumChatFormatting.RESET + message
            ));
        }
        return message;
    }
}