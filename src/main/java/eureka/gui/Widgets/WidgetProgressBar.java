package eureka.gui.Widgets;

import net.minecraft.util.ResourceLocation;

import eureka.core.RenderUtils;
import eureka.gui.GuiEngineeringDiary;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class WidgetProgressBar extends WidgetBase {
	private final ResourceLocation OUTLINE = new ResourceLocation("eureka:textures/gui/pieces/progressbarOutline.png");
	private final ResourceLocation BAR = new ResourceLocation("eureka:textures/gui/pieces/progressbar.png");
	private final int progress, maxProgress;

	public WidgetProgressBar(int x, int y, int width, int height, GuiEngineeringDiary gui, int progress, int maxProgress) {
		super(x, y, width, height, gui);
		this.progress = progress;
		this.maxProgress = maxProgress;
	}

	@Override
	public void render(int mouseX, int mouseY) {
		RenderUtils.drawImage(OUTLINE, x, y, width, height);
		if (progress > 0)
			RenderUtils.drawImage(BAR, x + 1, y + 1, width - 2, height - 2, (float) progress / maxProgress);
	}
}
