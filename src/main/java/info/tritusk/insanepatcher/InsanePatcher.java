package info.tritusk.insanepatcher;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.Name("InsanePatcher")
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.SortingIndex(987654721) // Reference to the 9876547210.33 incident; but that number cant fit into int
public class InsanePatcher implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {
                "info.tritusk.insanepatcher.PatcherGourmaryllis",
                "info.tritusk.insanepatcher.PatcherCulinaryGenerator"
        };
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
        // no op
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
