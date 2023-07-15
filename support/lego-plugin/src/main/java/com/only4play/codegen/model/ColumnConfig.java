package com.only4play.codegen.model;


public class ColumnConfig {

  private String columnName;


  private boolean ignoreUpdater;

  private boolean ignoreCreator;

  private boolean queryItem;

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public boolean isIgnoreUpdater() {
    return ignoreUpdater;
  }

  public void setIgnoreUpdater(boolean ignoreUpdater) {
    this.ignoreUpdater = ignoreUpdater;
  }

  public boolean isIgnoreCreator() {
    return ignoreCreator;
  }

  public void setIgnoreCreator(boolean ignoreCreator) {
    this.ignoreCreator = ignoreCreator;
  }

  public boolean isQueryItem() {
    return queryItem;
  }

  public void setQueryItem(boolean queryItem) {
    this.queryItem = queryItem;
  }
}
