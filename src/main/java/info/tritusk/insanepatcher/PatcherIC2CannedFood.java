package info.tritusk.insanepatcher;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public class PatcherIC2CannedFood implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("ic2.core.item.ItemTinCan")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            // The logic is follow:
            // For ic2 canned food, the food value is proportional to eat amount, with ratio of 1:1
            // i.e. one canned food provides half shank
            // as for saturation level, vanilla use the following function
            // Saturation Level = FoodValue * SaturationModifier * 2
            // For ic2, we have FoodValue = SaturationModifier = Stack Size,
            // making SaturationLevel = Math.pow(stackSize, 2) * 2
            // Differentiate this function will yield into Increment = 2 * stackSize
            // Thus, getFoodValues will return new FoodValues(1, stackSize * 2)

            node.interfaces.add("squeek/applecore/api/food/IEdible");
            MethodVisitor getFoodValues = node.visitMethod(Opcodes.ACC_PUBLIC,
                    "getFoodValues", "(Lnet/minecraft/item/ItemStack;)Lsqueek/applecore/api/food/FoodValues;",
                    null, null);
            getFoodValues.visitTypeInsn(Opcodes.NEW, "squeek/applecore/api/food/FoodValues");
            getFoodValues.visitInsn(Opcodes.DUP);
            getFoodValues.visitInsn(Opcodes.ICONST_1);
            getFoodValues.visitVarInsn(Opcodes.ALOAD, 1);
            getFoodValues.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/item/ItemStack", "stackSize", "I");
            getFoodValues.visitInsn(Opcodes.ICONST_2);
            getFoodValues.visitInsn(Opcodes.IMUL);
            getFoodValues.visitInsn(Opcodes.I2F);
            getFoodValues.visitMethodInsn(Opcodes.INVOKESPECIAL, "squeek/applecore/api/food/FoodValues", "<init>", "(IF)V", false);
            getFoodValues.visitInsn(Opcodes.ARETURN);

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);
            return writer.toByteArray();
        } else {
            return basicClass;
        }
    }

}
