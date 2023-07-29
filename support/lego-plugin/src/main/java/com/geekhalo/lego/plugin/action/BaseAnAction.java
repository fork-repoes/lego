package com.geekhalo.lego.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

abstract class BaseAnAction extends AnAction {
    public static final String APP_MODULE = "app";
    public static final String DOMAIN_MODULE = "domain";
    public static final String INFRA_MODULE = "infrastructure";

    protected Module findModule(Module[] modules, Module currentModule, String suffix) {
        String moduleName = currentModule.getName();
        int index = moduleName.lastIndexOf("-");
        String name = moduleName.substring(0, index);
        for (Module module : modules){
            if (module.getName().endsWith(name + "-" + suffix)){
                return module;
            }
        }
        return modules[0];
    }

    protected Module findCurrentModule(AnActionEvent e){
        VirtualFile currentFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        Module currentModule = ModuleUtilCore.findModuleForFile(currentFile, e.getProject());
        return currentModule;
    }

    protected String getPackage(AnActionEvent event){
        // 获取当前选中的文件或目录
        VirtualFile[] selectedFiles =
                event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        // 如果没有文件或目录被选中，则返回
        if (selectedFiles == null || selectedFiles.length == 0) {
            return null;
        }
        // 获取选中文件或目录的路径
        String path = selectedFiles[0].getPath();
        // 如果选中的是目录，则获取该目录的名称作为包名
        if (selectedFiles[0].isDirectory()) {
            int index = path.indexOf("src/") + 4;
            path = path.substring(index);
        } else {
            // 如果选中的是文件，则获取该文件所在的目录的名称作为包名
            path = selectedFiles[0].getParent().getPath();
            int index = path.indexOf("src/") + 4;
            path = path.substring(index);
        }
        // 替换路径中的斜杠为点号
        path = path.replace('/', '.');
        path = path.replace("main.java.","");

        // 输出选中的包名
        return path;
    }


    protected PsiClass getJavaClass(AnActionEvent e){
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        if (psiFile instanceof PsiJavaFile) {
            PsiJavaFile javaFile = (PsiJavaFile)psiFile;
            PsiClass[] classes = javaFile.getClasses();
            return classes[0];
        }
        return null;
    }
}
