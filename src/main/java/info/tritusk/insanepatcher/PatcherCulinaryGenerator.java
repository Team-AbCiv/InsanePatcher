package info.tritusk.insanepatcher;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class PatcherCulinaryGenerator implements IClassTransformer {

    private static final String METHOD_GET_ITEM = InsanePatcherSetup.isInRuntime() ? "func_77973_b" : "getItem";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorFood")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            MethodNode methodGenLevelGetter = node.methods.stream().filter(m -> m.name.equals("getGenLevelForStack")).findFirst().get();
            MethodNode methodFuelBurnGetter = node.methods.stream().filter(m -> m.name.equals("getFuelBurn")).findFirst().get();

            if (methodFuelBurnGetter != null && methodGenLevelGetter != null) {
                methodGenLevelGetter.instructions.clear();
                methodFuelBurnGetter.instructions.clear();

                methodGenLevelGetter.visitVarInsn(Opcodes.ALOAD, 1);
                Label labelInvalidCheck0 = new Label();
                methodGenLevelGetter.visitJumpInsn(Opcodes.IFNULL, labelInvalidCheck0);
                methodGenLevelGetter.visitVarInsn(Opcodes.ALOAD, 1);
                methodGenLevelGetter.visitMethodInsn(Opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "isFood", "(Lnet/minecraft/item/ItemStack;)Z", false);
                Label labelCakeCheck0 = new Label();
                methodGenLevelGetter.visitJumpInsn(Opcodes.IFEQ, labelCakeCheck0);
                methodGenLevelGetter.visitVarInsn(Opcodes.ALOAD, 0);
                methodGenLevelGetter.visitVarInsn(Opcodes.ALOAD, 1);
                methodGenLevelGetter.visitMethodInsn(Opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "getHungerValueRegen", "(Lnet/minecraft/item/ItemStack;)I", false);
                methodGenLevelGetter.visitInsn(Opcodes.I2D);
                methodGenLevelGetter.visitLdcInsn(8.0D);
                methodGenLevelGetter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/rwtema/extrautils/tileentity/generators/TileEntityGeneratorFood", "scale", "(DD)D", false);
                methodGenLevelGetter.visitLdcInsn(4.0D);
                methodGenLevelGetter.visitInsn(Opcodes.DMUL);
                methodGenLevelGetter.visitInsn(Opcodes.DRETURN);
                methodGenLevelGetter.visitLabel(labelCakeCheck0);
                methodGenLevelGetter.visitVarInsn(Opcodes.ALOAD, 1);
                methodGenLevelGetter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", METHOD_GET_ITEM, "()Lnet/minecraft/item/Item;", false);
                methodGenLevelGetter.visitFieldInsn(Opcodes.GETSTATIC, "net/minecraft/init/Items", "cake", "Lnet/minecraft/item/Item;");
                methodGenLevelGetter.visitJumpInsn(Opcodes.IF_ACMPNE, labelInvalidCheck0);
                methodGenLevelGetter.visitLdcInsn(64.0D);
                methodGenLevelGetter.visitInsn(Opcodes.DRETURN);
                methodGenLevelGetter.visitLabel(labelInvalidCheck0);
                methodGenLevelGetter.visitInsn(Opcodes.DCONST_0);
                methodGenLevelGetter.visitInsn(Opcodes.DRETURN);
                methodGenLevelGetter.visitEnd();

                methodFuelBurnGetter.visitVarInsn(Opcodes.ALOAD, 1);
                Label labelInvalidCheck1 = new Label();
                methodFuelBurnGetter.visitJumpInsn(Opcodes.IFNULL, labelInvalidCheck1);
                methodFuelBurnGetter.visitVarInsn(Opcodes.ALOAD, 1);
                methodFuelBurnGetter.visitMethodInsn(Opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "isFood", "(Lnet/minecraft/item/ItemStack;)Z", false);
                Label labelCakeCheck1 = new Label();
                methodFuelBurnGetter.visitJumpInsn(Opcodes.IFEQ, labelCakeCheck1);
                methodFuelBurnGetter.visitVarInsn(Opcodes.ALOAD, 1);
                methodFuelBurnGetter.visitMethodInsn(Opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "getHungerValueRegen", "(Lnet/minecraft/item/ItemStack;)I", false);
                methodFuelBurnGetter.visitJumpInsn(Opcodes.IFLE, labelInvalidCheck1);
                methodFuelBurnGetter.visitVarInsn(Opcodes.ALOAD, 0);
                methodFuelBurnGetter.visitVarInsn(Opcodes.ALOAD, 1);
                methodFuelBurnGetter.visitMethodInsn(Opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "getSaturationModifier", "(Lnet/minecraft/item/ItemStack;)F", false);
                methodFuelBurnGetter.visitInsn(Opcodes.F2D);
                methodFuelBurnGetter.visitLdcInsn(0.8D);
                methodFuelBurnGetter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/rwtema/extrautils/tileentity/generators/TileEntityGeneratorFood", "scale", "(DD)D", false);
                methodFuelBurnGetter.visitLdcInsn(1800.0D);
                methodFuelBurnGetter.visitInsn(Opcodes.DMUL);
                methodFuelBurnGetter.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math", "ceil", "(D)D", false);
                methodFuelBurnGetter.visitInsn(Opcodes.DRETURN);
                methodFuelBurnGetter.visitLabel(labelCakeCheck1);
                methodFuelBurnGetter.visitVarInsn(Opcodes.ALOAD, 1);
                methodFuelBurnGetter.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", METHOD_GET_ITEM, "()Lnet/minecraft/item/Item;", false);
                methodFuelBurnGetter.visitFieldInsn(Opcodes.GETSTATIC, "net/minecraft/init/Items", "cake", "Lnet/minecraft/item/Item;");
                methodFuelBurnGetter.visitJumpInsn(Opcodes.IF_ACMPNE, labelInvalidCheck1);
                methodFuelBurnGetter.visitLdcInsn(1500.0D);
                methodFuelBurnGetter.visitInsn(Opcodes.DRETURN);
                methodFuelBurnGetter.visitLabel(labelInvalidCheck1);
                methodFuelBurnGetter.visitInsn(Opcodes.DCONST_0);
                methodFuelBurnGetter.visitInsn(Opcodes.DRETURN);
                methodFuelBurnGetter.visitEnd();

                node.visitEnd();

                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                node.accept(writer);
                return writer.toByteArray();
            } else {
                return basicClass;
            }
        }
        return basicClass;
    }
}
