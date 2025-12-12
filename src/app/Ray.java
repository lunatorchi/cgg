package app;

import cgg_tools.Vec3;

/**
 * represents a ray
 * -> has an origin, direction and an interval along the ray (defined by tMin and tMax)
 */
public record Ray(Vec3 origin, Vec3 direction, double tMin, double tMax) {

    /**
     * returns 3d point where t is along the ray (distance)
     * @param t 
     * @return point at t
     */
    public Vec3 point_at(double t) {
        return Vec3.add(origin, Vec3.multiply(direction, t));
    }

    /**
     * checks if t is within the valid interval
     * @param t how far we currently are along the ray
     * @return true if valid, otherwise false
     */
    public boolean is_valid(double t) {
        return t >= tMin && t <= tMax;
    }
}
