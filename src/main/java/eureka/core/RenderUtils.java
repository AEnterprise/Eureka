package eureka.core;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RenderUtils {

	public static TextureManager textureManager() {
		return Minecraft.getMinecraft().getTextureManager();
	}

	public static void bindTexture(ResourceLocation texture) {
		textureManager().bindTexture(texture);
	}

	public static void drawCutIcon(IIcon icon, int x, int y, int width, int height, float cut) {
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + height, 0, icon.getMinU(), icon.getInterpolatedV(height));
		tess.addVertexWithUV(x + width, y + height, 0, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
		tess.addVertexWithUV(x + width, y + cut, 0, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
		tess.addVertexWithUV(x, y + cut, 0, icon.getMinU(), icon.getInterpolatedV(cut));
		tess.draw();
	}

	public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
		bindTexture(image);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + height, 0, 0, 1);
		tess.addVertexWithUV(x + width, y + height, 0, 1, 1);
		tess.addVertexWithUV(x + width, y, 0, 1, 0);
		tess.addVertexWithUV(x, y, 0, 0, 0);
		tess.draw();
	}

	public static void drawImage(ResourceLocation image, int x, int y, int width, int height, float cut) {
		bindTexture(image);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + height, 0, 0, cut);
		tess.addVertexWithUV(x + width * cut, y + height, 0, 1, cut);
		tess.addVertexWithUV(x + width * cut, y, 0, 1, 0);
		tess.addVertexWithUV(x, y, 0, 0, 0);
		tess.draw();
	}
}