package info.tritusk.insanepatcher;


import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.AnvilUpdateEvent;

public class PatcherTEItems {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onAnvilCrafting(AnvilUpdateEvent event) {

        // Note
        // This patcher will fix the problem of various items from Thermal Expansion
        // can be "repaired" in vanilla anvil, which should not happen from the beginning
        // of the time, as the repair will yield into creative-only item, which is op.
        //
        // In theory, this is not viable in survival mode as the cost is extremely high
        // (per my experiment, this requires 4000+ level of experience which is not allowed
        // in vanilla anvil).
        // However, in practice, this is doable:
        // Example 1: https://github.com/CoFH/Feedback/issues/1293
        // Example 2: Using ThaumicBase's Void Anvil.

        if (event.right.getItem().getClass().getName().startsWith("cofh")) {
            event.setCanceled(true);
        }

    }
}
