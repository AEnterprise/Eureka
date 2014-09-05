package eureka.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eureka.api.EurekaKnowledge;
import eureka.api.EurekaRegistry;
import eureka.api.client.gui.EurekaChapter;
import eureka.core.Utils;
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
@SideOnly(Side.CLIENT)
public class GuiEngineeringDiary extends GuiContainer {
	public static ResourceLocation texture = new ResourceLocation("eureka", "textures/gui/EngineeringDiary.png");
	public EntityPlayer player;
	public int category, startX[], lineLimit[], page, chapter, categoryOffset, chapterOffset;
	public boolean hasPrevPage;
	public ArrayList<String> categoryList = EurekaRegistry.getCategoriesList();
	public ArrayList<String> keys = EurekaRegistry.getKeys();
	public ArrayList<EurekaChapter> chaptersToDisplay = new ArrayList<EurekaChapter>(20);
	public ArrayList<String> chapterList = new ArrayList<String>(20);



	public GuiEngineeringDiary(EntityPlayer player) {
		super(new ContainerEngineeringDiary(player));
		this.player = player;
		category = 0;
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
		drawText();
		if (chapter != -1)
			chaptersToDisplay.get(chapter).drawCustomStuff(page);
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
		drawCategories();
		drawChapters();
		drawPageButtons(mouseX, mouseY);
		if (chapter != -1 && page == 0)
			drawProgressBar();
		renderItems();
	}

