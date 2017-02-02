package info.tritusk.insanepatcher;

import cpw.mods.fml.common.Loader;

public class Constants {

    public static final boolean APPLECORE_EXIST = Loader.isModLoaded("AppleCore");

    public static final boolean RUNTIME_ENV = InsanePatcherSetup.isInRuntime();

}
