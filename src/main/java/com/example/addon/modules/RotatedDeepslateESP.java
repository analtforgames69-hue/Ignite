package com.ignite.rotatedesp.modules;

import com.ignite.rotatedesp.RotatedESPAddon;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.math.BlockPos;

public class RotatedDeepslateESP extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final SettingGroup sgRender = this.settings.createGroup("Render");

    // Max search distance
    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("How far to search for rotated deepslate.")
        .defaultValue(16)
        .min(4)
        .sliderMax(128)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("How the ESP box is rendered.")
        .defaultValue(ShapeMode.Both)
        .build()
    );

    private final Setting<SettingColor> color = sgRender.add(new ColorSetting.Builder()
        .name("color")
        .description("Highlight color.")
        .defaultValue(new SettingColor(120, 150, 255, 255))
        .build()
    );

    public RotatedDeepslateESP() {
        super(RotatedESPAddon.CATEGORY, "rotated-deepslate-esp",
            "Highlights only rotated deepslate blocks (axis = X or Z).");
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        int r = range.get().intValue();
        BlockPos playerPos = mc.player.getBlockPos();
        BlockPos.Mutable pos = new BlockPos.Mutable();

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    pos.set(playerPos.getX() + x, playerPos.getY() + y, playerPos.getZ() + z);

                    var state = mc.world.getBlockState(pos);
                    Block block = state.getBlock();

                    // Only target DEEPSLATE that is a RotatedPillarBlock
                    if (block == Blocks.DEEPSLATE && block instanceof RotatedPillarBlock) {
                        var axis = state.get(RotatedPillarBlock.AXIS);

                        // Only highlight rotated (horizontal axis) blocks
                        if (axis != net.minecraft.util.math.Direction.Axis.Y) {
                            event.renderer.box(pos, color.get(), color.get(), shapeMode.get(), 0);
                        }
                    }
                }
            }
        }
    }
}
