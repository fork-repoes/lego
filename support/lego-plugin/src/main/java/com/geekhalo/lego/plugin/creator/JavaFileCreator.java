package com.geekhalo.lego.plugin.creator;

import com.google.common.base.Charsets;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiDirectoryFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.geekhalo.lego.plugin.util.Utils.findSourceFile;

public final class JavaFileCreator {

    public static void createJavaFileInPackage(Project project, Module module, String packageName, String className, String content) {
        PsiDirectory targetDirectory = findOrCreatePackageDirectory(project, module, packageName);

        if (targetDirectory != null) {
            // 创建 Java 文件
            createJavaFile(project, targetDirectory, className, content);
        }
    }

    private static PsiDirectory findOrCreatePackageDirectory(Project project, Module module, String packageName) {
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        VirtualFile[] sourceRoots = moduleRootManager.getSourceRoots();

        VirtualFile sourceFile = findSourceFile(sourceRoots);
        if (sourceFile == null){
            Messages.showMessageDialog("请先为 " + module.getName() + " 模块创建 Java 源码目录", "Warn", null);
            return null;
        }

        PsiDirectory sourceDirectory = PsiManager.getInstance(project)
                .findDirectory(sourceFile);

        // 根据包路径获取指定的 PsiDirectory
        PsiDirectory tmp = sourceDirectory;
        for (String dir : packageName.split("\\.")){
            PsiDirectory cDirectory = tmp.findSubdirectory(dir);
            if (cDirectory == null){
                final PsiDirectory cDirectoryTuUse = tmp;
                WriteCommandAction.runWriteCommandAction(project, () ->{
                    cDirectoryTuUse.createSubdirectory(dir);
                });
                tmp = tmp.findSubdirectory(dir);
            }else {
                tmp = cDirectory;
            }
        }
        return tmp;
    }

    private static void createJavaFile(Project project, PsiDirectory targetDirectory, String className, String content) {
        WriteCommandAction.runWriteCommandAction(project, () ->{
            try {
                // 创建 Java 文件
                PsiFile psiFile = targetDirectory.createFile(className + ".java");

                if (psiFile != null && psiFile.isValid()) {
                    VirtualFile virtualFile = psiFile.getVirtualFile();
                    if (virtualFile != null) {
                        virtualFile.setWritable(true);
                        // 将文本内容写入文件
                        virtualFile.setBinaryContent(content.getBytes(Charsets.UTF_8));
                        virtualFile.setCharset(Charsets.UTF_8);

                        // 将该文件标记为已修改，以便保存
                        PsiManager.getInstance(project).reloadFromDisk(psiFile);

                        // 在 IDEA 中将新创建文件展开
                        targetDirectory.navigate(true);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
