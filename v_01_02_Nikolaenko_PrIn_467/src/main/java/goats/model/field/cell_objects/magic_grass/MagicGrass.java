package goats.model.field.cell_objects.magic_grass;

import goats.model.field.CellObject;

/**
 * Волшебная трава.
 */
public abstract class MagicGrass extends CellObject {

    /**
     * Объем шагов.
     */
    protected int stepVolume;

    /**
     * Максимальный объем шагов.
     */
    protected int maxStepVolume;

    /**
     * Сила.
     */
    protected int power;

    /**
     * Максимальная сила.
     */
    protected int maxPower;

    /**
     * Конструтор.
     * @param stepVolume объем шагов. Должен быть > 0 и < максимального объема шагов.
     * @param maxStepVolume максмальный объем шагов. Должен быть > 0.
     * @param power сила. Должна быть > 0 и < максимальной силы.
     * @param maxPower максмальная сила. Должна быть > 0.
     * @throws IllegalArgumentException если объем шагов или максимальный объем шагов и сила и максимальная сила некорректны.
     */
    public MagicGrass(int stepVolume, int maxStepVolume, int power, int maxPower) {
        if(stepVolume < 0 || maxStepVolume < 0 || stepVolume > maxStepVolume) throw new IllegalArgumentException();
        if(power < 0 || maxPower < 0 || power > maxPower) throw new IllegalArgumentException();
        this.stepVolume = stepVolume;
        this.maxStepVolume = maxStepVolume;
        this.power = power;
        this.maxPower = maxPower;
    }

    /**
     * Получить объем шагов {@link MagicGrass#stepVolume}.
     * @return объем шагов.
     */
    public int getStepVolume() {
        return stepVolume;
    }

    /**
     * Получить максимальный объем шагов {@link MagicGrass#maxStepVolume}.
     * @return максимальный объем шагов.
     */
    public int getMaxStepVolume() {
        return maxStepVolume;
    }

    /**
     * Получить силу {@link MagicGrass#power}.
     * @return сила.
     */
    public int getPower() {
        return power;
    }

    /**
     * Получить максимальную силу{@link MagicGrass#maxPower}.
     * @return максимальная сила.
     */
    public int getMaxPower() {
        return maxPower;
    }

    /**
     * Отдать шаг.
     * @param stepAmount запрашиваемое кол-во шагов.
     * @return отданное кол-во шагов.
     */
    public int releaseSteps(int stepAmount) {
        if(stepAmount > stepVolume) return 0;
        stepVolume -= stepAmount;
        return stepAmount;
    }


    /**
     * Отдать сил.
     * @param powerAmount запрашиваемое кол-во силы.
     * @return отданное кол-во силы.
     */
    public int releasePower(int powerAmount) {
        if(powerAmount > stepVolume) return 0;
        stepVolume -= powerAmount;
        return powerAmount;
    }
}
