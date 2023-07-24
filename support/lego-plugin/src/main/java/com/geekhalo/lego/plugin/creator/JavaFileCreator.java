package com.geekhalo.lego.plugin.creator;

import com.google.common.base.Charsets;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiDirectoryFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JavaFileCreator {

    public static void createJavaFileInPackage(Project project, String packageName, String className, String content) {
        PsiDirectory targetDirectory = findOrCreatePackageDirectory(project, packageName);

        if (targetDirectory != null) {
            // 创建 Java 文件
            createJavaFile(project, targetDirectory, className, content);
        }
    }

    private static PsiDirectory findOrCreatePackageDirectory(Project project, String packageName) {

        PsiManager psiManager = PsiManager.getInstance(project);
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        // 查找或创建指定包的目录
        PsiPackage psiPackage = javaPsiFacade.findPackage(packageName);

        if (psiPackage == null) {
            List<String> fullDirs = new ArrayList<>();
            fullDirs.add("src");
            fullDirs.add("main");
            fullDirs.add("java");
            fullDirs.addAll(Arrays.asList(packageName.split("\\.")));
            VirtualFile baseDir = project.getBaseDir();
            PsiDirectory directory = psiManager.findDirectory(baseDir);
            PsiDirectory tmp = directory;
            for (String dir : fullDirs){
                PsiDirectory cDirectory = tmp.findSubdirectory(dir);
                if (cDirectory == null){
                    tmp = tmp.createSubdirectory(dir);
                }else {
                    tmp = cDirectory;
                }
            }
            psiPackage = JavaDirectoryService.getInstance().getPackage(tmp);
        }

        return psiPackage.getDirectories()[0];
    }

    private static void createJavaFile(Project project, PsiDirectory targetDirectory, String className, String content) {
        new WriteCommandAction.Simple(project) {
            @Override
            protected void run() throws Throwable {
                try {
                    // 获取文件模板管理器
//                    FileTemplateManager templateManager = FileTemplateManager.getInstance(project);
//                    FileTemplate javaFileTemplate = templateManager.getInternalTemplate("Java Class");

                    // 设置模板参数
//                    Properties properties = new Properties();
//                    properties.setProperty("NAME", className);

                    // 从模板生成文件内容
//                    String fileContent = javaFileTemplate.getText(properties);

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
                    // 将新文件添加到版本控制

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
