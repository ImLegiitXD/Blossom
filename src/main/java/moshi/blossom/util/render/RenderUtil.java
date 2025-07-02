package moshi.blossom.util.render;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    public static void drawCircleAt(double x, double y, double z, double radius, float width, Color color) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GlStateManager.disableDepth();

        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        GL11.glColor4f(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F
        );

        final float segments = 70.0F;
        for (int i = 0; i <= segments; i++) {
            double angle = Math.PI * 2 / segments * i;
            GL11.glVertex3d(
                    x + Math.sin(angle) * radius,
                    y,
                    z + Math.cos(angle) * radius
            );
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnd();

        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
    }
}