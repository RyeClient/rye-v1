/*
 * Copyright (c) Alya Client 2024-2025.
 *
 * This file belongs to Alya Client,
 * an open-source Fabric injection client.
 * Rye GitHub: https://github.com/AlyaClient/alya-beta.git
 *
 * THIS PROJECT DOES NOT HAVE A WARRANTY.
 *
 * Alya (and subsequently, its files) are all licensed under the MIT License.
 * Alya should have come with a copy of the MIT License.
 * If it did not, you may obtain a copy here:
 * MIT License: https://opensource.org/license/mit
 *
 */

package works.alya.module.impl.movement.flight.vanilla;

import works.alya.config.setting.impl.BooleanSetting;
import works.alya.config.setting.impl.NumberSetting;
import works.alya.event.IEventListener;
import works.alya.event.impl.MotionEvent;
import works.alya.module.Module;
import works.alya.module.SubModule;
import works.alya.utilities.player.MoveUtility;

public class NormalFlight extends SubModule {

    private final NumberSetting<Float> speed = new NumberSetting<>("Speed", "Flight speed multiplier", 1.5F, 0.1F, 10.0F);
    private final BooleanSetting preventVanillaKick = new BooleanSetting("Anti-Kick", "Prevent Vanilla kick when flying", true);
    private final BooleanSetting verticalEnabled = new BooleanSetting("Vertical", "Enable Vertical movement", true);

    public NormalFlight(final Module parent) {
        super("Normal", parent);
        this.addSettings(this.speed, this.preventVanillaKick, this.verticalEnabled);
    }

    private final IEventListener<MotionEvent> onMotion = event -> {
        if(!event.isPre()) return;
        if(this.mc.player == null) return;

        boolean up = this.mc.options.jumpKey.isPressed();
        boolean down = this.mc.options.sneakKey.isPressed();
        boolean verticalMovement = up || down;

        if(this.verticalEnabled.getValue()) {
            if(up)
                MoveUtility.setMotionY(this.speed.getValue() / 2);
            else if(down)
                MoveUtility.setMotionY(-this.speed.getValue() / 2);
        }

        if(this.preventVanillaKick.getValue() && !verticalMovement)
            MoveUtility.setMotionY(MoveUtility.getVanillaFallingSpeed() + 0.05);
        else if(!verticalMovement)
            MoveUtility.setMotionY(0);

        MoveUtility.setSpeed(speed.getValue(), true);
    };

}
