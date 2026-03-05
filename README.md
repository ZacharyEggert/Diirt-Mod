# Diirt

A NeoForge mod for Minecraft 1.21 that adds gravel-sounding variants of dirt, grass, and farmland with accelerated crop growth.

## Blocks

### Diirt
A dirt-like block with gravel sound effects. When exposed to light and adjacent to a vanilla Grass Block or Graass Block, it will naturally convert to Graass over time. Can be tilled with a hoe into Faarmland. Supports crops tagged `diirtmod:diirt_graass_placeable_crops`.

### Graass
A grass-like block with gravel sounds and biome-colored tinting. Functions like Diirt but already spread with grass. Can be tilled into Faarmland. Crops growing on Graass receive **double random ticks**, accelerating their growth.

### Faarmland
A farmland-like block with gravel sounds. Behaves like vanilla farmland (dries out without nearby water, reverts to Diirt when dry with no crop above) but crops receive **double random ticks** when the block is moist. Supports standard crops and crops tagged `diirtmod:faarmland_placeable_crops`.

## Tags

| Tag | Used By |
|-----|---------|
| `diirtmod:diirt_graass_placeable_crops` | Crops that can grow on Diirt and Graass |
| `diirtmod:faarmland_placeable_crops` | Crops that can only grow on Faarmland |

## Build & Run

```bash
./gradlew build        # compile + package JAR
./gradlew runClient    # launch Minecraft client with mod loaded
./gradlew runServer    # launch dedicated server
```

## Requirements

- Minecraft 1.21
- NeoForge 21.0.167+
- Java 21
