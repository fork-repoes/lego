package com.geekhalo.lego.plugin.template;

public class JoinInMemoryTemplate {
    private static final String TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import com.geekhalo.lego.annotation.joininmemory.JoinInMemory;\n" +
            "import org.springframework.core.annotation.AliasFor;\n" +
            "\n" +
            "import java.lang.annotation.ElementType;\n" +
            "import java.lang.annotation.Retention;\n" +
            "import java.lang.annotation.RetentionPolicy;\n" +
            "import java.lang.annotation.Target;\n" +
            "\n" +
            "@Target(ElementType.FIELD)\n" +
            "@Retention(RetentionPolicy.RUNTIME)\n" +
            "@JoinInMemory(keyFromSourceData = \"\",\n" +
            "        keyFromJoinData = \"#{id}\",\n" +
            "        loader = \"#{@xxx.yyy(#root)}\"\n" +
            ")\n" +
            "public @interface JoinAddressVOOnId {\n" +
            "    @AliasFor(\n" +
            "            annotation = JoinInMemory.class\n" +
            "    )\n" +
            "    String keyFromSourceData();\n" +
            "}\n";

    public static String create(CreateJoinInMemoryContext context){
        String content = TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        return content;
    }

    public static class CreateJoinInMemoryContext extends CreateClassContext{

        public CreateJoinInMemoryContext(String pkg, String clsName) {
            super(pkg, clsName);
        }
    }
}
