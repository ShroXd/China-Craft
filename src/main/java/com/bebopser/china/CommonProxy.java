package com.bebopser.china;

import com.bebopser.china.loader.ItemLoader;
import com.bebopser.china.loader.PotionLoader;
import com.bebopser.china.recipes.FoodCraftingRecipes;
import com.bebopser.china.recipes.ToolCraftingRecipes;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event)
    {
        new PotionLoader(event);
        new ItemLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        new FoodCraftingRecipes();
        new ToolCraftingRecipes();
    }

    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
