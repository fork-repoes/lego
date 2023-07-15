package com.only4play.codegen;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public class CodeGenBundle extends DynamicBundle {

  public static final String BUNDLE = "messages.CodeGenBundle";

  public CodeGenBundle() {
    super(BUNDLE);
  }

  private static final CodeGenBundle INSTANCE = new CodeGenBundle();

  public static @Nls String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object @NotNull ... params) {
    return INSTANCE.getMessage(key, params);
  }
}
