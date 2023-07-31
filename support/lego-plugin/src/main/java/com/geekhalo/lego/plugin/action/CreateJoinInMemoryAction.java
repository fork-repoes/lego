package com.geekhalo.lego.plugin.action;

import com.geekhalo.lego.plugin.creator.JavaFileCreator;
import com.geekhalo.lego.plugin.template.LazyLoaderTemplate;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CreateJoinInMemoryAction extends BaseAnAction{
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String pkg = getPackage(e);
        if (StringUtils.isEmpty(pkg)){
            Messages.showWarningDialog("选择正确的包名", "选择包");
            return;
        }
        String eName = Messages.showInputDialog("输入注解名称", "创建注解", null);
        if (StringUtils.isEmpty(eName)){
            Messages.showWarningDialog("输入注解名称", "创建注解");
            return;
        }

        LazyLoaderTemplate.CreateLazyLoaderContext context = new LazyLoaderTemplate.CreateLazyLoaderContext(pkg, eName);
        String content = LazyLoaderTemplate.create(context);
        Module currentModule = findCurrentModule(e);

        JavaFileCreator.createJavaFileInPackage(e.getProject(), currentModule, pkg, eName, content);
    }
}
