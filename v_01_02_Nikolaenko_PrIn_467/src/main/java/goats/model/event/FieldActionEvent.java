package goats.model.event;

import org.jetbrains.annotations.NotNull;
import goats.model.field.Cell;
import goats.model.field.cell_objects.Goat;

import java.util.EventObject;

/**
 * Объект события класса поля {@link goats.model.field.Field}.
 */
public class FieldActionEvent extends EventObject {

    /**
     * Коза.
     */
    private Goat goat;

    /**
     * Установить козу {@link FieldActionEvent#goat}.
     * @param goat коза.
     */
    public void setGoat(@NotNull Goat goat) {
        this.goat = goat;
    }

    /**
     * Получить козу {@link FieldActionEvent#goat}.
     * @return коза
     */
    public Goat getGoat() {
        return goat;
    }

    /**
     * Ячейка выхода.
     */
    private Cell teleport;

    /**
     * Получить ячейку выхода {@link FieldActionEvent#teleport}.
     * @return ячейка выхода.
     */
    public Cell getTeleport() {
        return teleport;
    }

    /**
     * Установить ячейку выхода {@link FieldActionEvent#teleport}.
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
    public FieldActionEvent(Object source) {
        super(source);
    }
}
