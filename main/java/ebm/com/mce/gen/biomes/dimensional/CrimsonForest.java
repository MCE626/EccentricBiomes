package ebm.com.mce.gen.biomes.dimensional;

import java.util.Random;

import ebm.com.mce.gen.trees.WorldGenMTree;
import ebm.com.mce.handlers.dimension.biomes.CrimsonBiomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

public class CrimsonForest extends CrimsonBiomes {
	private WorldGenerator worldGenMTree;
	int idfk = 1;
	
	public CrimsonForest(int id) {
		super(id);
		this.setHeight(height_Default);
		this.setDisableRain();
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.theBiomeDecorator.generateLakes = false;
		this.theBiomeDecorator.grassPerChunk = 10;
		this.theBiomeDecorator.flowersPerChunk = -999;
		this.theBiomeDecorator.mushroomsPerChunk = -999;
		this.theBiomeDecorator.treesPerChunk = 5;
		this.worldGenMTree = new WorldGenMTree();

		this.topBlock = Blocks.grass;
		this.fillerBlock = Blocks.dirt;
		this.biomeName = "Crimson Forest";
		this.color = 16735067;
	}

	public int getSkyColorByTemp(float f) {
		return 16735067;
	}

	public int getModdedBiomeFoliageColor(int original) {
		return 16735067;
	}

	public int getBiomeGrassColor(int x, int y, int z) {
		double d0 = plantNoise.func_151601_a((double) x * 0.0225D, (double) z * 0.0225D);
		return d0 < -0.1D ? 16735067 : 15877959;
	}
	
	public WorldGenAbstractTree func_150567_a(Random ran) {
		return (WorldGenAbstractTree) ((this.idfk == 1) && ran.nextInt(3) == 0
				? (this.idfk != 2 && ran.nextInt(13) != 0 ? worldGenMTree : worldGenMTree)
				: (ran.nextInt(3) == 0 ? worldGenMTree : worldGenMTree));
	}
}
