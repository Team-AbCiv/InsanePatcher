var JumpInsnNode = org.objectweb.asm.tree.JumpInsnNode;
var MethodInsnNode = org.objectweb.asm.tree.MethodInsnNode;
var Opcodes = org.objectweb.asm.Opcodes;

var methodOnUpdate = node.methods.stream().filter(function (m) {
    return m.name.equals("onUpdate");
}).findFirst().orElseThrow(function () {
    return new java.lang.Error();
});

// The assumption made here is that all food has superclass of ItemFood
// which has been checked in instanceof, however it is not the case in some mods
// namely AppleMilkTea and BambooMod. (Why the hell there is non-standard implementation)
// For those cases, special handlers must be added.
// Also, AppleMilkTea supports AppleCore, which can alleviate the issue (by let
// botania support AppleCore IEdible interface and then transform BambooMod to let
// it support AppleCore as well).

//Original 211, the invokevirtual in the stack.getItem() instanceof ItemFood check
var itemFoodCheckIvkVrtl = null;
var itemFoodCheckInstanceof = null;

//Original 468, the main for-loop jump before the return opcode, which is 471 return
var forLoopGoto = null;

//Original 254, cast the checked ItemFood
var itemFoodValueIvkVrtl = null;

var itr = methodOnUpdate.instructions.iterator();
while (itr.hasNext()) {
    var next = itr.next();
    if (next.getOpcode() == Opcodes.INVOKEVIRTUAL) {
        if (next.name.equals("func_150905_g")) { // You already knew the type by check the opcode.
            // func_150905_g is the method to get hunger value regen
            itemFoodValueIvkVrtl = next;
        }
    } else if (next.getOpcode() == Opcodes.INSTANCEOF) {
        itemFoodCheckIvkVrtl = next.getPrevious();
        itemFoodCheckInstanceof = next;
    } else if (next.getOpcode() == Opcodes.GOTO) {
        if (next.getNext().getNext().getNext().getNext().getNext().getNext() == null) {
            forLoopGoto = next;
        }
    }
}

if (itemFoodCheckIvkVrtl != null && itemFoodCheckInstanceof != null && forLoopGoto != null && itemFoodValueIvkVrtl != null) {
    methodOnUpdate.instructions.set(itemFoodCheckIvkVrtl.getNext().getNext(), new JumpInsnNode(Opcodes.IFEQ, forLoopGoto.getPrevious().getPrevious().getPrevious()));
    methodOnUpdate.instructions.set(itemFoodCheckIvkVrtl, new MethodInsnNode(Opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "isFood", "(Lnet/minecraft/item/ItemStack;)Z", false));
    methodOnUpdate.instructions.remove(itemFoodCheckInstanceof);

    methodOnUpdate.instructions.remove(itemFoodValueIvkVrtl.getPrevious().getPrevious().getPrevious());
    methodOnUpdate.instructions.remove(itemFoodValueIvkVrtl.getPrevious().getPrevious());
    methodOnUpdate.instructions.remove(itemFoodValueIvkVrtl.getPrevious());
    methodOnUpdate.instructions.set(itemFoodValueIvkVrtl, new MethodInsnNode(Opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "getHungerValueRegen", "(Lnet/minecraft/item/ItemStack;)I", false));
}
