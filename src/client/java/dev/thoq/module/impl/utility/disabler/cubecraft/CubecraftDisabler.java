/*
 * Copyright (c) Rye Client 2024-2025.
 *
 * This file belongs to Rye Client,
 * an open-source Fabric injection client.
 * Rye GitHub: https://github.com/RyeClient/rye-v1.git
 *
 * THIS PROJECT DOES NOT HAVE A WARRANTY.
 *
 * Rye (and subsequently, its files) are all licensed under the MIT License.
 * Rye should have come with a copy of the MIT License.
 * If it did not, you may obtain a copy here:
 * MIT License: https://opensource.org/license/mit
 *
 */

package dev.thoq.module.impl.utility.disabler.cubecraft;

import dev.thoq.event.IEventListener;
import dev.thoq.event.impl.MotionEvent;
import dev.thoq.event.impl.PacketSendEvent;
import dev.thoq.module.Module;
import dev.thoq.module.SubModule;
import net.minecraft.client.MinecraftClient;

public class CubecraftDisabler extends SubModule {
    public CubecraftDisabler(Module parent) {
        super("Cubecraft", parent);
    }

    @SuppressWarnings("unused")
    private final IEventListener<MotionEvent> motionEvent = event -> {
        if(mc.player == null) return;

        event.setOnGround(mc.player.age % 2 == 0);
    };
}
