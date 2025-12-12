package app.shape;

import app.Hit;
import app.Ray;
import app.material.Material;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

/**
 * Represents a sphere 
 */
public class SphereShape implements Shape {

    private final Vec3 center;
    private final double radius;
    private final Material material;

    public SphereShape(Vec3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    /**
     * Checks where and if ray hits sphere 
     *
     * @param ray the ray to test
     * @return Hit object if intersection is valid, otherwise null
     */
    public Hit intersect(Ray ray) {

        // Vector from ray origin to sphere center -> oc (origin center)
        Vec3 oc = Vec3.subtract(ray.origin(), center);
        // parts of a*t² + b*t + c = 0 
        double a = Vec3.dot(ray.direction(), ray.direction()); // a = d*d -> d is direction
        double b = 2.0 * Vec3.dot(oc, ray.direction()); // b *(oc*d)
        double c = Vec3.dot(oc, oc) - radius * radius; // c = oc * oc - (r*r)

        // b*b - 4*a*c
        double d = b * b - 4 * a * c;

        // d<0 means there is no solution -> no hit
        if (d < 0) {
            return null; 
        }

        double sqrtSphere = Math.sqrt(d);
        // possible intersection hits -> t is the distance
        double t1 = (-b - sqrtSphere) / (2.0 * a); 
        double t2 = (-b + sqrtSphere) / (2.0 * a);

        // chooses the smallest valid t within [tMin, tMax]
        double t = Double.POSITIVE_INFINITY;

        if (ray.is_valid(t1)){
            t = t1;
        } else if (ray.is_valid(t2)){
            t = t2;
        } else {
            return null; // both intersections invalid
        }
        // intersection point
        Vec3 hitPoint = ray.point_at(t);

        // surface normalized
        Vec3 normal = Vec3.normalize(Vec3.subtract(hitPoint, center));
        Vec2 uv = new Vec2(0, 0);

        // return hit
        return new Hit(t, hitPoint, normal, material, uv);
    }
}
