package java.goats.model.labirint;

import goats.model.labirints.Labirint;
import org.jetbrains.annotations.NotNull;
import goats.model.*;
import goats.model.field.Field;
import goats.model.field.cell_objects.magic_grass.Sunflower;
import goats.model.field.cell_objects.Goat;
import goats.model.labirints.Labirint;

public class TestLabirint extends Labirint {

    private static final int FIELD_HEIGHT = 3;
    private static final int FIELD_WIDTH = 3;
    private static final int DEFAULT_BATTERY_CHARGE = 10;

    @Override
    protected int fieldHeight() {
        return FIELD_HEIGHT;
    }

    @Override
    protected int fieldWidth() {
        return FIELD_WIDTH;
    }

    @Override
    protected Point exitPoint() {
        return new Point(2,2);
    }

    @Override
    protected void addGoats(@NotNull Field field) {
        Goat firstRobot = new Goat(new Sunflower(10,4));
        field.getCell(new Point(0,2)).addObject(firstRobot);
    }

    @Override
    protected void addMagicGrass(@NotNull Field field) {
        Sunflower battery = new Sunflower(DEFAULT_BATTERY_CHARGE,10);

        field.getCell(new Point(1, 2)).addObject(battery);
    }

}
