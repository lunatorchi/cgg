package app.sampler;

import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public class GridSampler implements Sampler {
    
    private final Sampler sampler;
    private final int samplesX;
    private final int samplesY;
    private final int imageWidth;
    private final int imageHeight;
    
    /**
     * Creates a GridSampler with square grid
     * @param sampler underlying sampler in our case raytracer
     * @param sampleNumber number of samples per axis
     * @param imageWidth width of the image in pixels
     * @param imageHeight height of the image in pixels
     */
    public GridSampler(Sampler sampler, int sampleNumber, int imageWidth, int imageHeight) {
        this(sampler, sampleNumber, sampleNumber, imageWidth, imageHeight);
    }
    
    /**
     * Creates a GridSampler with rectangular grid
     * @param sampler underlying sampler in our case raytracer
     * @param samplesX number of samples in x direction
     * @param samplesY number of samples in y direction
     * @param imageWidth width of the image in pixels
     * @param imageHeight height of the image in pixels
     */
    public GridSampler(Sampler sampler, int samplesX, int samplesY, int imageWidth, int imageHeight) {
        this.sampler = sampler;
        this.samplesX = samplesX;
        this.samplesY = samplesY;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }
    
    @Override
    public Color getColor(Vec2 samplePoint) {
        
        // to pixel coordinates
        double pixelX = samplePoint.x() * (imageWidth - 1);
        double pixelY = samplePoint.y() * (imageHeight - 1);
        
        int i = (int) pixelX;
        int j = (int) pixelY;
        
        // Accumulate colors from all sub-samples
        Color accumulated = Color.black;
        
        // Grid sampling formula: (i + (i' + 0.5)/n, j + (j' + 0.5)/m)
        for (int jPrime = 0; jPrime < samplesY; jPrime++) {
            for (int iPrime = 0; iPrime < samplesX; iPrime++) {
  
                double x = i + (iPrime + 0.5) / samplesX;
                double y = j + (jPrime + 0.5) / samplesY;
                
                double normalizedX = x / (imageWidth - 1);
                double normalizedY = y / (imageHeight - 1);
                
                Vec2 subSamplePoint = new Vec2(normalizedX, normalizedY);
                Color subColor = sampler.getColor(subSamplePoint);
                accumulated = Color.add(accumulated, subColor);
            }
        }
        
        int totalSamples = samplesX * samplesY;
        return Color.multiply(1.0 / totalSamples, accumulated);
    }
}