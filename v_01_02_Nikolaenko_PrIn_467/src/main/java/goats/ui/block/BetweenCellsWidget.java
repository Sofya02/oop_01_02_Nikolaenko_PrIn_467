package goats.ui.block;

import org.jetbrains.annotations.NotNull;
import goats.model.Orientation;

import javax.swing.*;
import java.awt.*;

/**
 * Виджет контейнера для виджетов между ячейками {@link BetweenCellsWidget}.
 */
public class BetweenCellsWidget extends JPanel {

    /**
     * Ориентация.
     */
    private final Orientation orientation;

    /**
     * Конструктор.
     * @param orientation ориентация.
     */
    public BetweenCellsWidget(@NotNull Orientation orientation) {
        super(new BorderLayout());
        this.orientation = orientation;
        setPreferredSize(getDimensionByOrientation());
        setBackground(Color.BLACK);
    }

    /**
     * Получить размеры виджета по ориентации {@link BetweenCellsWidget#orientation}
     * @return размеры.
     */
    private Dimension getDimensionByOrientation() {
        return (orientation == Orientation.VERTICAL) ? new Dimension(5, 120) : new Dimension(125, 5);
    }
}
