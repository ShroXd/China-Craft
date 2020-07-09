package com.bebopser.china.loader;

import com.bebopser.china.compat.jei.CategoryChoppingBoard;
import com.bebopser.china.compat.jei.RecipeChoppingBoard;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class JEICompatLoader implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new CategoryChoppingBoard(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void register(IModRegistry registry) {

        registry.addRecipes(RecipeChoppingBoard.getWrappedRecipeList(), "chinacraft.choppingboard");
    }
}
