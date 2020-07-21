package com.bebopser.china.block.tileentity;

import com.bebopser.china.block.CookingTable;
import com.bebopser.china.loader.RecipeLoader;
import com.bebopser.china.recipes.blocks.CookingTableRecipe;
import com.bebopser.china.recipes.virtual.ICookingTableRecipe;
import com.bebopser.china.util.NonNullListHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.Sys;

public class TileEntityCookingTable extends TileEntity implements ITickable {

    protected int cookTime = 0;
    protected int fuelTime = 0;
    protected int fuelTotalTime = 1;

    protected ItemStackHandler foodInventory = new ItemStackHandler();
    protected ItemStackHandler fuelInventory = new ItemStackHandler();
    protected ItemStackHandler outputInventory = new ItemStackHandler();

    // TODO: 引入熔炼铺
    protected ICookingTableRecipe cookingTableRecipe = new CookingTableRecipe(NonNullListHelper.createNonNullList(ItemStack.EMPTY), ItemStack.EMPTY);

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
            T result = (T) (facing == EnumFacing.DOWN ? outputInventory : facing == EnumFacing.UP ? foodInventory : fuelInventory);
            return result;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.foodInventory.deserializeNBT(compound.getCompoundTag("FoodInventory"));
        this.fuelInventory.deserializeNBT(compound.getCompoundTag("FuelInventory"));
        this.outputInventory.deserializeNBT(compound.getCompoundTag("OutputInventory"));
        this.cookTime = compound.getInteger("CookTime");
        this.fuelTime = compound.getInteger("FuelTime");
        this.fuelTotalTime = compound.getInteger("FuelTotalTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("FoodInventory", this.foodInventory.serializeNBT());
        compound.setTag("FuelInventory", this.fuelInventory.serializeNBT());
        compound.setTag("OutputInventory", this.outputInventory.serializeNBT());
        compound.setInteger("CookTime", this.cookTime);
        compound.setInteger("FuelTime", this.fuelTime);
        compound.setInteger("FuelTotalTime", this.fuelTotalTime);
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }

        ItemStack input = this.foodInventory.getStackInSlot(0).copy();
        if (input.isEmpty()) {
            reduceFuelTime();
            markDirty();
            return;
        }

        if (!cookingTableRecipe.isTheSameInput(input)) {
            cookingTableRecipe = RecipeLoader.managerCookingTable.getRecipe(input);
        }

        if (!cookingTableRecipe.getOutput().isEmpty()) {
            process();
            reduceFuelTime();
            markDirty();
        }
    }

    public void reduceFuelTime() {
        if (isBurning()) {
            fuelTime--;
            if (fuelTime == 0) {
                ItemStack itemFuel = fuelInventory.extractItem(0, 1, true);
                if (!isItemFuel(itemFuel)) {
                    CookingTable.setState(false, this.getWorld(), this.pos);
                }
            }
        }
    }

    public void process() {
        if (outputInventory.insertItem(0, cookingTableRecipe.getOutput().copy(), true).isEmpty()) {
            if (this.hasFuelOrIsBurning()) {
                this.cookTime++;
            } else {
                this.cookTime = 0;
            }
        } else {
            this.cookTime = 0;
        }

        if (this.cookTime == this.getTotalCookTime()) {
            foodInventory.extractItem(0, 1, false);
            outputInventory.insertItem(0, cookingTableRecipe.getOutput().copy(), false);
            this.cookTime = 0;
        }

        this.markDirty();
    }

    public boolean hasFuelOrIsBurning() {
        if (this.isBurning()) {
            return true;
        } else {
            ItemStack itemFuel = fuelInventory.extractItem(0, 1, true);
            if (isItemFuel(itemFuel)) {
                this.fuelTime = getItemBurnTime(itemFuel);
                this.fuelTotalTime = getItemBurnTime(itemFuel);
                Item cItem = fuelInventory.getStackInSlot(0).getItem().getContainerItem();
                if (cItem != null) {
                    fuelInventory.extractItem(0,1, false);
                    fuelInventory.insertItem(0, new ItemStack(cItem, 1), false);
                } else {
                    fuelInventory.extractItem(0, 1, false);
                }
                this.markDirty();
                CookingTable.setState(true, this.getWorld(), this.pos);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isBurning() {
        return this.fuelTime > 0;
    }

    public static boolean isItemFuel(ItemStack stack) {
        return getItemBurnTime(stack) > 0;
    }

    public static int getItemBurnTime(ItemStack stack) {
        return TileEntityFurnace.getItemBurnTime(stack);
    }

    public int getFuelTime() {
        return this.fuelTime;
    }

    public int getFuelTotalTime() {
        return this.fuelTotalTime;
    }

    public ItemStack getOutput() {
        return cookingTableRecipe.getOutput();
    }

    public int getTotalCookTime() {
        return 500;
    }

    public int getCookTime() {
        return this.cookTime;
    }
}
