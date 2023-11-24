package goats.model.field.cells;

import goats.model.field.Cell;
import goats.model.field.CellObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Box extends CellObject {


    public Box() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Box box = (Box) o;
        return Objects.equals(box,box);
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell cell) {
        if (cell instanceof ExitCell) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
