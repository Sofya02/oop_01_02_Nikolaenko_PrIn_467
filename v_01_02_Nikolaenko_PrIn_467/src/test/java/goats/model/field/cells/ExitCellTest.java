package java.goats.model.field.cells;

import goats.model.field.cells.ExitCell;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import goats.model.event.ExitCellActionEvent;
import goats.model.event.ExitCellActionListener;
import goats.model.field.cell_objects.magic_grass.Sunflower;
import goats.model.field.cell_objects.Goat;

import static org.junit.jupiter.api.Assertions.*;

public class ExitCellTest {

    private ExitCell exitCell;
    private Goat goat;

    private int countEvents = 0;

    private class EventListener implements ExitCellActionListener {

        @Override
        public void goatIsTeleported(@NotNull ExitCellActionEvent event) {
            countEvents += 1;
        }
    }

    @BeforeEach
    public void testSetup() {
        // Clear events count
        countEvents = 0;

        goat = new Goat(new Sunflower(10,4));

        exitCell = new ExitCell();
        exitCell.addExitCellActionListener(new EventListener());
    }

    @Test
    public void test_setGoat() {
        exitCell.addObject(goat);

        int expectedCountEvents = 1;

        assertEquals(expectedCountEvents, countEvents);
        assertNull(goat.getPosition());
        assertTrue(exitCell.getTeleportedGoats().contains(goat));
        assertEquals(1, exitCell.getTeleportedGoats().size());
    }

    @Test
    public void test_setGoat_setTeleportedGoat() {
        exitCell.addObject(goat);

        int expectedCountEvents = 1;

        assertThrows(IllegalArgumentException.class, () -> exitCell.addObject(goat));
        assertEquals(expectedCountEvents, countEvents);
        assertNull(goat.getPosition());
        assertTrue(exitCell.getTeleportedGoats().contains(goat));
        assertEquals(1, exitCell.getTeleportedGoats().size());
    }


    @Test
    public void test_getTeleportedGoat_empty() {
        assertTrue(exitCell.getTeleportedGoats().isEmpty());
    }
}
