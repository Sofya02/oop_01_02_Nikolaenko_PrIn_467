package java.goats.model.field.cell_objects.magic_grass;

import goats.model.field.cell_objects.magic_grass.Sunflower;
import goats.model.field.cells.CellWithMagicGrass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SunflowerTest {

    private static final int DEFAULT_TEST_STEP_VOLUME = 10;

    private Sunflower sunflower;

    @BeforeEach
    public void testSetup() {
        sunflower = new Sunflower(DEFAULT_TEST_STEP_VOLUME,4);
    }

    @Test
    public void test_crete_withNegativeStepVolume() {
        assertThrows(IllegalArgumentException.class, () -> new Sunflower(-1,-1));
    }


    @Test
    public void test_canLocateAtPosition_inEmptyCell() {
        CellWithMagicGrass cellWithMagicGrass = new CellWithMagicGrass();

        boolean result = sunflower.canLocateAtPosition(cellWithMagicGrass);

        assertTrue(result);
    }

    @Test
    public void test_canLocateAtPosition_inCellWithSunflower() {
        Sunflower anotherBattery = new Sunflower(10,8);
        CellWithMagicGrass cellWithMagicGrass = new CellWithMagicGrass();
        cellWithMagicGrass.addObject(anotherBattery);

        boolean result = sunflower.canLocateAtPosition(cellWithMagicGrass);

        assertFalse(result);
    }

    @Test
    public void test_canLocateAtPosition_alreadyHavePosition() {
        CellWithMagicGrass cellWithMagicGrass = new CellWithMagicGrass();
        cellWithMagicGrass.addObject(sunflower);

        boolean result = sunflower.canLocateAtPosition(cellWithMagicGrass);

        assertFalse(result);
    }

}