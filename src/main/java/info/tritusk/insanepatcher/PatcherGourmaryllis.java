package info.tritusk.insanepatcher;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class PatcherGourmaryllis implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("vazkii.botania.common.block.subtile.generating.SubTileGourmaryllis")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            MethodNode method = node.methods.stream().filter(m -> m.name.equals("onUpdate")).findFirst().get();

            //TODO do not use magic number. Use some kind of filter to find the correct pots

            //Original 218, the jump opcode in stack.getItem() instanceof ItemFood
            AbstractInsnNode itemFoodIfeq = method.instructions.get(218);
            //Original 468, before the last one, which is 471 return
            AbstractInsnNode forLoopGoto = method.instructions.getLast().getPrevious();
            //Original 254, the assumption is that all food has superclass of ItemFood
            //which has been checked in instanceof, however it is not the case in some mods
            //namely AppleMilkTea and BambooMod.
            //For those cases, special handlers must be added, probably using javascript.
            //TODO
            AbstractInsnNode checkcast = method.instructions.get(254);
        }

        return basicClass;
    }
}
