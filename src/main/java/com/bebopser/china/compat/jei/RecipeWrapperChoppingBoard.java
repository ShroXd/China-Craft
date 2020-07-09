package com.bebopser.china.compat.jei;

import com.bebopser.china.recipes.virtual.IChoppingBoardRecipe;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class RecipeWrapperChoppingBoard implements IRecipeWrapperFactory<IChoppingBoardRecipe> {

    @Override
    public IRecipeWrapper getRecipeWrapper(IChoppingBoardRecipe recipe) {
        return new RecipeChoppingBoard(recipe);
    }
}
