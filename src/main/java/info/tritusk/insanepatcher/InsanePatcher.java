package info.tritusk.insanepatcher;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.Name("InsanePatcher")
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(987654721) // Reference to the 9876547210.33 incident; but that number cant fit into int
public final class InsanePatcher implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "info.tritusk.insanepatcher.InsanePatcherMain" };
    }

    @Override
    public String getModContainerClass() {
        return "info.tritusk.insanepatcher.InsanePatcherContainer";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        InsanePatcherScriptingEngine.setupInsaneScripting((File)data.get("mcLocation"));
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
