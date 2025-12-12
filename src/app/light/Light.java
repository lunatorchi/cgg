
package app.light;

import cgg_tools.Color;
import cgg_tools.Vec3;


/**
 * Represents  a Light source.
 */
public interface Light {
    
    // normalized direction from receiver towards light source
    Vec3 to_light(Vec3 receiver_position);
    
    // distance between light source and receiver, or infinity
    double distance(Vec3 receiver_position);
    
    // how much light does arrive at receiver
    Color color_at(Vec3 receiver_position);
}