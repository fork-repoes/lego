package com.only4play.codegen.model;


import java.util.List;

public class GenTableModel {

  private String tableName;

  private List<ColumnConfig> list;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<ColumnConfig> getList() {
    return list;
  }

  public void setList(List<ColumnConfig> list) {
    this.list = list;
  }




}
