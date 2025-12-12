package app.shape;

import app.Hit;
import app.Ray;
import app.material.Material;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

public class BackgroundShape implements Shape {
    private final Material material;

    /**
     * creates background with constant color
     * @param color
     */
    public BackgroundShape(Material material) {
        this.material = material;
    }

    /**
     * returns hit with background color
     * @param ray to intersect
     * @return hit 
     */
    @Override
    public Hit intersect(Ray ray) {
        double t = Double.POSITIVE_INFINITY;
        Vec3 hitPoint = ray.point_at(t);
        Vec3 normal = Vec3.normalize(Vec3.multiply(ray.direction(), -1)); 
        Vec2 uv = new Vec2(0, 0);
        return new Hit(t, hitPoint, normal, material, uv);
    }  
}
