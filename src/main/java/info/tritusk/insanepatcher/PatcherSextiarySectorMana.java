package info.tritusk.insanepatcher;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

public class PatcherSextiarySectorMana implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("shift.sextiarysector.SSFluids")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            MethodNode method = node.methods.stream().filter(m -> m.name.equals("initFluids")).findFirst().orElseThrow(Error::new);

            AbstractInsnNode ldcString = null;
            Iterator<AbstractInsnNode> itr = method.instructions.iterator();
            while (itr.hasNext()) {
                AbstractInsnNode next = itr.next();
                if (next instanceof LdcInsnNode) {
                    if (((LdcInsnNode)next).cst.equals("Mana")) {
                        ldcString = next;
                        break;
                    }
                }
            }

            if (ldcString != null) {
                method.instructions.set(ldcString, new LdcInsnNode("SS_Mana"));
                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                node.accept(writer);
                return writer.toByteArray();
            } else {
                return basicClass;
            }
        }
        return basicClass;
    }
}
