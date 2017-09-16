package info.tritusk.insanepatcher;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

import java.util.Collections;

/**
 * @deprecated Not used besides providing metadata.
 *             <p>
 *             This class is scheduled to be removed once
 *             there is a better way to provide metadata of
 *             the mod itself.
 *             </p>
 */
@Deprecated
public final class InsanePatcherContainer extends DummyModContainer {

    public InsanePatcherContainer() {
        super(new ModMetadata());
        ModMetadata metadata = getMetadata();
        metadata.modId = "insane_patcher";
        metadata.name = "InsanePatcher";
        metadata.version = "@VERSION@";
        metadata.authorList = Collections.singletonList("3TUSK");
        metadata.description = "Inefficiently and brutally patch minecraft mod things and stuff to allow modpack makers to have a easier life.";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        // Not register into event bus, but still return true to ensure that FML knows it is active
        return true;
    }

}
