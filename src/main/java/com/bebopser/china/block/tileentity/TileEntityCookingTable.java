package com.bebopser.china.block.tileentity;

import com.bebopser.china.block.CookingTable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCookingTable extends TileEntity implements ITickable {

    protected int fuelTime = 0;
    protected int fuelTotalTime = 1;

    protected ItemStackHandler foodInventory = new ItemStackHandler();
    protected ItemStackHandler fuelInventory = new ItemStackHandler();
    protected ItemStackHandler outputInventory = new ItemStackHandler();

    // TODO: 引入熔炼铺

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
        this.fuelTime = compound.getInteger("FuelTime");
        this.fuelTotalTime = compound.getInteger("FuelTotalTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("FoodInventory", this.foodInventory.serializeNBT());
        compound.setTag("FuelInventory", this.fuelInventory.serializeNBT());
        compound.setTag("OutputInventory", this.outputInventory.serializeNBT());
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
        } else {
            CookingTable.setState(false, this.getWorld(), this.pos);
        }
    }

    public boolean isBurning() {
        return this.fuelTime > 0;
    }

    public boolean isItemFuel(ItemStack stack) {
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
}
