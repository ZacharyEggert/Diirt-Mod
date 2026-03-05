package org.ex_nihilo.diirt.blocks;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.TriState;
import org.ex_nihilo.diirt.DiirtMod;
import org.ex_nihilo.diirt.registries.BlockRegistry;
import org.jetbrains.annotations.NotNull;

@ParametersAreNonnullByDefault
public class FaarmlandBlock extends FarmBlock {
    public FaarmlandBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, BlockState plant) {
        return (plant.is(DiirtMod.FAARMLAND_PLACEABLE_CROPS) || plant.is(BlockTags.CROPS)) ? TriState.TRUE : TriState.FALSE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int moisture = state.getValue(MOISTURE);
        if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
            if (moisture > 0) {
                level.setBlock(pos, state.setValue(MOISTURE, moisture - 1), 2);
            } else if (!hasPlantAbove(level, pos)) {
                level.setBlockAndUpdate(pos, BlockRegistry.DIIRT_BLOCK.get().defaultBlockState());
                return;
            }
        } else if (moisture < 7) {
            level.setBlock(pos, state.setValue(MOISTURE, 7), 2);
        }

        if (moisture > 0) {
            FertileSoilBlock.acceleratedTick(level, pos, random);
        }
    }

    private static boolean isNearWater(BlockGetter level, BlockPos pos) {
        for (BlockPos nearPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (level.getFluidState(nearPos).is(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPlantAbove(BlockGetter level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return above.is(DiirtMod.FAARMLAND_PLACEABLE_CROPS) || above.is(BlockTags.CROPS);
    }
}
