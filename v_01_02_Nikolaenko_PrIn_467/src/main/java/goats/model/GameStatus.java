package goats.model;

/**
 * Статус игры.
 */
public enum GameStatus {

    /**
     * Найден победитель.
     */
    WINNER_FOUND,

    /**
     * Игра идёт.
     */
    GAME_IS_ON,

    /**
     * Коза имеет низкий заряд.
     */
    GOAT_HAVE_LOW_CHARGE,
    /**
     * Игра прервана.
     */
    GAME_ABORTED
}
