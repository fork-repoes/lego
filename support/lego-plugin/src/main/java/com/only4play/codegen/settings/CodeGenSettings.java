package com.only4play.codegen.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "CodeGenSettings", storages = @Storage("codeSettings.xml"))
public class CodeGenSettings implements PersistentStateComponent<CodeGenSettings> {


  @Override
  public @Nullable CodeGenSettings getState() {
    return null;
  }

  @Override
  public void loadState(@NotNull CodeGenSettings state) {

  }
}
