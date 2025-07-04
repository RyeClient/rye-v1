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

package dev.thoq.module.impl.utility.disabler;

import dev.thoq.module.Module;
import dev.thoq.module.ModuleCategory;
import dev.thoq.module.impl.utility.disabler.cubecraft.CubecraftDisabler;
import dev.thoq.module.impl.utility.disabler.omnisprint.OmniSprintDisabler;
import dev.thoq.module.impl.utility.disabler.spartan.SpartanDisabler;

public class DisablerModule extends Module {
    public DisablerModule() {
        super("Disabler", "Partially or fully disable some anticheats", ModuleCategory.UTILITY);

        this.addSubmodules(
                new SpartanDisabler(this),
                new OmniSprintDisabler(this),
                new CubecraftDisabler(this)
        );
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
