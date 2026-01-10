package app.material;

import app.Hit;
import cgg_tools.Color;
import cgg_tools.Vec3;

public class MirrorMaterial implements Material{
    
    private final Color tint;

    public MirrorMaterial(Color tint) {
        this.tint = tint;
    }

    public MirrorMaterial(){
        this(Color.white);
    }

    @Override
    public boolean does_ambient_lighting() {
        return false;
    }

    @Override
    public boolean does_direct_lighting() {
        return false;
    }

   @Override
    public boolean does_emission() {
        return false;
    }
    
    @Override
    public boolean does_reflection() {
        return true;
    }

    @Override
    public Vec3 generate_reflection_direction(Hit hit, Vec3 to_viewer) {
        // r = -v + 2(nv)n
        Vec3 normal = hit.normal();
        return Vec3.reflect(to_viewer, normal);
    }

    @Override
    public Color shade(Hit hit, Vec3 to_viewer, Vec3 to_light, Color incoming_light) {
        return Color.multiply(tint, incoming_light);
    }
}
