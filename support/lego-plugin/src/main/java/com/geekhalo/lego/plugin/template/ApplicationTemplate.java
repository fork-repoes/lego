package com.geekhalo.lego.plugin.template;

public class ApplicationTemplate {
    private static final String COMMAND_TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import com.geekhalo.lego.core.command.CommandServiceDefinition;\n" +
            "import {commandRepositoryTypeFull};\n" +
            "import {aggTypeFull};\n" +
            "\n" +
            "@CommandServiceDefinition(\n" +
            "        domainClass = {aggType}.class,\n" +
            "        repositoryClass = {commandRepositoryType}.class\n" +
            ")\n" +
            "public interface {className} {\n" +
            "\n" +
            "}\n";

    private static final String QUERY_TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import com.geekhalo.lego.core.query.QueryServiceDefinition;\n" +
            "import {queryRepositoryTypeFull};\n" +
            "import {aggTypeFull};\n" +
            "\n" +
            "@QueryServiceDefinition(\n" +
            "        repositoryClass = {queryRepositoryType}.class,\n" +
            "        domainClass = {aggType}.class\n" +
            ")\n" +
            "public interface {className} {\n" +
            "\n" +
            "}\n";
    public static String createCommandApplication(CreateCommandApplicationContext context){
        String content = COMMAND_TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{aggType}", context.getAggType());
        content = content.replace("{aggTypeFull}", context.getAggTypeFull());
        content = content.replace("{commandRepositoryType}", context.getCommandRepositoryType());
        content = content.replace("{commandRepositoryTypeFull}", context.getCommandRepositoryTypeFull());

        return content;
    }

    public static class CreateCommandApplicationContext extends CreateClassContext{
        private String commandRepositoryType;
        private String commandRepositoryTypeFull;
        public CreateCommandApplicationContext(String pkg, String clsName) {
            super(pkg, clsName);
        }

        public void setCommandRepositoryTypeFull(String commandRepositoryTypeFull){
            this.commandRepositoryTypeFull = commandRepositoryTypeFull;
            this.commandRepositoryType = getType(commandRepositoryTypeFull);
        }

        public String getCommandRepositoryType() {
            return commandRepositoryType;
        }

        public String getCommandRepositoryTypeFull() {
            return commandRepositoryTypeFull;
        }
    }

    public static String createQueryApplication(CreateQueryApplicationContext context){
        String content = QUERY_TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{aggType}", context.getAggType());
        content = content.replace("{aggTypeFull}", context.getAggTypeFull());
        content = content.replace("{queryRepositoryType}", context.getQueryRepositoryType());
        content = content.replace("{queryRepositoryTypeFull}", context.getQueryRepositoryTypeFull());

        return content;
    }

    public static class CreateQueryApplicationContext extends CreateClassContext{
        private String queryRepositoryType;
        private String queryRepositoryTypeFull;
        public CreateQueryApplicationContext(String pkg, String clsName) {
            super(pkg, clsName);
        }

        public void setQueryRepositoryTypeFull(String queryRepositoryTypeFull){
            this.queryRepositoryTypeFull = queryRepositoryTypeFull;
            this.queryRepositoryType = getType(queryRepositoryTypeFull);
        }

        public String getQueryRepositoryType() {
            return queryRepositoryType;
        }

        public String getQueryRepositoryTypeFull() {
            return queryRepositoryTypeFull;
        }
    }
}
