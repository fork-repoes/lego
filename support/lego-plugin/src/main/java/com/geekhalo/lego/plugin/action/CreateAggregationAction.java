package com.geekhalo.lego.plugin.action;

import com.geekhalo.lego.plugin.ui.CreateAggregationDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class CreateAggregationAction extends BaseAnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String pkg = getPackage(e);
        if (pkg == null){
            Messages.showMessageDialog("请选择包", "Warn", null);
            return;
        }
        Project project = e.getData(PlatformDataKeys.PROJECT);
        Module currentModule = findCurrentModule(e);

        Module[] modules = ModuleManager.getInstance(project).getModules();

        Module appModule = findModule(modules, currentModule, APP_MODULE);
        Module domainModule = findModule(modules, currentModule, DOMAIN_MODULE);
        Module infraModule = findModule(modules, currentModule, INFRA_MODULE);

        CreateAggregationDialog createAggregationDialog = new CreateAggregationDialog(project, pkg, appModule, domainModule, infraModule);
        createAggregationDialog.pack();
        createAggregationDialog.setVisible(true);
    }



}
