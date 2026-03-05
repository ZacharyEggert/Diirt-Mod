package org.ex_nihilo.diirt;

import org.ex_nihilo.diirt.registries.BlockRegistry;
import org.ex_nihilo.diirt.registries.ItemRegistry;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(DiirtMod.MODID)
public class DiirtMod {

    public static final String MODID = "diirtmod";
//    public static final Logger LOGGER = LogUtils.getLogger();

    public static final TagKey<Block> FAARMLAND_PLACEABLE_CROPS = TagKey.create(Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(MODID, "faarmland_placeable_crops"));
    public static final TagKey<Block> DIIRT_GRAASS_PLACEABLE_CROPS = TagKey.create(Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(MODID, "diirt_graass_placeable_crops"));

    // FML will recognize some parameter types like IEventBus or ModContainer and
    // pass them in automatically.
    public DiirtMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (CustomMod)
        // to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in
        // this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the Deferred Register to the mod event bus so that the contained
        // registries get registered
        BlockRegistry.BLOCKS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config
        // file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//
//        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
//            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
//        }
//
//        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());
//
//        Config.ITEM_STRINGS.get().forEach(item -> LOGGER.info("ITEM >> {}", item));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods
    // in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = DiirtMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
//            LOGGER.info("HELLO FROM CLIENT SETUP");
//            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }

        @SubscribeEvent
        static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            event.register(
                    (state, level, pos, tintIndex) -> level != null && pos != null
                            ? BiomeColors.getAverageGrassColor(level, pos)
                            : GrassColor.get(0.5, 1.0),
                    BlockRegistry.GRAASS_BLOCK.get());
        }

        @SubscribeEvent
        static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register(
                    (stack, tintIndex) -> GrassColor.get(0.5, 1.0),
                    BlockRegistry.GRAASS_BLOCK.get());
        }
    }
}
