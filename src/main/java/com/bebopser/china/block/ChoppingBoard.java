package com.bebopser.china.block;

import com.bebopser.china.ChinaCraft;
import com.bebopser.china.Reference;
import com.bebopser.china.block.tileentity.TileEntityChoppingBoard;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChoppingBoard extends BlockContainer {

    private final boolean isWorking;

    public ChoppingBoard(String name, boolean isWorking) {
        super(Material.WOOD);

        this.setRegistryName(name);
        this.setUnlocalizedName(Reference.MODID + "." + name);
        this.setHardness(5F);
        this.setCreativeTab(Reference.tab);

        this.isWorking = isWorking;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityChoppingBoard();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return true;

        player.openGui(ChinaCraft.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof IInventory) {
            InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileEntity);
        }

        super.breakBlock(world, pos, state);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public void add(ChoppingBoard recipe) {
    }
}
