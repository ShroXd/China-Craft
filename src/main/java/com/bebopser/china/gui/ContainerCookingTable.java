package com.bebopser.china.gui;

import com.bebopser.china.block.tileentity.TileEntityCookingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCookingTable extends Container {

    public TileEntityCookingTable tileEntityCookingTable;
    private IItemHandler foodItem;
    private IItemHandler fuelItem;
    private IItemHandler outputItem;
    protected int fuelTime = 0;
    protected int fuelTotalTime = 0;

    public ContainerCookingTable(EntityPlayer player, TileEntity tileEntity) {
        super();
        this.foodItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.fuelItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        this.outputItem = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
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
}
