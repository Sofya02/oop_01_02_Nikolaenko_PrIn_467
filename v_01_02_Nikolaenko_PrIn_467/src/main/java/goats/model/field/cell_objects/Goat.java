package goats.model.field.cell_objects;

import org.jetbrains.annotations.NotNull;
import goats.model.Direction;
import goats.model.event.GoatActionEvent;
import goats.model.event.GoatActionListener;
import goats.model.field.Cell;
import goats.model.field.MobileCellObject;
import goats.model.field.cell_objects.magic_grass.MagicGrass;
import goats.model.field.cell_objects.magic_grass.Portable;
import goats.model.field.cells.CellWithMagicGrass;
import goats.model.field.cells.ExitCell;

import java.util.ArrayList;

/**
 * Коза.
 */
public class Goat extends MobileCellObject {

    /**
     * Количество шагов для перемещения.
     */
    private static final int AMOUNT_OF_STEP_FOR_MOVE = 1;

    /**
     * Внутренний источник питания козы.
     */
    private MagicGrass innerMagicGrass;

    /**
     * Констрктор.
     * @param innerMagicGrass внутренний источник питания.
     */
    public Goat(@NotNull MagicGrass innerMagicGrass) {
        this.innerMagicGrass = innerMagicGrass;
    }

    @Override
    public void move(@NotNull Direction direction) {
        Cell oldPosition = position;
        Cell newPosition = canMove(direction);
        if (newPosition != null) {
            boolean isSpendChargeSuccess = spendMagicStepVolume(AMOUNT_OF_STEP_FOR_MOVE, false);
            if (isSpendChargeSuccess) {
                fireGoatIsMoved(oldPosition, newPosition);
                position.takeObject(position.getMobileCellObject());
                newPosition.addObject(this);
            }
        }
    }

    @Override
    protected Cell canMove(@NotNull Direction direction) {
        Cell result = null;

        Cell neighborCell = position.getNeighborCell(direction);
        if (neighborCell != null && canLocateAtPosition(neighborCell)) {
            result = neighborCell;
        }

        return result;
    }

    @Override
    public boolean canLocateAtPosition(@NotNull Cell newPosition) {
        if ((newPosition instanceof ExitCell) && (((ExitCell) newPosition).getTeleportedGoats().contains(this))) return false;
        return newPosition.getMobileCellObject() == null;
    }

    /**
     * Заменить волшебную траву {@link Goat#innerMagicGrass}.
     */
    public void changeMagicGrass() {
        if ((position instanceof CellWithMagicGrass)
                && ((CellWithMagicGrass) position).getMagicGrass() != null
                && (((CellWithMagicGrass) position).getMagicGrass() instanceof Portable)) {
            innerMagicGrass = (MagicGrass) position.takeObject(((CellWithMagicGrass) position).getMagicGrass());
            fireGoatChangeMagicGrass(innerMagicGrass);
        }
    }

    /**
     * Установить волшебную траву {@link Goat#innerMagicGrass}
     * @param magicGrass волшебная трава.
     */
    public void setMagicGrass(@NotNull MagicGrass magicGrass) {
        this.innerMagicGrass = magicGrass;
    }

    /**
     * Получить объем шагов {@link MagicGrass#getStepVolume()}.
     * @return объем шагов.
     */
    public Integer getStepVolume() {
        return innerMagicGrass.getStepVolume();
    }

    /**
     * Получить максимальный объем шагов {@link MagicGrass#getMaxStepVolume()}.
     * @return максимальный объем шагов.
     */
    public Integer getMaxStepVolume() {
        return innerMagicGrass.getMaxStepVolume();
    }


    /**
     * Получить силу {@link MagicGrass#getPower()}.
     * @return сила.
     */
    public Integer getPower() {
        return innerMagicGrass.getPower();
    }

    /**
     * Получить максимальную силу {@link MagicGrass#getMaxPower()}.
     * @return максимальная сила.
     */
    public Integer getMaxPower() {
        return innerMagicGrass.getMaxPower();
    }

    /**
     * Получить волшебную траву {@link Goat#innerMagicGrass}.
     * @return волшебная трава.
     */
    public MagicGrass getMagicGrass() {
        return innerMagicGrass;
    }

    /**
     * Потратить заряд волшебной травы {@link Goat#innerMagicGrass}.
     * @param amountOfSteep запрашиваемый объем шагов.
     * @param ignoreShortage игнорировать ли нехватку объема шагов.
     * @return отдала ли волшебная запрашиваемый объем шагов.
     */
    private boolean spendMagicStepVolume(int amountOfSteep, boolean ignoreShortage) {
        boolean result = true;

        int released = innerMagicGrass.releaseSteps(amountOfSteep);
        if (released != amountOfSteep) {
            if (ignoreShortage) {
                innerMagicGrass.releaseSteps(innerMagicGrass.getStepVolume());
            } else {
                result = false;
            }
        }
        return result;
    }

    /**
     * Потратить силы волшебной травы {@link Goat#innerMagicGrass}.
     * @param amountOfPower запрашиваемое кол-во силы.
     * @param ignoreShortage игнорировать ли нехватку силы.
     * @return отдала ли волшебная запрашиваемое кол-во силы.
     */
    private boolean spendMagicGrassPower(int amountOfPower, boolean ignoreShortage) {
        boolean result = true;

        int released = innerMagicGrass.releasePower(amountOfPower);
        if (released != amountOfPower) {
            if (ignoreShortage) {
                innerMagicGrass.releasePower(innerMagicGrass.getStepVolume());
            } else {
                result = false;
            }
        }
        return result;
    }

    /**
     * Список слушателей, подписанных на события игры.
     */
    private final ArrayList<GoatActionListener> goatListListener = new ArrayList<>();

    /**
     * Добавить нвоого слушателя за событиями игры.
     * @param listener слушатель.
     */
    public void addGoatActionListener(GoatActionListener listener) {
        goatListListener.add(listener);
    }

    /**
     * Удалить слушателя за событиями игры.
     * @param listener слушатель.
     */
    public void removeGoatActionListener(GoatActionListener listener) {
        goatListListener.remove(listener);
    }

    /**
     * Оповестить сулшателей {@link Goat#goatListListener}, что коза переместилась.
     * @param oldPosition ячейка откуда переместилась коза.
     * @param newPosition ячейка куда переместилась коза.
     */
    private void fireGoatIsMoved(@NotNull Cell oldPosition, @NotNull Cell newPosition) {
        for (GoatActionListener listener : goatListListener) {
            GoatActionEvent event = new GoatActionEvent(listener);
            event.setGoat(this);
            event.setFromCell(oldPosition);
            event.setToCell(newPosition);
            listener.goatIsMoved(event);
        }
    }

    /**
     * Оповестить сулшателей {@link Goat#goatListListener}, что коза сменила волшебную траву.
     * @param changedMagicGrass новый волшебная трава.
     */
    private void fireGoatChangeMagicGrass(MagicGrass changedMagicGrass) {
        for (GoatActionListener listener : goatListListener) {
            GoatActionEvent event = new GoatActionEvent(listener);
            event.setGoat(this);
            event.setMagicGrass(changedMagicGrass);
            listener.goatChangedMagicGrass(event);
        }
    }


}
