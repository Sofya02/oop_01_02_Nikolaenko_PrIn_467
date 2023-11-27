package goats.model.field.cells;

import goats.model.Direction;
import goats.model.event.BoxActionEvent;
import goats.model.event.BoxActionListener;
import goats.model.field.Cell;
import goats.model.field.MobileCellObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Box extends MobileCellObject {

    private int weight;

    public int getWeight() {
        return  this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Box() {}

    /*@Override
    public void move(@NotNull Direction direction) {
        Cell oldPosition = position;
        Cell newPosition = canMove(direction);
        if(newPosition!=null){
            position.takeObject(position.getMobileCellObject());
            newPosition.addObject(this);
        }
    }*/

    @Override
    public void move(@NotNull Direction direction) {
        Cell oldPosition = position;
        Cell newPosition = canMove(direction, 2);
        Cell neighborCell = position.getNeighborCell(direction);
        if (newPosition != null) {
            if (neighborCell.getCellObject() instanceof Box) {
                ((Box) neighborCell.getCellObject()).move(direction);
            }
            fireBoxIsMoved(oldPosition, newPosition);
            position.takeObject(position.getMobileCellObject());
            newPosition.addObject(this);
        }
    }

    @Override
    protected Cell canMove(@NotNull Direction direction) {
        return null;
    }
    public Cell canMove(@NotNull Direction direction, int power) {
        Cell result = null;

        if (power < this.weight) {
            return result;
        }

        Cell neighborCell = position.getNeighborCell(direction);
        if (neighborCell != null && canLocateAtPosition(neighborCell)) {
            if (neighborCell.getCellObject() instanceof Box) {
                Cell resultNext = ((Box) neighborCell.getCellObject()).canMove(direction, power - 1);
                if (resultNext == null) {
                    return result;
                } else {
                    result = neighborCell;
                }
            } else {
                result = neighborCell;
            }
        }

        return result;
    }

//    @Override
//    public Cell canMove(@NotNull Direction direction) {
//        Cell result = null;
//
//        Cell neighborCell = position.getNeighborCell(direction);
//        if (neighborCell != null && canLocateAtPosition(neighborCell)) {
//            result = neighborCell;
//        }
//
//        return result;
//    }


    @Override
    public boolean canLocateAtPosition(@NotNull Cell position) {
        if (position instanceof ExitCell || ((position instanceof CellWithMagicGrass)
                && ((CellWithMagicGrass) position).getMagicGrass() != null))  return false;
        return position.getCellObject() == null;
    }

    /**
     * Список слушателей, подписанных на события игры.
     */
    private final ArrayList<BoxActionListener> boxListListener = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями игры.
     * @param listener слушатель.
     */
    public void addBoxActionListener(BoxActionListener listener) {
        boxListListener.add(listener);
    }

    public void removeBoxActionListener(BoxActionListener listener) {
        boxListListener.remove(listener);
    }

    private void fireBoxIsMoved(@NotNull Cell oldPosition, @NotNull Cell newPosition) {
        for (BoxActionListener listener : boxListListener) {
            BoxActionEvent event = new BoxActionEvent(listener);
            event.setBox(this);
            event.setFromCell(oldPosition);
            event.setToCell(newPosition);
            listener.boxIsMoved(event);
        }
    }


}
