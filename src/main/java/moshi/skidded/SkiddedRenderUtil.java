package moshi.skidded;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class SkiddedRenderUtil {
    public static void renderOne() {
        checkSetupFBO();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(3.0F);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }

    public static void checkSetupFBO() {
        Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }

    public static void setupFBO(Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }

    public static void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }

    public static void renderThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }

    public static void renderFour(int color) {
        setColor(color);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0F, -2000000.0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
    }

    public static void renderFive() {
        GL11.glPolygonOffset(1.0F, 2000000.0F);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }

    public static void setColor(int colorHex) {
        float alpha = (colorHex >> 24 & 0xFF) / 255.0F;
        float red = (colorHex >> 16 & 0xFF) / 255.0F;
        float green = (colorHex >> 8 & 0xFF) / 255.0F;
        float blue = (colorHex & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, (alpha == 0.0F) ? 1.0F : alpha);
    }

    protected static IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    protected static FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    protected static FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    protected static FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);

    public static Vector4d get2DVector(Entity entity) {
        Minecraft mc = Minecraft.getMinecraft();

        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks;

        double width = entity.width / 1.5D;
        double height = entity.height + (entity.isSneaking() ? -0.3D : 0.2D);

        AxisAlignedBB axis = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);

        List<Vector3d> vectors = Arrays.asList(
                new Vector3d(axis.minX, axis.minY, axis.minZ),
                new Vector3d(axis.minX, axis.maxY, axis.minZ),
                new Vector3d(axis.maxX, axis.minY, axis.minZ),
                new Vector3d(axis.maxX, axis.maxY, axis.minZ),
                new Vector3d(axis.minX, axis.minY, axis.maxZ),
                new Vector3d(axis.minX, axis.maxY, axis.maxZ),
                new Vector3d(axis.maxX, axis.minY, axis.maxZ),
                new Vector3d(axis.maxX, axis.maxY, axis.maxZ)
        );

        mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);

        Vector4d position = null;
        for (Vector3d vector : vectors) {
            FloatBuffer otherVec = GLAllocation.createDirectFloatBuffer(4);
            GL11.glGetFloat(2982, modelview);
            GL11.glGetFloat(2983, projection);
            GL11.glGetInteger(2978, viewport);

            if (GLU.gluProject(
                    (float)(vector.x - mc.getRenderManager().viewerPosX),
                    (float)(vector.y - mc.getRenderManager().viewerPosY),
                    (float)(vector.z - mc.getRenderManager().viewerPosZ),
                    modelview, projection, viewport, otherVec)) {

                vector = new Vector3d(
                        (otherVec.get(0) / (new ScaledResolution(mc)).getScaleFactor()),
                        ((Display.getHeight() - otherVec.get(1)) / (new ScaledResolution(mc)).getScaleFactor()),
                        otherVec.get(2)
                );
            }
            if (vector.z >= 0.0D && vector.z < 1.0D) {
                if (position == null) {
                    position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
                }
                position.x = Math.min(vector.x, position.x);
                position.y = Math.min(vector.y, position.y);
                position.z = Math.max(vector.x, position.z);
                position.w = Math.max(vector.y, position.w);
            }
        }

        return position;
    }
}