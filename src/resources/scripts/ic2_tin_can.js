var Opcodes = org.objectweb.asm.Opcodes;

node.interfaces.add("squeek/applecore/api/food/IEdible");

var getFoodValues = node.visitMethod(Opcodes.ACC_PUBLIC, "getFoodValues", "(Lnet/minecraft/item/ItemStack;)Lsqueek/applecore/api/food/FoodValues;", null, null);

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
