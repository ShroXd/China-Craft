package com.bebopser.china.block.tileentity;

import com.bebopser.china.recipes.virtual.IChoppingBoardRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import static com.bebopser.china.loader.RecipeLoader.managerChoppingBoard;
import static com.bebopser.china.recipes.blocks.ChoppingBoardRecipe.EMPTY_RECIPE;

public class TileEntityChoppingBoard extends TileEntity implements ITickable {

    protected int cookTime = 0;
    protected IChoppingBoardRecipe usedRecipe = EMPTY_RECIPE;

    protected ItemStackHandler InventoryRawFood = new ItemStackHandler();
    protected ItemStackHandler InventoryTool = new ItemStackHandler();
    protected ItemStackHandler InventoryDrink = new ItemStackHandler();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            if (facing == EnumFacing.EAST) {
                return (T) InventoryRawFood;
            }
            else if (facing == EnumFacing.SOUTH) {
                return (T) InventoryTool;
            }
            else {
                return (T) InventoryDrink;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.InventoryRawFood.deserializeNBT(compound.getCompoundTag("InventoryRawFood"));
        this.InventoryTool.deserializeNBT(compound.getCompoundTag("InventoryTool"));
        this.InventoryDrink.deserializeNBT(compound.getCompoundTag("InventoryDrink"));
        this.cookTime = compound.getInteger("CookTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("InventoryRawFood", this.InventoryRawFood.serializeNBT());
        compound.setTag("InventoryTool", this.InventoryTool.serializeNBT());
        compound.setTag("InventoryDrink", this.InventoryTool.serializeNBT());
        compound.setInteger("CookTime", this.cookTime);

        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            ItemStack rawFood = InventoryRawFood.getStackInSlot(0).copy();
            ItemStack tool = InventoryTool.getStackInSlot(0).copy();

/*            if (tool.isEmpty() || !(tool.getItem() instanceof ItemTool)) {
                this.cookTime = 0;
                this.markDirty();
                return;
            }*/



            if (!usedRecipe.isTheSameInput(rawFood, tool)) {
                usedRecipe = managerChoppingBoard.getRecipes(rawFood, tool);
            }

            if (!usedRecipe.getOutput().isEmpty()) {
                ItemStack output = usedRecipe.getOutput().copy();
                if (this.InventoryDrink.insertItem(0, output, true).isEmpty() &&
                    rawFood.getCount() >= usedRecipe.getRawFoodInput().get(0).getCount() &&
                    tool.getCount() >= usedRecipe.getToolInput().get(0).getCount()) {

                    if (++this.cookTime >= 200) {
                        InventoryDrink.insertItem(0, output, false);
                        InventoryRawFood.extractItem(0, usedRecipe.getRawFoodInput().get(0).getCount(), false);
                        InventoryTool.extractItem(0, usedRecipe.getToolInput().get(0).getCount(), false);

                        if (tool.isItemStackDamageable()) {

                            if (tool.getItemDamage() + 1 <= tool.getMaxDamage()) {
                                InventoryTool.setStackInSlot(0, new ItemStack(tool.getItem(), 1, tool.getItemDamage() + 1));
                            } else {
                                InventoryTool.extractItem(0, 1, false);
                            }

                        } else if (tool.isStackable()) {
                            InventoryTool.extractItem(0, usedRecipe.getToolInput().get(0).getCount(), false);
                        } else {
                            InventoryTool.setStackInSlot(0, tool.getItem().getContainerItem(tool));
                        }

                        this.cookTime = 0;
                    }

                    this.markDirty();
                    return;
                }

                this.cookTime = 0;
                this.markDirty();
                return;
            }
        }
    }

    public int getCookTime() {
        return this.cookTime;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
