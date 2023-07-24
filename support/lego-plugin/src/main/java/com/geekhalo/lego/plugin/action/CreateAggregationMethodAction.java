package com.geekhalo.lego.plugin.action;

import com.geekhalo.lego.plugin.ui.CreateAggregationMethodDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class CreateAggregationMethodAction extends BaseAnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiClass psiClass = getJavaClass(e);
        if (psiClass == null){
            Messages.showMessageDialog("请选择类", "Warn", null);
            return;
        }

        Module currentModule = findCurrentModule(e);
        Module[] modules = ModuleManager.getInstance(e.getProject()).getModules();
        Module appModule = findModule(modules, currentModule, APP_MODULE);
        Module domainModule = findModule(modules, currentModule, DOMAIN_MODULE);
        Module infraModule = findModule(modules, currentModule, INFRA_MODULE);


        CreateAggregationMethodDialog dialog = new CreateAggregationMethodDialog(e.getProject(),
                appModule, domainModule, infraModule,
                psiClass);
        dialog.pack();
        dialog.setVisible(true);
    }

}
