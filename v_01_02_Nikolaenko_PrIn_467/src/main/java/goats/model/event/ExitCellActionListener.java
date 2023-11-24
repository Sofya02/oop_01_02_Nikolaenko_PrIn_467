package goats.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * Интерфейс слушателя события класса ячейки выхода {@link goats.model.field.cells.ExitCell}.
 */
public interface ExitCellActionListener extends EventListener {

    /**
     * Коза телепортировалась.
     * @param event объект собтыия класса ячейки выхода.
     */
    void goatIsTeleported(@NotNull ExitCellActionEvent event);
}
