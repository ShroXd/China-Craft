package com.bebopser.china.compat.jei;

import com.bebopser.china.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CategoryChoppingBoard implements IRecipeCategory<IRecipeWrapper> {

    protected final IDrawable background;
    protected final IDrawableAnimated progressBar;

    public CategoryChoppingBoard(IGuiHelper helper) {
        ResourceLocation backgroundTexture = new ResourceLocation(Reference.MODID, "textures/gui/chopping_board.png");
        background = helper.createDrawable(backgroundTexture, 41, 17, 94, 50);
        IDrawableStatic progressBarOverlay = helper.createDrawable(backgroundTexture, 176, 0, 24, 17);
        progressBar = helper.createAnimatedDrawable(progressBarOverlay, 300, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public String getUid() {
        return Reference.MODID + "." + "choppingboard";
    }

    @Override
    public String getTitle() {
        return I18n.format("jei." + Reference.MODID + ".category" + ".choppingboard");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        progressBar.draw(minecraft, 33, 16);
    }

    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup items = recipeLayout.getItemStacks();
        items.init(0, true, 1, 15);
        items.set(0, ((RecipeChoppingBoard) recipeWrapper).getRawFoodInputs());

        items.init(1, true, 74, 2);
        items.set(1, ((RecipeChoppingBoard) recipeWrapper).getToolInputs());

        items.init(2, false, 74, 30);
        items.set(2, ((RecipeChoppingBoard) recipeWrapper).getOutputs());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        setRecipe(recipeLayout, recipeWrapper);
    }

    @Override
    public String getModName()
    {
        return "ChinaCraft";
    }


}
