package com.bebopser.china.recipes;

import com.bebopser.china.loader.ItemLoader;
import com.bebopser.china.util.RecipesUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class FoodCraftingRecipes {

    public FoodCraftingRecipes() {

        RecipesUtil.addRecipes("dough", new ShapelessOreRecipe(
                new ResourceLocation(""),
                new ItemStack(ItemLoader.rawFood, 1, 1),    // dough
                new ItemStack(ItemLoader.rawFood, 1, 0),    // flour
                Items.WATER_BUCKET
        ));

        RecipesUtil.addRecipes("flour_pastry", new ShapelessOreRecipe(
                new ResourceLocation(""),
                new ItemStack(ItemLoader.rawFood, 2, 2),    // flour_pastry
                new ItemStack(ItemLoader.rawFood, 1, 1),    // dough
                new ItemStack(ItemLoader.tool, 1, 1)        // rolling_pin
        ));

        RecipesUtil.addRecipes("flour_silk", new ShapelessOreRecipe(
                new ResourceLocation(""),
                new ItemStack(ItemLoader.rawFood, 2, 3),    // flour_silk
                new ItemStack(ItemLoader.rawFood, 1, 2),    // flour_pastry
                new ItemStack(ItemLoader.tool, 1, 2)        // kitchen_knife
        ));
    }
}
