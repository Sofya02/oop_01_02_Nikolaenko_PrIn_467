package goats.ui.cell;

import goats.model.field.cell_objects.magic_grass.Sunflower;
import goats.ui.utils.ImageUtils;

import java.awt.*;
import java.io.File;

/**
 * Виджет подсолнуха.
 * @see Sunflower
 */
public class SunflowerWidget extends MagicGrassWidget {

    public SunflowerWidget(Sunflower sunflower) {
        super(sunflower);
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
            file = new File(ImageUtils.IMAGE_PATH + "sunflower.png");
        } else if (cellItemState == State.DEFAULT) {
            file = new File(ImageUtils.IMAGE_PATH + "sunflower.png");
        }
        return file;
    }
}
