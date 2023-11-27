package goats.model.field;

import org.jetbrains.annotations.NotNull;
import goats.model.*;

import java.util.*;

/**
 * Ячейка.
 */
public abstract class Cell {

    /**
     * Объекты, расположенные в ячекйке.
     */
    protected List<CellObject> objectList = new ArrayList<>();

    /**
     * Добавить объект в ячейку {@link Cell#objectList}.
     * @param cellObject объект, добавляемый в ячейку.
     */
    public void addObject(@NotNull CellObject cellObject) {
        boolean isPositionSetSuccess = cellObject.setPosition(this);
        if(!isPositionSetSuccess) throw new IllegalArgumentException();
        objectList.add(cellObject);
    }

    /**
     * Изъять объект из ячейки.
     * @param cellObject запрашиваемый объект.
     * @return запрашиваемый объект. null - если объект не содержится в ячейке {@link Cell#objectList}.
     */
    public CellObject takeObject(CellObject cellObject) {
        CellObject result = null;
        if(objectList.contains(cellObject)) {
            objectList.remove(cellObject);
            cellObject.setPosition(null);
            result = cellObject;
        }
        return result;
    }

    /**
     * Получить перемещаемый объект ячейки {@link Cell#objectList}.
     * @return перемещаемый объект ячейки
     */
    public MobileCellObject getMobileCellObject() {
        return (MobileCellObject) objectList.stream().filter(i -> i instanceof MobileCellObject).findFirst().orElse(null);
    }

    public CellObject getCellObject() {
        return (CellObject) objectList.stream().filter(i -> i instanceof CellObject).findFirst().orElse(null);
    }


    /**
     * Соседние ячейки.
     */
    private final Map<Direction, Cell> neighborCells = new EnumMap<>(Direction.class);

    /**
     * Получить соседние ячейки {@link Cell#neighborCells}.
     * @return соседние ячейки
     */
    public final Map<Direction, Cell> getNeighborCells() {
        return Collections.unmodifiableMap(neighborCells);
    }

    /**
     * Получить соседнюю ячейку в заданном направлении.
     * @param direction направление.
     * @return соседняя ячейка. null, если в заданном направлении нет соседней ячейки.
     */
    public Cell getNeighborCell(@NotNull Direction direction) {
        return neighborCells.get(direction);
    }

    /**
     * Установить ячейку сосденей {@link Cell#neighborCells}.
     * @param neighborCell сосденяя ячейка.
     * @param direction направление.
     * @throws IllegalArgumentException если переданная ячейка не может быть соседней.
     */
    public void setNeighbor(@NotNull Cell neighborCell, @NotNull Direction direction) {
        if (neighborCell == this || neighborCells.containsKey(direction) || neighborCells.containsValue(neighborCell))
            throw new IllegalArgumentException();
        neighborCells.put(direction, neighborCell);
        if (neighborCell.getNeighborCell(direction.getOppositeDirection()) == null) {
            neighborCell.setNeighbor(this, direction.getOppositeDirection());
        }
    }

    /**
     * Получить направление с соседней ячейкой.
     * @param other соседняя ячейка.
     * @return направление.
     */
    public Direction getNeighborDirection(@NotNull Cell other) {
        for (var i : neighborCells.entrySet()) {
            if (i.getValue().equals(other)) return i.getKey();
        }
        return null;
    }


}
