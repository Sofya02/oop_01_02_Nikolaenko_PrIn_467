package goats.model.event;

import org.jetbrains.annotations.NotNull;
import goats.model.GameStatus;
import goats.model.field.cell_objects.Goat;

import java.util.EventObject;

/**
 * Объект события класса игры {@link goats.model.Game}.
 */
public class GameActionEvent extends EventObject {

    /**
     * Коза.
     */
    private Goat goat;

    /**
     * Установить козу {@link GameActionEvent#goat}.
     * @param goat коза.
     */
    public void setGoat(@NotNull Goat goat) {
        this.goat = goat;
    }

    /**
     * Получить козу {@link GameActionEvent#goat}.
     * @return коза.
     */
    public Goat getGoat() {
        return goat;
    }

    /**
     * Статус игры.
     */
    private GameStatus status;

    /**
     * Получить статус игры {@link GameActionEvent#status}.
     * @return статус игры.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Установить статус игры {@link GameActionEvent#status}.
     * @param status статус игры.
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameActionEvent(Object source) {
        super(source);
    }
}