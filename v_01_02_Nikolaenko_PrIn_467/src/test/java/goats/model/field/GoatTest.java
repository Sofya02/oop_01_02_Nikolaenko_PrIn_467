package java.goats.model.field;

import goats.model.field.Cell;
import goats.model.field.cell_objects.magic_grass.Sunflower;
import goats.model.field.cells.CellWithMagicGrass;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import goats.model.Direction;
import goats.model.event.GoatActionEvent;
import goats.model.event.GoatActionListener;
import goats.model.field.cell_objects.Goat;

import java.goats.model.field.cell_objects.magic_grass.NotPortablePowerSupply;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoatTest {

    private enum EVENT {ROBOT_MOVED}

    private final List<EVENT> events = new ArrayList<>();
    private final List<EVENT> expectedEvents = new ArrayList<>();

    private class EventsListener implements GoatActionListener {

        @Override
        public void goatIsMoved(@NotNull GoatActionEvent event) {
            events.add(EVENT.ROBOT_MOVED);
        }

        @Override
        public void goatChangedMagicGrass(@NotNull GoatActionEvent event) {
            // Not implemented yet
        }

    }

    private Cell cell;
    private Cell neighborCell;
    private final Direction direction = Direction.NORTH;

    private final static int DEFAULT_TEST_BATTERY_CHARGE = 10;
    private static final int AMOUNT_OF_CHARGE_FOR_MOVE = 1;

    private Goat goat;

    @BeforeEach
    public void testSetup() {
        // clean events
        events.clear();
        expectedEvents.clear();

        // create goat
        goat = new Goat(new Sunflower(DEFAULT_TEST_BATTERY_CHARGE,4));
        goat.addGoatActionListener(new EventsListener());

        // create field
        cell = new CellWithMagicGrass();
        neighborCell = new CellWithMagicGrass();
        cell.setNeighbor(neighborCell, direction);
    }


    @Test
    public void test_canStayAtPosition_emptyCell() {
        assertTrue(goat.canLocateAtPosition(cell));
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_canStayAtPosition_cellWithRobot() {
        cell.addObject(goat);

        assertFalse(goat.canLocateAtPosition(cell));
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_canStayAtPosition_cellWithBattery() {
        cell.addObject(new Sunflower(DEFAULT_TEST_BATTERY_CHARGE,2));

        assertTrue(goat.canLocateAtPosition(cell));
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotActiveAndEnoughCharge() {
        cell.addObject(goat);

        goat.move(direction);

        expectedEvents.add(EVENT.ROBOT_MOVED);

        assertEquals(goat, neighborCell.getMobileCellObject());
        assertEquals(neighborCell, goat.getPosition());
        assertNull(cell.getMobileCellObject());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE - AMOUNT_OF_CHARGE_FOR_MOVE, goat.getStepVolume());
        assertEquals(expectedEvents, events);
    }

    @Test
    public void test_move_noCellInDirectionAndRobotActiveAndEnoughCharge() {
        neighborCell.addObject(goat);

        goat.move(Direction.NORTH);

        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, goat.getStepVolume());
        assertEquals(neighborCell, goat.getPosition());
        assertEquals(goat, neighborCell.getMobileCellObject());
        assertTrue(events.isEmpty());
    }


    @Test
    public void test_move_emptyCellInDirectionAndRobotNotActiveAndEnoughCharge() {
        cell.addObject(goat);
        goat.move(direction);

        assertEquals(goat, cell.getMobileCellObject());
        assertEquals(cell, goat.getPosition());
        assertNull(neighborCell.getMobileCellObject());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, goat.getStepVolume());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_move_emptyCellInDirectionAndRobotActiveAndNotEnoughCharge() {
        cell.addObject(goat);

        goat.setMagicGrass(new Sunflower(0,0));
        goat.move(direction);

        assertEquals(goat, cell.getMobileCellObject());
        assertEquals(cell, goat.getPosition());
        assertNull(neighborCell.getMobileCellObject());
        assertEquals(0, goat.getStepVolume());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_changeBattery_robotIsActiveCellContainsBattery() {
        cell.addObject(goat);
        Sunflower newBattery = new Sunflower(5,3);
        cell.addObject(newBattery);

        goat.changeMagicGrass();

        assertNull(((CellWithMagicGrass) cell).getMagicGrass());
        assertEquals(newBattery.getStepVolume(), goat.getStepVolume());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_changeBattery_robotIsActiveCellNotContainsBattery() {
        cell.addObject(goat);

        Sunflower robotBattery = new Sunflower(DEFAULT_TEST_BATTERY_CHARGE,6);
        goat.setMagicGrass(robotBattery);

        goat.changeMagicGrass();

        assertEquals(robotBattery.getStepVolume(), goat.getStepVolume());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_changePowerSupply_isPortable() {
        cell.addObject(goat);
        int chargeOfPortableBattery = 5;
        Sunflower portableBattery = new Sunflower(chargeOfPortableBattery,4);
        cell.addObject(portableBattery);

        goat.changeMagicGrass();

        assertNull(((CellWithMagicGrass)cell).getMagicGrass());
        assertEquals(chargeOfPortableBattery, goat.getStepVolume());
    }

    @Test
    public void test_changePowerSupply_isNotPortable() {
        cell.addObject(goat);
        int chargeOfPortableBattery = 5;
        NotPortablePowerSupply notPortablePowerSupply = new NotPortablePowerSupply(chargeOfPortableBattery, DEFAULT_TEST_BATTERY_CHARGE,2,4);
        cell.addObject(notPortablePowerSupply);

        goat.changeMagicGrass();

        assertEquals(notPortablePowerSupply,((CellWithMagicGrass)cell).getMagicGrass());
        assertEquals(DEFAULT_TEST_BATTERY_CHARGE, goat.getStepVolume());
    }
}

