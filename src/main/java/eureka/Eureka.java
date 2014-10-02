package eureka;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;


import eureka.api.EurekaRegistry;
import eureka.client.gui.GuiHandler;
import eureka.core.BlockDetector;
import eureka.core.EventHandler;
import eureka.items.ItemEngineeringDiary;
import eureka.json.FileReader;
import eureka.network.PacketHandler;
import eureka.proxy.BaseProxy;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of LGPLv3
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

@Mod(modid = "eureka", name = "Eureka", version = "@VERSION@", acceptedMinecraftVersions = "1.7.10")
public class Eureka {
	public static Item engineeringDiary;
	public static CreativeTabs eureka = new CreativeTabs("Eureka") {

		@Override
		public Item getTabIconItem() {
			return engineeringDiary;
		}

	};
	public static Item pipePart;
	public static Item glassShard;
	//This let it crash. However just for testing i changed
	@Instance("eureka")
	public static Eureka instance;
	@SidedProxy(clientSide = "eureka.proxy.ClientProxy", serverSide = "eureka.proxy.ServerProxy")
	public static BaseProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		engineeringDiary = new ItemEngineeringDiary();
		engineeringDiary.setCreativeTab(eureka).setUnlocalizedName("engineeringDiary");
		GameRegistry.registerItem(engineeringDiary, "engineeringDiary");
		BlockDetector blockDetector = new BlockDetector();
		FMLCommonHandler.instance().bus().register(blockDetector);
		MinecraftForge.EVENT_BUS.register(blockDetector);
		FileReader.setMainfolder(new File(event.getModConfigurationDirectory(), "Eureka"));
		EurekaRegistry.registerCategory("Eureka", new ItemStack(engineeringDiary));
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		FMLCommonHandler.instance().bus().register(new EventHandler.FML());
		MinecraftForge.EVENT_BUS.register(new EventHandler.Forge());
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		PacketHandler.init();
	}
	@Mod.EventHandler
	public void doneLoading(FMLLoadCompleteEvent event){
		FileReader.readFiles();
	}
}