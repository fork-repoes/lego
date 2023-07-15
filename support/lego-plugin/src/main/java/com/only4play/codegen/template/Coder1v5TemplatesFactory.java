package com.only4play.codegen.template;

import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.platform.ProjectTemplate;
import com.intellij.platform.ProjectTemplatesFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Coder1v5TemplatesFactory extends ProjectTemplatesFactory {

  @Override
  public String @NotNull [] getGroups() {
    return new String[0];
  }

  @Override
  public ProjectTemplate @NotNull [] createTemplates(@Nullable String group,
      @NotNull WizardContext context) {
    return new ProjectTemplate[0];
  }
}
