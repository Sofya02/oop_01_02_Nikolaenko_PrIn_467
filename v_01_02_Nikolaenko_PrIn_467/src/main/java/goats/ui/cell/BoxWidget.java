package goats.ui.cell;

import goats.model.field.cells.Box;
import goats.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Виджет ящика.
 * @see Box
 */
public class BoxWidget extends CellItemWidget{

    /**
     * Ящик.
     */
    private final  Box box;

    /**
     * Конструтор.
     * @param box ящик.
     */
    public BoxWidget(Box box) {
        super();
        this.box = box;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            image = ImageUtils.resizeImage(image, 60, 90);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public CellWidget.Layer getLayer() {
        return CellWidget.Layer.BOTTOM;
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60, 110);
    }

    /**
     * Получить файл изображения ящика.
     * @return файл изображения ящика.
     */
    private File getImageFile() {

        return new File(ImageUtils.IMAGE_PATH + "Ящик.png");

    }
}