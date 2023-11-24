package goats.model.event;

import org.jetbrains.annotations.NotNull;
import goats.model.field.Cell;
import goats.model.field.cell_objects.Goat;
import goats.model.field.cell_objects.magic_grass.MagicGrass;

import java.util.EventObject;

/**
 * Объект события класса коза {@link Goat}.
 */
public class GoatActionEvent extends EventObject {

    /**
     * Коза.
     */
    private Goat goat;

    /**
     * Ячейка откуда переместилась коза {@link GoatActionEvent#goat}.
     */
    private Cell fromCell;

    /**
     * Ячейка куда переместилась коза {@link GoatActionEvent#goat}.
     */
    private Cell toCell;

    /**
     * Волшебная трава.
     */
    private MagicGrass magicGrass;

    /**
     * Установить ячейку {@link GoatActionEvent#fromCell} откуда переместилась коза {@link GoatActionEvent#goat}.
     * @param fromCell ячейка откуда переместилась коза.
     */
    public void setFromCell(Cell fromCell) {
        this.fromCell = fromCell;
    }

    /**
     * Получить ячейку {@link GoatActionEvent#fromCell} откуда переместилась коза {@link GoatActionEvent#goat}.
     * @return ячейка откуда переместилась коза.
     */
    public Cell getFromCell() {
        return fromCell;
    }

    /**
     * Установить ячейку {@link GoatActionEvent#toCell} куда переместилась коза {@link GoatActionEvent#goat}.
     * @param toCell ячейка куда переместилась коза.
     */
    public void setToCell(Cell toCell) {
        this.toCell = toCell;
    }

    /**
     * Получить ячейку {@link GoatActionEvent#toCell} куда переместилась коза {@link GoatActionEvent#goat}.
     * @return ячейка куда переместилась коза.
     */
    public Cell getToCell() {
        return toCell;
    }

    /**
     * Установить волшебную траву {@link GoatActionEvent#magicGrass}.
     * @param magicGrass волшебная трава.
     */
    public void setMagicGrass(@NotNull MagicGrass magicGrass) {
        this.magicGrass = magicGrass;
    }

    /**
     * Получить волшебную траву {@link GoatActionEvent#magicGrass}.
     * @return волшебная трава.
     */
    public MagicGrass getMagicGrass() {
        return magicGrass;
    }

    /**
     * Установить козу {@link GoatActionEvent#goat}.
     * @param goat коза.
     */
    public void setGoat(@NotNull Goat goat) {
        this.goat = goat;
    }

    /**
     * Получить козу {@link GoatActionEvent#goat}.
     * @return коза.
     */
    public Goat getGoat() {
        return goat;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GoatActionEvent(Object source) {
        super(source);
    }
}
