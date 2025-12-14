package app.shape;

import app.Hit;
import app.Ray;
import app.material.Material;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

public class DiscShape implements Shape {
    private final Vec3 center;
    private final double radius;
    private final Material material;

    /**
     * creates disc in x-z level
     * @param center point of disc
     * @param radius of disc
     * @param color of disc
     */
    public DiscShape(Vec3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    /**
     * intersection of ray with disc
     * @param ray to intersect with
     * @return hit if ray intersects with disc within its radius othwerwise null
     */
    @Override
    public Hit intersect(Ray ray) {
        double dy = ray.direction().y();
        if (dy == 0){
            return null; // ray parallel to level
        } 

        double t = (center.y() - ray.origin().y()) / dy;
        if (!ray.is_valid(t)){
            return null;
        } 

        Vec3 hitPoint = ray.point_at(t);
        double dx = hitPoint.x() - center.x();
        double dz = hitPoint.z() - center.z();
        if (dx*dx + dz*dz > radius*radius){
            return null; // outside of radius
        } 

        double u = (dx / radius + 1.0) / 2.0;
        double v = (dz / radius + 1.0) / 2.0;
        Vec2 uv = new Vec2(u, v);

        Vec3 normal = new Vec3(0, 1, 0);
        
        return new Hit(t, hitPoint, normal, material, uv);
    }
}
