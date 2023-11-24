package goats.ui.cell;

import goats.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Виджет ячейки выхода.
 * @see goats.model.field.cells.ExitCell
 */
public class ExitWidget extends CellWidget{
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            BufferedImage image = ImageIO.read(new File(ImageUtils.IMAGE_PATH + "exit.png"));
            image = ImageUtils.resizeImage(image, 118, 112);
            g.drawImage(image, 1, 1, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
