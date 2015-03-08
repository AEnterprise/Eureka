package eureka.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import eureka.api.EurekaAPI;
import eureka.api.ICategory;
import eureka.api.IEurekaInfo;
import eureka.core.PlayerResearch;
import eureka.core.RenderUtils;
import eureka.core.TextGetter;
import eureka.gui.Widgets.WidgetBase;
import eureka.gui.Widgets.WidgetCategory;
import eureka.gui.Widgets.WidgetCategoryDown;
import eureka.gui.Widgets.WidgetCategoryUp;
import eureka.gui.Widgets.WidgetChapter;
import eureka.gui.Widgets.WidgetChapterDown;
import eureka.gui.Widgets.WidgetChapterUp;
import eureka.gui.Widgets.WidgetNextPage;
import eureka.gui.Widgets.WidgetPrevPage;
import eureka.gui.Widgets.WidgetProgressBar;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiEngineeringDiary extends GuiContainer {
	private ResourceLocation TEXTURE = new ResourceLocation("eureka:textures/gui/EngineeringDiary.png");
	private ArrayList<WidgetBase> widgets = new ArrayList<WidgetBase>();
	private int numCategories = EurekaAPI.API.getCategories().size();
	private int categoryOffset, maxCategoryOffset, chapterOffset, maxChapterOffset, numChapters, page, pages;
	private List<ICategory> categories = EurekaAPI.API.getCategories();
	private ICategory currentCategory;
	private List<IEurekaInfo> chapters;
	private IEurekaInfo currentChapter;
	private PlayerResearch research;
	private int currentLine = 1;
	private int[] xOffSet = {
			70,
			75,
			52,
			35,
			25,
			15,
			10,
			10,
			10,
			10,
			10,
			10,
			10,
			10,
			10,
			10
	};
	private int[] maxLength = new int[]{
			80,
			40,
			38,
			35,
			35,
			40,
			40,
			40,
			40,
			40,
			37,
			35,
			33,
			30,
			30
	};
	private HashMap<Integer, List<String>> pageLists = new HashMap<Integer, List<String>>();

	public GuiEngineeringDiary(PlayerResearch research) {
		super(new ContainerEngineeringDiary());
		this.research = research;
		xSize = 256;
		ySize = 179;
		currentCategory = categories.get(0);
		categoryOffset = 0;
		maxCategoryOffset = categories.size() > 7 ? categories.size() - 7 : 0;
		resetChapters();
		resetWidgets();
		page = 0;
		splitIntroPages(currentCategory.getName());
	}

	private void resetChapters() {
		chapters = EurekaAPI.API.getKeys(currentCategory.getName());
		currentChapter = null;
		numChapters = chapters.size();
		maxChapterOffset = numChapters - 7;
	}

	private void splitIntroPages(String key) {
		int pagenum = 0;
		page = 0;
		pages = 0;
		pageLists.clear();
		if (!TextGetter.getDesc(key).equals("")) {
			pageLists.put(0, splitLine(TextGetter.getDesc(key), 3));
			pages++;
			pagenum++;
		}
		int linenum = 3;
		List<String> list = TextGetter.getText(key);
		List<String> page = new ArrayList<String>();
		if (list.isEmpty())
			return;
		for (String line : list) {
			if (line.length() > maxLength[linenum]) {
				String s = line;
				while (s.length() > maxLength[linenum]) {
					List<String> temp = splitLine(s, linenum);
					page.add(temp.get(0));
					s = temp.get(1);
					linenum++;
					if (linenum == 13) {
						String[] a = new String[page.size()];
						page.toArray(a);
						List<String> pagelist = new ArrayList<String>();
						for (String b : a)
							pagelist.add(b);
						pageLists.put(pagenum, pagelist);
						linenum = 3;
						pagenum++;
						page.clear();
						pages++;
					}
				}
				page.add(s);
				linenum++;
				if (linenum == 13) {
					String[] a = new String[page.size()];
					page.toArray(a);
					List<String> pagelist = new ArrayList<String>();
					for (String b : a)
						pagelist.add(b);
					pageLists.put(pagenum, pagelist);
					linenum = 3;
					pagenum++;
					page.clear();
					pages++;
				}
			} else {
				page.add(line);
				linenum++;
				if (linenum == 13) {
					String[] a = new String[page.size()];
					page.toArray(a);
					List<String> pagelist = new ArrayList<String>();
					for (String b : a)
						pagelist.add(b);
					pageLists.put(pagenum, pagelist);
					linenum = 3;
					pagenum++;
					page.clear();
					pages++;
				}
			}
		}
		pageLists.put(pagenum, page);
	}

	private List<String> splitLine(String line, int linenum) {
		List<String> list = new ArrayList<String>();
		String lines[] = WordUtils.wrap(line, maxLength[linenum]).split(System.getProperty("line.separator"));
		list.add(lines[0]);
		String temp = "";
		for (int t = 1; t < lines.length; t++) {
			temp = temp + " " + lines[t];
		}
		list.add(temp);
		return list;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float fl, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtils.bindTexture(TEXTURE);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 256, 179);
		for (WidgetBase widget : widgets) {
			widget.render(x, y);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String title;
		currentLine = 1;
		if (currentChapter == null) {
			title = TextGetter.getTitle(currentCategory.getName());
		} else {
			title = TextGetter.getTitle(currentChapter.getName());
			currentLine = 3;
			if (page == 0) {
				String text = StatCollector.translateToLocal("engineeringDiary.requiredResearch");
				List<String> list = currentChapter.getRequiredResearch();
				if (!list.isEmpty()) {
					for (String research : list) {
						text += " " + TextGetter.getTitle(research) + ",";
					}
					text = text.substring(0, text.length() - 1);
				} else {
					text += " " + StatCollector.translateToLocal("engineeringDiary.noRequiredResearch");
				}
				splitAndDrawText(text, 0xFF0000);
				String progress;
				if (research.getProgress(currentChapter.getName()) != EurekaAPI.API.getMaxProgress(currentChapter.getName())) {
					progress = StatCollector.translateToLocal("engineeringDiary.progress") + " " + research.getProgress(currentChapter.getName()) + " / " + EurekaAPI.API.getMaxProgress(currentChapter.getName());
				} else {
					progress = StatCollector.translateToLocal("engineeringDiary.unlocked");
				}
				drawText(progress, 0xffa500);
				splitAndDrawText("How to make progress: " + TextGetter.getProgressText(currentChapter.getName()), 0x00ff00);
			}
		}
		drawText(pageLists.get(page), 0xFFFFFF);
		drawText(title, 0, 0xFFCC00, true);
	}

	private void drawText(List<String> lines, int color) {
		if (lines == null || lines.isEmpty())
			return;
		for (String text : lines) {
			if (currentLine == 16)
				return;
			drawText(text, color);
		}
	}

	private void splitAndDrawText(String text, int color) {
		drawText(splitLine(text, currentLine), color);
	}

	private void drawText(String text, int color) {
		drawText(text, currentLine, color, false);
	}

	private void drawText(String text, int line, int color, boolean centered) {
		fontRendererObj.drawString(text, centered ? (int) (xOffSet[line] + maxLength[line] - text.length() * 2.5) : xOffSet[line], 15 + 12 * line, color);
		currentLine++;
	}

	public void onWidgetClicked(WidgetBase widget) {
		if (widget instanceof WidgetCategory) {
			currentCategory = ((WidgetCategory) widget).getCategory();

			splitIntroPages(currentCategory.getName());
			resetChapters();
		} else if (widget instanceof WidgetCategoryDown) {
			categoryOffset++;
		} else if (widget instanceof WidgetCategoryUp) {
			categoryOffset--;
		} else if (widget instanceof WidgetChapter) {
			currentChapter = ((WidgetChapter) widget).getChapter();
			splitIntroPages(currentChapter.getName());
		} else if (widget instanceof WidgetChapterUp) {
			chapterOffset--;
		} else if (widget instanceof WidgetChapterDown) {
			chapterOffset++;
		} else if (widget instanceof WidgetNextPage) {
			page++;
		} else if (widget instanceof WidgetPrevPage) {
			page--;
		}
		resetWidgets();
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		for (WidgetBase widget : widgets) {
			if (widget.getBounds().contains(x, y)) {
				widget.clicked();
				return;
			}
		}
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);
		List<String> tooltips = new ArrayList<String>();

		for (WidgetBase widget : widgets)
			if (widget.getBounds().contains(x, y))
				widget.addTooltip(tooltips, isShiftKeyDown());

		if (!tooltips.isEmpty()) {
			List<String> finalLines = new ArrayList<String>();
			for (String line : tooltips) {
				String[] lines = WordUtils.wrap(line, 30).split(System.getProperty("line.separator"));
				for (String wrappedLine : lines) {
					finalLines.add(wrappedLine);
				}
			}
			drawHoveringText(finalLines, x - 5, y, fontRendererObj);
		}
	}

	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height) {
		super.setWorldAndResolution(mc, width, height);
		resetWidgets();
	}

	private void resetWidgets() {
		widgets.clear();

		//add category tabs
		int max = numCategories > 7 ? 7 : numCategories;
		for (int t = 0; t < max; t++) {
			WidgetCategory widget = new WidgetCategory(guiLeft - 25, guiTop + 5 + 24 * t, 25, 24, this, categories.get(t + categoryOffset));
			widget.setSelected(widget.getCategory() == currentCategory);
			widgets.add(widget);
		}

		//add up and down buttons for the categories
		if (numCategories > 7 && categoryOffset < maxCategoryOffset)
			widgets.add(new WidgetCategoryDown(guiLeft - 20, guiTop + 180, 40, 16, this));
		if (categoryOffset > 0)
			widgets.add(new WidgetCategoryUp(guiLeft - 20, guiTop - 16, 40, 16, this));

		//add chapter tabs
		max = numChapters > 7 ? 7 : numChapters;
		for (int t = 0; t < max; t++) {
			WidgetChapter widget = new WidgetChapter(guiLeft + 256, guiTop + 5 + 24 * t, 25, 24, this, chapters.get(t + chapterOffset));
			widget.setSelected(widget.getChapter() == currentChapter);
			widgets.add(widget);
		}

		//add up and down buttons for the chapters
		if (numChapters > 7 && chapterOffset < maxChapterOffset)
			widgets.add(new WidgetChapterDown(guiLeft + 245, guiTop + 180, 40, 16, this));
		if (chapterOffset > 0)
			widgets.add(new WidgetChapterUp(guiLeft + 245, guiTop - 16, 40, 16, this));

		//add progress bar if needed
		if (currentChapter != null)
			widgets.add(new WidgetProgressBar(guiLeft + 60, guiTop + 25, 180, 7, this, research.getProgress(currentChapter.getName()), currentChapter.getMaxProgress()));

		//page cycling buttons
		if (page < pages && (currentChapter == null || research.isFinished(currentChapter.getName())))
			widgets.add(new WidgetNextPage(guiLeft + 215, guiTop + 155, 16, 16, this));

		if (page > 0)
			widgets.add(new WidgetPrevPage(guiLeft + 20, guiTop + 10, 16, 16, this));
	}

	public FontRenderer getFontRendererObj() {
		return fontRendererObj;
	}
}
