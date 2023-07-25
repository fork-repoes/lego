package com.geekhalo.lego.plugin.util;

import com.intellij.openapi.vfs.VirtualFile;

public class Utils {
    public static VirtualFile findSourceFile(VirtualFile[] sourceRoots) {
        for (VirtualFile file : sourceRoots){
            if (file.getPath().contains("src/main/java")){
                return file;
            }
        }
        return null;
    }
}
