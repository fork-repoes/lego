package com.geekhalo.lego.plugin.template;

public class RepositoryTemplate {
    private static final String COMMAND_TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "\n" +
            "import com.geekhalo.lego.core.command.CommandRepository;\n" +
            "import {aggTypeFull};\n" +
            "import {idTypeFull};\n" +
            "\n" +
            "import java.util.Optional;\n" +
            "\n" +
            "public interface {className} extends CommandRepository<{aggType}, {idType}> {\n" +
            "\n" +
            "\n" +
            "\n" +
            "}\n";
    private static final String QUERY_TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "\n" +
            "import com.geekhalo.lego.core.query.QueryRepository;\n" +
            "import {aggTypeFull};\n" +
            "import {idTypeFull};\n" +
            "\n" +
            "import java.util.Optional;\n" +
            "\n" +
            "public interface {className} extends QueryRepository<{aggType}, {idType}> {\n" +
            "\n" +
            "\n" +
            "\n" +
            "}\n";

    public static String createCommand(CreateCommandRepositoryContext context) {
        String content = COMMAND_TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{aggType}", context.getAggType());
        content = content.replace("{aggTypeFull}", context.getAggTypeFull());
        content = content.replace("{idType}", context.getIdType());
        content = content.replace("{idTypeFull}", context.getAggTypeFull());
        return content;
    }

    public static String createQuery(CreateQueryRepositoryContext context) {
        String content = QUERY_TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{aggType}", context.getAggType());
        content = content.replace("{aggTypeFull}", context.getAggTypeFull());
        content = content.replace("{idType}", context.getIdType());
        content = content.replace("{idTypeFull}", context.getAggTypeFull());
        return content;
    }

    public static class CreateCommandRepositoryContext extends CreateClassContext{

        public CreateCommandRepositoryContext(String pkg, String clsName) {
            super(pkg, clsName);
        }
    }

    public static class CreateQueryRepositoryContext extends CreateClassContext{

        public CreateQueryRepositoryContext(String pkg, String clsName) {
            super(pkg, clsName);
        }
    }
}
