package app.material;

import app.ConstantColor;
import app.Hit;
import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

/**
 * Phong material implementation with ambient, diffuse, and specular components.
 */
public class PhongMaterial implements Material {
    
    private final Sampler k_ambient;   // ambient reflection coefficient
    private final Sampler k_diffuse;   // diffuse reflection coefficient
    private final Sampler k_specular;  // specular reflection coefficient
    private final double shininess;  // Phong exponent (gamma)
    
    /**
     * Creates a Phong material
     * @param k_ambient Reflexionskoeffizient (RGB)
     * @param k_diffuse  Reflexionskoeffizient (RGB)
     * @param k_specular Reflexionskoeffizient (RGB)
     * @param shininess Phong-Exponent
     */
    public PhongMaterial(Sampler k_ambient, Sampler k_diffuse, Sampler k_specular, double shininess) {
        this.k_ambient = k_ambient;
        this.k_diffuse = k_diffuse;
        this.k_specular = k_specular;
        this.shininess = shininess;
    }
    

    @Override
    public boolean does_ambient_lighting() {
        return true;
    }

    @Override
    public boolean does_direct_lighting() {
        return true;
    }

   @Override
    public boolean does_emission() {
        return false;
    }
    
    @Override
    public boolean does_reflection() {
        return false;
    }
    
    @Override
    public Color ambient(Hit hit, Color ambientLight) {
        // Hadamard product
        return Color.multiply(k_ambient.getColor(hit.uv()), ambientLight);
    }

    @Override
    public Color shade(Hit hit, Vec3 to_viewer, Vec3 to_light, Color incoming_light) {
        Vec3 normal = hit.normal();
        Vec2 uv = hit.uv();
        // DIFFUSE
        // Calculate n * s 
        double ns = Math.max(0, Vec3.dot(normal, to_light));
        
        // L_diffuse = (n*s)* k_diffuse ⊙ L
        Color diffuse = Color.multiply(Color.multiply(k_diffuse.getColor(uv), incoming_light), ns);
        
        // SPECULAR
        // Calculate reflection vector: r = -s + 2(n*s)n
        Vec3 reflection = Vec3.reflect(to_light, normal);
        
        // Calculate r * v 
        double rv = Math.max(0, Vec3.dot(reflection, to_viewer));
        
        // L_specular = (r * v)^γ * k_specular ⊙ L
        double spec_factor = Math.pow(rv, shininess);
        Color specular = Color.multiply(
            Color.multiply(k_specular.getColor(uv), incoming_light),
            spec_factor
        );
        
        return Color.add(diffuse, specular);
    }
}