node.interfaces.add("squeek/applecore/api/food/IEdible");

var getFoodValues = node.visitMethod(opcodes.ACC_PUBLIC, "getFoodValues", "(Lnet/minecraft/item/ItemStack;)Lsqueek/applecore/api/food/FoodValues;", null, null);

getFoodValues.visitTypeInsn(opcodes.NEW, "squeek/applecore/api/food/FoodValues");
getFoodValues.visitInsn(opcodes.DUP);
getFoodValues.visitInsn(opcodes.ICONST_1);
getFoodValues.visitVarInsn(opcodes.ALOAD, 1);
getFoodValues.visitFieldInsn(opcodes.GETFIELD, "net/minecraft/item/ItemStack", "stackSize", "I");
getFoodValues.visitInsn(opcodes.ICONST_2);
getFoodValues.visitInsn(opcodes.IMUL);
getFoodValues.visitInsn(opcodes.I2F);
getFoodValues.visitMethodInsn(opcodes.INVOKESPECIAL, "squeek/applecore/api/food/FoodValues", "<init>", "(IF)V", false);
getFoodValues.visitInsn(opcodes.ARETURN);
