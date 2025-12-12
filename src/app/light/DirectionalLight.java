package app.light;

import cgg_tools.Color;
import cgg_tools.Vec3;

/**
 * Implements a DirectionalLight with parallel rays
 */
public record DirectionalLight(Vec3 light_direction, Color light_color) implements Light {

    // normalized light direction
    public DirectionalLight {
        light_direction = Vec3.normalize(light_direction);
    }

    // direction from point to light
    @Override
    public Vec3 to_light(Vec3 receiver_position) {
        return Vec3.negate(light_direction);
    }
    
    // infinite distance
    @Override
    public double distance(Vec3 receiver_position) {
        return Double.MAX_VALUE;
    }
    
    // constant color for light
    @Override
    public Color color_at(Vec3 receiver_position) {
        return light_color;
    }
}