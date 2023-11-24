package goats.model.field.cell_objects.magic_grass;

import goats.model.field.Cell;
import goats.model.field.cells.CellWithMagicGrass;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FlowerPot extends MagicGrass implements Portable {

    /**
     * Максимальный объем шагов.
     */
    private static final int MAX_STEP_VOLUME = 10;

    private static final int MAX_POWER = 3;


    /**
     * Конструтор.
     * @param step_volume объем шагов.
     */
    public FlowerPot(int step_volume, int power) {
        super(step_volume, MAX_STEP_VOLUME, power, MAX_POWER );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowerPot flowerPot = (FlowerPot) o;
        return Objects.equals(stepVolume, flowerPot.stepVolume) && Objects.equals(power, flowerPot.power);
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell cell) {
        return position == null && (cell instanceof CellWithMagicGrass)
                && ((CellWithMagicGrass) cell).getMagicGrass() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepVolume);
    }

    @Override
    public String toString() {
        return "FlowerPot{" + "stepVolume=" + stepVolume + "power=" + power + '}';
    }
}
