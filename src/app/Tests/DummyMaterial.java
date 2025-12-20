package app.tests;

import app.Hit;
import app.material.Material;
import app.sampler.Raytracer;
import cgg_tools.Color;
import cgg_tools.Vec3;

public class DummyMaterial implements Material {
    
    private static final Color colorPink = new Color(0.8, 0.4, 0.6);
    
    @Override
    public Color ambient(Hit hit, Color ambientLight) {
        return Color.black;
    }
    
    @Override
    public Color shade(Hit hit, Vec3 to_viewer, Vec3 to_light, Color incoming_light) {
        return Raytracer.shade(hit.normal(), colorPink);
    }
}