package com.bebopser.china.block.tileentity;

import com.bebopser.china.gui.ContainerChoppingBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityChoppingBoard extends TileEntityLockable implements IInventory, ITickable {

    private NonNullList<ItemStack> furnaceItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);

    private int cookTime;
    private int totalCookTime;

    private String choppingBoardName;

    @Override
    public int getSizeInventory() {
        return furnaceItemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.furnaceItemStacks) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.furnaceItemStacks.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemStack = this.furnaceItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
        this.furnaceItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    public int getCookTime(ItemStack stack) {
        return 200;
    }

    @Override
    public void clear() {
        this.furnaceItemStacks.clear();
    }


    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq(
                    (double) this.pos.getX() + 0.5D,
                    (double) this.pos.getY() + 0.5D,
                    (double) this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index != 2;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.cookTime;
            case 1:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.cookTime = value;
            case 1:
                this.totalCookTime = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 2;
    }

    @Override
    public void update() {

    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerChoppingBoard(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return null;
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.choppingBoardName : "container.chopping_board";
    }

    @Override
    public boolean hasCustomName() {
        return this.furnaceItemStacks != null && !this.furnaceItemStacks.isEmpty();
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.furnaceItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);

        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("TotalCookTime");

        if (compound.hasKey("CustomName", 8)) {
            this.choppingBoardName = compound.getString("CustomName");
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("CookTime", (short) this.cookTime);
        compound.setInteger("TotalCookTime", (short) this.totalCookTime);
        ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);

        if (this.hasCustomName()) {
            compound.setString("CustomName", this.choppingBoardName);
        }

        return compound;
    }

    private boolean canSmelt() {
        if (((ItemStack) this.furnaceItemStacks.get(0)).isEmpty()) {
            return false;
        }
        return true;
    }
}
