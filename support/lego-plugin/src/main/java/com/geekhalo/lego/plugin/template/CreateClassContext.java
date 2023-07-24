package com.geekhalo.lego.plugin.template;


public abstract class CreateClassContext {
    private final String pkg;
    private final String clsName;
    private String idType;
    private String idTypeFull;
    private String aggType;
    private String aggTypeFull;

    public CreateClassContext(String pkg, String clsName) {
        this.pkg = pkg;
        this.clsName = clsName;
    }

    public String getPkg() {
        return pkg;
    }

    public String getClsName() {
        return clsName;
    }

    public String getIdType() {
        return idType;
    }

    public String getIdTypeFull(){
        return idTypeFull;
    }

    public String getAggType() {
        return aggType;
    }

    public String getAggTypeFull() {
        return aggTypeFull;
    }

    public void setAggTypeFull(String aggTypeFull){
        this.aggTypeFull = aggTypeFull;
        this.aggType = getType(aggTypeFull);
    }

    public void setIdTypeFull(String idTypeFull) {
        this.idTypeFull = idTypeFull;
        this.idType = getType(idTypeFull);
    }

    protected String getType(String fullType){
        return fullType.substring(fullType.lastIndexOf('.') + 1);
    }
}
