package eureka.client.gui;

import eureka.core.EurekaRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiEngineeringDiary extends GuiContainer {
	public static ResourceLocation texture = new ResourceLocation("eureka", "textures/gui/EngineeringDiary.png");
	public EntityPlayer player;
	public int screen, startX[], lineLimit[], page, line, chapter;


	public GuiEngineeringDiary(EntityPlayer player) {
		super(new ContainerEngineeringDiary(player));
		this.player = player;
		screen = -1;
		startX = new int[20];
		lineLimit = new int[20];
		page = 0;
		chapter = -1;

		startX[0] = 85;
		startX[1] = 85;
		startX[2] = 85;
		startX[3] = 85;
		startX[4] = 50;
		startX[5] = 45;
		startX[6] = 40;
		startX[7] = 35;
		startX[8] = 30;
		startX[9] = 25;
		startX[10] = 25;
		startX[11] = 25;
		startX[12] = 25;
		startX[13] = 25;
		startX[14] = 25;
		startX[15] = 25;
		startX[16] = 25;
		startX[17] = 25;
		startX[18] = 25;
		startX[19] = 25;

		lineLimit[0] = 13;
		lineLimit[1] = 13;
		lineLimit[2] = 16;
		lineLimit[3] = 16;
		lineLimit[4] = 17;
		lineLimit[5] = 18;
		lineLimit[6] = 18;
		lineLimit[7] = 21;
		lineLimit[8] = 21;
		lineLimit[9] = 22;
		lineLimit[10] = 22;
		lineLimit[11] = 21;
		lineLimit[12] = 19;
		lineLimit[13] = 19;
		lineLimit[14] = 18;
		lineLimit[15] = 17;
		lineLimit[16] = 14;
		lineLimit[17] = 14;
		lineLimit[18] = 13;
		lineLimit[19] = 12;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		xSize = 210;
		ySize = 180;
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 30, 0, xSize, ySize);
		ArrayList<String> categoryList = EurekaRegistry.getCategoriesList();
		ArrayList<String> keys = EurekaRegistry.getKeys();
		for (int teller = 0; teller < categoryList.size(); teller++){
			if (teller == chapter){
				drawTexturedModalRect(x + 7, y + (24 * teller + 5), 124, 180, 24, 24);
			} else {
				drawTexturedModalRect(x + 7, y + (24 * teller + 5), 98, 180, 24, 24);
			}
			RenderItem item = new RenderItem();
			item.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), EurekaRegistry.getCategoryDisplayStack(categoryList.get(teller)), x + 12, y + 24 * teller + 9);
		}
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int status) {
		super.mouseMovedOrUp(mouseX, mouseY, status);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		if (mouseX > x + 7 && mouseX < x +  31 &&  (mouseY - y) / 25 < EurekaRegistry.getCategoriesList().size()) {
			chapter = (mouseY - y) / 25;
			page = 0;
		}
	}
}
