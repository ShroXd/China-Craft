package com.bebopser.china.loader;

import com.bebopser.china.recipes.blocks.ChoppingBoardRecipe;
import com.bebopser.china.recipes.blocks.ChoppingBoardRecipeManager;
import com.bebopser.china.recipes.virtual.IChoppingBoardRecipe;
import com.bebopser.china.recipes.virtual.IChoppingBoardRecipeManager;
import com.bebopser.china.util.NonNullListHelper;
import net.minecraft.item.ItemStack;

public class RecipeLoader {

    public final static IChoppingBoardRecipeManager<IChoppingBoardRecipe> managerChoppingBoard = new ChoppingBoardRecipeManager();
    
    public RecipeLoader() {
        addChoppingBoardRecipe();
    }

    private void addChoppingBoardRecipe() {
        managerChoppingBoard.add(new ChoppingBoardRecipe(
                NonNullListHelper.createNonNullList(new ItemStack(ItemLoader.rawFood, 1, 1)),
                NonNullListHelper.createNonNullList(new ItemStack(ItemLoader.tool, 1, 1)),
                new ItemStack(ItemLoader.rawFood, 1, 2)
        ));

        managerChoppingBoard.add(new ChoppingBoardRecipe(
                NonNullListHelper.createNonNullList(new ItemStack(ItemLoader.rawFood, 1, 2)),
                NonNullListHelper.createNonNullList(new ItemStack(ItemLoader.tool, 1, 2)),
                new ItemStack(ItemLoader.rawFood, 1, 3)
        ));
    }

}
