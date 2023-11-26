package goats.ui;

import org.jetbrains.annotations.NotNull;
import goats.model.*;
import goats.model.event.GoatActionEvent;
import goats.model.field.Cell;
import goats.model.event.FieldActionEvent;
import goats.model.event.FieldActionListener;
import goats.model.event.GoatActionListener;
import goats.model.field.Field;
import goats.model.field.cell_objects.Goat;
import goats.ui.block.BetweenCellsWidget;
import goats.ui.cell.*;

import javax.swing.*;
import java.util.List;

public class FieldWidget extends JPanel {

    private final Field field;
    private final WidgetFactory widgetFactory;

    public FieldWidget(@NotNull Field field, @NotNull  WidgetFactory widgetFactory) {
        this.field = field;
        this.widgetFactory = widgetFactory;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        fillField();
        subscribeOnRobots();
        field.addFieldActionListener(new FieldController());
    }

    private void fillField() {
        if(field.getHeight() > 0) {
            JPanel startRowWalls = createRowWalls(0, Direction.NORTH);
            add(startRowWalls);
        }

        for (int i = 0; i < field.getHeight(); ++i) {
            JPanel row = createRow(i);
            add(row);
            JPanel rowWalls = createRowWalls(i, Direction.SOUTH);
            add(rowWalls);
        }
    }

    private JPanel createRow(int rowIndex) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < field.getWidth(); ++i) {
            Point point = new Point(i, rowIndex);
            Cell cell = field.getCell(point);
            CellWidget cellWidget = widgetFactory.create(cell);

            if(i == 0)  {
                BetweenCellsWidget westCellWidget = new BetweenCellsWidget(Orientation.VERTICAL);
                row.add(westCellWidget);
            }

            row.add(cellWidget);
            BetweenCellsWidget eastCellWidget = new BetweenCellsWidget(Orientation.VERTICAL);
            row.add(eastCellWidget);
        }
        return row;
    }

    private JPanel createRowWalls(int rowIndex, Direction direction) {
        if(direction == Direction.EAST || direction == Direction.WEST) throw new IllegalArgumentException();
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < field.getWidth(); ++i) {
            Point point = new Point(i, rowIndex);
            Cell cell = field.getCell(point);
            BetweenCellsWidget southCellWidget = new BetweenCellsWidget(Orientation.HORIZONTAL);
            row.add(southCellWidget);
        }
        return row;
    }

    private void subscribeOnRobots() {
        List<Goat> goats = field.getGoatsOnField();
        for(Goat goat : goats) {
            goat.addGoatActionListener(new GoatController());

        }
    }

    private class GoatController implements GoatActionListener {

        @Override
        public void goatIsMoved(@NotNull GoatActionEvent event) {
            CellItemWidget robotWidget = widgetFactory.getWidget(event.getGoat());
            CellWidget from = widgetFactory.getWidget(event.getFromCell());
            CellWidget to = widgetFactory.getWidget(event.getToCell());
            from.removeItem(robotWidget);
            to.addItem(robotWidget);
            robotWidget.requestFocus();
        }

        @Override
        public void goatMovedBox(@NotNull GoatActionEvent event) {/**************/
            CellItemWidget boxWidget = widgetFactory.getWidget(event.getGoat());
            CellWidget from = widgetFactory.getWidget(event.getFromCell());
            CellWidget to = widgetFactory.getWidget(event.getToCell());
            from.removeItem(boxWidget);
            to.addItem(boxWidget);
            boxWidget.requestFocus();
        }

        @Override
        public void goatChangedMagicGrass(@NotNull GoatActionEvent event) {
            Goat goat = event.getGoat();
            CellWidget cellWidget = widgetFactory.getWidget(goat.getPosition());
            CellItemWidget batteryWidget = widgetFactory.getWidget(event.getMagicGrass());
            cellWidget.removeItem(batteryWidget);
            widgetFactory.remove(event.getMagicGrass());
        }



    }

    private class FieldController implements FieldActionListener {

        @Override
        public void goatIsTeleported(@NotNull FieldActionEvent event) {
            Goat goat = event.getGoat();
            Cell teleport = event.getTeleport();
            CellWidget teleportWidget = widgetFactory.getWidget(teleport);
            CellItemWidget robotWidget = widgetFactory.getWidget(goat);
            teleportWidget.removeItem(robotWidget);
        }
    }
}
