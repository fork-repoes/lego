package com.geekhalo.lego.plugin.template;

public class ContextTemplate {
    private static final String CONTEXT_TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import {commandTypeFull}; \n" +
            "import lombok.Data;\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "@NoArgsConstructor\n" +
            "@Data\n" +
            "public class {className}{\n" +
            "    private {commandType} command;\n" +
            "\n" +
            "    private {className}({commandType} command){\n " +
            "        this.command = command;\n" +
            "    }\n" +
            "\n" +
            "    public static {className} apply({commandType} command) {\n" +
            "        {className} context = new {className}(command);\n" +
            "        return context;\n" +
            "    }\n" +
            "}\n";
    public static String create(CreateContextContext context){
        String content = CONTEXT_TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{commandType}", context.getCommandType());
        content = content.replace("{commandTypeFull}", context.getCommandTypeFull());

        return content;
    }

    public static class CreateContextContext extends CreateClassContext{
        private String commandType;
        private String commandTypeFull;

        public CreateContextContext(String pkg, String clsName) {
            super(pkg, clsName);
        }

        public void setCommandTypeFull(String commandTypeFull){
            this.commandTypeFull = commandTypeFull;
            this.commandType = getType(commandTypeFull);
        }

        public String getCommandType() {
            return commandType;
        }

        public String getCommandTypeFull() {
            return commandTypeFull;
        }
    }
}
