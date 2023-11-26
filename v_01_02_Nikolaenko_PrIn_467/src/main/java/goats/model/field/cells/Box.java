package goats.model.field.cells;

import goats.model.Direction;
import goats.model.event.GoatActionListener;
import goats.model.field.Cell;
import goats.model.field.MobileCellObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Box extends MobileCellObject {

    public Box() {}

    @Override
    public void move(@NotNull Direction direction) {
        Cell oldPosition = position;
        Cell newPosition = canMove(direction);
        if(newPosition!=null){
            position.takeObject(position.getMobileCellObject());
            newPosition.addObject(this);
        }
    }

    @Override
    public Cell canMove(@NotNull Direction direction) {
        Cell result = null;

        Cell neighborCell = position.getNeighborCell(direction);
        if (neighborCell != null && canLocateAtPosition(neighborCell)) {
            result = neighborCell;
        }

        return result;
    }


    @Override
    public boolean canLocateAtPosition(@NotNull Cell position) {
        if (position instanceof ExitCell || ((position instanceof CellWithMagicGrass)
                && ((CellWithMagicGrass) position).getMagicGrass() != null))  return false;
        return position.getMobileCellObject() == null;
    }

    /**
     * Список слушателей, подписанных на события игры.
     */
    private final ArrayList<GoatActionListener> boxListListener = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями игры.
     * @param listener слушатель.
     */
    public void addBoxActionListener(GoatActionListener listener) {
        boxListListener.add(listener);
    }



}
