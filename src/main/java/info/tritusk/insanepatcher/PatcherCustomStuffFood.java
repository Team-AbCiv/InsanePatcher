package info.tritusk.insanepatcher;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class PatcherCustomStuffFood implements IClassTransformer {

    private static final String GET_META = InsanePatcherSetup.isInRuntime() ? "func_77960_j" : "getItemDamage";
    private static final String GET_FOODSTATE = InsanePatcherSetup.isInRuntime() ? "func_71024_bL" : "getFoodStats";
    private static final String ADD_STATE = InsanePatcherSetup.isInRuntime() ? "func_75122_a" : "addStats";
    private static final String ON_FOOD_EATEN = InsanePatcherSetup.isInRuntime() ? "func_77654_b" : "onEaten";
    private static final String STACK_SIZE = InsanePatcherSetup.isInRuntime() ? "field_77994_a" : "stackSize";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("cubex2.cs2.item.ItemCSFood")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            node.interfaces.add("squeek/applecore/api/food/IEdible");
            MethodNode onFoodEaten = node.methods.stream().filter(m -> m.name.equals(ON_FOOD_EATEN)).findFirst().orElseThrow(Error::new);
            Iterator<AbstractInsnNode> itr = onFoodEaten.instructions.iterator();
            while (itr.hasNext()) {
                AbstractInsnNode insn = itr.next();
                itr.remove();
                if (insn.getOpcode() == Opcodes.INVOKEVIRTUAL && ((MethodInsnNode)insn).owner.equals("net/minecraft/util/FoodStats")) {
                    break;
                }
            }

            InsnList insnListToAppend = new InsnList();
            insnListToAppend.add(new InsnNode(Opcodes.ICONST_1));
            insnListToAppend.add(new VarInsnNode(Opcodes.ALOAD, 1));
            insnListToAppend.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/item/ItemStack", STACK_SIZE, "I"));
            insnListToAppend.add(new InsnNode(Opcodes.ISUB));
            insnListToAppend.add(new VarInsnNode(Opcodes.ALOAD, 3));
            insnListToAppend.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/player/EntityPlayer", GET_FOODSTATE, "()Lnet/minecraft/util/FoodStats;", false));
            insnListToAppend.add(new VarInsnNode(Opcodes.ALOAD, 1));
            insnListToAppend.add(new VarInsnNode(Opcodes.ALOAD, 3));
            insnListToAppend.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "getFoodValues", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;)Lsqueek/applecore/api/food/FoodValues;", false));
            insnListToAppend.add(new VarInsnNode(Opcodes.ASTORE, 4));
            insnListToAppend.add(new VarInsnNode(Opcodes.ALOAD, 4));
            insnListToAppend.add(new FieldInsnNode(Opcodes.GETFIELD, "squeek/applecore/api/food/FoodValues", "hunger", "I"));
            insnListToAppend.add(new VarInsnNode(Opcodes.ALOAD, 4));
            insnListToAppend.add(new FieldInsnNode(Opcodes.GETFIELD, "squeek/applecore/api/food/FoodValues", "saturationModifier", "F"));
            insnListToAppend.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/util/FoodStats", ADD_STATE, "(IF)V", false));
            onFoodEaten.instructions.insert(onFoodEaten.instructions.getFirst(), insnListToAppend);

            MethodVisitor getFoodValues = node.visitMethod(Opcodes.ACC_PUBLIC, "getFoodValues", "(Lnet/minecraft/item/ItemStack;)Lsqueek/applecore/api/food/FoodValues;", null, null);
            getFoodValues.visitVarInsn(Opcodes.ALOAD, 1);
            getFoodValues.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", GET_META, "()I", false);
            getFoodValues.visitVarInsn(Opcodes.ISTORE, 4);
            getFoodValues.visitTypeInsn(Opcodes.NEW, "squeek/applecore/api/food/FoodValues");
            getFoodValues.visitInsn(Opcodes.DUP);
            getFoodValues.visitVarInsn(Opcodes.ALOAD, 0);
            getFoodValues.visitFieldInsn(Opcodes.GETFIELD, "cubex2/cs2/item/ItemCSFood", "attributes", "Lcubex2/cs2/item/attributes/ItemFoodAttributes;");
            getFoodValues.visitFieldInsn(Opcodes.GETFIELD, "cubex2/cs2/item/attributes/ItemFoodAttributes", "hunger", "[I");
            getFoodValues.visitVarInsn(Opcodes.ILOAD, 4);
            getFoodValues.visitInsn(Opcodes.IALOAD);
            getFoodValues.visitVarInsn(Opcodes.ALOAD, 0);
            getFoodValues.visitFieldInsn(Opcodes.GETFIELD, "cubex2/cs2/item/ItemCSFood", "attributes", "Lcubex2/cs2/item/attributes/ItemFoodAttributes;");
            getFoodValues.visitFieldInsn(Opcodes.GETFIELD, "cubex2/cs2/item/attributes/ItemFoodAttributes", "saturation", "[F");
            getFoodValues.visitVarInsn(Opcodes.ILOAD, 4);
            getFoodValues.visitInsn(Opcodes.FALOAD);
            getFoodValues.visitMethodInsn(Opcodes.INVOKESPECIAL, "squeek/applecore/api/food/FoodValues", "<init>", "(IF)V", false);
            getFoodValues.visitInsn(Opcodes.ARETURN);
            getFoodValues.visitEnd();

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);
            return writer.toByteArray();
        }

        return basicClass;
    }
}
