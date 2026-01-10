package app.material;

import app.Hit;
import cgg_tools.Color;
import cgg_tools.RandomUtil;
import cgg_tools.Sampler;
import cgg_tools.Vec3;


public class DiffuseMaterial implements Material {
    
    private final Sampler albedo;
    
    public DiffuseMaterial(Sampler albedo) {
        this.albedo = albedo;
    }
    
    public DiffuseMaterial(Color albedo) {
        this.albedo = (uv) -> albedo;
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
        Vec3 normal = hit.normal();
        
        Vec3 randomVec;
        do {
            randomVec = new Vec3(
                RandomUtil.random() * 2 - 1,
                RandomUtil.random() * 2 - 1,
                RandomUtil.random() * 2 - 1
            );
        } while (Vec3.squaredLength(randomVec) > 1.0);
        
        Vec3 direction = Vec3.normalize(Vec3.add(normal, randomVec));
        
        if (Vec3.dot(direction, normal) < 0) {
            direction = Vec3.negate(direction);
        }
        
        return direction;
    }
    
    @Override
    public Color shade(Hit hit, Vec3 to_viewer, Vec3 to_light, Color incoming_light) {
        Color albedoColor = albedo.getColor(hit.uv());
        return Color.multiply(albedoColor, incoming_light);
    }
}