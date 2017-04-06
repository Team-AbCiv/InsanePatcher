var Opcodes = org.objectweb.asm.Opcodes;

var methodGetFoodValue = node.methods.stream().filter(function (m) {
    return m.name.equals("func_150905_g");
}).findFirst().orElseThrow(function () {
    return new java.lang.Error();
});

methodGetFoodValue.instructions.clear();
methodGetFoodValue.visitFieldInsn(Opcodes.GETSTATIC, "ruby/bamboo/item/ItemBambooFood", "foodMap", "Ljava/util/Map;");
methodGetFoodValue.visitVarInsn(Opcodes.ALOAD, 1);
methodGetFoodValue.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77960_j", "()I", false);
methodGetFoodValue.visitFieldInsn(Opcodes.GETSTATIC, "ruby/bamboo/item/ItemBambooFood", "MAX_ELEMENT_COUNT", "I");
methodGetFoodValue.visitInsn(Opcodes.IREM);
methodGetFoodValue.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
methodGetFoodValue.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
methodGetFoodValue.visitTypeInsn(Opcodes.CHECKCAST, "ruby/bamboo/item/EnumFood");
methodGetFoodValue.visitFieldInsn(Opcodes.GETFIELD, "ruby/bamboo/item/EnumFood", "heal", "I");
methodGetFoodValue.visitInsn(Opcodes.IRETURN);
methodGetFoodValue.visitEnd();

// func_77960_j -> getItemDamage
