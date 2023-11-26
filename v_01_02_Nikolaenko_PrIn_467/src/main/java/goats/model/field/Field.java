package goats.model.field;

import goats.model.field.cells.Box;
import org.jetbrains.annotations.NotNull;
import goats.model.Direction;
import goats.model.Point;
import goats.model.event.*;
import goats.model.field.cell_objects.Goat;
import goats.model.field.cells.CellWithMagicGrass;
import goats.model.field.cells.ExitCell;

import java.util.*;

/**
 * Поле.
 */
public class Field {

    /**
     * Ячейки поля.
     */
    private final Map<Point, Cell> cells = new HashMap<>();

    /**
     * Ширина поля.
     */
    private final int width;

    /**
     * Высота поля.
     */
    private final int height;

    /**
     * Ячейка выхода.
     */
    private final ExitCell exitCell;

    /**
     * Конструтор.
     * @param width ширина. Должна быть > 0.
     * @param height высота. Должна быть > 0.
     * @param exitPoint координата ячейки выхода.
     * @throws IllegalArgumentException если ширина, высота или координата ячейки переданы некорректные.
     */
    public Field(int width, int height, @NotNull Point exitPoint) {
        if(width <= 0) throw new IllegalArgumentException("Field width must be more than 0");
        if(height <= 0) throw new IllegalArgumentException("Field height must be more than 0");
        if(exitPoint.getX() >= width || exitPoint.getY() >= height)
            throw new IllegalArgumentException("exit point coordinates must be in range from 0 to weight or height");

        this.width = width;
        this.height = height;

        buildField(exitPoint);
        this.exitCell = (ExitCell) getCell(exitPoint);

        // Subscribe on exit cell
        ((ExitCell) getCell(exitPoint)).addExitCellActionListener(new ExitCellObserver());
    }

    /**
     * Построить игровое поле.
     * @param exitPoint координата ячйки выхода.
     */
    private void buildField(Point exitPoint) {
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                Point p = new Point(x, y);
                Cell cell = p.equals(exitPoint)? new ExitCell() : new CellWithMagicGrass();
                if(x > 0) cell.setNeighbor(getCell(p.to(Direction.WEST, 1)), Direction.WEST);
                if(y > 0) cell.setNeighbor(getCell(p.to(Direction.NORTH, 1)), Direction.NORTH);
                cells.put(p, cell);
            }
        }
    }

    /**
     * Получить ширину поля {@link Field#width}.
     * @return ширина поля.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Получить высоту поля {@link Field#height}.
     * @return высота поля.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Получить ячейку по заданной координате.
     * @param point координата.
     * @return ячейка.
     */
    public Cell getCell(@NotNull Point point) {
        return cells.get(point);
    }

    /**
     * Получить коз на поле.
     * @return список коз на поле.
     */
    public List<Goat> getGoatsOnField() {
        List<Goat> goats = new ArrayList<>();
        for(var i : cells.entrySet()) {

            MobileCellObject object =  i.getValue().getMobileCellObject();

            if(object !=null && object instanceof Goat  ) {
                goats.add((Goat)object);
            }
        }
        return goats;
    }

    /**
     * Получить ящики на поле.
     * @return список ящиков на поле.
     */
    public List<Box> getBoxesOnField() {
        List<Box> boxes = new ArrayList<>();
        for(var i : cells.entrySet()) {
            MobileCellObject object =  i.getValue().getMobileCellObject();

            if(object !=null && object instanceof Box  ) {
                boxes.add((Box)object);
            }
        }
        return boxes;
    }

    /**
     * Получить телепортированных коз.
     * @return телепортированные козы.
     */
    public List<Goat> getTeleportedGoats() {
        return exitCell.getTeleportedGoats();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return width == field.width &&
                height == field.height &&
                Objects.equals(cells, field.cells) &&
                Objects.equals(exitCell, field.exitCell);
    }


    @Override
    public String toString() {
        return "Field{" +
                "cells=" + cells +
                ", width=" + width +
                ", height=" + height +
                ", exitPoint=" + exitCell +
                '}';
    }


    /**
     * Класс, реализующий наблюдение за событиями {@link ExitCellActionListener}.
     */
    class ExitCellObserver implements ExitCellActionListener {

        @Override
        public void goatIsTeleported(@NotNull ExitCellActionEvent event) {
            fireGoatIsTeleported(event.getGoat(), event.getTeleport());
        }
    }

    /**
     * Список слушателей, подписанных на события поля.
     */
    private final ArrayList<FieldActionListener> fieldListListener = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями поля.
     * @param listener слушатель.
     */
    public void addFieldActionListener(FieldActionListener listener) {
        fieldListListener.add(listener);
    }

    /**
     * Удалить слушателя за событиями поля.
     * @param listener слушатель.
     */
    public void removeFieldCellActionListener(FieldActionListener listener) {
        fieldListListener.remove(listener);
    }

    /**
     * Оповестиьт слушателей {@link Field#fieldListListener}, что коза телепортировалась.
     * @param goat телепорированная коза.
     * @param teleport телпорт.
     */
    private void fireGoatIsTeleported(@NotNull Goat goat, @NotNull Cell teleport) {
        for(FieldActionListener listener: fieldListListener) {
            FieldActionEvent event = new FieldActionEvent(listener);
            event.setGoat(goat);
            event.setTeleport(teleport);
            listener.goatIsTeleported(event);
        }
    }
}
