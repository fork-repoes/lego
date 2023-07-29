package com.geekhalo.lego.plugin.template;

public class LazyLoaderTemplate {
    private static final String TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import com.geekhalo.lego.annotation.loader.LazyLoadBy;\n" +
            "\n" +
            "import java.lang.annotation.ElementType;\n" +
            "import java.lang.annotation.Retention;\n" +
            "import java.lang.annotation.RetentionPolicy;\n" +
            "import java.lang.annotation.Target;\n" +
            "\n" +
            "import static {pkg}.{className}.BEAN_NAME;\n" +
            "\n" +
            "@Target({ElementType.METHOD, ElementType.FIELD})\n" +
            "@Retention(RetentionPolicy.RUNTIME)\n" +
            "@LazyLoadBy(\"#{@\"+ BEAN_NAME +\".{loadMethodName}(${param1})}\")\n" +
            "public @interface {className} {\n" +
            "    String BEAN_NAME = \"{BeanName}\";\n" +
            "    String param1();\n" +
            "}\n";

    public static String create(CreateLazyLoaderContext context){
        String content = TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        return content;
    }

    public static class CreateLazyLoaderContext extends CreateClassContext{

        public CreateLazyLoaderContext(String pkg, String clsName) {
            super(pkg, clsName);
        }
    }
}
