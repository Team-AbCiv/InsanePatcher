package info.tritusk.insanepatcher;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.Collections;

public class InsanePatcherContainer extends DummyModContainer {

    private static final ModMetadata METADATA = new ModMetadata();

    static {
        METADATA.modId = "insane_patcher";
        METADATA.name = "InsanePatcher";
        METADATA.version = "@VERSION@";
        METADATA.authorList = Collections.singletonList("3TUSK");
        METADATA.description = "Inefficiently and brutally patch minecraft mod things and stuff to allow modpack makers to have a easier life.";
    }

    public InsanePatcherContainer() {
        super(METADATA);
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PatcherTEItems());
    }
}
