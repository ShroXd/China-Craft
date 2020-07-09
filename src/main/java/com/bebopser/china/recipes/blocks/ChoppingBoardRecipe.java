package com.bebopser.china.recipes.blocks;

import com.bebopser.china.recipes.virtual.IChoppingBoardRecipe;
import com.bebopser.china.util.NonNullListHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public class ChoppingBoardRecipe implements IChoppingBoardRecipe {

    private final ItemStack outpout;
    private final NonNullList<ItemStack> rawFood, tool;
    public static final IChoppingBoardRecipe EMPTY_RECIPE = new ChoppingBoardRecipe(
            NonNullListHelper.createNonNullList(ItemStack.EMPTY),
            NonNullListHelper.createNonNullList(ItemStack.EMPTY),
            ItemStack.EMPTY
    );

    public ChoppingBoardRecipe(NonNullList<ItemStack> rawFood, NonNullList<ItemStack> tool, ItemStack outpout) {
        this.rawFood = rawFood;
        this.tool = tool;
        this.outpout = outpout;
    }

    @Override
    public NonNullList<ItemStack> getRawFoodInput() {
        return rawFood;
    }

    @Override
    public NonNullList<ItemStack> getToolInput() {
        return tool;
    }

    @Override
    public ItemStack getOutput() {
        return outpout;
    }

    @Override
    public boolean isTheSameInput(ItemStack rawFood, ItemStack tool) {
        return !this.outpout.isEmpty() &&
                checkSame(this.rawFood, rawFood) &&
                checkSame(this.tool, tool);
    }

    private boolean checkSame(NonNullList<ItemStack> stacks, @Nonnull ItemStack target) {
        for (ItemStack input : stacks) {
            if (OreDictionary.itemMatches(input, target, true)) {
                return true;
            }
        }

        return false;
    }
}
