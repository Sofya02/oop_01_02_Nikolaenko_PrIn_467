package java.goats.model.field.cell_objects.magic_grass;

import goats.model.field.cell_objects.magic_grass.MagicGrass;
import org.jetbrains.annotations.NotNull;
import goats.model.field.Cell;

public class NotPortablePowerSupply extends MagicGrass {
    public NotPortablePowerSupply(int stepVolume, int maxStepVolume, int power, int maxPower) {
        super( stepVolume, maxStepVolume,  power,  maxPower);
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell cell) {
        return true;
    }
}
