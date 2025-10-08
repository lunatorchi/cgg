
package app;

import cgg_tools.Color;
import cgg_tools.ImageWriter;

public class Image implements cgg_tools.Image {

    // TODO create a double array for storage of the RGB Pixels
    public Image(int width, int height) {
    }

    // TODO store the RGB values of the pixel (i,j) in the image
    @Override
    public void setPixel(int i, int j, Color color) {
    }

    // TODO use ImageWriter.writePNG to implement this
    public void writePNG(String name) {
        System.out.format("Implement function `app.Image.writePng` to actually write image `%s`\n", name);
    }

    // TODO return the RGB color of a given pixel (i,j)
    @Override
    public Color getPixel(int i, int j) {
        return Color.black;
    }

    // TODO return the image width 
    @Override
    public int width() {
        return -1;
    }

    // TODO return the image height 
    @Override
    public int height() {
        return -1;
    }

}
