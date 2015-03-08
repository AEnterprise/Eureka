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
public class WidgetNextPage extends WidgetBase {
	private final ResourceLocation NORMAL = new ResourceLocation("eureka:textures/gui/pieces/nextPageYellow.png");
	private final ResourceLocation MOUSEOVER = new ResourceLocation("eureka:textures/gui/pieces/nextPageRed.png");

	public WidgetNextPage(int x, int y, int width, int height, GuiEngineeringDiary gui) {
		super(x, y, width, height, gui);
	}

	@Override
	public void render(int mouseX, int mouseY) {
		if (!enabled)
			return;
		RenderUtils.drawImage(getBounds().contains(mouseX, mouseY) ? MOUSEOVER : NORMAL, x, y, width, height);
	}
}
