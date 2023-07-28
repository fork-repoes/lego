package com.geekhalo.lego.plugin.template;

public class EnumTemplate {
    private static final String TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import com.geekhalo.lego.common.enums.CommonEnum;\n" +
            "\n" +
            "public enum {className} implements CommonEnum {\n" +
            "    ;\n" +
            "    private final int code;\n" +
            "    private final String descr;\n" +
            "\n" +
            "    {className}(int code, String descr){\n" +
            "        this.code = code;\n" +
            "        this.descr = descr;\n" +
            "    }\n" +
            "    @Override\n" +
            "    public int getCode() {\n" +
            "        return this.code;\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public String getDescription() {\n" +
            "        return this.descr;\n" +
            "    }\n" +
            "}\n";

    public static String create(CreateEnumContext context){
        String content = TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        return content;
    }

    public static class CreateEnumContext extends CreateClassContext{

        public CreateEnumContext(String pkg, String clsName) {
            super(pkg, clsName);
        }
    }
}
