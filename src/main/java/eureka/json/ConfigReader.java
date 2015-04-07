package eureka.json;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import eureka.api.BasicEurekaCategory;
import eureka.api.BasicEurekaInfo;
import eureka.api.EurekaAPI;
import eureka.core.Logger;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ConfigReader {
	public static File mainfolder, catagoryFolder, keyFolder, localizations;
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void read() {
		createFolderIfNeeded(mainfolder);
		if (!mainfolder.exists())
			return;
		catagoryFolder = new File(mainfolder, "Categories");
		keyFolder = new File(mainfolder, "Keys");
		localizations = new File(mainfolder, "Localizations");
		createFolderIfNeeded(catagoryFolder);
		createFolderIfNeeded(keyFolder);
		createFolderIfNeeded(localizations);
		createFolderIfNeeded(new File(localizations, "en_US"));

		if (!catagoryFolder.exists())
			return;

		for (File file : catagoryFolder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".json");
			}
		})) {
			readCategory(file);
		}
		if (!keyFolder.exists())
			return;
		for (File file : keyFolder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".json");
			}
		})) {
			readKey(file);
		}
	}

	private static void readCategory(File file) {
		try {
			JSONCategory category = gson.fromJson(new FileReader(file), JSONCategory.class);
			EurekaAPI.API.registerCategory(new BasicEurekaCategory(category.name, getStack(category.displayStack)));
		} catch (Throwable t) {
			Logger.error("Failed to read category file (" + file.getName() + ")");
		}
	}

	private static void readKey(File file) {
		try {
			JSONChapter chapter = gson.fromJson(new FileReader(file), JSONChapter.class);
			List<ItemStack> objects = new ArrayList<ItemStack>();
			for (String stack : chapter.blockedObjects)
				objects.add(getStack(stack));
			EurekaAPI.API.register(new BasicEurekaInfo(chapter.name, chapter.category, chapter.maxProgress, getStack(chapter.displayStack), chapter.requiredResearch.toArray(new String[100])));
			for (ItemStack stack : objects) {
				EurekaAPI.API.bindToKey(stack, chapter.name);
			}
			if (chapter.progressOption != null) {
				Object obj;
				ItemStack stack = getStack(chapter.progressObject);
				if (stack != null && stack.getItem() instanceof ItemBlock)
					obj = ((ItemBlock) stack.getItem()).field_150939_a;
				else
					obj = stack;
				EurekaAPI.API.registerProgressOption(chapter.name, chapter.progressOption, obj);
			}
		} catch (Throwable t) {
			Logger.error("Failed to read category file (" + file.getName() + ")");
			t.printStackTrace();
		}
	}

	private static ItemStack getStack(String name) {
		if (Strings.isNullOrEmpty(name))
			return null;
		int meta = -1;
		if (name.contains("@")) {
			meta = Integer.getInteger(name.split("@")[1]);
		}
		String split[] = name.split(":");
		ItemStack stack = GameRegistry.findItemStack(split[0], split[1], 1);
		if (meta != -1 && stack != null)
			stack.setItemDamage(meta);
		return stack;
	}

	private static void createFolderIfNeeded(File folder) {
		try {
			if (!Files.exists(folder.toPath()))
				Files.createDirectory(folder.toPath());
		} catch (Throwable e) {
			Logger.error("Something went wrong while checking or creating the Eureka folders");
			e.printStackTrace();
		}
	}
}
