package goats.model.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * Интерфейс слушателя события класса игры {@link goats.model.Game}.
 */
public interface GameActionListener extends EventListener {

    /**
     * Коза переместилась.
     * @param event объект события класса игры.
     */
    void goatIsMoved(@NotNull GameActionEvent event);

    /**
     * Коза телепортировалась.
     * @param event объект события класса игры.
     */
    void goatIsTeleported(@NotNull GameActionEvent event);

    /**
     * Статус игры изменился.
     * @param event объект события класса игры.
     */
    void gameStatusChanged(@NotNull GameActionEvent event);
}
