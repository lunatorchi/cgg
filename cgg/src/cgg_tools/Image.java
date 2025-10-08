package cgg_tools;

public interface Image {

    public void setPixel(int i, int j, Color color);

    public Color getPixel(int i, int j);

    public int width();

    public int height();
}
