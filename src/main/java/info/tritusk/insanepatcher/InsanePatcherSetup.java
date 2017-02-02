package info.tritusk.insanepatcher;

import cpw.mods.fml.relauncher.IFMLCallHook;

import java.util.Map;

public class InsanePatcherSetup implements IFMLCallHook {

    private static boolean isInRuntime;

    @Override
    public void injectData(Map<String, Object> data) {
        isInRuntime = (Boolean)data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public Void call() throws Exception {
        return null;
    }

    public static boolean isInRuntime() {
        return isInRuntime;
    }
}
