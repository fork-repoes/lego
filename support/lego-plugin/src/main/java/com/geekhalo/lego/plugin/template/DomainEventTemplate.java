package com.geekhalo.lego.plugin.template;

public class DomainEventTemplate {
    private static final String TEMPLATE = "" +
            "package {pkg};\n" +
            "\n" +
            "import com.geekhalo.lego.core.command.support.AbstractDomainEvent;\n" +
            "import {idTypeFull};\n" +
            "\n" +
            "public abstract class {className}\n" +
            "        extends AbstractDomainEvent<{idType}, {aggType}> {\n" +
            "    public {className}({aggType} agg) {\n" +
            "        super(agg);\n" +
            "    }\n" +
            "}\n";

    private static final String EVENT_TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import lombok.Value;\n" +
            "import {parentTypeFull};\n" +
            "import {aggTypeFull};\n" +
            "\n" +
            "@Value\n" +
            "public class {className}\n" +
            "        extends {parentType}{\n" +
            "    public {className}({aggType} agg){\n" +
            "        super(agg);\n" +
            "    }\n" +
            "}\n";
    public static String createAbstractEvent(CreateAbstractDomainEventContext context) {
        String content = TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{idTypeFull}", context.getIdTypeFull());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{idType}", context.getIdType());
        content = content.replace("{aggType}", context.getAggType());
        return content;
    }

    public static class CreateAbstractDomainEventContext extends CreateClassContext{
        public CreateAbstractDomainEventContext(String pkg, String clsName) {
            super(pkg, clsName);
        }
    }

    public static String createEvent(CreateDomainEventContext context){
        String content = EVENT_TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{parentType}", context.getParentType());
        content = content.replace("{parentTypeFull}", context.getParentTypeFull());
        content = content.replace("{aggType}", context.getAggType());
        content = content.replace("{aggTypeFull}", context.getAggTypeFull());
        return content;
    }

    public static class CreateDomainEventContext extends CreateClassContext{
        private String parentType;
        private String parentTypeFull;


        public CreateDomainEventContext(String pkg, String clsName) {
            super(pkg, clsName);
        }

        public void setParentTypeFull(String parentTypeFull){
            this.parentTypeFull = parentTypeFull;
            this.parentType = getType(parentTypeFull);
        }


        public String getParentType() {
            return parentType;
        }

        public String getParentTypeFull() {
            return parentTypeFull;
        }

    }
}
