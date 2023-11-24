package goats.model;

import org.jetbrains.annotations.NotNull;
import goats.model.event.*;
import goats.model.field.cell_objects.Goat;
import goats.model.field.cells.ExitCell;
import goats.model.field.Field;
import goats.model.labirints.Labirint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Игра.
 */
public class Game {

    /**
     * Статус игры.
     */
    private GameStatus gameStatus;

    /**
     * Активный коза.
     */
    private Goat goat;

    /**
     * Коза-победитель.
     */
    private Goat winner;

    /**
     * Игровое поле.
     */
    private Field gameField;

    public Game(Labirint labirint) {
        startGame(labirint);
    }

    /**
     * Старт новой игры
     * @param labirint лабиринт, содержащий расстановку элементов на поле
     */
    public void startGame(@NotNull Labirint labirint) {
        setStatus(GameStatus.GAME_IS_ON);

        buildField(labirint);

        gameField.addFieldActionListener(new FieldObserver());

        for(var i : gameField.getGoatsOnField()) {
            i.addGoatActionListener(new GoatObserver());

        }

    }

    /**
     * Прервать игру
     */
    public void abort() {
        setStatus(GameStatus.GAME_ABORTED);
    }

    /**
     * Получить текущий статус игры {@link Game#gameStatus}
     * @return текующий статус игры
     */
    public GameStatus getStatus() {
        return gameStatus;
    }

    private void setStatus(GameStatus status) {
        if(gameStatus != status) {
            gameStatus = status;
            fireGameStatusIsChanged(gameStatus);
        }
    }

    /**
     * Получить Козу-победителя.
     * Возвращается [null], если победитель не найден.
     * @return коза-победитель
     */
    public Goat getWinner() {
        return winner;
    }

    /**
     * Получить активного коза {@link Game#goat}.
     * Возвращается null, если ни один коза не является активным.
     * @return активный коза.
     */
    public Goat getGoat() {
        return goat;
    }

    /**
     * Получить игровое поле {@link Game#gameField}.
     * @return игровое поле.
     */
    public Field getGameField() {
        return gameField;
    }

    /**
     * Получить коз на поле.
     * @return неизменяемый список коз, находящихся на поле.
     */
    public List<Goat> getGoatsOnField() {
        return Collections.unmodifiableList(gameField.getGoatsOnField());
    }

    /**
     * Получить телепортированных коз.
     * @return неизменяемый список телепортированных коз.
     */
    public List<Goat> getTeleportedGoats() {
        return Collections.unmodifiableList(gameField.getTeleportedGoats());
    }

    /**
     * Обновить состояние игры.
     */
    private void updateGameState() {
        GameStatus status = determineOutcomeGame();
        setStatus(status);
    }

    /**
     * Определить исход игры.
     * @return статус игры.
     */
    private GameStatus determineOutcomeGame() {
        GameStatus result = GameStatus.GAME_IS_ON;

        List<Goat> goatsOnField = gameField.getGoatsOnField();
        List<Goat> teleportedGoats = gameField.getTeleportedGoats();


        if(!goatsOnField.isEmpty() && teleportedGoats.isEmpty() && goatsHasLowCharge(goatsOnField)) {
            result = GameStatus.GOAT_HAVE_LOW_CHARGE;
        }

        if(teleportedGoats.size() == 1 && goatsHasLowCharge(goatsOnField)) {
            setWinner(teleportedGoats.get(0));
            result = GameStatus.WINNER_FOUND;
        }


        return result;
    }

    /**
     * Имеют ли все козы нулевой заряд.
     * @param goats список коз.
     * @return true - если все козы имеют нулевой заряд.
     */
    private boolean goatsHasLowCharge(@NotNull List<Goat> goats) {
        boolean result = true;

        for(int i = 0; i < goats.size() && result; ++i) {
            result = goats.get(i).getStepVolume() == 0;
        }

        return result;
    }

    /**
     * Построить игровое поле.
     * @param labirint лабиринт, содержащий расстановку элементов на поле.
     */
    private void buildField(@NotNull Labirint labirint) {
        gameField = labirint.buildField();
    }

    private void setWinner(@NotNull Goat goat) {
        winner = goat;
    }


    /**
     * Класс, реализующий наблюдение за событиями {@link GoatActionListener}.
     */
    private class GoatObserver implements GoatActionListener {

        @Override
        public void goatIsMoved(@NotNull GoatActionEvent event) {
            fireGoatIsMoved(event.getGoat());
            if(!(event.getToCell() instanceof ExitCell)){
                updateGameState();
            }
        }

        @Override
        public void goatChangedMagicGrass(@NotNull GoatActionEvent event) {
            // Not implemented yet
        }

    }

    /**
     * Класс, реализующий наблюдение за событиями {@link FieldActionListener}.
     */
    private class FieldObserver implements FieldActionListener {

        @Override
        public void goatIsTeleported(@NotNull FieldActionEvent event) {
            updateGameState();
            fireGoatIsTeleported(event.getGoat());
        }
    }

    /**
     * Список слушателей, подписанных на события игры.
     */
    private final ArrayList<GameActionListener> gameActionListeners = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями игры.
     * @param listener слушатель.
     */
    public void addGameActionListener(@NotNull GameActionListener listener) {
        gameActionListeners.add(listener);
    }

    /**
     * Удалить слушателя за событиями игры.
     * @param listener слушатель.
     */
    public void removeGameActionListener(@NotNull GameActionListener listener) {
        gameActionListeners.remove(listener);
    }

    /**
     * Оповестить сулшателей {@link Game#gameActionListeners}, что коза переместилась.
     * @param goat коза, которая переместилась.
     */
    private void fireGoatIsMoved(@NotNull Goat goat) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setGoat(goat);
            listener.goatIsMoved(event);
        }
    }

    /**
     * Оповестить сулшателей {@link Game#gameActionListeners}, что коза телепортировалась.
     * @param goat коза, которая телепортировалась.
     */
    private void fireGoatIsTeleported(@NotNull Goat goat) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setGoat(goat);
            listener.goatIsTeleported(event);
        }
    }

    /**
     * Оповестить сулшателей {@link Game#gameActionListeners}, что статус игры изменился.
     * @param status статус игры.
     */
    private void fireGameStatusIsChanged(@NotNull GameStatus status) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setStatus(status);
            listener.gameStatusChanged(event);
        }
    }
}
