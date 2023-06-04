package com.geekhalo.lego.core.command.support.handler;

import java.util.Optional;

public interface AggLoader<REPOSITORY, CMD, AGG> {
    Optional<AGG> load(REPOSITORY repository, CMD cmd);
}
