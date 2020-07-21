package com.bebopser.china.compat.jei;

import com.bebopser.china.recipes.virtual.ICookingTableRecipe;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class RecipeWrapperCookingTable implements IRecipeWrapperFactory<ICookingTableRecipe> {
    @Override
    public IRecipeWrapper getRecipeWrapper(ICookingTableRecipe recipe) {
        return new RecipeCookingTable(recipe);
    }
}
