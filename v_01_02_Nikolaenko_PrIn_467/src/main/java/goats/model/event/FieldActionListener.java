package goats.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * Интерфейс слушателя события класса поля {@link goats.model.field.Field}.
 */
public interface FieldActionListener extends EventListener {
    /**
     * Коза телеропортировалась.
     * @param event объект события класса поляю.
     */
    void goatIsTeleported(@NotNull FieldActionEvent event);
}
