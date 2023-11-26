package goats.model.event;

import org.jetbrains.annotations.NotNull;
import goats.model.field.cell_objects.Goat;

import java.util.EventListener;

/**
 * Интерфейс слушателя события класса коза {@link Goat}.
 */
public interface GoatActionListener extends EventListener {

    /**
     * Коза переместился.
     * @param event объект события класса коза.
     */
    void goatIsMoved(@NotNull GoatActionEvent event);

    /**
     * Коза заменила волшебную траву.
     * @param event объект события класса коза.
     */
    void goatChangedMagicGrass(@NotNull GoatActionEvent event);

    void goatMovedBox(GoatActionEvent event);
}
