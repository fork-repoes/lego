package com.geekhalo.lego.plugin.template;

public class CommandTemplate {
    private static final String CREATE_TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import com.geekhalo.lego.core.command.CommandForCreate;\n" +
            "import lombok.Data;\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "@Data\n" +
            "@AllArgsConstructor\n" +
            "@NoArgsConstructor\n" +
            "public class {className} implements CommandForCreate {\n" +
            "\n" +
            "\n" +
            "}\n";
    private static final String UPDATE_BY_ID_TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import com.geekhalo.lego.core.command.CommandForUpdateById;\n" +
            "import {idTypeFull};\n" +
            "import lombok.Data;\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "import javax.validation.constraints.NotNull;\n" +
            "\n" +
            "@Data\n" +
            "@AllArgsConstructor\n" +
            "@NoArgsConstructor\n" +
            "public class {className} implements CommandForUpdateById<{id}> {\n" +
            "    @NotNull\n" +
            "    private final {id} id;\n" +
            "\n" +
            "    public {className}({id} id){\n" +
            "        this.id = id;\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public {id} getId() {\n" +
            "        return id;\n" +
            "    }\n" +
            "\n" +
            "    public static {className} apply({id} id){\n" +
            "        {className} command = new {className}(id); \n" +
            "        return command;\n" +
            "    }\n" +
            "}\n";

    private static final String UPDATE_BY_KEY_TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "\n" +
            "import com.geekhalo.lego.core.command.CommandForUpdateByKey;\n" +
            "import {keyTypeFull};\n" +
            "import lombok.Data;\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "@Data\n" +
            "@AllArgsConstructor\n" +
            "@NoArgsConstructor\n" +
            "public class {className} implements CommandForUpdateByKey<{keyType}> {\n" +
            "\n" +
            "    private final {keyType} key;\n" +
            "\n" +
            "    public {className}({keyType} key) {\n" +
            "        this.key = key;\n "+
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public  {keyType} getKey(){\n" +
            "        return this.key;\n" +
            "    }\n" +
            "\n" +
            "    public static {className} apply({keyType} key){\n" +
            "        {className} command = new {className}(key); \n" +
            "        return command;\n" +
            "    }\n" +
            "}\n";

    private static final String SYNC_TEMPLATE =
            "package {pkg};\n" +
            "\n" +
            "import com.geekhalo.lego.core.command.CommandForSync;\n" +
            "import {keyTypeFull};\n" +
            "import lombok.Data;\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "\n" +
            "\n" +
            "@Data\n" +
            "@AllArgsConstructor\n" +
            "@NoArgsConstructor\n" +
            "public class {className} implements CommandForSync<{keyType}> {\n" +
            "    private final {keyType} key;\n" +
            "\n "+
            "    public {className}({keyType} key) {\n" +
            "        this.key = key;\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public {keyType} getKey() {\n" +
            "        return this.key;\n" +
            "    }\n" +
            "\n" +
            "    public static {className} apply({keyType} key){\n" +
            "        {className} command = new {className}(key); \n" +
            "        return command;\n" +
            "    }\n" +
            "}\n";

    public static String createCreateCommand(CreateCreateCommandContext context){
        String content = CREATE_TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());

        return content;
    }

    public static String createUpdateCommand(CreateUpdateCommandContext context){
        String content = UPDATE_BY_ID_TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{id}", context.getIdType());
        content = content.replace("{idTypeFull}", context.getIdTypeFull());

        return content;
    }

    public static String createUpdateByKeyCommand(CreateUpdateCommandContext context){
        String content = UPDATE_BY_KEY_TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{keyType}", context.getKeyType());
        content = content.replace("{keyTypeFull}", context.getKeyTypeFull());

        return content;
    }

    public static String createSyncCommand(CreateSyncCommandContext context){
        String content = SYNC_TEMPLATE;
        content = content.replace("{pkg}", context.getPkg());
        content = content.replace("{className}", context.getClsName());
        content = content.replace("{keyType}", context.getKeyType());
        content = content.replace("{keyTypeFull}", context.getKeyTypeFull());

        return content;
    }

    public static class CreateCreateCommandContext extends CreateClassContext{

        public CreateCreateCommandContext(String pkg, String clsName) {
            super(pkg, clsName);
        }
    }

    public static class CreateUpdateCommandContext extends CreateClassContext{
        private String keyType;
        private String keyTypeFull;
        public CreateUpdateCommandContext(String pkg, String clsName) {
            super(pkg, clsName);
        }

        public void setKeyTypeFull(String keyTypeFull){
            this.keyTypeFull = keyTypeFull;
            this.keyType = getType(keyTypeFull);
        }

        public String getKeyType() {
            return keyType;
        }

        public String getKeyTypeFull() {
            return keyTypeFull;
        }
    }

    public static class CreateSyncCommandContext extends CreateClassContext{

        private String keyType;
        private String keyTypeFull;
        public CreateSyncCommandContext(String pkg, String clsName) {
            super(pkg, clsName);
        }

        public void setKeyTypeFull(String keyTypeFull){
            this.keyTypeFull = keyTypeFull;
            this.keyType = getType(keyTypeFull);
        }

        public String getKeyType() {
            return keyType;
        }

        public String getKeyTypeFull() {
            return keyTypeFull;
        }
    }
}
