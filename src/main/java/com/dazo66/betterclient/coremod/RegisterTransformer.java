package com.dazo66.betterclient.coremod;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dazo66
 */
public class RegisterTransformer {

    private MainTransformer mainT;

    public RegisterTransformer(MainTransformer mainTIn) {
        mainT = mainTIn;
    }

    public void register() {
//        mainT.register(new HookSetRecipeListEvent());
    }

    private class HookSetRecipeListEvent implements IRegisterTransformer {

        @Override
        public String getMcVersion() {
            return "[1.8,1.12.2]";
        }

        @Override
        public List<String> getClassName() {
            return Arrays.asList("");
        }

        @Override
        public byte[] transform(String name, String transformedName, byte[] basicClass) {
            ClassReader classReader = new ClassReader(basicClass);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 0);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
    }

}
