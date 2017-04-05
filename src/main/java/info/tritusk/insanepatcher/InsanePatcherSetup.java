package info.tritusk.insanepatcher;

import cpw.mods.fml.relauncher.IFMLCallHook;
import org.lwjgl.Sys;

import java.io.File;
import java.util.Map;

public class InsanePatcherSetup implements IFMLCallHook {

    private static boolean isInRuntime;

    @Override
    public void injectData(Map<String, Object> data) {
        InsanePatcherScriptingEngine.setupInsaneScripting((File)data.get("mcLocation"));
        isInRuntime = (Boolean)data.get("runtimeDeobfuscationEnabled");
        InsanePatcherScriptingEngine.LOG.info("Successfully setup InsanePatcher scripting engine.");
    }

    @Override
    public Void call() throws Exception {
        return null;
    }

    public static boolean isInRuntime() {
        return isInRuntime;
    }
}
