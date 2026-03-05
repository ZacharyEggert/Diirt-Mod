package org.ex_nihilo.diirt.blocks;

import javax.annotation.ParametersAreNonnullByDefault;

import org.ex_nihilo.diirt.DiirtMod;
import org.ex_nihilo.diirt.registries.BlockRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.util.TriState;

@ParametersAreNonnullByDefault
public class FertileSoilBlock extends Block {
    public FertileSoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {
        if (stack.canPerformAction(ItemAbilities.HOE_TILL) && level.getBlockState(pos.above()).isAir()) {
            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide) {
                level.setBlockAndUpdate(pos, BlockRegistry.FAARMLAND_BLOCK.get().defaultBlockState());
                stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing,
            BlockState plant) {
        return plant.is(DiirtMod.DIIRT_GRAASS_PLACEABLE_CROPS) ? TriState.TRUE : TriState.FALSE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        acceleratedTick(level, pos, random);
    }

    public static void acceleratedTick(ServerLevel level, BlockPos pos, RandomSource random) {
        BlockState above = level.getBlockState(pos.above());
        if (!above.is(DiirtMod.DIIRT_GRAASS_PLACEABLE_CROPS) && !above.is(DiirtMod.FAARMLAND_PLACEABLE_CROPS)
                && !above.is(BlockTags.CROPS))
            return;

        boolean same = true;
        int height = 1;
        while (same) {
            if (pos.above(height).getY() < level.getMaxBuildHeight()) {
                BlockState next = level.getBlockState(pos.above(height));
                if (next.is(above.getBlock())) {
                    next.randomTick(level, pos.above(height), random);
                    next.randomTick(level, pos.above(height), random);
                    height++;
                } else {
                    same = false;
                }
            } else {
                same = false;
            }
        }
    }
}
