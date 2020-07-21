package com.bebopser.china.loader;

import com.bebopser.china.recipes.blocks.ChoppingBoardRecipe;
import com.bebopser.china.recipes.blocks.ChoppingBoardRecipeManager;
import com.bebopser.china.recipes.blocks.CookingTableRecipe;
import com.bebopser.china.recipes.blocks.CookingTableRecipeManager;
import com.bebopser.china.recipes.virtual.IChoppingBoardRecipe;
import com.bebopser.china.recipes.virtual.IChoppingBoardRecipeManager;
import com.bebopser.china.recipes.virtual.ICookingTableRecipe;
import com.bebopser.china.recipes.virtual.ICookingTableRecipeManager;
import com.bebopser.china.util.NonNullListHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class RecipeLoader {

    public final static IChoppingBoardRecipeManager<IChoppingBoardRecipe> managerChoppingBoard = new ChoppingBoardRecipeManager();
    public final static ICookingTableRecipeManager<ICookingTableRecipe> managerCookingTable = new CookingTableRecipeManager();
    
    public RecipeLoader() {
        addChoppingBoardRecipe();
        addCookingTableRecipe();
    }

    private void addChoppingBoardRecipe() {

        registerAllDamage(ItemSeriesLoader.rawFood, ItemLoader.rolling_pin, new ItemStack(ItemSeriesLoader.rawFood, 3, 2));


        managerChoppingBoard.add(new ChoppingBoardRecipe(
                NonNullListHelper.createNonNullList(new ItemStack(ItemSeriesLoader.rawFood, 1, 2)),
                NonNullListHelper.createNonNullList(new ItemStack(ItemSeriesLoader.tool, 1, 2)),
                new ItemStack(ItemSeriesLoader.rawFood, 1, 3)
        ));
    }

    private void addCookingTableRecipe() {
        managerCookingTable.add(new CookingTableRecipe(
                NonNullListHelper.createNonNullList(new ItemStack(ItemSeriesLoader.edibleFood, 1, 0)),
                new ItemStack(ItemSeriesLoader.edibleFood, 1, 1)
        ));
    }

    private void registerAllDamage(ItemFood rawFood, Item tool, ItemStack outpout) {
        for (int i = 0; i < tool.getMaxDamage() + 1; i++) {
            managerChoppingBoard.add(new ChoppingBoardRecipe(
                    NonNullListHelper.createNonNullList(new ItemStack(rawFood, 1, 1)),
                    NonNullListHelper.createNonNullList(new ItemStack(tool, 1, i)),
                    outpout
            ));
        }
    }

}
