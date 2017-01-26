package info.tritusk.insanepatcher;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public class InsanePatcher implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {
          "info.tritusk.insanepatcher.PatcherGourmaryllis"
        };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
