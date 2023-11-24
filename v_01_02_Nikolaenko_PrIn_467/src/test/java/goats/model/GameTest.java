package java.goats.model;

import goats.model.Direction;
import goats.model.Game;
import goats.model.GameStatus;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import goats.model.event.GameActionEvent;
import goats.model.event.GameActionListener;
import goats.model.field.cell_objects.Goat;

import java.goats.model.labirint.TestLabirint;
import java.goats.utils.Pair;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;

    private enum Event {GOAT_MOVED, ROBOT_SKIP_STEP, GOAT_TELEPORTED}

    private List<Pair<Event, Goat>> events = new ArrayList<>();
    private List<Pair<Event, Goat>> expectedEvents = new ArrayList<>();

    private class EventListener implements GameActionListener {

        @Override
        public void goatIsMoved(@NotNull GameActionEvent event) {
            events.add(new Pair<>(Event.GOAT_MOVED, event.getGoat()));
        }

        @Override
        public void goatIsTeleported(@NotNull GameActionEvent event) {
            events.add(new Pair<>(Event.GOAT_TELEPORTED, event.getGoat()));
        }

        @Override
        public void gameStatusChanged(@NotNull GameActionEvent event) {

        }
    }

    @BeforeEach
    public void testSetup() {
        events.clear();
        expectedEvents.clear();
        game = new Game(new TestLabirint());
        game.addGameActionListener(new EventListener());
    }

    @Test
    public void test_finishGame() {
        game.abort();
        assertEquals(GameStatus.GAME_ABORTED, game.getStatus());
    }

    @Test
    public void test_goatMoved_success() {
        Goat goat = game.getGoat();
        expectedEvents.add(new Pair<>(Event.GOAT_MOVED, goat));
        goat.move(Direction.EAST);
        assertEquals(expectedEvents, events);
        assertEquals(GameStatus.GAME_IS_ON, game.getStatus());
    }

    @Test
    public void test_goatMoved_incorrectDirection() {
        Goat goat = game.getGoat();
        goat.move(Direction.WEST);
        assertEquals(expectedEvents, events);
        assertEquals(GameStatus.GAME_IS_ON, game.getStatus());
    }

    @Test
    public void test_goatTeleported() {
        Goat goat = game.getGoat();
        goat.move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.GOAT_MOVED, goat));
        goat.move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.GOAT_MOVED, goat));
        expectedEvents.add(new Pair<>(Event.GOAT_TELEPORTED, goat));
        assertEquals(expectedEvents, events);
        assertEquals(GameStatus.GAME_IS_ON, game.getStatus());
    }

    @Test
    public void test_winnerFound() {
        Goat goat = game.getGoat();
        goat.move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.GOAT_MOVED, goat));
        goat.move(Direction.EAST);
        expectedEvents.add(new Pair<>(Event.GOAT_MOVED, goat));
        expectedEvents.add(new Pair<>(Event.GOAT_TELEPORTED, goat));
        assertNull(goat);
        assertEquals(expectedEvents, events);
        assertEquals(goat, game.getWinner());
        assertEquals(GameStatus.WINNER_FOUND, game.getStatus());
    }
}
