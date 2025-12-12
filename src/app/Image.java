
package app;

import cgg_tools.Color;
import cgg_tools.ImageWriter;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public class Image implements cgg_tools.Image {
    private double[][][] pixel;
    private int width;
    private int height;

    /**
     * Creates a new RGB Image with given width and height.
     * @param width number of pixel columns
     * @param height number of pixel rows
     */
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        pixel = new double[height][width][3];
    }

    /**
     * Sets the color of pixel at (i,j).
     * @param i x-achse
     * @param j y-achse
     * @param color the color of the pixel at (i,j) 
     */
    @Override
    public void setPixel(int i, int j, Color color) {
        if (i >= 0 && i < pixel[0].length && j >= 0 && j < pixel.length) {
            pixel[j][i][0] = color.r(); // R
            pixel[j][i][1] = color.g(); // G
            pixel[j][i][2] = color.b(); // B
        } else {
            System.err.println("Attempted to set pixel outside image boundaries: (" + i + ", " + j + ")");
        }
    }

    /**
     * Saves image in as png with given name.
     * @param name file name under which it will be saved in cgg/images
     */
    public void writePNG(String name) {
        double[] data = new double[3 * pixel.length * pixel[0].length];
        int index = 0;
        for (int j = 0; j < pixel.length; j++) {
            for (int i = 0; i < pixel[j].length; i++) {
                data[index++] = pixel[j][i][0]; 
                data[index++] = pixel[j][i][1]; 
                data[index++] = pixel[j][i][2]; 
            }
        }
        ImageWriter.writePNG(name, data, pixel[0].length, pixel.length);
    }

    /**
     * iterates over images pixels and  sets the pixels color
     * @param sampler object that provides color of pixel in a specific pos
     */
    public void sample(Sampler sampler) {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                // normalized [0,1] coordinates
                double x = (double) i / (width - 1);
                double y = (double) j / (height - 1);

                Vec2 samplePoint = new Vec2(x, y);
                Color pixelColor = sampler.getColor(samplePoint);  // sampler tells us color
                setPixel(i, j, pixelColor);
            }
        }
    }

    // TODO return the RGB color of a given pixel (i,j)
    @Override
    public Color getPixel(int i, int j) {
        return Color.black;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

}
