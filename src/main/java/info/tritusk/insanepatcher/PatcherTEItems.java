package info.tritusk.insanepatcher;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.AnvilUpdateEvent;

public class PatcherTEItems {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onAnvilCrafting(AnvilUpdateEvent event) {
        // This patcher will fix the problem of various items from Thermal Expansion
        // can be "repaired" in vanilla anvil, which should not happen from the beginning
        // of the time, as the repair will yield into creative-only item, which is op.
        if (event.right.getItem().getClass().getName().startsWith("cofh.thermalexpansion")) {
            // Only detect the second slot, so that using vanilla enchantment book won't be affected.
            event.setCanceled(true);
        }
    }
}
