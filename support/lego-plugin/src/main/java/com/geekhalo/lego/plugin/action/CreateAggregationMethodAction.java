package com.geekhalo.lego.plugin.action;

import com.geekhalo.lego.plugin.ui.CreateAggregationMethodDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class CreateAggregationMethodAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiClass psiClass = getJavaClass(e);

        String aggClass = psiClass.getName();
        CreateAggregationMethodDialog dialog = new CreateAggregationMethodDialog(e.getProject(), aggClass);
        dialog.pack();
        dialog.setVisible(true);
    }

    private PsiClass getJavaClass(AnActionEvent e){
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        if (psiFile instanceof PsiJavaFile) {
            PsiJavaFile javaFile = (PsiJavaFile)psiFile;
            PsiClass[] classes = javaFile.getClasses();
            return classes[0];
        }
        return null;
    }
}