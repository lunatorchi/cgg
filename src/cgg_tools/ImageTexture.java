// module "Computergrafik Grundlagen", authors Hartmut Schirmacher + Henrik Tramberend
// Berliner Hochschule für Technik
// contact hschirmacher@bht-berlin.de

package cgg_tools;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageTexture implements Sampler {

    private BufferedImage image;
    public final int width;
    public final int height;
    private final double componentScale;
    private final int components;

    /**
     * Constructs an ImageTexture from an image file.
     *
     * @param filename The path to the image file to load
     * @throws RuntimeException if the image cannot be read or is invalid
     */
    public ImageTexture(String filename, boolean gamma) {
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.err.println("Cannot read image from: " + filename);
            System.exit(1);
        }

        if (image == null) {
            System.err.println("Error reading image from: " + filename);
            System.exit(1);
        }

        width = image.getWidth();
        height = image.getHeight();
        components = image.getRaster().getNumBands();

        System.out.format(
                "texture: %s: %dx%d, components: %d\n",
                filename,
                width,
                height,
                components);

        switch (image.getSampleModel().getDataType()) {
            case DataBuffer.TYPE_BYTE:
                componentScale = 255;
                break;
            case DataBuffer.TYPE_USHORT:
                componentScale = 65535;
                break;
            default:
                componentScale = 1;
                break;
        }
        if (gamma){
            gammaCorrection();
        }
        
    }

    private void gammaCorrection() {
        double gamma = 2.2;

        // loop over all pixels
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                // read pixels
                double[] pixelBuffer = new double[components];
                image.getRaster().getPixel(i, j, pixelBuffer);
                
                // scale pixels to [0..1]
                double r = pixelBuffer[0] / componentScale;
                double g = pixelBuffer[1] / componentScale;
                double b = pixelBuffer[2] / componentScale;
                
                // apply gamma correction -> c^2.2
                r = Math.pow(r, gamma);
                g = Math.pow(g, gamma);
                b = Math.pow(b, gamma);
                
                // scale pixel back to [0.. componentScale]
                pixelBuffer[0] = r * componentScale;
                pixelBuffer[1] = g * componentScale;
                pixelBuffer[2] = b * componentScale;
                
                // wirte pixels back
                image.getRaster().setPixel(i, j, pixelBuffer);
            }
        }
    }

    public Color getColor(Vec2 at) {
        // if (u < 0 || u > 1 || v < 0 || v > 1)
        // return black;

        double u = at.u();
        double v = 1.0 - at.v();
        int x = (int) ((u - Math.floor(u)) * width);
        int y = (int) ((v - Math.floor(v)) * height);

        double[] pixelBuffer = new double[4];
        image.getRaster().getPixel(x, y, pixelBuffer);
        Color color = new Color(pixelBuffer[0], pixelBuffer[1], pixelBuffer[2]);
        return Color.divide(color, componentScale);
    }
}
