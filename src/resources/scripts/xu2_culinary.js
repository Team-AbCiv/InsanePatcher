var Label = org.objectweb.asm.Label;

var methodGenLevelGetter = node.methods.stream().filter(function (m){
    return m.name.equals("getGenLevelForStack");
}).findFirst().orElseThrow(function (){
    return new java.lang.Error();
});
var methodFuelBurnGetter = node.methods.stream().filter(function (m){
    return m.name.equals("getFuelBurn");
}).findFirst().orElseThrow(function (){
    return new java.lang.Error();
});

methodGenLevelGetter.instructions.clear();
methodFuelBurnGetter.instructions.clear();
methodGenLevelGetter.visitVarInsn(opcodes.ALOAD, 1);
var labelInvalidCheck0 = new Label();
methodGenLevelGetter.visitJumpInsn(opcodes.IFNULL, labelInvalidCheck0);
methodGenLevelGetter.visitVarInsn(opcodes.ALOAD, 1);
methodGenLevelGetter.visitMethodInsn(opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "isFood", "(Lnet/minecraft/item/ItemStack;)Z", false);
var labelCakeCheck0 = new Label();
methodGenLevelGetter.visitJumpInsn(opcodes.IFEQ, labelCakeCheck0);
methodGenLevelGetter.visitVarInsn(opcodes.ALOAD, 0);
methodGenLevelGetter.visitVarInsn(opcodes.ALOAD, 1);
methodGenLevelGetter.visitMethodInsn(opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "getHungerValueRegen", "(Lnet/minecraft/item/ItemStack;)I", false);
methodGenLevelGetter.visitInsn(opcodes.I2D);
methodGenLevelGetter.visitLdcInsn(8.0);
methodGenLevelGetter.visitMethodInsn(opcodes.INVOKEVIRTUAL, "com/rwtema/extrautils/tileentity/generators/TileEntityGeneratorFood", "scale", "(DD)D", false);
methodGenLevelGetter.visitLdcInsn(4.0);
methodGenLevelGetter.visitInsn(opcodes.DMUL);
methodGenLevelGetter.visitInsn(opcodes.DRETURN);
methodGenLevelGetter.visitLabel(labelCakeCheck0);
methodGenLevelGetter.visitVarInsn(opcodes.ALOAD, 1);
methodGenLevelGetter.visitMethodInsn(opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77973_b", "()Lnet/minecraft/item/Item;", false);
methodGenLevelGetter.visitFieldInsn(opcodes.GETSTATIC, "net/minecraft/init/Items", "cake", "Lnet/minecraft/item/Item;");
methodGenLevelGetter.visitJumpInsn(opcodes.IF_ACMPNE, labelInvalidCheck0);
methodGenLevelGetter.visitLdcInsn(64.0);
methodGenLevelGetter.visitInsn(opcodes.DRETURN);
methodGenLevelGetter.visitLabel(labelInvalidCheck0);
methodGenLevelGetter.visitInsn(opcodes.DCONST_0);
methodGenLevelGetter.visitInsn(opcodes.DRETURN);
methodGenLevelGetter.visitEnd();

methodFuelBurnGetter.visitVarInsn(opcodes.ALOAD, 1);
var labelInvalidCheck1 = new Label();
methodFuelBurnGetter.visitJumpInsn(opcodes.IFNULL, labelInvalidCheck1);
methodFuelBurnGetter.visitVarInsn(opcodes.ALOAD, 1);
methodFuelBurnGetter.visitMethodInsn(opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "isFood", "(Lnet/minecraft/item/ItemStack;)Z", false);
var labelCakeCheck1 = new Label();
methodFuelBurnGetter.visitJumpInsn(opcodes.IFEQ, labelCakeCheck1);
methodFuelBurnGetter.visitVarInsn(opcodes.ALOAD, 1);
methodFuelBurnGetter.visitMethodInsn(opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "getHungerValueRegen", "(Lnet/minecraft/item/ItemStack;)I", false);
methodFuelBurnGetter.visitJumpInsn(opcodes.IFLE, labelInvalidCheck1);
methodFuelBurnGetter.visitVarInsn(opcodes.ALOAD, 0);
methodFuelBurnGetter.visitVarInsn(opcodes.ALOAD, 1);
methodFuelBurnGetter.visitMethodInsn(opcodes.INVOKESTATIC, "info/tritusk/insanepatcher/FoodUtil", "getSaturationModifier", "(Lnet/minecraft/item/ItemStack;)F", false);
methodFuelBurnGetter.visitInsn(opcodes.F2D);
methodFuelBurnGetter.visitLdcInsn(0.8);
methodFuelBurnGetter.visitMethodInsn(opcodes.INVOKEVIRTUAL, "com/rwtema/extrautils/tileentity/generators/TileEntityGeneratorFood", "scale", "(DD)D", false);
methodFuelBurnGetter.visitLdcInsn(1800.0);
methodFuelBurnGetter.visitInsn(opcodes.DMUL);
methodFuelBurnGetter.visitMethodInsn(opcodes.INVOKESTATIC, "java/lang/Math", "ceil", "(D)D", false);
methodFuelBurnGetter.visitInsn(opcodes.DRETURN);
methodFuelBurnGetter.visitLabel(labelCakeCheck1);
methodFuelBurnGetter.visitVarInsn(opcodes.ALOAD, 1);
methodFuelBurnGetter.visitMethodInsn(opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77973_b", "()Lnet/minecraft/item/Item;", false);
methodFuelBurnGetter.visitFieldInsn(opcodes.GETSTATIC, "net/minecraft/init/Items", "cake", "Lnet/minecraft/item/Item;");
methodFuelBurnGetter.visitJumpInsn(opcodes.IF_ACMPNE, labelInvalidCheck1);
methodFuelBurnGetter.visitLdcInsn(1500.0);
methodFuelBurnGetter.visitInsn(opcodes.DRETURN);
methodFuelBurnGetter.visitLabel(labelInvalidCheck1);
methodFuelBurnGetter.visitInsn(opcodes.DCONST_0);
methodFuelBurnGetter.visitInsn(opcodes.DRETURN);
methodFuelBurnGetter.visitEnd();

node.visitEnd();

// TODO: "func_77973_b" <-> "getItem", for debug in dev env
