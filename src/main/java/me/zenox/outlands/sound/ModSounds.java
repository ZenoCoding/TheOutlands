package me.zenox.outlands.sound;

import me.zenox.outlands.Main;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    public static SoundEvent ENERGY_BEAM_CHARGE = registerSoundEvent("energy_beam_charge");
    public static SoundEvent ENERGY_BEAM_FIRE = registerSoundEvent("energy_beam_fire");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Main.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
