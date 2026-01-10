package app.sampler;

import app.Camera;
import app.Hit;
import app.Ray;
import app.Scene;
import app.light.Light;
import app.material.Material;
import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

public class Raytracer implements Sampler {

    private Camera camera;
    private Scene scene;
    private Color background;

    private static final double epsilon = 1e-4; // to avoid shadow acne 
    private static final int MAX_Depth = 5; // max recursive depth

    private static final boolean debug_Backside = true;
    private static final Color backside_color = Color.magenta;

    public Raytracer(Camera camera, Scene scene, Color background) {
        this.camera = camera;
        this.scene = scene;
        this.background = background;
    }

    public Color getColor(Vec2 samplePoint) {
        // create ray starting at camera that passes through samplePoint
        Ray ray = camera.generate_ray(samplePoint);
        return getColor(ray, MAX_Depth);
    }

    /**
     * Sets color in image to either backgroundcolor if ray hits nothing or the spheres color with shading if hit
     * @param samplePoint coordinates of pixel
     * @return color of the pixel
     */
    public Color getColor(Ray ray, int depth) {

        if (depth == 0) {
            return Color.black;
        }
        Hit hit = scene.shapes().intersect(ray);

        if(hit != null) {
            Vec3 to_viewer = Vec3.normalize(Vec3.negate(ray.direction()));
            double face_backside = Vec3.dot(hit.normal(),to_viewer);
            /* checks if we are viewing the surface from the back if yes than its pink 
            -> important for closed shapes */
            if(debug_Backside && face_backside < 0 ){
                return backside_color;
            }
            
            
            Material material = hit.material();

            Color result = Color.black;

            // Ambient
            if (material.does_ambient_lighting()) {
                result = Color.add(result, material.ambient(hit, scene.ambientLight()));
            }

            // Direct lighting
            if (material.does_direct_lighting()) {
                for (Light light : scene.lights()) {
                    if (visibleLight(hit, light)) {
                        Vec3 to_light = light.to_light(hit.point());
                        Color incoming_light = light.color_at(hit.point());
                        Color shade = material.shade(hit, to_viewer, to_light, incoming_light);
                        result = Color.add(result, shade);
                    }
                }
            }

            // Emission
            if (material.does_emission()) {
                Color emission = material.emission(hit, to_viewer);
                result = Color.add(result, emission);
            }

            // reflection
            if (material.does_reflection()) {
                // reflection direction
                Vec3 reflection_direction = material.generate_reflection_direction(hit, to_viewer);
                
                // second ray to avoid shadow acne
                Vec3 reflection_origin = Vec3.add(hit.point(), Vec3.multiply(epsilon, reflection_direction));
                Ray reflection_ray = new Ray(reflection_origin, reflection_direction, 0, Double.POSITIVE_INFINITY);
                
                // calculate color from reflection direction 
                Color incoming_light = getColor(reflection_ray, depth - 1);
                
                // shade() to calculate how much is reflected 
                Color reflected = material.shade(hit, to_viewer, reflection_direction, incoming_light);
                result = Color.add(result, reflected);
            }
            return result;
        }
        return background;
    }

    /**
     * Test if light is visible from hit point
     * @param hit point on surface
     * @param light being tested
     * @return true if ligth is visible otherwise false
     */
    private boolean visibleLight(Hit hit, Light light){
        Vec3 to_light = light.to_light(hit.point());
        double light_distance = light.distance(hit.point());
        Vec3 shadow_origin = Vec3.add(hit.point(), Vec3.multiply(epsilon, to_light));
        Ray shadow_ray = new Ray(shadow_origin, to_light, 0, light_distance);
        Hit shadow_hit = scene.shapes().intersect(shadow_ray);
        
        if (shadow_hit != null && shadow_hit.t() < light_distance) {
            return false;
        }
        
        return true;
    }

    /**
     * OLD -> still used by DUMMYMaterial
     * shading function that uses ambient and diffuse
     * @param normal normalized surface at hit
     * @param color of object
     * @return shaded color
     * this function was provided in the task description
     */
    public static Color shade(Vec3 normal, Color color) {
        Vec3 lightDir = Vec3.normalize(new Vec3(1, 1, 0.5));
        double cos_angle = Math.max(0, Vec3.dot(lightDir, normal));
        Color ambient = Color.multiply(0.1, color);
        Color diffuse = Color.multiply(0.9 * cos_angle, color);
        return Color.add(ambient, diffuse);
    }
}
