package eureka.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.client.FMLClientHandler;

import eureka.api.EurekaAPI;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TextGetter {
	private static HashMap<String, String> titles = new HashMap<String, String>();
	private static HashMap<String, String> descriptions = new HashMap<String, String>();
	private static HashMap<String, List<String>> texts = new HashMap<String, List<String>>();
	private static HashMap<String, String> progressTexts = new HashMap<String, String>();

	public static String getTitle(String key) {
		if (titles.containsKey(key))
			return titles.get(key);
		getText(key);
		if (titles.containsKey(key))
			return titles.get(key);
		return "ERROR, UNABLE TO FIND LOCALIZATION FILE";
	}

	public static String getDesc(String key) {
		if (!EurekaAPI.API.keyRegistered(key))
			return "";
		if (descriptions.containsKey(key))
			return descriptions.get(key);
		getText(key);
		if (descriptions.containsKey(key))
			return descriptions.get(key);
		return "";
	}

	public static String getProgressText(String key) {
		if (!EurekaAPI.API.keyRegistered(key))
			return "";
		if (progressTexts.containsKey(key))
			return progressTexts.get(key);
		getText(key);
		if (progressTexts.containsKey(key))
			return progressTexts.get(key);
		return "";
	}

	public static List<String> getText(String key) {
		if (texts.containsKey(key))
			return texts.get(key);
		else
			texts.put(key, getText(key, FMLClientHandler.instance().getCurrentLanguage(), !EurekaAPI.API.keyRegistered(key)));
		return texts.get(key);
	}

	private static List<String> getText(String key, String lang, boolean category) {
		List<String> list = new ArrayList<String>();
		InputStream stream = null;
		ResourceLocation location = new ResourceLocation("eureka:localizations/" + lang + "/" + key + ".txt");
		try {
			IResource resource = FMLClientHandler.instance().getClient().getResourceManager().getResource(location);
			stream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			String line = reader.readLine();
			while (line != null) {
				list.add(line);
				line = reader.readLine();
			}
			reader.close();
			titles.put(key, list.get(0));
			if (!category) {
				descriptions.put(key, list.get(1));
				progressTexts.put(key, list.get(2));
				list.remove(0);
				list.remove(0);
			}

			list.remove(0);
		} catch (Exception e) {
			if (!lang.equals("en_US"))
				return getText(key, "en_US", category);
		} finally {
			try {
				stream.close();
			} catch (Exception e) {
				//ignore it, file doesn't exist
			}
		}

		return list;
	}
}
