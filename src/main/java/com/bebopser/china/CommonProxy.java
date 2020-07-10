package com.bebopser.china;

import com.bebopser.china.loader.*;
import com.bebopser.china.recipes.FoodCraftingRecipes;
import com.bebopser.china.recipes.ToolCraftingRecipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event)
    {
        new BlockLoader();
        new ItemSeriesLoader(event);
        new ItemLoader();
        new PotionLoader(event);
        new TileEntityLoader();
    }

    public void init(FMLInitializationEvent event)
    {
        new RecipeLoader();
        new FoodCraftingRecipes();
        new ToolCraftingRecipes();
        new GuiLoader();
    }

    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
