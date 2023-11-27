package goats.model.labirints;

import goats.model.field.cell_objects.magic_grass.FlowerPot;
import goats.model.field.cells.Box;
import org.jetbrains.annotations.NotNull;
import goats.model.*;
import goats.model.field.Field;
import goats.model.field.cell_objects.Goat;
import goats.model.field.cell_objects.magic_grass.Sunflower;

/**
 * Лабиринт маленького поля.
 */
public class SmallLabirint extends Labirint {

    /**
     * Высота поля.
     */
    private static final int FIELD_HEIGHT = 4;

    /**
     * Ширина поля.
     */
    private static final int FIELD_WIDTH = 6;

    /**
     * Стандартный заряд батарейки.
     */
    private static final int DEFAULT_BATTERY_CHARGE = 20;

    /**
     * Стандартный заряд батарейки.
     */
    private static final int DEFAULT_POWER = 5;

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
        return new Point(5,3);
    }

    @Override
    protected void addGoats(@NotNull Field field) {
        Goat firstGoat = new Goat(new Sunflower(10, 0));
        field.getCell(new Point(0,0)).addObject(firstGoat);
    }

    @Override
    protected void addMagicGrass(@NotNull Field field) {
        Sunflower sunflower = new Sunflower(DEFAULT_BATTERY_CHARGE, DEFAULT_POWER);
        FlowerPot flowerPot = new FlowerPot(10,3);

        field.getCell(new Point(1, 2)).addObject(sunflower);
        field.getCell(new Point(4, 2)).addObject(flowerPot);
    }

    @Override
    protected void addBox(@NotNull Field field){
        Box box = new Box();
        //Box box2 = new Box();
        field.getCell(new Point(2, 1)).addObject(box);
        //field.getCell(new Point(4, 1)).addObject(box2);
        //box.move(Direction.EAST);
    }

}
