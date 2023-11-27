package goats.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface BoxActionListener extends EventListener {

    public void boxIsMoved(@NotNull BoxActionEvent event);
}
