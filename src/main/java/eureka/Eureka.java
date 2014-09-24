package eureka;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import eureka.items.ItemEngineeringDiary;
import eureka.items.ItemGlassShard;
import eureka.items.ItemPipePart;
import eureka.network.PacketHandler;
import eureka.proxy.BaseProxy;
import eureka.utils.BCUtils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

@Mod(modid = "eureka", name = "Eureka", version = "@VERSION@", acceptedMinecraftVersions = "1.7.10")
public class Eureka {
	public static Item engineeringDiary;
	public static Item pipePart;
	public static Item glassShard;

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
		if (Loader.isModLoaded("BuildCraft|Core") && false) {
			glassShard = new ItemGlassShard();
			GameRegistry.registerItem(glassShard, "glassShard");

			//Automatization
			EurekaRegistry.registerCategory("Buildcraft|Automatization", new ItemStack(BCUtils.BCItems.QUARRY));
			EurekaRegistry.register(new EurekaInfo("autoWorkbench", "Buildcraft|Automatization", 1, 50, new ItemStack(BCUtils.BCItems.AUTOWORKBENCH)));
			EurekaRegistry.register(new EurekaInfo("tank", "Buildcraft|Automatization", 1, 10, new ItemStack(BCUtils.BCItems.TANK)));
			EurekaRegistry.register(new EurekaInfo("miningWell", "Buildcraft|Automatization", 1, 200, new ItemStack(BCUtils.BCItems.MINING_WELL)));
			EurekaRegistry.register(new EurekaInfo("pump", "Buildcraft|Automatization", 1, 6, new ItemStack(BCUtils.BCItems.PUMP), "tank", "miningWell"));
			EurekaRegistry.register(new EurekaInfo("floodgate", "Buildcraft|Automatization", 1, 6, new ItemStack(BCUtils.BCItems.FLOODGATE), "tank", "pump"));
			EurekaRegistry.register(new EurekaInfo("quarry", "Buildcraft|Automatization", 1, 500, new ItemStack(BCUtils.BCItems.QUARRY), "miningWell"));
			EurekaRegistry.register(new EurekaInfo("refinery", "Buildcraft|Automatization", 1, 1, new ItemStack(BCUtils.BCItems.REFINERY)));
			EurekaRegistry.register(new EurekaInfo("filteredBuffer", "Buildcraft|Automatization", 1, 1, new ItemStack(BCUtils.BCItems.FILTERED_BUFFER)));
			EurekaRegistry.register(new EurekaInfo("chute", "Buildcraft|Automatization", 1, 1, new ItemStack(BCUtils.BCItems.HOPPER)));
			BCUtils.addBCKey("tile.autoWorkbenchBlock", "autoWorkbench", new ItemStack(BCUtils.BCItems.WOODEN_GEAR, 4), new ItemStack(Blocks.crafting_table));
			BCUtils.addBCKey("tile.tankBlock", "tank", new ItemStack(Blocks.glass, 8));
			BCUtils.addBCKey("tile.miningWellBlock", "miningWell", new ItemStack(Items.iron_ingot, 6), new ItemStack(Items.iron_pickaxe), new ItemStack(Items.redstone), new ItemStack(BCUtils.BCItems.IRON_GEAR));
			BCUtils.addBCKey("tile.pumpBlock", "pump", new ItemStack(BCUtils.BCItems.MINING_WELL), new ItemStack(BCUtils.BCItems.TANK));
			BCUtils.addBCKey("tile.floodGateBlock", "floodgate", new ItemStack(Items.iron_ingot, 4), new ItemStack(Blocks.iron_bars, 3), new ItemStack(BCUtils.BCItems.TANK), new ItemStack(BCUtils.BCItems.IRON_GEAR));
			BCUtils.addBCKey("tile.machineBlock", "quarry", new ItemStack(BCUtils.BCItems.IRON_GEAR, 3), new ItemStack(GameRegistry.findItem("BuildCraft|Core", "goldGearItem"), 2), new ItemStack(BCUtils.BCItems.DIAMOND_GEAR, 2), new ItemStack(Items.redstone), new ItemStack(Items.diamond_pickaxe));
			BCUtils.addBCKey("tile.refineryBlock", "refinery", new ItemStack(Blocks.redstone_torch, 2), new ItemStack(BCUtils.BCItems.TANK, 3), new ItemStack(BCUtils.BCItems.DIAMOND_GEAR));
			BCUtils.addBCKey("tile.filteredBufferBlock", "filteredBuffer", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_DIAMOND), new ItemStack(Blocks.chest), new ItemStack(Blocks.piston), new ItemStack(Blocks.planks, 6));
			BCUtils.addBCKey("tile.blockHopper", "chute", new ItemStack(BCUtils.BCItems.STONE_GEAR), new ItemStack(Blocks.chest), new ItemStack(Items.iron_ingot, 5));

