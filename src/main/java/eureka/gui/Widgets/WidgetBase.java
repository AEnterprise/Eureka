package eureka.gui.Widgets;

import java.awt.Rectangle;
import java.util.List;

import eureka.gui.GuiEngineeringDiary;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class WidgetBase {
	protected int x, y, width, height;
	protected boolean enabled;
	protected GuiEngineeringDiary gui; //not making a base class cause this is the only gui in the entire mod, will change later if needed

	public WidgetBase(int x, int y, int width, int height, GuiEngineeringDiary gui) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gui = gui;
		enabled = true;
	}

	public abstract void render(int mouseX, int mouseY);

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void clicked() {
		if (enabled)
			gui.onWidgetClicked(this);
	}

	public WidgetBase setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public void addTooltip(List<String> list, boolean shift) {

	}
}