	private void drawProgressBar() {
		xSize = 210;
		ySize = 180;
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x + 95, y + 38, 148, 180, 60, 7);
		String key = chapterList.get(chapter);
		drawTexturedModalRect(x + 96, y + 39, 148, 187, EurekaKnowledge.getProgress(player, key) * 58 / EurekaRegistry.getMaxValue(key), 7);
	}

	private void renderItems(){
		xSize = 210;
		ySize = 180;
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		for (int teller = 0; teller < 7; teller++){

			if (teller + categoryOffset < categoryList.size()) {
				RenderItem item = new RenderItem();
				item.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), EurekaRegistry.getCategoryDisplayStack(categoryList.get(teller + categoryOffset)), x + 12, y + 24 * teller + 9);
				GL11.glDisable(GL11.GL_LIGHTING);

			}
			if (teller + chapterOffset < chapterList.size()) {
				RenderItem item = new RenderItem();
				item.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), (EurekaRegistry.getDisplayStack(chapterList.get(teller + chapterOffset))), x + 177, y + 24 * teller + 9);
				GL11.glDisable(GL11.GL_LIGHTING);
			}
		}
		GL11.glEnable(GL11.GL_LIGHTING);
	}


	private void drawPageButtons(int mouseX, int mouseY) {
		xSize = 210;
		ySize = 180;
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		boolean hasNextpage = false;
		if (!chaptersToDisplay.isEmpty() && chapter != -1)
			hasNextpage = chaptersToDisplay.get(chapter).hasNextPage(page);
		if (chapter == -1)
			hasNextpage = !(Utils.localize("engineeringDiary." + categoryList.get(category) + ".page" + (page + 1)).equals("engineeringDiary." + categoryList.get(category) + ".page" + Integer.toString(page + 1)));
		if (hasNextpage && (chapter == -1 || EurekaKnowledge.isFinished(player, chapterList.get(chapter))))
			drawTexturedModalRect(x + 143, y + 149, 82, 196, 16, 16);
		if (hasNextpage && mouseX > 143 + x && mouseX < 159 + x && mouseY > 149 + y && mouseY < 164 + y && (chapter == -1 || EurekaKnowledge.isFinished(player, chapterList.get(chapter))))
			drawTexturedModalRect(x + 143, y + 149, 82, 180, 16, 16);
		if (hasPrevPage)
			drawTexturedModalRect(x + 44, y + 13, 66, 196, 16, 16);
		if (hasPrevPage && mouseX > 44 + x && mouseX < 60 + x && mouseY > 13 + y && mouseY < 28 + y)
			drawTexturedModalRect(x + 44, y + 13, 66, 180, 16, 16);
		if (categoryList.size() - categoryOffset > 7){
			drawTexturedModalRect(x + 2, y + 180, 188, 192, 41, 17);
			if (mouseX > x + 2 && mouseX < x + 43 && mouseY > y + 180 && mouseY < y + 197)
				drawTexturedModalRect(x + 2, y + 180, 148, 192, 40, 17);
		}
		if (categoryOffset > 0){
			drawTexturedModalRect(x + 2, y - 15, 188, 210, 41, 17);
			if (mouseX > x + 2 && mouseX < x + 43 && mouseY > y - 15 && mouseY < y)
				drawTexturedModalRect(x + 2, y -15 , 148, 210, 40, 17);
		}
		if (chaptersToDisplay.size() - chapterOffset > 7) {
			drawTexturedModalRect(x + 165, y + 180, 18, 212, 40, 17);
			if (mouseX > x + 165 && mouseX < x + 205 && mouseY > y + 180 && mouseY < y + 197)
				drawTexturedModalRect(x + 165, y + 180, 58, 212, 40, 17);
		}
		if (chapterOffset > 0) {
			drawTexturedModalRect(x + 160, y - 15, 18, 230, 40, 17);
			if (mouseX > x + 160 && mouseX < x + 200 && mouseY > y - 15 && mouseY < y)
				drawTexturedModalRect(x + 160, y - 15, 58, 230, 40, 17);
		}
	}

	private void drawCategories(){
		xSize = 210;
		ySize = 180;
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		for (int teller = 0; teller < 7; teller++) {
			if (teller + categoryOffset < categoryList.size())
				if (teller + categoryOffset == category) {
					drawTexturedModalRect(x + 7, y + (24 * teller + 5), 124, 180, 24, 24);
				} else {
					drawTexturedModalRect(x + 7, y + (24 * teller + 5), 98, 180, 24, 24);
				}
		}

	}

	private void drawChapters(){
		xSize = 210;
		ySize = 180;
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		for (int teller = 0; teller < 7; teller++){
			if (teller + chapterOffset < chaptersToDisplay.size())
				if (teller + chapterOffset == chapter) {
					drawTexturedModalRect(x + 174, y + (24 * teller + 5), 123, 204, 25, 23);
				} else {
					drawTexturedModalRect(x + 174, y + (24 * teller + 5), 98, 204, 25, 23);
				}
		}

	}

	private void drawText(){
		if (chapter == -1){
			String categoryName = categoryList.get(category);
			writeText(Utils.localize("engineeringDiary." + categoryName + ".title"), 0, 0xFFCC00);
			writeText(Utils.localize("engineeringDiary." + categoryName + ".page" + page), 4, 0xFFFFFF);
		} else {
			String chapterName = chapterList.get(chapter);
			writeText(Utils.localize("engineeringDiary." + chapterName + ".title"), 0, 0xFFCC00);
			if (page == 0){
				writeText(Utils.localize("engineeringDiary.requiredResearch"), 5, 0xFF0000);
				writeText(chaptersToDisplay.get(chapter).getRequiredResearch(), 6, 0x0000FF);
				int line = writeText(Utils.localize("engineeringDiary.progress") + " " + EurekaKnowledge.getProgress(player, chapterName) + " / " + EurekaRegistry.getMaxValue(chapterName), 8, 0xFFFF00);
				if (!EurekaKnowledge.isFinished(player, chapterName)) {
					line = writeText(chaptersToDisplay.get(chapter).howToMakeProgress(), line, 0xFF6600);
				} else {
					line = writeText(Utils.localize("engineeringDiary.unlocked"), line, 0xFF6600);
				}
				writeText(chaptersToDisplay.get(chapter).getText(page), line, 0xFFFFFF);
			} else {
				writeText(chaptersToDisplay.get(chapter).getText(page), 4, 0xFFFFFF);
			}

		}
	}

	public int writeText(String text, int line, int color){
		if (text == null)
			return line;
		String[] words = text.split(" ", 0);
		String output = "";
		for (String word: words){
			if (line == 20)
				return line;
			if (output.length() + word.length() > lineLimit[line]){
				drawTextAtLine(output, line, color);
				output = "";
				line++;
			}
			output = output + word + " ";
		}
		drawTextAtLine(output, line, color);
		line += 2;
		return line;
	}

	public void drawTextAtLine(String text, int line, int color){
		fontRendererObj.drawString(text, startX[line], line*8 + 6, color);
	}

	private void rebuildChapterList(){
		chaptersToDisplay.clear();
		chapterList.clear();
		for (String key: keys){
			if (EurekaRegistry.getCategory(key).equals(categoryList.get(category))) {
				chaptersToDisplay.add(EurekaRegistry.getChapterGui(key));
				chapterList.add(key);
			}
		}
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int status) {
		super.mouseMovedOrUp(mouseX, mouseY, status);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		int max = 7;
		if (categoryList.size() < 7)
			max = categoryList.size();
		if (mouseX > x + 7 && mouseX < x +  31 &&  (mouseY - y) / 25  < max) {
			page = 0;
			category = ((mouseY - y) / 25) + categoryOffset;
			chapter = -1;
			chapterOffset = 0;
		}
		max = 7;
		if (chaptersToDisplay.size() < 7)
			max = chaptersToDisplay.size();
		if (mouseX > x + 174 && mouseX < x + 198 && (mouseY - y) / 25 < max){
			page = 0;
			chapter = ((mouseY - y) / 25) + chapterOffset;
		}
		boolean hasNextpage = false;
		if (!chaptersToDisplay.isEmpty() && chapter != -1)
			hasNextpage = chaptersToDisplay.get(chapter).hasNextPage(page);
		if (chapter == -1)
			hasNextpage = !(Utils.localize("engineeringDiary." + categoryList.get(category) + ".page" + (page + 1)).equals("engineeringDiary." + categoryList.get(category) + ".page" + Integer.toString(page + 1)));
		if (hasNextpage && mouseX > 143 + x && mouseX < 159 + x && mouseY > 149 + y && mouseY < 164 + y && (chapter == -1 || EurekaKnowledge.isFinished(player, chapterList.get(chapter))))
			page++;
		if (hasPrevPage && mouseX > 34 + x && mouseX < 59 + x && mouseY > 13 + y && mouseY < 28 + y)
			page--;
		if (page > 0) {
			hasPrevPage = true;
		} else{
			hasPrevPage = false;
		}
		if (mouseX > x + 2 && mouseX < x + 43 && mouseY > y + 180 && mouseY < y + 197 && categoryList.size() - categoryOffset > 7)
			categoryOffset++;
		if (mouseX > x + 2 && mouseX < x + 43 && mouseY > y - 15 && mouseY < y && categoryOffset > 0)
			categoryOffset--;

		if (chaptersToDisplay.size() - chapterOffset > 7 && mouseX > x + 165 && mouseX < x + 205 && mouseY > y + 180 && mouseY < y + 197)
			chapterOffset++;
		if (chapterOffset > 0 && mouseX > x + 160 && mouseX < x + 200 && mouseY > y - 15 && mouseY < y)
			chapterOffset--;
		rebuildChapterList();
	}
}
