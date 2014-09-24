package eureka.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eureka.api.EurekaRegistry;
import eureka.core.Logger;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class FileReader {
	public static File mainfolder, catagoryFolder, keyFolder;
	protected static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void setMainfolder (File folder){
		mainfolder = folder;
	}

	public static void readFiles() {
		try {
			createFolderIfNeeded(mainfolder);
			catagoryFolder = new File(mainfolder, "Categories");
			keyFolder = new File(mainfolder, "Keys");
			createFolderIfNeeded(catagoryFolder);
			createFolderIfNeeded(keyFolder);
			eurekaCategory();
			CategoryEntry category;
			for (File file : catagoryFolder.listFiles(new FileFilter())) {
				readFile(file);
				category = gson.fromJson(new java.io.FileReader(file), CategoryEntry.class);
				if (category.displaystackType == null || category.displaystackName == null || category.displaystackModID == null || category.name == null) {
					Logger.error("Error while reading catagory file " + file.toString());
					return;
				}
				ItemStack stack = null;
				if (category.displaystackType.toLowerCase().equals("block"))
					stack = new ItemStack(GameRegistry.findBlock(category.displaystackModID, category.displaystackName));
				else if (category.displaystackType.toLowerCase().equals("item"))
					stack = new ItemStack(GameRegistry.findItem(category.displaystackModID, category.displaystackName));
				if (stack == null) {
					Logger.error("Unable to get the displaystack from the GameRegistry for category" + category.name + "check type, modid and the name used to register it");
				}
				EurekaRegistry.registerCategory(category.name, stack);

			}
		} catch (Throwable e) {
			Logger.error("Something went wrong while reading the Eureka JSON files, please report this including following stacktrace: ");
			e.printStackTrace();
		}
	}

	public static void createFolderIfNeeded(File folder) {
		try {
			if (!Files.exists(folder.toPath()))
				Files.createDirectory(folder.toPath());
		} catch (Throwable e) {
			Logger.error("Something went wrong while checking or creating the Eureka folders");
			e.printStackTrace();
		}
	}

	public static void eurekaCategory(){
		try {
			File file = new File(catagoryFolder, "Eureka.json");
			if (!Files.exists(file.toPath())){
				CategoryEntry category = new CategoryEntry("Eureka", "eureka", "engineeringDiary", "item");
				Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
				writer.write(gson.toJson(category));
				writer.close();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private static void readFile(File file) {

	}
}
