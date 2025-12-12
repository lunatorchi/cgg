package app;

import app.light.Light;
import app.shape.Shape;
import cgg_tools.Color;
import java.util.List;


public record Scene(Shape shapes, List<Light> lights, Color ambientLight) {
    
    /**
     * Creates a scene with single shape group ambient light and multiple lights
     * @param shapes 
     * @param lights 
     * @param ambientLight 
     */
    public Scene {
        
    }
}