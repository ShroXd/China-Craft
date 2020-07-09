package com.bebopser.china.gui;

import com.bebopser.china.block.tileentity.TileEntityChoppingBoard;
import com.bebopser.china.item.items.ItemCustomTool;
import com.bebopser.china.item.items.ItemRawFood;
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

public class ContainerChoppingBoard extends Container {

    public TileEntityChoppingBoard tileEntity;
    private IItemHandler rawFoodItem;
    private IItemHandler toolItem;
    private IItemHandler drinkItem;
    protected int cookTime = 0;

    public ContainerChoppingBoard(EntityPlayer player, TileEntity tileEntity) {
        super();

        this.rawFoodItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.EAST);
        this.toolItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
        this.drinkItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

        // 物品输入槽
        this.addSlotToContainer(new SlotItemHandler(this.rawFoodItem, 0, 43, 33)
        {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() instanceof ItemRawFood;
            }
        });
        this.addSlotToContainer(new SlotItemHandler(this.toolItem, 0, 116, 20)
        {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() instanceof ItemCustomTool;
            }
        });

        // 输出槽
        this.addSlotToContainer(new SlotItemHandler(this.drinkItem, 0, 116, 48)
        {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });

        // 背包
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // 外部物品栏
        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
        this.tileEntity = (TileEntityChoppingBoard) tileEntity;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = inventorySlots.get(index);

        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = slot.getStack(),
                  oldStack = newStack.copy();

        boolean isMerged = false;

        if (index >= 0 && index < 3) {
            isMerged = mergeItemStack(newStack, 3, 39, true);
        } else if (index >= 3 && index < 30) {
            isMerged = mergeItemStack(newStack, 0, 2, false)
                    || mergeItemStack(newStack, 30, 39, false);
        } else if (index >= 30 && index < 39) {
            isMerged = mergeItemStack(newStack, 0, 2, false)
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

        this.cookTime = tileEntity.getCookTime();

        for (IContainerListener i : this.listeners) {
            i.sendWindowProperty(this, 0, this.cookTime);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        switch (id) {
            case 0:
                this.cookTime = data;
                break;
            default:
                break;
        }
    }

    public int getCookTime() {
        return this.cookTime;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(this.tileEntity.getPos()) <= 64;
    }
}
