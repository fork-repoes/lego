package com.geekhalo.lego.plugin.action;

import com.geekhalo.lego.plugin.creator.JavaFileCreator;
import com.geekhalo.lego.plugin.template.EnumTemplate;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CreateEnumAction extends BaseAnAction{
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String pkg = getPackage(e);
        if (StringUtils.isEmpty(pkg)){
            Messages.showWarningDialog("选择正确的包名", "选择包");
            return;
        }
        String eName = Messages.showInputDialog("输入枚举名称", "创建枚举", null);
        if (StringUtils.isEmpty(eName)){
            Messages.showWarningDialog("输入枚举名称", "创建枚举");
            return;
        }

        EnumTemplate.CreateEnumContext context = new EnumTemplate.CreateEnumContext(pkg, eName);
        String content = EnumTemplate.create(context);
        Module currentModule = findCurrentModule(e);

        JavaFileCreator.createJavaFileInPackage(e.getProject(), currentModule, pkg, eName, content);
    }
}
