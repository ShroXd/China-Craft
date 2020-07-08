package com.bebopser.china.loader;

import com.bebopser.china.ChinaCraft;
import com.bebopser.china.gui.ContainerChoppingBoard;
import com.bebopser.china.gui.GuiChoppingBoard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class GuiLoader implements IGuiHandler {

    public static final int GUI_CHOPPING_BOARD = 1;

    public GuiLoader() {
        NetworkRegistry.INSTANCE.registerGuiHandler(ChinaCraft.instance, this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_CHOPPING_BOARD:
                return new ContainerChoppingBoard(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_CHOPPING_BOARD:
                return new GuiChoppingBoard(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
            default:
                return null;
        }
    }
}