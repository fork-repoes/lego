package com.geekhalo.lego.plugin.action;

import com.geekhalo.lego.plugin.ui.CreateAggregationDialog;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class CreateAggregationAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String pkg = getPackage(e);
        if (pkg == null){
            Messages.showMessageDialog("请选择包", "Warn", null);
            return;
        }
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);

        CreateAggregationDialog createAggregationDialog = new CreateAggregationDialog(project, pkg, psiFile);
        createAggregationDialog.pack();
        createAggregationDialog.setVisible(true);
    }


    private String getPackage(AnActionEvent event){
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


}
