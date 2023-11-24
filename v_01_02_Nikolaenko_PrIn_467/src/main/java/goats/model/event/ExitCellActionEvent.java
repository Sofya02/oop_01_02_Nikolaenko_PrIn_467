package goats.model.event;

import org.jetbrains.annotations.NotNull;
import goats.model.field.Cell;
import goats.model.field.cell_objects.Goat;

import java.util.EventObject;

/**
 * Объект собтыия класса ячейки выхода {@link goats.model.field.cells.ExitCell}.
 */
public class ExitCellActionEvent extends EventObject {

    /**
     * Коза.
     */
    private Goat goat;

    /**
     * Установить козу {@link ExitCellActionEvent#goat}.
     * @param goat коза.
     */
    public void setGoat(@NotNull Goat goat) {
        this.goat = goat;
    }

    /**
     * Получить козу {@link ExitCellActionEvent#goat}.
     * @return коза.
     */
    public Goat getGoat() {
        return goat;
    }

    /**
     * Ячейка выхода.
     */
    private Cell teleport;

    /**
     * Получить ячейку выхода {@link ExitCellActionEvent#teleport}.
     * @return ячейка выхода.
     */
    public Cell getTeleport() {
        return teleport;
    }

    /**
     * Установить ячейку выхода {@link ExitCellActionEvent#teleport}.
     * @param teleport ячейка выхода.
     */
    public void setTeleport(Cell teleport) {
        this.teleport = teleport;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ExitCellActionEvent(Object source) {
        super(source);
    }
}
