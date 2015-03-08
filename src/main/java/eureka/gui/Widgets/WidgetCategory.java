package eureka.gui.Widgets;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import eureka.api.ICategory;
import eureka.core.Logger;
import eureka.core.RenderUtils;
import eureka.core.TextGetter;
import eureka.gui.GuiEngineeringDiary;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class WidgetCategory extends WidgetBase {
	private final ResourceLocation UNSELECTED = new ResourceLocation("eureka:textures/gui/pieces/categoryUnselected.png");
	private final ResourceLocation SELECTED = new ResourceLocation("eureka:textures/gui/pieces/categorySelected.png");
	private final ICategory category;
	private boolean selected;

	public WidgetCategory(int x, int y, int width, int height, GuiEngineeringDiary gui, ICategory category) {
		super(x, y, width, height, gui);
		this.category = category;
		selected = false;
	}

	@Override
	public void render(int mouseX, int mouseY) {
		if (!enabled)
			return;
		RenderUtils.drawImage(selected ? SELECTED : UNSELECTED, x, y, width, height);
		renderItem(category.getDisplayStack(), x + 6, y + 4);
	}

	private void renderItem(ItemStack stack, int x, int y) {
		RenderItem itemRender = new RenderItem();
		try {
			itemRender.renderItemAndEffectIntoGUI(gui.getFontRendererObj(), RenderUtils.textureManager(), stack, x, y);
		} catch (Throwable e) {
			try {
				itemRender.renderItemIntoGUI(gui.getFontRendererObj(), RenderUtils.textureManager(), stack, x, y);
			} catch (Throwable e2) {
				Logger.error("Failed to render item for category " + category.getName());
			}
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}

	public WidgetCategory setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public ICategory getCategory() {
		return category;
	}

	@Override
	public void addTooltip(List<String> list, boolean shift) {
		list.add(TextGetter.getTitle(category.getName()));
	}
}
