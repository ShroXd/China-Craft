package com.bebopser.china.block;


import com.bebopser.china.ChinaCraft;
import com.bebopser.china.Reference;
import com.bebopser.china.block.tileentity.TileEntityChoppingBoard;
import com.bebopser.china.loader.GuiLoader;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class ChoppingBoard extends Block implements ITileEntityProvider {

    protected static final AxisAlignedBB CHOPPINGBOARD_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4375D, 1.0D);
    private static boolean keepInventory = false;

    public ChoppingBoard(String name) {
        super(Material.WOOD);

        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(3.5F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(Reference.tab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityChoppingBoard();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess sourcem, BlockPos pos) {
        return CHOPPINGBOARD_AABB;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            int id = GuiLoader.GUI_CHOPPING_BOARD;
            player.openGui(ChinaCraft.instance, id, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!keepInventory) {
            TileEntityChoppingBoard choppingBoard = (TileEntityChoppingBoard) world.getTileEntity(pos);
            IItemHandler rawFoodSlot = choppingBoard.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.EAST);
            IItemHandler toolSlot = choppingBoard.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.SOUTH);
            IItemHandler drinkSlot = choppingBoard.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

            popSlot(rawFoodSlot, world, pos);
            popSlot(toolSlot, world, pos);
            popSlot(drinkSlot, world, pos);

/*            for (int i = rawFoodSlot.getSlots() - 1; i >=0; --i) {
                if (rawFoodSlot.getStackInSlot(i) != ItemStack.EMPTY) {
                    Block.spawnAsEntity(world, pos, rawFoodSlot.getStackInSlot(i));
                    ((IItemHandlerModifiable) rawFoodSlot).setStackInSlot(i, ItemStack.EMPTY);
                }
            }

            for (int i = toolSlot.getSlots() - 1; i >=0; --i) {
                if (toolSlot.getStackInSlot(i) != ItemStack.EMPTY) {
                    Block.spawnAsEntity(world, pos, toolSlot.getStackInSlot(i));
                    ((IItemHandlerModifiable) toolSlot).setStackInSlot(i, ItemStack.EMPTY);
                }
            }

            for (int i = drinkSlot.getSlots() - 1; i >=0; --i) {
                if (drinkSlot.getStackInSlot(i) != ItemStack.EMPTY) {
                    Block.spawnAsEntity(world, pos, drinkSlot.getStackInSlot(i));
                    ((IItemHandlerModifiable) drinkSlot).setStackInSlot(i, ItemStack.EMPTY);
                }
            }*/
        }
    }

    private void popSlot(IItemHandler inventory, World world, BlockPos pos) {
        for (int i = inventory.getSlots() - 1; i >= 0; --i)
        {
            if (inventory.getStackInSlot(i) != ItemStack.EMPTY)
            {
                Block.spawnAsEntity(world, pos, inventory.getStackInSlot(i));
                ((IItemHandlerModifiable) inventory).setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }
}
