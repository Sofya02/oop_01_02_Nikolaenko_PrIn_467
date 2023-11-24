package goats.model.field.cell_objects.magic_grass;

import org.jetbrains.annotations.NotNull;
import goats.model.field.Cell;
import goats.model.field.cells.CellWithMagicGrass;

import java.util.Objects;

/**
 * Подсолнух
 */
public class Sunflower extends MagicGrass implements Portable {

    /**
     * Максимальный объем шагов.
     */
    private static final int MAX_STEP_VOLUME = 20;

    private static final int MAX_POWER = 5;


    /**
     * Конструтор.
     * @param step_volume объем шагов.
     */
    public Sunflower(int step_volume, int power) {
        super(step_volume, MAX_STEP_VOLUME, power, MAX_POWER );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sunflower sunflower = (Sunflower) o;
        return Objects.equals(stepVolume, sunflower.stepVolume) && Objects.equals(power, sunflower.power);
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
        return "Sunflower{" + "stepVolume=" + stepVolume + "power=" + power + '}';
    }
}
