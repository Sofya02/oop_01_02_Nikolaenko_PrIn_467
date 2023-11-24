package goats.model.field.cells;

import org.jetbrains.annotations.NotNull;
import goats.Utils.BuildConfig;
import goats.model.event.ExitCellActionEvent;
import goats.model.event.ExitCellActionListener;
import goats.model.field.Cell;
import goats.model.field.CellObject;
import goats.model.field.cell_objects.Goat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Ячейка выхода.
 */
public class ExitCell extends Cell {

    /**
     * время ождания перед телепортацией.
     */
    private static final int SLEEP_TIME = 1000;

    /**
     * Список телепортированных коз.
     */
    private final List<Goat> teleportedGoats = new ArrayList<>();

    /**
     * Получить телепортированных коз {@link ExitCell#teleportedGoats}.
     * @return список телепортированных коз.
     */
    public List<Goat> getTeleportedGoats() {
        return Collections.unmodifiableList(teleportedGoats);
    }

    @Override
    public void addObject(@NotNull CellObject goat) {
        if(!(goat instanceof Goat)) throw new IllegalArgumentException();
        super.addObject(goat);
        if (BuildConfig.buildType == BuildConfig.BuildType.RELEASE) {
            Timer timer = new Timer(SLEEP_TIME, e -> teleportGoat());
            timer.setRepeats(false);
            timer.start();
        } else {
            teleportGoat();
        }
    }

    /**
     * Телепортировать козу.
     */
    private void teleportGoat() {
        Goat goat = (Goat) takeObject(getMobileCellObject());
        teleportedGoats.add(goat);
        fireGoatIsTeleported(goat);
    }

    /**
     * Список слушателей, подписанных на события ячейки выхода.
     */
    private final ArrayList<ExitCellActionListener> exitCellListListener = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями ячейки выхода.
     * @param listener слушатель.
     */
    public void addExitCellActionListener(ExitCellActionListener listener) {
        exitCellListListener.add(listener);
    }

    /**
     * Удалить слушателя за событиями ячейки выхода.
     * @param listener слушатель.
     */
    public void removeExitCellActionListener(ExitCellActionListener listener) {
        exitCellListListener.remove(listener);
    }

    /**
     * Оповестить сулшателей {@link ExitCell#exitCellListListener}, что коза телепортирована.
     * @param goat телепортированная коза.
     */
    private void fireGoatIsTeleported(Goat goat) {
        for (ExitCellActionListener listener : exitCellListListener) {
            ExitCellActionEvent event = new ExitCellActionEvent(listener);
            event.setGoat(goat);
            event.setTeleport(this);
            listener.goatIsTeleported(event);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ExitCell exitCell = (ExitCell) o;
        return Objects.equals(teleportedGoats, exitCell.teleportedGoats) &&
                Objects.equals(exitCellListListener, exitCell.exitCellListListener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "ExitCell{" +
                "teleportedGoats=" + teleportedGoats +
                ", exitCellListListener=" + exitCellListListener +
                '}';
    }
}
