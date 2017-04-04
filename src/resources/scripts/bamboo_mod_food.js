var methodGetFoodValue = node.methods.stream().filter(function (m) {
    return m.name.equals("func_150905_g");
}).findFirst().orElseThrow(function () {
    return new java.lang.Error();
});

methodGetFoodValue.instructions.clear();
methodGetFoodValue.visitFieldInsn(opcodes.GETSTATIC, "ruby/bamboo/item/ItemBambooFood", "foodMap", "Ljava/util/Map;");
methodGetFoodValue.visitVarInsn(opcodes.ALOAD, 1);
methodGetFoodValue.visitMethodInsn(opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77960_j", "()I", false); //TODO: support dev env
methodGetFoodValue.visitFieldInsn(opcodes.GETSTATIC, "ruby/bamboo/item/ItemBambooFood", "MAX_ELEMENT_COUNT", "I");
methodGetFoodValue.visitInsn(opcodes.IREM);
methodGetFoodValue.visitMethodInsn(opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
methodGetFoodValue.visitMethodInsn(opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
methodGetFoodValue.visitTypeInsn(opcodes.CHECKCAST, "ruby/bamboo/item/EnumFood");
methodGetFoodValue.visitFieldInsn(opcodes.GETFIELD, "ruby/bamboo/item/EnumFood", "heal", "I");
methodGetFoodValue.visitInsn(opcodes.IRETURN);
methodGetFoodValue.visitEnd();

// func_77960_j -> getItemDamage
