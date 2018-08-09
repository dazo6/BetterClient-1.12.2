package com.dazo66.betterclient.coremod;

import com.dazo66.betterclient.BetterClient;
import com.dazo66.betterclient.coremod.transformer.GuiCloseEventInject;
import com.google.common.base.Strings;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import net.minecraftforge.fml.common.versioning.VersionParser;
import net.minecraftforge.fml.common.versioning.VersionRange;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.HashMap;
import java.util.List;

/**
 * @author Dazo66
 */
public class MainTransformer implements IClassTransformer {

    public static MainTransformer mainTransformer;

    private HashMap<String, IRegisterTransformer> map = new HashMap<>();
    private String MCVERSION = getMCVERSION();

    public MainTransformer() {
        mainTransformer = this;
        register(new GuiCloseEventInject());
        BetterClient.registerFeatures();
        BetterClient.registerTransformerClass(this);
    }

    public static byte[] clearMethod(String name, String transformedName, byte[] basicClass, List<String> methodInfo) {
        ClassReader classReader = new ClassReader(basicClass);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        for (MethodNode method : classNode.methods) {
            if (methodInfo.contains(method.name) && methodInfo.contains(method.desc)) {
                method.instructions.clear();
                method.instructions.add(new InsnNode(Opcodes.RETURN));
            }
        }
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    public static String getMCVERSION() {
        try {
            return (String) (ForgeVersion.class.getField("mcVersion")).get("");
        } catch (Exception e) {
            return "";
        }
    }

    public void register(IRegisterTransformer iRegisterTransformer) {
        List<String> name = iRegisterTransformer.getClassName();
        if (isVersionAllow(iRegisterTransformer.getMcVersion())) {
            for (String s : name) {
                map.put(s, iRegisterTransformer);
            }
            FMLLog.log.info("{} Register SUCCESS", iRegisterTransformer.getClass().getSimpleName());
        } else {
            FMLLog.log.warn("This MCVersion is {} but Transformer {} accept MCVersion is {} that ignore this Transformer", MCVERSION, iRegisterTransformer.getClass().getSimpleName(), iRegisterTransformer.getMcVersion());
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        IRegisterTransformer irtf = null;
        if (map.containsKey(transformedName)) {
            irtf = map.get(transformedName);
            FMLLog.log.info("CLASS: " + irtf.getClass().getSimpleName() + " Transformer SUCCESS");
            return irtf.transform(name, transformedName, basicClass);
        } else if (map.containsKey(name)) {
            irtf = map.get(name);
            FMLLog.log.info("CLASS: " + irtf.getClass().getSimpleName() + " Transformer SUCCESS");
            return irtf.transform(name, transformedName, basicClass);
        } else {
            return basicClass;
        }

    }

    private boolean isVersionAllow(String transMcVersion) {
        VersionRange range;
        String mcVersionString = transMcVersion;
        if ("[1.12]".equals(mcVersionString)) {
            mcVersionString = "[1.12,1.12.2]";
        }
        if ("[1.12.1]".equals(mcVersionString) || "[1.12,1.12.1]".equals(mcVersionString)) {
            mcVersionString = "[1.12,1.12.2]";
        }

        if (!Strings.isNullOrEmpty(mcVersionString)) {
            range = VersionParser.parseRange(mcVersionString);
        } else {
            range = Loader.instance().getMinecraftModContainer().getStaticVersionRange();
        }
        return range.containsVersion(new DefaultArtifactVersion(MCVERSION));
    }

}
