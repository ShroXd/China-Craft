package com.bebopser.china.compat.jei;

import com.bebopser.china.loader.RecipeLoader;
import com.bebopser.china.recipes.virtual.ICookingTableRecipe;
import com.bebopser.china.util.NonNullListHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

public class RecipeCookingTable implements IRecipeWrapper {

    private final ICookingTableRecipe recipe;

    public static List<RecipeCookingTable> getWrappedRecipeList() {
        List<RecipeCookingTable> recipesToReturn = new ArrayList<>();
        for (ICookingTableRecipe recipe : RecipeLoader.managerCookingTable.getRecipes()) {
            recipesToReturn.add(new RecipeCookingTable(recipe));
        }
        return recipesToReturn;
    }

    public RecipeCookingTable(ICookingTableRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.getInputs());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    public NonNullList<ItemStack> getInputs() {
        return NonNullListHelper.createNonNullList(recipe.getInputs());
    }

    public NonNullList<ItemStack> getOutputs() {
        return NonNullListHelper.createNonNullList(recipe.getOutput());
    }
}
