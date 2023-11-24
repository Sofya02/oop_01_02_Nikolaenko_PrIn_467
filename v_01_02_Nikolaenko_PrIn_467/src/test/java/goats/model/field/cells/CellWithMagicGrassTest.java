package java.goats.model.field.cells;

import goats.model.field.cells.CellWithMagicGrass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import goats.model.field.cell_objects.magic_grass.Sunflower;
import goats.model.field.cell_objects.magic_grass.MagicGrass;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CellWithMagicGrassTest {

    private CellWithMagicGrass cell;
    private MagicGrass magicGrass;

    @BeforeEach
    public void testSetup() {
        cell = new CellWithMagicGrass();
        magicGrass = new Sunflower(10,4);
    }

    @Test
    public void test_setMagicGrass_inEmptyCell() {

        cell.addObject(magicGrass);

        assertEquals(magicGrass, cell.getMagicGrass());
    }

    @Test
    public void test_setMagicGrass_inCellWithMagicGrass() {
        cell.addObject(magicGrass);
        Sunflower anotherPowerSupply = new Sunflower(4,2);

        assertThrows(IllegalArgumentException.class, () -> cell.addObject(anotherPowerSupply));
    }

    @Test
    public void test_setMagicGrass_alreadySetMagicGrassToAnotherCell() {
        cell.addObject(magicGrass);

        CellWithMagicGrass anotherCell = new CellWithMagicGrass();

        assertThrows(IllegalArgumentException.class, () -> anotherCell.addObject(magicGrass));
    }

    @Test
    public void test_takeMagicGrass_fromCellWithMagicGrass(){
        cell.addObject(magicGrass);

        assertEquals(magicGrass, cell.takeObject(cell.getMagicGrass()));
        assertNull(cell.getMagicGrass());
        assertNull(magicGrass.getPosition());
    }

    @Test
    public void test_takeMagicGrass_fromCellWithoutMagicGrass() {
        assertNull(cell.takeObject(cell.getMagicGrass()));
    }
}
