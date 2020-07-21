package com.bebopser.china.block;

import com.bebopser.china.ChinaCraft;
import com.bebopser.china.Reference;
import com.bebopser.china.block.tileentity.TileEntityCookingTable;
import com.bebopser.china.loader.BlockLoader;
import com.bebopser.china.loader.GuiLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class CookingTable extends BlockContainer implements ITileEntityProvider {

    private final boolean isBurning;
    private static boolean keepInventory;
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public CookingTable(String name, float lightLevel, boolean isBurning) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setLightLevel(lightValue);
        this.setHardness(3.5F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(Reference.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        this.isBurning = isBurning;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCookingTable();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        // TODO: 改成烹饪台
        drops.add(new ItemStack(BlockLoader.cooking_table, 1));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            int id = GuiLoader.GUI_COOKING_TABLE;
            player.openGui(ChinaCraft.instance, id, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!keepInventory) {
            TileEntityCookingTable te = (TileEntityCookingTable) world.getTileEntity(pos);
            IItemHandler inventory0 = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            IItemHandler inventory1 = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
            IItemHandler inventory2 = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

            for (int i = inventory0.getSlots() - 1; i >= 0; --i) {
                if (inventory0.getStackInSlot(i) != ItemStack.EMPTY) {
                    Block.spawnAsEntity(world, pos, inventory0.getStackInSlot(i));
                    ((IItemHandlerModifiable) inventory0).setStackInSlot(i, ItemStack.EMPTY);
                }
            }

            for (int i = inventory1.getSlots() - 1; i >= 0; --i) {
                if (inventory1.getStackInSlot(i) != ItemStack.EMPTY) {
                    Block.spawnAsEntity(world, pos, inventory1.getStackInSlot(i));
                    ((IItemHandlerModifiable) inventory1).setStackInSlot(i, ItemStack.EMPTY);
                }
            }

            for (int i = inventory2.getSlots() - 1; i >= 0; --i) {
                if (inventory2.getStackInSlot(i) != ItemStack.EMPTY) {
                    Block.spawnAsEntity(world, pos, inventory2.getStackInSlot(i));
                    ((IItemHandlerModifiable) inventory2).setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumFacing = EnumFacing.getHorizontal(meta);

        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumFacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        // TODO: 修改
        items.add(new ItemStack(BlockLoader.cooking_table));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(BlockLoader.cooking_table);
    }

    public static void setState(boolean active, World world, BlockPos pos) {
        IBlockState iBlockState = world.getBlockState(pos);
        TileEntity tileEntity = world.getTileEntity(pos);
        keepInventory = true;

        if (active) {
            world.setBlockState(pos, BlockLoader.lit_cooking_table.getDefaultState().withProperty(FACING, iBlockState.getValue(FACING)));
        } else {
            world.setBlockState(pos, BlockLoader.cooking_table.getDefaultState().withProperty(FACING, iBlockState.getValue(FACING)));
        }

        keepInventory = false;

        if (tileEntity != null) {
            tileEntity.validate();
            world.setTileEntity(pos, tileEntity);
        }
    }
}
