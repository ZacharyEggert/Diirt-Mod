package org.ex_nihilo.diirt.blocks;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.ex_nihilo.diirt.registries.BlockRegistry;

@ParametersAreNonnullByDefault
public class DiirtBlock extends FertileSoilBlock {
    public DiirtBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        if (level.getMaxLocalRawBrightness(pos.above()) < 4) return;

        for (int i = 0; i < 4; i++) {
            BlockPos nearby = pos.offset(
                random.nextInt(3) - 1,
                random.nextInt(5) - 3,
                random.nextInt(3) - 1
            );
            BlockState nearbyState = level.getBlockState(nearby);
            if (nearbyState.is(Blocks.GRASS_BLOCK) || nearbyState.is(BlockRegistry.GRAASS_BLOCK)) {
                level.setBlockAndUpdate(pos, BlockRegistry.GRAASS_BLOCK.get().defaultBlockState());
                return;
            }
        }
    }
}
