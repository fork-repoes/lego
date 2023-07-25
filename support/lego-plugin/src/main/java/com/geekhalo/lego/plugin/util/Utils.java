package com.geekhalo.lego.plugin.util;

import com.geekhalo.lego.plugin.ui.MethodNamePair;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class Utils {

    public static final String DOMAIN = "domain";
    public static final String APP = "app";

    public static VirtualFile findSourceFile(VirtualFile[] sourceRoots) {
        for (VirtualFile file : sourceRoots){
            if (file.getPath().contains("src/main/java")){
                return file;
            }
        }
        return null;
    }

    public static String domainPkgToApp(String domainPkg){
        return domainPkg.replace(DOMAIN, APP);
    }

    public static String createCommandRepositoryByAgg(String aggClass) {
        return aggClass + "CommandRepository";
    }

    @NotNull
    public static String createAbstractDomainEventByAgg(String aggClass) {
        return "Abstract" + aggClass + "Event";
    }

    @NotNull
    public static String createQueryApplicationByAgg(String aggClass) {
        return aggClass + "QueryApplication";
    }

    @NotNull
    public static String createCommandApplicationByAgg(String aggClass) {
        return aggClass + "CommandApplication";
    }

    @NotNull
    public static String createQueryRepositoryByAgg(String aggClass) {
        return aggClass + "QueryRepository";
    }

    @NotNull
    public static String createDomainEventTypeName(MethodNamePair methodNamePair) {
        return methodNamePair.getTarget() + methodNamePair.getPastAction() + "Event";
    }

    @NotNull
    public static String createContextTypeName(MethodNamePair methodNamePair) {
        return methodNamePair.getAction() + methodNamePair.getTarget() + "Context";
    }

    @NotNull
    public static String createCommandTypeName(MethodNamePair methodNamePair) {
        return methodNamePair.getAction() + methodNamePair.getTarget() + "Command";
    }

}
