package goats.model.labirints;

import org.jetbrains.annotations.NotNull;
import goats.model.field.Field;
import goats.model.Point;

/**
 * Лабиринт.
 */
public abstract class Labirint {

    /**
     * Построить поле.
     * @return поле.
     */
    public Field buildField() {

        Field field = new Field(fieldWidth(), fieldHeight(), exitPoint());

        addGoats(field);
        addMagicGrass(field);

        return field;
    }

    /**
     * Высота поля.
     * @return высота поля.
     */
    protected abstract int fieldHeight();

    /**
     * Ширина поля.
     * @return ширина поля.
     */
    protected abstract int fieldWidth();

    /**
     * Координаты ячейки выхода.
     * @return координаты ячейки выхода.
     */
    protected abstract Point exitPoint();

    /**
     * Добавить коз на поле.
     * @param field поле.
     */
    protected abstract void addGoats(@NotNull Field field);

    /**
     * Добавить волшебную траву на поле.
     * @param field поле.
     */
    protected abstract void addMagicGrass(@NotNull Field field);


}
