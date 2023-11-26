package goats.ui;

import goats.model.field.cell_objects.magic_grass.FlowerPot;
import org.jetbrains.annotations.NotNull;
import goats.model.field.CellObject;
import goats.model.field.MobileCellObject;
import goats.model.field.cell_objects.magic_grass.MagicGrass;
import goats.model.field.cell_objects.magic_grass.Sunflower;
import goats.model.field.cells.CellWithMagicGrass;
import goats.model.field.cells.ExitCell;
import goats.model.field.cells.Box;
import goats.model.field.cell_objects.Goat;
import goats.model.field.Cell;
import goats.ui.cell.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetFactory {

    private final Map<Cell, CellWidget> cells = new HashMap<>();
    private final Map<CellObject, CellItemWidget> cellObjects = new HashMap<>();

    public CellWidget create(@NotNull Cell cell) {
        if(cells.containsKey(cell)) return cells.get(cell);

        CellWidget item = (cell instanceof ExitCell) ? new ExitWidget() : new CellWidget();

        MobileCellObject mobileCellObject = cell.getMobileCellObject();
        if(mobileCellObject != null) {
            CellItemWidget robotWidget = create(mobileCellObject);
            item.addItem(robotWidget);
        }

        if(cell instanceof CellWithMagicGrass) {
            MagicGrass magicGrass = ((CellWithMagicGrass) cell).getMagicGrass();
            if (magicGrass != null) {
                CellItemWidget powerSupplyWidget = create(magicGrass);
                item.addItem(powerSupplyWidget);
            }
        }

        cells.put(cell, item);
        return item;
    }

    public CellWidget getWidget(@NotNull Cell cell) {
        return cells.get(cell);
    }

    public void remove(@NotNull Cell cell) { cells.remove(cell); }

    public CellItemWidget create(@NotNull CellObject cellObject) {
        if(cellObjects.containsKey(cellObject)) return cellObjects.get(cellObject);

        CellItemWidget createdWidget = null;
        if(cellObject instanceof Goat) {
            createdWidget = new GoatWidget((Goat) cellObject);
        } else if(cellObject instanceof Sunflower) {
            createdWidget = new SunflowerWidget((Sunflower) cellObject);
        } else if(cellObject instanceof FlowerPot) {
            createdWidget = new FlowerPotWidget((FlowerPot) cellObject);
        }
        else if(cellObject instanceof Box ) {
            createdWidget = new BoxWidget((Box) cellObject);
        }
        else {
            throw new IllegalArgumentException();
        }

        cellObjects.put(cellObject, createdWidget);
        return createdWidget;
    }

    public CellItemWidget getWidget(@NotNull CellObject cellObject) {
        return cellObjects.get(cellObject);
    }

    public void remove(@NotNull CellObject cellObject) { cellObjects.remove(cellObject); }

}
