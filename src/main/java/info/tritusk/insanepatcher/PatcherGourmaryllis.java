package info.tritusk.insanepatcher;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class PatcherGourmaryllis implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("vazkii.botania.common.block.subtile.generating.SubTileGourmaryllis")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            MethodNode method = node.methods.stream().filter(m -> m.name.equals("onUpdate")).findFirst().get();

            // Note:
            // The assumption made here is that all food has superclass of ItemFood
            // which has been checked in instanceof, however it is not the case in some mods
            // namely AppleMilkTea and BambooMod.
            // For those cases, special handlers must be added.
            // Javascript may be used for easier expansion.
            // Also, AppleMilkTea supports AppleCore, which can alleviate the issue (by let
            // botania support AppleCore IEdible interface and then transform BambooMod to let
            // it support AppleCore as well).

            //Original 218, the if-zero-then-jump in stack.getItem() instanceof ItemFood
            AbstractInsnNode itemFoodInstanceof = null;
            AbstractInsnNode itemFoodIfeq = null;
            //Original 468, the main for-loop jump before the return opcode, which is 471 return
            AbstractInsnNode forLoopGoto = null;
            //Original 254, cast the checked ItemFood
            AbstractInsnNode checkcast = null;

            // TODO Review the node finding logic
            Iterator<AbstractInsnNode> itr = method.instructions.iterator();
            while (itr.hasNext()) {
                AbstractInsnNode next = itr.next();
                if (next instanceof TypeInsnNode ) {
                    if (((TypeInsnNode)next).getOpcode() == Opcodes.INSTANCEOF) {
                        itemFoodInstanceof = next;
                        itemFoodIfeq = next.getNext();
                    } else if (((TypeInsnNode)next).getOpcode() == Opcodes.CHECKCAST && ((TypeInsnNode)next).desc.contains("ItemFood")) {
                        checkcast = next;
                    }
                } else if (next instanceof JumpInsnNode) {
                    if (((JumpInsnNode)next).getOpcode() == Opcodes.GOTO && next.getNext().getOpcode() == Opcodes.RETURN) {
                        forLoopGoto = next;
                    }
                }
            }

            if (itemFoodInstanceof != null && itemFoodIfeq != null && forLoopGoto != null & checkcast != null) {
                // No idea yet
                // maybe like this, for minimum overhaul sake:
                // aload 6
                // invokestatic info.tritusk.insanepatcher.PatcherGourmaryllis.isFood
                // ifeq (original 468, ending goto of the loop)
                // ...
                // ...
                // aload 6
                // invokestatic info.tritusk.insanepatcher.PatcherGourmaryllis.getHungerValueRegen
                // istore 7? // not sure yet
                //
            }
        }

        return basicClass;
    }

    public static boolean isFood(ItemStack item) {
        return item.getItem() instanceof ItemFood; // TODO support AppleCore, so that it can indirectly support AppleMilkTea
    }

    // TODO Consider about the parameter
    public static int getHungerValueRegen(ItemStack item) {
        return 0;
    }
}
