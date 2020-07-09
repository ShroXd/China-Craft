package com.bebopser.china.compat.jei;

import com.bebopser.china.loader.RecipeLoader;
import com.bebopser.china.recipes.virtual.IChoppingBoardRecipe;
import com.bebopser.china.util.NonNullListHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

public class RecipeChoppingBoard implements IRecipeWrapper {

    private final IChoppingBoardRecipe recipe;

    public RecipeChoppingBoard(IChoppingBoardRecipe recipe) {
        this.recipe = recipe;
    }

    public static List<RecipeChoppingBoard> getWrappedRecipeList() {
        List<RecipeChoppingBoard> recipesToReturn = new ArrayList<>();
        for (IChoppingBoardRecipe recipe : RecipeLoader.managerChoppingBoard.getRecipes()) {
            recipesToReturn.add(new RecipeChoppingBoard(recipe));
        }
        return recipesToReturn;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, getInputs());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    public NonNullList<ItemStack> getRawFoodInputs() {
        return NonNullListHelper.createNonNullList(recipe.getRawFoodInput());
    }

    public NonNullList<ItemStack> getToolInputs() {
        return NonNullListHelper.createNonNullList(recipe.getToolInput());
    }

    public NonNullList<ItemStack> getInputs() {
        NonNullList<ItemStack> list = NonNullListHelper.createNonNullList(recipe.getRawFoodInput());
        list.addAll(recipe.getToolInput());
        return list;
    }

    public NonNullList<ItemStack> getOutputs() {
        return NonNullListHelper.createNonNullList(recipe.getOutput());
    }

}
