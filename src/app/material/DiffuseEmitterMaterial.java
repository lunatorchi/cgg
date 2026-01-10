package app.material;

import app.Hit;
import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec3;

public class DiffuseEmitterMaterial implements Material{
    private final Sampler emissionSampler;
    
    public DiffuseEmitterMaterial(Sampler emissionSampler) {
        this.emissionSampler = emissionSampler;
    }
    
    public DiffuseEmitterMaterial(Color emissionColor) {
        this.emissionSampler = (uv) -> emissionColor;
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
        return true;
    }

    @Override
    public boolean does_reflection() {
        return false;
    }
    
    @Override
    public Color emission(Hit hit, Vec3 to_viewer) {
        return emissionSampler.getColor(hit.uv());
    }
}
