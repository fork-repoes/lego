package com.only4play.codegen.ui;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.table.JBTable;
import com.only4play.codegen.constants.Constants;
import com.only4play.codegen.model.ParamsCollector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;

public class GenDialog extends DialogWrapper {

  private Project project;

  private List<DbTable> tables;

  private ParamsCollector paramsCollector;

  private Module @NotNull [] modules;

  public GenDialog(Project project,List<DbTable> tables) {
    super(project);
    super.setTitle("生成配置");
    super.setOKButtonText(Constants.OK_TXT);
    super.setCancelButtonText(Constants.CANCEL_TXT);
    this.project = project;
    this.tables = tables;
    this.modules = ModuleManager.getInstance(this.project).getModules();
    this.paramsCollector = new ParamsCollector();
  }

  @Override
  protected @Nullable JComponent createCenterPanel() {

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    ComboBox<Module> module = new ComboBox<>(modules);
    module.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        Module item = (Module) e.getItem();
        this.paramsCollector.setModule(item);
      }
    });
    panel.add(LabeledComponent.create(module, "选择生成的工程", BorderLayout.NORTH),BorderLayout.NORTH);

    JBTable jbTable = new JBTable();
    jbTable.setRowHeight(20);
    jbTable.add(new JBLabel("字段"));
    jbTable.add(new JBLabel("数据类型"));
    jbTable.add(new JBLabel("creator配置"));
    jbTable.add(new JBLabel("updater配置"));
    jbTable.add(new JBLabel("查询配置"));
    panel.add(jbTable,BorderLayout.CENTER);
    return panel;
  }

  @Override
  public void doOKAction() {
    //写完文件
    super.doOKAction();
  }


}
