package com.geekhalo.lego.plugin.ui;

import org.apache.commons.lang.WordUtils;

public class MethodNamePair{
    private String action;
    private String target;

    MethodNamePair(String action, String target){
        this.action = action;
        this.target = target;
    }

    public static MethodNamePair parseMethod(String methodName, String aggClassName) {
        Integer firstUp = -1;
        for (int i = 0; i< methodName.length(); i++){
            char c = methodName.charAt(i);
            if (Character.isUpperCase(c) && i != 0){
                firstUp = i;
            }
        }
        if (firstUp == -1){
            String action = WordUtils.capitalize(methodName);
            String target = aggClassName;
            return new MethodNamePair(action, target);
        }else {
            String action = WordUtils.capitalize(methodName.substring(0, firstUp));
            String target = WordUtils.capitalize(methodName.substring(firstUp));
            return new MethodNamePair(action, target);
        }
    }

    public String getPastAction(){
        if (action.endsWith("e")){
            return WordUtils.capitalize(action + "d");
        }else {
            return WordUtils.capitalize(action + "ed");
        }
    }

    public String getAction() {
        return action;
    }

    public String getTarget() {
        return target;
    }
}
