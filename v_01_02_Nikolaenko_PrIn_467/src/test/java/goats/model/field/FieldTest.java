package java.goats.model.field;

import goats.model.field.Cell;
import goats.model.field.Field;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import goats.model.Direction;
import goats.model.Point;
import goats.model.event.FieldActionEvent;
import goats.model.event.FieldActionListener;
import goats.model.field.cell_objects.magic_grass.Sunflower;
import goats.model.field.cell_objects.Goat;
import goats.model.field.cells.ExitCell;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private int eventCount = 0;

    class FieldObserver implements FieldActionListener {

        @Override
        public void goatIsTeleported(@NotNull FieldActionEvent event) {
            eventCount += 1;
        }
    }

    private Field field;

    @BeforeEach
    public void testSetup() {
        eventCount = 0;
        field = new Field(2, 2, new Point(1, 1));
        field.addFieldActionListener(new FieldObserver());
    }

    @Test
    public void test_create_withCorrectParams() {
        Cell cell_0_0 = field.getCell(new Point(0, 0));
        Cell cell_0_1 = field.getCell(new Point(1, 0));
        Cell cell_1_0 = field.getCell(new Point(0, 1));
        Cell cell_1_1 = field.getCell(new Point(1, 1));

        Assertions.assertEquals(Direction.SOUTH, cell_0_0.getNeighborDirection(cell_1_0));
        assertEquals(Direction.SOUTH, cell_0_1.getNeighborDirection(cell_1_1));
        assertEquals(Direction.NORTH, cell_1_1.getNeighborDirection(cell_0_1));
        assertEquals(Direction.NORTH, cell_1_0.getNeighborDirection(cell_0_0));
        assertEquals(Direction.EAST, cell_0_0.getNeighborDirection(cell_0_1));
        assertEquals(Direction.EAST, cell_1_0.getNeighborDirection(cell_1_1));
        assertEquals(Direction.WEST, cell_0_1.getNeighborDirection(cell_0_0));
        assertEquals(Direction.WEST, cell_1_1.getNeighborDirection(cell_1_0));
        assertTrue(cell_1_1 instanceof ExitCell);
    }

    @Test
    public void test_create_withNegativeWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(-1, 1, new Point(0, 0)));
    }

    @Test
    public void test_create_withZeroWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(0, 1, new Point(0, 0)));
    }

    @Test
    public void test_create_withNegativeHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Field(1, -1, new Point(0, 0)));
    }

    @Test
    public void test_create_withZeroHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Field(1, 0, new Point(0, 0)));
    }

    @Test
    public void test_create_withIncorrectExitPoint() {
        assertThrows(IllegalArgumentException.class, () -> new Field(1, 1, new Point(2, 2)));
    }

    @Test
    public void test_getGoatOnField_empty() {
        assertTrue(field.getGoatsOnField().isEmpty());
    }

    @Test
    public void test_getGoatsOnField_oneGoat() {
        Goat robot = new Goat(new Sunflower(10,6));
        field.getCell(new Point(0, 0)).addObject(robot);

        assertTrue(field.getGoatsOnField().contains(robot));
        assertEquals(1, field.getGoatsOnField().size());
    }


    @Test
    public void test_getTeleportedGoat_empty() {
        assertTrue(field.getTeleportedGoats().isEmpty());
    }

    @Test
    public void test_TeleportedGoats_oneGoat() {
        Goat robot = new Goat(new Sunflower(10,4));
        field.getCell(new Point(1, 1)).addObject(robot);

        assertTrue(field.getTeleportedGoats().contains(robot));
        assertEquals(1, field.getTeleportedGoats().size());
    }

    @Test
    public void test_teleportEvent_oneGoat() {
        int expectedEventCount = 1;
        Goat robot = new Goat(new Sunflower(10,4));

        field.getCell(new Point(1, 1)).addObject(robot);

        assertEquals(expectedEventCount, eventCount);
    }


}
