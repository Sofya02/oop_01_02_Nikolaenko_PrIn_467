package goats.model.event;

import goats.model.field.Cell;
import goats.model.field.cells.Box;
import org.jetbrains.annotations.NotNull;

import java.util.EventObject;

public class BoxActionEvent extends EventObject {

    /**
     * Ящик
     */
    private Box box;

    /**
     * Ячейка откуда переместился ящик {@link BoxActionEvent#box}.
     */
    private Cell fromCell;

    /**
     * Ячейка откуда переместился ящик {@link BoxActionEvent#box}.
     */
    private Cell toCell;

    /**
     * Установить ячейку {@link BoxActionEvent#fromCell} откуда переместился ящик {@link BoxActionEvent#box}.
     * @param fromCell ячейка откуда переместился ящик.
     */
    public void setFromCell(Cell fromCell) {
        this.fromCell = fromCell;
    }

    /**
     * Получить ячейку {@link BoxActionEvent#fromCell} откуда переместился ящик {@link BoxActionEvent#box}.
     * @return ячейка откуда переместился ящик.
     */
    public Cell getFromCell() {
        return fromCell;
    }


    /**
     * Установить ячейку {@link BoxActionEvent#toCell} куда переместился ящик {@link BoxActionEvent#box}.
     * @param toCell ячейка куда переместился ящик.
     */
    public void setToCell(Cell toCell) {
        this.toCell = toCell;
    }

    /**
     * Получить ячейку {@link BoxActionEvent#toCell} куда переместился ящик {@link BoxActionEvent#box}.
     * @return ячейка куда переместился ящик.
     */
    public Cell getToCell() {
        return toCell;
    }

    /**
     * Установить ящик {@link BoxActionEvent#box}.
     * @param box ящик.
     */
    public void setBox(@NotNull Box box) {
        this.box = box;
    }

    /**
     * Получить ящик {@link BoxActionEvent#box}.
     * @return ящик.
     */
    public Box getBox() {
        return box;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public BoxActionEvent(Object source) {
        super(source);
    }

}
