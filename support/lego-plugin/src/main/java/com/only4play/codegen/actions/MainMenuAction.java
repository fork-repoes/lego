package com.only4play.codegen.actions;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiElement;
import com.only4play.codegen.ui.GenDialog;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class MainMenuAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    PsiElement[] data = e.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
    if (Objects.isNull(data)) {
      return;
    }
    List<DbTable> tables =
        Arrays.stream(data)
            .filter(Objects::nonNull)
            .filter(d -> d instanceof DbTable)
            .map(DbTable.class::cast)
            .collect(Collectors.toList());
    GenDialog dialog = new GenDialog(e.getProject(),tables);
    dialog.show();
  }



}
