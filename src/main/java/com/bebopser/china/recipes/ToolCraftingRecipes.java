package com.bebopser.china.recipes;

import com.bebopser.china.loader.ItemSeriesLoader;
import com.bebopser.china.util.RecipesUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ToolCraftingRecipes {

    public ToolCraftingRecipes() {

        RecipesUtil.addRecipes("kitchen_knife", new ShapedOreRecipe(
                new ResourceLocation(""),
                new ItemStack(ItemSeriesLoader.tool, 1, 2),
                "A  ", " A ", "  B",
                'A', Items.IRON_INGOT,
                'B', Items.STICK
        ));
    }
}
