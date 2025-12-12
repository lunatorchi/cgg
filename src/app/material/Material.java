package app.material;

import app.Hit;
import cgg_tools.Color;
import cgg_tools.Vec3;

/**
 * Material defines hoe surfaces of objects interact with light (ambient and shading)
 */
public interface Material {
    
    Color ambient(Hit hit, Color ambient_light);
    
    Color shade(Hit hit, Vec3 to_viewer, Vec3 to_light, Color incoming_light);
}