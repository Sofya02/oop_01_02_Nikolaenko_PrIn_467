package goats.ui.cell;

import goats.model.Direction;
import goats.model.field.cell_objects.Goat;
import goats.ui.utils.GameWidgetsUtils;
import goats.ui.utils.ImageUtils;
import goats.ui.cell.CellWidget.Layer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Виджет козы.
 * @see Goat
 */
public class GoatWidget extends CellItemWidget {

    /**
     * Коза.
     */
    private final Goat goat;

    /**
     * Конструтор.
     * @param goat коза.
     */
    public GoatWidget(Goat goat) {
        super();
        this.goat = goat;
        setFocusable(true);
        addKeyListener(new KeyController());
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            image = ImageUtils.resizeImage(image, 60, 90);
            image = goatImageWithChargeText(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public Layer getLayer() {
        return CellWidget.Layer.BOTTOM;
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60, 110);
    }

    /**
     * Получить изобаржение с текстом заряда.
     * @param robotImage изображение козы.
     * @return изобаржение с текстом заряда.
     */
    private BufferedImage goatImageWithChargeText(BufferedImage robotImage) {
        BufferedImage img = new BufferedImage(robotImage.getWidth(), 110, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.drawImage(robotImage, 0, 0, null);

        if(cellItemState == State.DEFAULT) {
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.setColor(goatChargeTextColor());
            g.drawString(goatChargeText(), -1, 105);
            g.drawString(goatPowerText(), 35, 105);
        }

        return img;
    }

    /**
     * Получить текст заряда козы.
     * @return текст заряда козы.
     */
    private String goatChargeText() {
        return goat.getStepVolume() + "/" + goat.getMaxStepVolume();
    }
    /**
     * Получить текст силы козы.
     * @return текст силы козы.
     */
    private String goatPowerText() {
        return goat.getPower() + "/" + goat.getMaxPower();
    }

    /**
     * Получить цвет текста заряда.
     * @return цвет текста заряда.
     */
    private Color goatChargeTextColor() {
        return GameWidgetsUtils.volumwStepAndPowerTextColor(goat.getStepVolume(), goat.getMaxStepVolume());
    }

    /**
     * Получить цвет текста заряда.
     * @return цвет текста заряда.
     */
    private Color goatPowerTextColor() {
        return GameWidgetsUtils.volumwStepAndPowerTextColor(goat.getPower(), goat.getMaxPower());
    }

    /**
     * Получить файл изображения козы.
     * @return файл изображения козы.
     */
    private File getImageFile() {

        return new File(ImageUtils.IMAGE_PATH + "Коза.png");

    }

    /**
     * Внутренний класс-обработчик событий. Придает специфицеское поведение виджету.
     */
    private class KeyController implements KeyListener {

        @Override
        public void keyTyped(KeyEvent arg0) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int keyCode = ke.getKeyCode();

            moveAction(keyCode);
            changeBatteryAction(keyCode);

            repaint();
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
        }


        private void changeBatteryAction(int keyCode) {
            if(keyCode == KeyEvent.VK_G) {
                goat.changeMagicGrass();
            }
        }

        private void moveAction(int keyCode){
            Direction direction = directionByKeyCode(keyCode);
            System.out.println(" go to " + direction);
            if(direction != null) {
                goat.move(direction);
            }
        }

        private Direction directionByKeyCode(int keyCode) {
            Direction direction = null;
            switch (keyCode) {
                case KeyEvent.VK_W:
                    direction = Direction.NORTH;
                    break;
                case KeyEvent.VK_S:
                    direction = Direction.SOUTH;
                    break;
                case KeyEvent.VK_A:
                    direction = Direction.WEST;
                    break;
                case KeyEvent.VK_D:
                    direction = Direction.EAST;
                    break;
            }
            return direction;
        }
    }
}
