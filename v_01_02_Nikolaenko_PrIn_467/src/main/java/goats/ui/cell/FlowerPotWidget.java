package goats.ui.cell;

import goats.model.field.cell_objects.magic_grass.FlowerPot;
import goats.ui.utils.ImageUtils;

import java.awt.*;
import java.io.File;

public class FlowerPotWidget extends MagicGrassWidget{

    public FlowerPotWidget(FlowerPot flowerPot) {
        super(flowerPot);
    }

    @Override
    protected Dimension getDimension() {
        Dimension dimension = null;

        if(cellItemState == State.SMALL) {
            dimension = new Dimension(50, 66);
        } else if (cellItemState == State.DEFAULT) {
            dimension = new Dimension(120, 120);
        }

        return dimension;
    }

    @Override
    protected File getImageFile() {
        File file = null;
        if (cellItemState == State.SMALL) {
            file = new File(ImageUtils.IMAGE_PATH + "flower-pot.png");
        } else if (cellItemState == State.DEFAULT) {
            file = new File(ImageUtils.IMAGE_PATH + "flower-pot.png");
        }
        return file;
    }
}
