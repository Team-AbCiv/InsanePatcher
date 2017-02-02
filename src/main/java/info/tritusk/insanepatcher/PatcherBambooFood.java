package info.tritusk.insanepatcher;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class PatcherBambooFood implements IClassTransformer {

    private static final String GET_META = InsanePatcherSetup.isInRuntime() ? "func_77960_j" : "getItemDamage";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("ruby.bamboo.item.ItemBambooFood")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            MethodNode method = node.methods.stream().filter(m -> m.name.equals("func_150905_g")).findFirst().get();

            if (method != null) {
                method.instructions.clear();
                method.visitFieldInsn(Opcodes.GETSTATIC, "ruby/bamboo/item/ItemBambooFood", "foodMap", "Ljava/util/Map;");
                method.visitVarInsn(Opcodes.ALOAD, 1);
                method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", GET_META, "()I", false);
                method.visitFieldInsn(Opcodes.GETSTATIC, "ruby/bamboo/item/ItemBambooFood", "MAX_ELEMENT_COUNT", "I");
                method.visitInsn(Opcodes.IREM);
                method.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                method.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
                method.visitTypeInsn(Opcodes.CHECKCAST, "ruby/bamboo/item/EnumFood");
                method.visitFieldInsn(Opcodes.GETFIELD, "ruby/bamboo/item/EnumFood", "heal", "I");
                method.visitInsn(Opcodes.IRETURN);
                method.visitEnd();
                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                node.accept(writer);
                return writer.toByteArray();
            } else {
                return basicClass;
            }
        } else {
            return basicClass;
        }
    }
}
