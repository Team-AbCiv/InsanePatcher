package info.tritusk.insanepatcher;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.common.MinecraftForge;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdible;

import java.util.Iterator;

public class PatcherGourmaryllis implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("vazkii.botania.common.block.subtile.generating.SubTileGourmaryllis")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            MethodNode method = node.methods.stream().filter(m -> m.name.equals("onUpdate")).findFirst().get();

            // The assumption made here is that all food has superclass of ItemFood
            // which has been checked in instanceof, however it is not the case in some mods
            // namely AppleMilkTea and BambooMod. (Why the hell there is non-standard implementation)
            // For those cases, special handlers must be added.
            // Also, AppleMilkTea supports AppleCore, which can alleviate the issue (by let
            // botania support AppleCore IEdible interface and then transform BambooMod to let
            // it support AppleCore as well).

            //Original 211, the invokevirtual in the stack.getItem() instanceof ItemFood check
            AbstractInsnNode itemFoodCheckIvkVrtl = null;
            AbstractInsnNode itemFoodCheckInstanceof = null;

            //Original 468, the main for-loop jump before the return opcode, which is 471 return
            AbstractInsnNode forLoopGoto = null;

            //Original 254, cast the checked ItemFood
            AbstractInsnNode itemFoodValueIvkVrtl = null;

            Iterator<AbstractInsnNode> itr = method.instructions.iterator();
            while (itr.hasNext()) {
                AbstractInsnNode next = itr.next();
                if (next instanceof MethodInsnNode) {
                    if (next.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        if (((MethodInsnNode)next).name.equals("func_150905_g")) {
                            // func_150905_g is the method to get hunger value regen,
                            // which should always be obfuscated in 1.7.10
                            itemFoodValueIvkVrtl = next;
                        }
                    }
                } else if (next instanceof TypeInsnNode) {
                    if (next.getOpcode() == Opcodes.INSTANCEOF) {
                        itemFoodCheckIvkVrtl = next.getPrevious();
                        itemFoodCheckInstanceof = next; //This is the only instanceof instruction in onUpdate() method.
                    }
                } else if (next instanceof JumpInsnNode) {
                    if (next.getOpcode() == Opcodes.GOTO) {
                        if (next.getNext().getNext().getNext().getNext().getNext().getNext() == null) {
                            forLoopGoto = next;
                        }
                    }
                }
            }

            if (itemFoodCheckIvkVrtl != null && itemFoodCheckInstanceof != null && forLoopGoto != null && itemFoodValueIvkVrtl != null) {
                // It will become something like this:
                // aload 6
                // invokestatic info.tritusk.insanepatcher.PatcherGourmaryllis.isFood
                // ifeq ??? //I didn't calculate the result line number
                // ...
                // ...
                // aload 6
                // invokestatic info.tritusk.insanepatcher.PatcherGourmaryllis.getHungerValueRegen
                // istore 7

                method.instructions.set(itemFoodCheckIvkVrtl.getNext().getNext(),
                        new JumpInsnNode(Opcodes.IFEQ, (LabelNode)forLoopGoto.getPrevious().getPrevious().getPrevious()));
                method.instructions.set(itemFoodCheckIvkVrtl,
                        new MethodInsnNode(Opcodes.INVOKESTATIC,
                                "info/tritusk/insanepatcher/PatcherGourmaryllis",
                                "isFood",
                                "(Lnet/minecraft/item/ItemStack;)Z", false));
                method.instructions.remove(itemFoodCheckInstanceof);

                method.instructions.remove(itemFoodValueIvkVrtl.getPrevious().getPrevious().getPrevious());
                method.instructions.remove(itemFoodValueIvkVrtl.getPrevious().getPrevious());
                method.instructions.remove(itemFoodValueIvkVrtl.getPrevious());
                method.instructions.set(itemFoodValueIvkVrtl,
                        new MethodInsnNode(Opcodes.INVOKESTATIC,
                                "info/tritusk/insanepatcher/PatcherGourmaryllis",
                                "getHungerValueRegen",
                                "(Lnet/minecraft/item/ItemStack;)I", false));
                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                node.accept(writer);
                return writer.toByteArray();
            } else {
                return basicClass;
            }
        } else {
            return basicClass;
        }
    }

    public static boolean isFood(ItemStack stack) {
        return stack.getItem() instanceof ItemFood || (Constants.APPLECORE_EXIST && stack.getItem() instanceof IEdible);
    }

    public static int getHungerValueRegen(ItemStack stack) {
        // This assumes that the food class does not inherit from ItemFood.
        if (Constants.APPLECORE_EXIST && stack.getItem() instanceof IEdible) {
            FoodValues foodValues = ((IEdible)stack.getItem()).getFoodValues(stack);
            FoodEvent.GetFoodValues event = new FoodEvent.GetFoodValues(stack, foodValues);
            MinecraftForge.EVENT_BUS.post(event);
            return foodValues.hunger;
        } else if (stack.getItem() instanceof ItemFood) {
            return ((ItemFood)stack.getItem()).func_150905_g(stack);
        } else {
            return 0; // Cover all cases
        }
    }
}
