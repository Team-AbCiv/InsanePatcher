package info.tritusk.insanepatcher;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

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

}
