package com.geekhalo.lego.plugin.template;

public class AggregationTemplate {
    private static final String TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import {parentClassFull};\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "import javax.persistence.*;\n" +
            "\n" +
            "@Entity\n" +
            "@Table\n" +
            "@Data\n" +
            "@NoArgsConstructor\n" +
            "public class {className} extends {parentClass} { \n" +
            "    @Id\n" +
            "    private Long id;" +
            "\n" +
            "\n" +
            "\n}";
    public static String create(CreateAggregationContext context) {
        String content = TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{parentClassFull}", context.getParentClassFull());
        content = content.replace("{parentClass}", context.getParentClass());
        return content;
    }

    public static class CreateAggregationContext extends CreateClassContext{
        private String parentClassFull;
        private String parentClass;
        public CreateAggregationContext(String pkg, String clsName) {
            super(pkg, clsName);
        }

        public void setParentClassFull(String parentClassFull){
            this.parentClassFull = parentClassFull;
            this.parentClass = getType(parentClassFull);
        }

        public String getParentClassFull() {
            return parentClassFull;
        }

        public String getParentClass() {
            return parentClass;
        }
    }
}
