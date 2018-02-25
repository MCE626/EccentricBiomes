package ebm.com.mce.gen.trees;

import java.util.Random;

import ebm.com.mce.common.mod_ebm;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenBoneTree2 extends WorldGenAbstractTree {
	public WorldGenBoneTree2() {
		super(false);
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		while (world.isAirBlock(x, y, z) && y > 2) {
			--y;
		}

		Block block = world.getBlock(x, y, z);

		if (block != mod_ebm.bloodBoneBlock) {
			return false;
		} else {
			for (int i = -2; i <= 2; i++) {
				for (int j = -2; j <= 2; j++) {
					if (world.isAirBlock(x + i, y - i, z + j) && world.isAirBlock(x + i, y - 2, z + j)
							&& !world.isAirBlock(x + i, y, z + j)) {
						return false;
					}
				}
			}

			int base = 1;
			int branch = 1;
			int h = 1;

			for (int i = 0; i < base; i++) {
				buildBlock(world, x, y + h, z, mod_ebm.boneLogBlood, 0);
				h++;
			}

			int c = 1;
			for (int i = 0; i < branch; i++) {
				generateBranch(world, rand, x, y + h, z, c);
				c++;
				h += 2;
			}

			generateTop(world, x, y + h, z);
			return true;
		}
	}

	public void generateTop(World world, int x, int y, int z) {
		buildBlock(world, x, y, z, mod_ebm.boneLogBlood, 0);
	}

	public void generateBranch(World world, Random rand, int x, int y, int z, int p) {
		buildBlock(world, x, y, z, mod_ebm.boneLogBlood, 0);
		buildBlock(world, x, y + 1, z, mod_ebm.boneLogBlood, 0);
	}

	public void buildBlock(World world, int x, int y, int z, Block block, int meta) {
		if (world.isAirBlock(x, y, z)) {
			world.setBlock(x, y, z, block, meta, 2);
		}
	}
}