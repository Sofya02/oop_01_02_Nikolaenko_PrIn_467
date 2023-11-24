package java.goats.model.field;

import goats.model.field.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import goats.model.Direction;
import goats.model.field.cell_objects.magic_grass.Sunflower;
import goats.model.field.cell_objects.Goat;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    private Cell cell;

    public CellTest() {
    }


    @BeforeEach
    public void testSetup() {

        cell = new CellTestModel();
    }

    @Test
    public void test_setRobot_InEmptyCell() {
        Goat robot = new Goat(new Sunflower(10,8));

        cell.addObject(robot);

        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(cell, robot.getPosition());
    }

    @Test
    public void test_takeRobot_FromCellWithRobot() {
        Goat robot = new Goat(new Sunflower(10,8));

        cell.addObject(robot);

        assertEquals(robot, cell.takeObject(cell.getMobileCellObject()));
        assertNull(robot.getPosition());
        assertNull(cell.getMobileCellObject());
    }


    @Test
    public void test_setRobot_ToCellAgain() {
        Goat robot = new Goat(new Sunflower(10,8));

        cell.addObject(robot);

        assertThrows(IllegalArgumentException.class, () -> cell.addObject(robot));
        assertEquals(robot, cell.getMobileCellObject());
        assertEquals(cell, robot.getPosition());
    }

    @Test
    public void test_setNeighborCell() {
        Cell neighborCell = new CellTestModel();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);

        assertEquals(neighborCell, cell.getNeighborCell(direction));
        assertEquals(cell, neighborCell.getNeighborCell(direction.getOppositeDirection()));
    }


    @Test
    public void test_setNeighborCell_doubleSided() {
        Cell neighborCell = new CellTestModel();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);
        assertThrows(IllegalArgumentException.class, () -> neighborCell.setNeighbor(cell, direction.getOppositeDirection()));
        assertEquals(neighborCell, cell.getNeighborCell(direction));
        assertEquals(cell, neighborCell.getNeighborCell(direction.getOppositeDirection()));
    }

    @Test
    public void test_setNeighborCell_twoTimesInOneDirection() {
        Cell neighborCell = new CellTestModel();
        Cell anotherCell = new CellTestModel();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);
        assertThrows(IllegalArgumentException.class, () -> cell.setNeighbor(anotherCell, direction));
        assertEquals(neighborCell, cell.getNeighborCell(direction));
        assertEquals(cell, neighborCell.getNeighborCell(direction.getOppositeDirection()));
    }

    @Test
    public void test_setNeighborCell_alreadyNeighborWithAnotherDirection() {
        Cell neighborCell = new CellTestModel();
        Direction direction = Direction.NORTH;
        Direction anotherDirection = Direction.SOUTH;

        cell.setNeighbor(neighborCell, direction);
        assertThrows(IllegalArgumentException.class, () -> cell.setNeighbor(neighborCell, anotherDirection));
        assertEquals(neighborCell, cell.getNeighborCell(direction));
        assertEquals(cell, neighborCell.getNeighborCell(direction.getOppositeDirection()));
    }

    @Test
    public void test_setNeighborCell_setSelfAsNeighbor() {
        Direction direction = Direction.NORTH;

        assertThrows(IllegalArgumentException.class, () -> cell.setNeighbor(cell, direction));
        assertNull(cell.getNeighborCell(direction));
    }

    @Test
    public void test_isNeighbor_WhenNeighborCellExists() {
        Cell neighborCell = new CellTestModel();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);
        assertEquals(direction, cell.getNeighborDirection(neighborCell));
    }

    @Test
    public void test_isNeighbor_WhenNeighborCellNotExists() {
        Cell neighborCell = new CellTestModel();

        assertNull(cell.getNeighborDirection(neighborCell));
    }


}