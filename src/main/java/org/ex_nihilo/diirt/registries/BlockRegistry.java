package org.ex_nihilo.diirt.registries;

import static org.ex_nihilo.diirt.DiirtMod.MODID;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.ex_nihilo.diirt.blocks.DiirtBlock;
import org.ex_nihilo.diirt.blocks.FaarmlandBlock;
import org.ex_nihilo.diirt.blocks.FertileSoilBlock;

public class BlockRegistry {
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(MODID);

  public static final DeferredHolder<Block, Block> DIIRT_BLOCK = BLOCKS.register("diirt_block",
      registryName -> new DiirtBlock(
          BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).sound(SoundType.GRAVEL).randomTicks()));
  public static final DeferredHolder<Block, Block> GRAASS_BLOCK = BLOCKS.register("graass_block",
      registryName -> new FertileSoilBlock(
          BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRAVEL).randomTicks()));
  public static final DeferredHolder<Block, Block> FAARMLAND_BLOCK = BLOCKS.register("faarmland_block",
      registryName -> new FaarmlandBlock(
          BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND).sound(SoundType.GRAVEL).randomTicks()));
}