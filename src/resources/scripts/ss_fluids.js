var method = node.methods.stream().filter(function (method) {
    return method.name.equals("initFluids");
}).findFirst().orElseThrow(function () {
    return new java.lang.Error();
});

var ldcString = null;

var itr = method.instructions.iterator();

while (itr.hasNext()) {
    var next = itr.next();
    if (next instanceof LdcInsnNode) {
        if (next.cst.equals("Mana")) { // TODO: make sure we cast the type
            ldcString = next;
            break;
        }
    }
}

if (ldcString != null) {
    method.instructions.set(ldcString, new org.objectweb.asm.tree.LdcInsnNode("SS_Mana"));
}
