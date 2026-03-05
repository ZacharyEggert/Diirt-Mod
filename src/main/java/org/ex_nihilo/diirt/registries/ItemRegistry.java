package org.ex_nihilo.diirt.registries;

import static org.ex_nihilo.diirt.DiirtMod.MODID;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<BlockItem> DIIRT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("diirt_block",
            BlockRegistry.DIIRT_BLOCK);
    public static final DeferredItem<BlockItem> GRAASS_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("graass_block",
            BlockRegistry.GRAASS_BLOCK);
    public static final DeferredItem<BlockItem> FAARMLAND_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("faarmland_block",
            BlockRegistry.FAARMLAND_BLOCK);
}
