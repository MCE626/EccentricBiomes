package ebm.com.mce.blocks.saplings;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebm.com.mce.common.mod_ebm;
import ebm.com.mce.gen.trees.WorldGenMTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class mSapling extends BlockSapling implements IGrowable {
	private IIcon[] textures;
	public static final String[] SAPLINGNAMES = new String[] { "m" };
	private static final int TYPES = 1;
	private static WorldGenerator tree;

	public mSapling() {
		tree = new WorldGenMTree(true);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		textures = new IIcon[SAPLINGNAMES.length];
		for (int i = 0; i < SAPLINGNAMES.length; ++i) {
			textures[i] = iconRegister.registerIcon("mod_ebm:sapling_m");
		}
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderBlockPass() {
		return 0;
	}

	public boolean shouldSideBeRendered(IBlockAccess blockAcc, int x, int y, int z, int side) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta < 0 || meta >= SAPLINGNAMES.length) {
			meta = 0;
		}
		return textures[meta];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
		for (int i = 0; i < SAPLINGNAMES.length; ++i) {
			list.add(new ItemStack(block, 1, i));
		}
	}

	/**
	 * Is this position a valid spawn position. <br>
	 * Test block below placed sapling to see if it can sustain a plant?
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param metadata
	 * @return boolean
	 */
	public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
		Block block = world.getBlock(x, y - 1, z);
		switch (metadata) {
		default:
			return block == Blocks.grass || block == Blocks.dirt || block == mod_ebm.crimsonGrass
					|| block == mod_ebm.crimsonDirt;
		}
	}

	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		return isValidPosition(world, x, y, z, -1);
	}

	public boolean canBlockStay(World world, int x, int y, int z) {
		Block soil = world.getBlock(x, y - 1, z);
		if (world.getBlockMetadata(x, y, z) != 7)
			return (world.getFullBlockLightValue(x, y, z) >= 6 || world.canBlockSeeTheSky(x, y, z))
					&& (soil != null && (soil == Blocks.grass || soil == Blocks.dirt || soil == mod_ebm.crimsonGrass
							|| soil == mod_ebm.crimsonDirt));
		else
			return (world.getFullBlockLightValue(x, y, z) >= 6 || world.canBlockSeeTheSky(x, y, z))
					&& (soil != null && (soil == Blocks.grass || soil == Blocks.dirt || soil == mod_ebm.crimsonGrass
							|| soil == mod_ebm.crimsonDirt));
	}

	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.isRemote) {
			return;
		}
		if (world.getBlockLightValue(x, y + 1, z) >= 6 && random.nextInt(7) == 0) {
			this.func_149878_d(world, x, y, z, random);
		}
	}

	public void func_149878_d(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z) & TYPES;
		Object obj = null;
		if (obj == null) {
			obj = tree;
		}

		if (obj != null) {
			world.setBlockToAir(x, y, z);
			if (!((WorldGenerator) obj).generate(world, rand, x, y, z)) {
				world.setBlock(x, y, z, this, meta, 2);
			}
		}
	}

	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
			IPlantable plantable) {
		if (world.getBlock(x, y, z) == Blocks.grass || world.getBlock(x, y, z) == Blocks.dirt
				|| world.getBlock(x, y, z) == mod_ebm.crimsonGrass || world.getBlock(x, y, z) == mod_ebm.crimsonDirt) {
			return true;
		} else {
			return false;
		}
	}

	public void onPlantGrow(World world, int x, int y, int z, int sourceX, int sourceY, int sourceZ) {
		if (this == Blocks.dirt) {
			world.setBlock(x, y, z, Blocks.dirt, 0, 0);
		}

		else if (this == mod_ebm.crimsonDirt) {
			world.setBlock(x, y, z, mod_ebm.crimsonDirt, 0, 0);
		}
	}

}