			//Transport Pipes
			EurekaRegistry.registerCategory("Buildcraft|TransportPipes", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_DIAMOND));
			EurekaRegistry.register(new EurekaInfo("woodItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_WOOD)));
			EurekaRegistry.register(new EurekaInfo("cobblestoneItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_COBBLESTONE)));
			EurekaRegistry.register(new EurekaInfo("stoneItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_STONE), "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("quartzItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_QUARTZ), "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("sandstoneItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_SANDSTONE), "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("goldItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_GOLD), "stoneItems"));
			EurekaRegistry.register(new EurekaInfo("ironItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_IRON), "stoneItems"));
			EurekaRegistry.register(new EurekaInfo("voidItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_VOID), "stoneItems"));
			EurekaRegistry.register(new EurekaInfo("structurePipe", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_STRUCTURE), "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("obsidianItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_OBSIDIAN), "woodItems"));
			EurekaRegistry.register(new EurekaInfo("emeraldItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_EMERALD), "woodItems"));
			EurekaRegistry.register(new EurekaInfo("diamondItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_DIAMOND), "ironItems"));
			EurekaRegistry.register(new EurekaInfo("lazuliItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_LAPIS), "diamondItems"));
			EurekaRegistry.register(new EurekaInfo("diazuliItems", "Buildcraft|TransportPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_ITEMS_DIAZULI), "diamondItems"));
			handlePipe("Wood");
			handlePipe("Cobblestone");
			handlePipe("Stone");
			handlePipe("Quartz");
			handlePipe("Sandstone");
			handlePipe("Gold");
			handlePipe("Iron");
			handlePipe("Void");
			handlePipe("Obsidian");
			handlePipe("Emerald");
			handlePipe("Diamond");
			handlePipe("Lapis");
			handlePipe("Daizuli");
			BCUtils.addBCKey("item.PipeItemsWood", "woodItems", new ItemStack(getPipePart("Wood"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsCobblestone", "cobblestoneItems", new ItemStack(getPipePart("Cobblestone"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsStone", "stoneItems", new ItemStack(getPipePart("Stone"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsQuartz", "quartzItems", new ItemStack(getPipePart("Quartz"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsSandstone", "sandstoneItems", new ItemStack(getPipePart("Sandstone"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsGold", "goldItems", new ItemStack(getPipePart("Gold"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsIron", "ironItems", new ItemStack(getPipePart("Iron"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsVoid", "voidItems", new ItemStack(getPipePart("Void"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsObsidian", "obsidianItems", new ItemStack(getPipePart("Obsidian"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsEmerald", "emeraldItems", new ItemStack(getPipePart("Emerald"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsDiamond", "diamondItems", new ItemStack(getPipePart("Diamond"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsLapis", "lazuliItems", new ItemStack(getPipePart("Lapis"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeItemsDaizuli", "diazuliItems", new ItemStack(getPipePart("Daizuli"), 2), new ItemStack(glassShard));
			BCUtils.addBCKey("item.PipeStructureCobblestone", "structurePipe", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_COBBLESTONE), new ItemStack(Blocks.gravel));


			//Fluid pipes
			EurekaRegistry.registerCategory("Buildcraft|FluidPipes", new ItemStack(BCUtils.BCItems.PIPE_FLUID_EMERALD));
			EurekaRegistry.register(new EurekaInfo("woodenFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_FLUID_WOOD), "woodItems"));
			EurekaRegistry.register(new EurekaInfo("cobblestoneFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_FLUID_COBBLESTONE), "woodenFluid", "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("stoneFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_FLUID_STONE), "cobblestoneFluid", "stoneItems"));
			EurekaRegistry.register(new EurekaInfo("sandstoneFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_FLUID_SANDSTONE), "stoneFluid", "sandstoneItems"));
			EurekaRegistry.register(new EurekaInfo("quartzFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_FLUID_QUARTZ), "stoneFluid", "quartzItems"));
			EurekaRegistry.register(new EurekaInfo("goldFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_FLUID_GOLD), "stoneFluid", "goldItems"));
			EurekaRegistry.register(new EurekaInfo("ironFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_FLUID_IRON), "cobblestoneFluid", "ironItems"));
			EurekaRegistry.register(new EurekaInfo("emeraldFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_FLUID_EMERALD), "woodenFluid", "emeraldItems"));
			EurekaRegistry.register(new EurekaInfo("voidFluid", "Buildcraft|FluidPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_FLUID_VOID), "cobblestoneFluid", "voidItems"));
			BCUtils.addBCKey("item.PipeFluidsWood", "woodenFluid", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_WOOD), new ItemStack(BCUtils.BCItems.SEALANT));
			BCUtils.addBCKey("item.PipeFluidsCobblestone", "cobblestoneFluid", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_COBBLESTONE), new ItemStack(BCUtils.BCItems.SEALANT));
			BCUtils.addBCKey("item.PipeFluidsStone", "stoneFluid", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_STONE), new ItemStack(BCUtils.BCItems.SEALANT));
			BCUtils.addBCKey("item.PipeFluidsSandstone", "sandstoneFluid", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_SANDSTONE), new ItemStack(BCUtils.BCItems.SEALANT));
			BCUtils.addBCKey("item.PipeFluidsQuartz", "quartzFluid", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_QUARTZ), new ItemStack(BCUtils.BCItems.SEALANT));
			BCUtils.addBCKey("item.PipeFluidsGold", "goldFluid", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_GOLD), new ItemStack(BCUtils.BCItems.SEALANT));
			BCUtils.addBCKey("item.PipeFluidsIron", "ironFluid", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_IRON), new ItemStack(BCUtils.BCItems.SEALANT));
			BCUtils.addBCKey("item.PipeFluidsEmerald", "emeraldFluid", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_EMERALD), new ItemStack(BCUtils.BCItems.SEALANT));
			BCUtils.addBCKey("item.PipeFluidsVoid", "voidFluid", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_VOID), new ItemStack(BCUtils.BCItems.SEALANT));

			//Kinetic pipes
			EurekaRegistry.registerCategory("Buildcraft|PowerPipes", new ItemStack(BCUtils.BCItems.PIPE_POWER_EMERALD));
			EurekaRegistry.register(new EurekaInfo("woodenPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_POWER_WOOD), "woodItems", "stilringEngine"));
			EurekaRegistry.register(new EurekaInfo("cobblestonePower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_POWER_COBBLESTONE), "woodenPower", "cobblestoneItems"));
			EurekaRegistry.register(new EurekaInfo("stonePower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_POWER_STONE), "cobblestonePower", "stoneItems"));
			EurekaRegistry.register(new EurekaInfo("quartzPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_POWER_QUARTZ), "stonePower", "quartzItems"));
			EurekaRegistry.register(new EurekaInfo("ironPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_POWER_IRON), "quartzPower", "ironItems"));
			EurekaRegistry.register(new EurekaInfo("goldPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_POWER_GOLD), "quartzPower", "goldItems"));
			EurekaRegistry.register(new EurekaInfo("diamondPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_POWER_DIAMOND), "goldPower", "diamondItems"));
			EurekaRegistry.register(new EurekaInfo("emeraldPower", "Buildcraft|PowerPipes", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_POWER_EMERALD), "diamondPower"));
			BCUtils.addBCKey("item.PipePowerWood", "woodenPower", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_WOOD), new ItemStack(Items.redstone));
			BCUtils.addBCKey("item.PipePowerCobblestone", "cobblestonePower", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_COBBLESTONE), new ItemStack(Items.redstone));
			BCUtils.addBCKey("item.PipePowerStone", "stonePower", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_STONE), new ItemStack(Items.redstone));
			BCUtils.addBCKey("item.PipePowerQuartz", "quartzPower", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_QUARTZ), new ItemStack(Items.redstone));
			BCUtils.addBCKey("item.PipePowerIron", "ironPower", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_IRON), new ItemStack(Items.redstone));
			BCUtils.addBCKey("item.PipePowerGold", "goldPower", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_GOLD), new ItemStack(Items.redstone));
			BCUtils.addBCKey("item.PipePowerDiamond", "diamondPower", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_DIAMOND), new ItemStack(Items.redstone));
			BCUtils.addBCKey("item.PipePowerEmerald", "emeraldPower", new ItemStack(BCUtils.BCItems.PIPE_ITEMS_EMERALD), new ItemStack(Items.redstone));


			//Building
			EurekaRegistry.registerCategory("Buildcraft|Building", new ItemStack(BCUtils.BCItems.BUILDER));
			EurekaRegistry.register(new EurekaInfo("landmark", "Buildcraft|Building", 1, 1, new ItemStack(BCUtils.BCItems.MARKER)));
			EurekaRegistry.register(new EurekaInfo("pathmarker", "Buildcraft|Building", 1, 1, new ItemStack(BCUtils.BCItems.PATHMARKER), "landmark"));
			EurekaRegistry.register(new EurekaInfo("template", "Buildcraft|Building", 1, 1, new ItemStack(BCUtils.BCItems.TEMPLATE)));
			EurekaRegistry.register(new EurekaInfo("blueprint", "Buildcraft|Building", 1, 1, new ItemStack(BCUtils.BCItems.BLUEPRINT), "template"));
			EurekaRegistry.register(new EurekaInfo("blueprintLibrary", "Buildcraft|Building", 1, 1, new ItemStack(BCUtils.BCItems.LIBRARY), "blueprint", "landmark"));
			EurekaRegistry.register(new EurekaInfo("filler", "Buildcraft|Building", 1, 1, new ItemStack(BCUtils.BCItems.FILLER), "landmark"));
			EurekaRegistry.register(new EurekaInfo("architectTable", "Buildcraft|Building", 1, 1, new ItemStack(BCUtils.BCItems.ARCHITECT), "blueprint", "landmark"));
			EurekaRegistry.register(new EurekaInfo("builder", "Buildcraft|Building", 1, 1, new ItemStack(BCUtils.BCItems.BUILDER), "architectTable", "pathmarker", "filler"));
			BCUtils.addBCKey("tile.markerBlock", "landmark", new ItemStack(Blocks.redstone_torch), new ItemStack(Items.dye, 1, 4));
			BCUtils.addBCKey("tile.pathMarkerBlock", "pathmarker", new ItemStack(Blocks.redstone_torch), new ItemStack(Items.dye, 1, 2));
			BCUtils.addBCKey("tile.libraryBlock", "blueprintLibrary", new ItemStack(Blocks.bookshelf), new ItemStack(BCUtils.BCItems.BLUEPRINT, 8));
			BCUtils.addBCKey("tile.fillerBlock", "filler", new ItemStack(Items.dye, 2, 0), new ItemStack(Items.dye, 2, 11), new ItemStack(BCUtils.BCItems.MARKER), new ItemStack(Blocks.crafting_table), new ItemStack(BCUtils.BCItems.GOLD_GEAR, 2), new ItemStack(Blocks.chest));
			BCUtils.addBCKey("tile.architectBlock", "architectTable", new ItemStack(Items.dye, 2, 0), new ItemStack(Items.dye, 2, 11), new ItemStack(BCUtils.BCItems.MARKER), new ItemStack(Blocks.crafting_table), new ItemStack(BCUtils.BCItems.DIAMOND_GEAR, 2), new ItemStack(BCUtils.BCItems.BLUEPRINT));
			BCUtils.addBCKey("tile.builderBlock", "builder", new ItemStack(Items.dye, 2, 0), new ItemStack(Items.dye, 2, 11), new ItemStack(BCUtils.BCItems.MARKER), new ItemStack(Blocks.crafting_table), new ItemStack(BCUtils.BCItems.DIAMOND_GEAR, 2), new ItemStack(Blocks.chest));

			//Silicon
			EurekaRegistry.registerCategory("Buildcraft|Silicon", new ItemStack(BCUtils.BCItems.LASER));
			EurekaRegistry.register(new EurekaInfo("laser", "Buildcraft|Silicon", 1, 1, new ItemStack(BCUtils.BCItems.LASER)));
			EurekaRegistry.register(new EurekaInfo("assemblyTable", "Buildcraft|Silicon", 1, 1, new ItemStack(BCUtils.BCItems.LASERTABLES, 1, 0), "laser"));
			EurekaRegistry.register(new EurekaInfo("integrationTable", "Buildcraft|Silicon", 1, 1, new ItemStack(BCUtils.BCItems.LASERTABLES, 1, 2), "assemblyTable"));
			EurekaRegistry.register(new EurekaInfo("advancedCraftingTable", "Buildcraft|Silicon", 1, 1, new ItemStack(BCUtils.BCItems.LASERTABLES, 1, 1), "laser", "autoWorkbench"));
			EurekaRegistry.register(new EurekaInfo("pipePlug", "Buildcraft|Silicon", 1, 1, new ItemStack(BCUtils.BCItems.PIPE_PLUG)));
			BCUtils.addBCKey("tile.laserBlock", "laser", new ItemStack(Blocks.obsidian, 2), new ItemStack(Items.diamond, 2), new ItemStack(Items.redstone, 5));
			BCUtils.addBCKey("lasertable1", "assemblyTable", new ItemStack(Blocks.obsidian, 6), new ItemStack(Items.redstone), new ItemStack(Items.diamond), new ItemStack(BCUtils.BCItems.DIAMOND_GEAR));
			BCUtils.addBCKey("lasertabble2", "advancedCraftingTable", new ItemStack(Blocks.obsidian, 6), new ItemStack(Blocks.crafting_table), new ItemStack(Blocks.chest), new ItemStack(BCUtils.BCItems.CHIPSET_REDSTONE));
			BCUtils.addBCKey("lasertable3", "integrationTable", new ItemStack(Blocks.obsidian, 6), new ItemStack(Items.redstone), new ItemStack(BCUtils.BCItems.CHIPSET_REDSTONE), new ItemStack(BCUtils.BCItems.DIAMOND_GEAR));



			//Engines
			EurekaRegistry.registerCategory("Buildcraft|Engines", new ItemStack(BCUtils.BCItems.ENGINES, 1, 3));
			EurekaRegistry.register(new EurekaInfo("redstoneEngine", "Buildcraft|Engines", 1, 1, new ItemStack(BCUtils.BCItems.ENGINES, 1, 0)));
			EurekaRegistry.register(new EurekaInfo("stilringEngine", "Buildcraft|Engines", 1, 1, new ItemStack(BCUtils.BCItems.ENGINES, 1, 1), "redstoneEngine"));
			EurekaRegistry.register(new EurekaInfo("combustionEngine", "Buildcraft|Engines", 1, 1, new ItemStack(BCUtils.BCItems.ENGINES, 1, 2), "stilringEngine"));
			BCUtils.addBCKey("engineBlock0", "redstoneEngine", new ItemStack(Blocks.planks, 3), new ItemStack(Blocks.piston), new ItemStack(BCUtils.BCItems.WOODEN_GEAR, 2));
			BCUtils.addBCKey("engineBlock1", "stilringEngine", new ItemStack(Blocks.cobblestone, 3), new ItemStack(Blocks.piston), new ItemStack(BCUtils.BCItems.STONE_GEAR));
			BCUtils.addBCKey("engineBlock2", "combustionEngine", new ItemStack(Items.iron_ingot, 3), new ItemStack(Blocks.piston), new ItemStack(BCUtils.BCItems.IRON_GEAR));

		}
	}

	public void handlePipe(String material){
		pipePart = new ItemPipePart(material).setUnlocalizedName("pipePart" + material);
		GameRegistry.registerItem(pipePart, "pipePart" + material);
		GameRegistry.addRecipe(new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitems" + material.toLowerCase())), "PGP", 'P', pipePart, 'G', glassShard);
	}

	public Item getPipePart(String material){
		return GameRegistry.findItem("eureka", "pipePart" + material);
	}
}