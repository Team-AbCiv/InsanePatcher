package info.tritusk.insanepatcher;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.tree.ClassNode;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.HashMap;
import java.util.jar.JarFile;

public final class InsanePatcherScriptingEngine {

    private InsanePatcherScriptingEngine() {}

    private static final ScriptEngine ENGINE = new ScriptEngineManager(null).getEngineByName("nashorn");

    private static final Map<String, Reader> TRANSFORMERS = new HashMap<>();

    static void setupInsaneScripting(File mcLocation, File jarItself) {
        final File scriptFolder = new File(mcLocation, "insane_patchers");
        if (!scriptFolder.exists() || !scriptFolder.isDirectory()) {
            if (scriptFolder.mkdir()) {
                if (jarItself != null && jarItself.exists() && jarItself.isFile()) {
                    try {
                        final JarFile jf = new JarFile(jarItself);
                        jf.stream().filter(entry -> entry.getName().startsWith("scripts")).forEach(entry -> {
                            try {
                                IOUtils.copy(jf.getInputStream(entry), new FileOutputStream(new File(scriptFolder, entry.getName().substring(8))));
                            } catch (IOException ignored) {}
                        });
                    } catch (IOException e) {
                        System.err.println("Failed on release bundled patchers. You may want to download them manually.");
                    }
                } else {
                    System.out.println("Well we didn't get the jar... either we are in dev environment, or nothing we really can deal with it.");
                }
            }
        }

        final File cfgFile = new File(scriptFolder, "target.properties");
        Properties cfg = new Properties();
        try {
            cfg.load(new FileInputStream(cfgFile));
        } catch (IOException failOnLoad) {
            System.err.println("Failed on setting up config, please manually setup your config file");
        }
        cfg.stringPropertyNames().forEach(className -> {
            try {
                TRANSFORMERS.putIfAbsent(className, new FileReader(new File(scriptFolder, cfg.getProperty(className))));
            } catch (FileNotFoundException ignored) {}
        });

        try {
            ENGINE.eval("var foodUtil = info.tritusk.insanepatcher.FoodUtil");
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

    static void process(String transformedName, ClassNode node) {
        try {
            ENGINE.put("node", node);
            ENGINE.eval(TRANSFORMERS.get(transformedName));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    static boolean shouldTransform(String transformedName) {
        return TRANSFORMERS.containsKey(transformedName);
    }
}
