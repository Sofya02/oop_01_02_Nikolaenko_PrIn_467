package goats.model.field.cells;

import goats.model.field.Cell;
import goats.model.field.cell_objects.magic_grass.MagicGrass;

/**
 * Ячейка с источником питания.
 */
public class CellWithMagicGrass extends Cell {

    /**
     * Получить источник питания.
     * @return источник питания.
     */
    public MagicGrass getMagicGrass() {
        return (MagicGrass) objectList.stream().filter(i -> i instanceof MagicGrass).findFirst().orElse(null);
    }
}
