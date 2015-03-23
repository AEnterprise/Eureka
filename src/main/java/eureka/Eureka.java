package eureka;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;

import eureka.api.BasicEurekaCategory;
import eureka.api.EurekaAPI;
import eureka.core.EurekaAPIImplementation;
import eureka.core.EurekaCommand;
import eureka.core.EventHandler;
import eureka.gui.GuiHandler;
import eureka.items.ItemEngineeringDiary;
import eureka.json.ConfigReader;
import eureka.networking.PacketHandler;
import eureka.proxy.IProxy;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

@Mod(modid = "eureka", name = "Eureka", version = "@MODVERSION@", acceptedMinecraftVersions = "1.7.10")
public class Eureka {

	@Mod.Instance("eureka")
	public static Eureka instance;

	@SidedProxy(clientSide = "eureka.proxy.ClientProxy", serverSide = "eureka.proxy.ServerProxy")
	public static IProxy proxy;
	public static ItemEngineeringDiary engineeringDiary;
	public static CreativeTabs eureka = new CreativeTabs("Eureka") {

		@Override
		public Item getTabIconItem() {
			return engineeringDiary;
		}

	};

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		EurekaAPI.API = new EurekaAPIImplementation();
		engineeringDiary = new ItemEngineeringDiary();
		GameRegistry.registerItem(engineeringDiary, "engineeringDiary");
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		FMLCommonHandler.instance().bus().register(new EventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		EurekaAPI.API.registerCategory(new BasicEurekaCategory("Eureka", new ItemStack(engineeringDiary)));
		PacketHandler.init();
		ConfigReader.mainfolder = new File(event.getModConfigurationDirectory(), "Eureka");
		ConfigReader.read();
	}

	@Mod.EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		event.registerServerCommand(new EurekaCommand());
	}

}
