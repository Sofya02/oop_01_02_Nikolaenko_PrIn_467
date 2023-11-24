package goats.ui.cell;

import goats.model.field.cell_objects.magic_grass.MagicGrass;
import goats.ui.utils.GameWidgetsUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Виджет источника питания.
 * @see MagicGrass
 */
public abstract class MagicGrassWidget extends CellItemWidget {

    /**
     * Источник питания.
     */
    protected final MagicGrass magicGrass;

    /**
     * Конструтор.
     * @param magicGrass источник питания.
     */
    public MagicGrassWidget(MagicGrass magicGrass) {
        this.magicGrass = magicGrass;
        setToolTipText("Объем шагов: " + magicGrass.getStepVolume() + "/" + magicGrass.getMaxStepVolume());
    }

    @Override
    public CellWidget.Layer getLayer() {
        return CellWidget.Layer.TOP;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            image = magicGrassImageWithVolumeStepAndPowerText(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Получить изображение источника питания с наложенным текстом объема шагов и силы.
     * @param magicGrassImage изображение источник питания.
     * @return изображение источника питания с наложенным текстом объема шагов и силы.
     */
    private BufferedImage magicGrassImageWithVolumeStepAndPowerText(BufferedImage magicGrassImage) {
        BufferedImage img = new BufferedImage(magicGrassImage.getWidth(), 120, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.drawImage(magicGrassImage, 0, 0, null);

        if(cellItemState == State.DEFAULT) {
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.setColor(magicGrassVolumeStepTextColor());
            g.drawString(magicGrassVolumeStepText(), 10, 80);
            g.setColor(volumePowerTextColor());
            g.drawString(volumePowerText(), 16, 95);

        }

        return img;
    }


    /**
     * Получить текст объема шагов источника питания.
     * @return текст объема шагов источника питания.
     */
    private String magicGrassVolumeStepText() {
        return magicGrass.getStepVolume() + "/" + magicGrass.getMaxStepVolume();
    }

    /**
     * Получить цвет объема шагов заряда источника питания.
     * @return цвет объема шагов заряда источника питания.
     */
    private Color magicGrassVolumeStepTextColor() {
        return GameWidgetsUtils.volumwStepAndPowerTextColor(magicGrass.getStepVolume(), magicGrass.getMaxStepVolume());
    }

    /**
     * Получить текст силы источника питания.
     * @return текст силы источника питания.
     */
    private String volumePowerText() {
        return magicGrass.getPower() + "/" + magicGrass.getMaxPower();
    }

    /**
     * Получить цвет текста силы источника питания.
     * @return цвет текста силы источника питания.
     */
    private Color volumePowerTextColor() {
        return GameWidgetsUtils.volumwStepAndPowerTextColor(magicGrass.getPower(), magicGrass.getMaxPower());
    }

    /**
     * Получить файл изображения.
     * @return файл изображения.
     */
    protected abstract File getImageFile();
}
