package com.bebopser.china.recipes.blocks;

import com.bebopser.china.recipes.virtual.IChoppingBoardRecipe;
import com.bebopser.china.recipes.virtual.IChoppingBoardRecipeManager;
import com.bebopser.china.util.NonNullListHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collection;

public class ChoppingBoardRecipeManager implements IChoppingBoardRecipeManager<IChoppingBoardRecipe> {

    private static final ArrayList<IChoppingBoardRecipe> recipes = new ArrayList<>();

    @Override
    public boolean equal(IChoppingBoardRecipe recipe1, IChoppingBoardRecipe recipe2) {
        return recipe1.equals(recipe2);
    }

    @Override
    public void add(IChoppingBoardRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public void remove(IChoppingBoardRecipe recipe) {
        java.util.Iterator<IChoppingBoardRecipe> iter = recipes.iterator();

        while (iter.hasNext()) {
            if (iter.next().equals(recipe)) {
                iter.remove();
                return;
            }
        }
    }

    @Override
    public void remove(NonNullList<ItemStack> rawFoods, NonNullList<ItemStack> tools, ItemStack output) {

        IChoppingBoardRecipe recipe = getRecipes(rawFoods, tools);
        if (OreDictionary.itemMatches(output, recipe.getOutput(), false)) {
            remove(recipe);
        }
    }

    @Override
    public void removeAll() {
        recipes.clear();
    }

    @Override
    public Collection<IChoppingBoardRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public IChoppingBoardRecipe getRecipes(ItemStack rawFood, ItemStack tool) {
        for (IChoppingBoardRecipe recipe : recipes) {
            if (recipe.isTheSameInput(rawFood, tool)) {
                return recipe;
            }
        }
        return new ChoppingBoardRecipe(
                NonNullListHelper.createNonNullList(rawFood),
                NonNullListHelper.createNonNullList(tool),
                ItemStack.EMPTY
        );
    }

    @Override
    public IChoppingBoardRecipe getRecipes(NonNullList<ItemStack> rawFoods, NonNullList<ItemStack> tools) {
        for (ItemStack theRawFood : rawFoods) {
            for (ItemStack theTool : tools) {
                IChoppingBoardRecipe recipe = getRecipes(theRawFood, theTool);
                if (!recipe.getOutput().isEmpty()) {
                    return recipe;
                }
            }
        }

        return new ChoppingBoardRecipe(rawFoods, tools, ItemStack.EMPTY);
    }

}
