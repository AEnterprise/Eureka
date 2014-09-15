package eureka;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;

import eureka.api.EurekaInfo;
import eureka.api.EurekaRegistry;
import eureka.client.gui.GuiHandler;
import eureka.core.EventHandler;
import eureka.core.ItemEngineeringDiary;
import eureka.network.PacketHandler;
import eureka.proxy.BaseProxy;
import eureka.utils.BCUtils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftAdditions.wordpress.com/
 * Eureka is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */

@Mod(modid = "eureka", name = "Eureka", version = "@VERSION@", acceptedMinecraftVersions = "1.7.10")
public class Eureka {
	public static Item engineeringDiary;

	//This let it crash. However just for testing i changed
	@Instance("eureka")
	public static Eureka instance;

	@SidedProxy(clientSide="eureka.proxy.ClientProxy", serverSide="eureka.proxy.ServerProxy")
	public static BaseProxy proxy;

	public static CreativeTabs eureka = new CreativeTabs("Eureka") {

		@Override
		public Item getTabIconItem() {
			return engineeringDiary;
		}

	};

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		engineeringDiary = new ItemEngineeringDiary();
		engineeringDiary.setCreativeTab(eureka).setUnlocalizedName("engineeringDiary");
		GameRegistry.registerItem(engineeringDiary, "engineeringDiary");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		FMLCommonHandler.instance().bus().register(new EventHandler.FML());
		MinecraftForge.EVENT_BUS.register(new EventHandler.Forge());
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		PacketHandler.init();
		EurekaRegistry.registerCategory("Eureka", new ItemStack(engineeringDiary));
		if (Loader.isModLoaded("BuildCraft|Core")) {
			EurekaRegistry.registerCategory("Buildcraft|Automatization", new ItemStack(GameRegistry.findBlock("BuildCraft|Factory", "machineBlock")));
			EurekaRegistry.register(new EurekaInfo("autoWorkbench", "Buildcraft|Automatization", 1, 50, new ItemStack(GameRegistry.findBlock("BuildCraft|Factory", "autoWorkbenchBlock"))));
			EurekaRegistry.register(new EurekaInfo("tank", "Buildcraft|Automatization", 1, 10, new ItemStack(GameRegistry.findBlock("BuildCraft|Factory", "tankBlock"))));
			EurekaRegistry.register(new EurekaInfo("miningWell", "Buildcraft|Automatization", 1, 200, new ItemStack(GameRegistry.findBlock("BuildCraft|Factory", "miningWellBlock"))));
			EurekaRegistry.register(new EurekaInfo("pump", "Buildcraft|Automatization", 1, 6, new ItemStack(GameRegistry.findBlock("BuildCraft|Factory", "pumpBlock")), "tank", "miningWell"));
			EurekaRegistry.register(new EurekaInfo("floodgate", "Buildcraft|Automatization", 1, 6, new ItemStack(GameRegistry.findBlock("BuildCraft|Factory", "floodGateBlock")), "tank", "pump"));
			EurekaRegistry.register(new EurekaInfo("quarry", "Buildcraft|Automatization", 1, 5, new ItemStack(GameRegistry.findBlock("BuildCraft|Factory", "machineBlock")), "miningWell"));
			EurekaRegistry.register(new EurekaInfo("refinery", "Buildcraft|Automatization", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Factory", "refineryBlock"))));
			EurekaRegistry.register(new EurekaInfo("filteredBuffer", "Buildcraft|Automatization", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Transport", "filteredBufferBlock"))));
			EurekaRegistry.register(new EurekaInfo("chute", "Buildcraft|Automatization", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Factory", "blockHopper"))));

			//Transport Pipes
			EurekaRegistry.registerCategory("Buildcraft|TransportPipes", new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsdiamond")));
			EurekaRegistry.register(new EurekaInfo("woodItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemswood"))));
			EurekaRegistry.register(new EurekaInfo("cobblestoneItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemscobblestone"))));
			EurekaRegistry.register(new EurekaInfo ("stoneItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsstone")), "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("quartzItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsquartz")), "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("sandstoneItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemssandstone")), "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("goldItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsgold")), "stoneItems"));
			EurekaRegistry.register(new EurekaInfo("ironItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsiron")), "stoneItems"));
			EurekaRegistry.register(new EurekaInfo("voidItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsvoid")), "stoneItems"));
			EurekaRegistry.register(new EurekaInfo("structurePipe", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipestructurecobblestone")), "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("obsidianItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsobsidian")), "woodItems"));
			EurekaRegistry.register(new EurekaInfo("emeraldItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsemerald")), "woodItems"));
			EurekaRegistry.register(new EurekaInfo("diamondItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsdiamond")), "ironItems"));
			EurekaRegistry.register(new EurekaInfo("lazuliItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemslapis")), "diamondItems"));
			EurekaRegistry.register(new EurekaInfo("diazuliItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsdaizuli")), "diamondItems"));

			//Fluid pipes
			EurekaRegistry.registerCategory("Buildcraft|FluidPipes", new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsemerald")));
			EurekaRegistry.register(new EurekaInfo("woodenFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidswood")), "woodItems"));
			EurekaRegistry.register(new EurekaInfo("cobblestoneFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidscobblestone")), "woodenFluid", "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("stoneFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsstone")), "cobblestoneFluid", "stoneItems"));
			EurekaRegistry.register(new EurekaInfo("goldFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsgold")), "stoneFluid", "goldItems"));
			EurekaRegistry.register(new EurekaInfo("ironFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsiron")), "cobblestoneFluid", "ironItems"));
			EurekaRegistry.register(new EurekaInfo("emeraldFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsemerald")), "woodenFluid", "emeraldItems"));
			EurekaRegistry.register(new EurekaInfo("voidFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsvoid")), "cobblestoneFluid", "voidItems"));

			//Kinetic pipes
			EurekaRegistry.registerCategory("Buildcraft|PowerPipes", new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerdiamond")));
			EurekaRegistry.register(new EurekaInfo("woodenPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerwood")), "woodItems", "stilringEngine"));
			EurekaRegistry.register(new EurekaInfo("cobblestonePower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowercobblestone")), "woodenPower", "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("stonePower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerstone")), "cobblestonePower", "stoneItems"));
			EurekaRegistry.register(new EurekaInfo("quartzPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerquartz")), "stonePower", "quartzItems"));
			EurekaRegistry.register(new EurekaInfo("ironPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepoweriron")), "quartzPower", "ironItems"));
			EurekaRegistry.register(new EurekaInfo("goldPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowergold")), "quartzPower", "goldItems"));
			EurekaRegistry.register(new EurekaInfo("diamondPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerdiamond")), "goldPower", "diamondItems"));


			//Building
			EurekaRegistry.registerCategory("Buildcraft|Building", new ItemStack(GameRegistry.findBlock("BuildCraft|Builders", "builderBlock")));
			EurekaRegistry.register(new EurekaInfo("landmark", "Buildcraft|Building", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Builders", "markerBlock"))));
			EurekaRegistry.register(new EurekaInfo("pathmarker", "Buildcraft|Building", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Builders", "pathMarkerBlock")), "landmark"));
			EurekaRegistry.register(new EurekaInfo("template", "Buildcraft|Building", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Builders", "templateItem"))));
			EurekaRegistry.register(new EurekaInfo("blueprint", "Buildcraft|Building", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Builders", "blueprintItem")), "template"));
			EurekaRegistry.register(new EurekaInfo("blueprintLibrary", "Buildcraft|Building", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Builders", "libraryBlock")), "blueprint", "landmark"));
			EurekaRegistry.register(new EurekaInfo("filler", "Buildcraft|Building", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Builders", "fillerBlock")), "landmark"));
			EurekaRegistry.register(new EurekaInfo("architectTable", "Buildcraft|Building", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Builders", "architectBlock")), "blueprint", "landmark"));
			EurekaRegistry.register(new EurekaInfo("builder", "Buildcraft|Building", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Builders", "builderBlock")), "architectTable", "pathmarker", "filler"));

			//Silicon
			EurekaRegistry.registerCategory("Buildcraft|Silicon", new ItemStack(GameRegistry.findBlock("BuildCraft|Silicon", "laserBlock")));
			EurekaRegistry.register(new EurekaInfo("laser", "Buildcraft|Silicon", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Silicon", "laserBlock"))));
			EurekaRegistry.register(new EurekaInfo("assemblyTable", "Buildcraft|Silicon", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Silicon", "null"), 1, 0), "laser"));
			EurekaRegistry.register(new EurekaInfo("integrationTable", "Buildcraft|Silicon", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Silicon", "null"), 1, 2), "assemblyTable"));
			EurekaRegistry.register(new EurekaInfo("advancedCraftingTable", "Buildcraft|Silicon", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Silicon", "null"), 1, 1), "laser", "autoWorkbench"));
			EurekaRegistry.register(new EurekaInfo("pipePlug", "Buildcraft|Silicon", 1, 1, new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "pipePlug"))));

			//Engines
			EurekaRegistry.registerCategory("Buildcraft|Engines", new ItemStack(GameRegistry.findBlock("BuildCraft|Energy", "engineBlock"), 1, 3));
			EurekaRegistry.register(new EurekaInfo("redstoneEngine", "Buildcraft|Engines", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Energy", "engineBlock"), 1, 0)));
			EurekaRegistry.register(new EurekaInfo("stilringEngine", "Buildcraft|Engines", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Energy", "engineBlock"), 1, 1), "redstoneEngine"));
			EurekaRegistry.register(new EurekaInfo("combustionEngine", "Buildcraft|Engines", 1, 1, new ItemStack(GameRegistry.findBlock("BuildCraft|Energy", "engineBlock"), 1, 2), "stilringEngine"));

			//add keys for block recognition
			BCUtils.addBCKey("tile.autoWorkbenchBlock", "autoWorkbench");
		}
	}


}