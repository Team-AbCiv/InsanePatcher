package info.tritusk.insanepatcher;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class InsanePatcherMain implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (InsanePatcherScriptingEngine.shouldTransform(transformedName)) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);
            try {
                InsanePatcherScriptingEngine.process(transformedName, node);
            } catch (Throwable throwable) {
                InsanePatcherScriptingEngine.LOG.error("Error occurred while transforming {}, changes will be ignored", transformedName);
                if (InsanePatcherScriptingEngine.DEBUG) {
                    InsanePatcherScriptingEngine.LOG.catching(throwable);
                }
                return basicClass;
            }
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);
            return writer.toByteArray();
        } else {
            return basicClass;
        }
    }
}
