package com.bebopser.china.gui;

import com.bebopser.china.block.tileentity.TileEntityCookingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCookingTable extends Container {

    public TileEntityCookingTable tileEntityCookingTable;
    private IItemHandler foodItem;
    private IItemHandler fuelItem;
    private IItemHandler outputItem;
    protected int cookTime = 0;
    protected int fuelTime = 0;
    protected int fuelTotalTime = 0;

    public ContainerCookingTable(EntityPlayer player, TileEntity tileEntity) {
        super();
        this.foodItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.fuelItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        this.outputItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        this.addSlotToContainer(new SlotItemHandler(this.foodItem, 0, 53, 20));
        this.addSlotToContainer(new SlotItemHandler(this.fuelItem, 0, 53, 56));
        this.addSlotToContainer(new SlotItemHandler(this.outputItem, 0, 116, 38)
        {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9,
                                                                8 + j * 18,
                                                                51 + i * 18 + 33));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }

        this.tileEntityCookingTable = (TileEntityCookingTable) tileEntity;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(this.tileEntityCookingTable.getPos()) <= 64;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();
        boolean isMerged = false;

        if (index >= 0 && index < 2) {
            isMerged = mergeItemStack(newStack, 3, 39, true);
        } else if (index >= 3 && index < 30) {
            isMerged = mergeItemStack(newStack, 0, 1, false)
                    || TileEntityCookingTable.isItemFuel(newStack) && mergeItemStack(newStack, 1, 2, false)
                    || mergeItemStack(newStack, 30, 39, false);
        } else if (index >= 30 && index < 39) {
            isMerged = mergeItemStack(newStack, 0, 1, false)
                    || TileEntityCookingTable.isItemFuel(newStack) && mergeItemStack(newStack, 1, 2, false)
                    || mergeItemStack(newStack, 3, 30, false);
        }

        if (!isMerged) {
            return ItemStack.EMPTY;
        }

        if (newStack.getCount() == 0) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }

        slot.onTake(player, newStack);
        return oldStack;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        this.cookTime = tileEntityCookingTable.getCookTime();
        this.fuelTime = tileEntityCookingTable.getFuelTime();
        this.fuelTotalTime = tileEntityCookingTable.getFuelTotalTime();

        for (IContainerListener i : this.listeners) {
            i.sendWindowProperty(this, 0, this.fuelTime);
            i.sendWindowProperty(this, 1, this.fuelTotalTime);
            i.sendWindowProperty(this, 2, this.cookTime);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);

        switch (id) {
            case 0:
                this.fuelTime = data;
                break;
            case 1:
                this.fuelTotalTime =data;
                break;
            case 2:
                this.cookTime = data;
                break;
            default:
                break;
        }
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public int getFuelTime() {
        return this.fuelTime;
    }

    public int getFuelTotalTime() {
        return this.fuelTotalTime;
    }
}
