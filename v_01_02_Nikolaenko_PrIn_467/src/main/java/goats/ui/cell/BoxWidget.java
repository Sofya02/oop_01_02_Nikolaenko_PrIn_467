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
            image = ImageUtils.resizeImage(image, 100, 100);
            image = boxImage(image);
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
        return new Dimension(100, 100);
    }

    /**
     * Получить файл изображения ящика.
     * @return файл изображения ящика.
     */
    private File getImageFile() {

        return new File(ImageUtils.IMAGE_PATH + "Ящик.png");

    }
    private BufferedImage boxImage(BufferedImage boxImage) {
        BufferedImage img = new BufferedImage(boxImage.getWidth(), 120, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.drawImage(boxImage, 0, 0, null);

        return img;
    }
}
