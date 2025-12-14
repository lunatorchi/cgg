package app.shape;

import app.Hit;
import app.Ray;
import app.material.Material;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

public record RectShape(Vec3 anchor_point, double width, double depth, Material material) implements Shape {

    /**
     * intersection of ray with rectangle
     * @param ray to intersect with
     * @return hit if ray intersects with rectangle within its bounds othwerwise null
     */
    @Override
    public Hit intersect(Ray ray){
        double dy = ray.direction().y();
        if (dy == 0){
            return null; // ray parallel to level
        }

        double t = (anchor_point.y() - ray.origin().y()) / dy;
        if (!ray.is_valid(t)) {
            return null;
        }

        Vec3 hitPoint = ray.point_at(t);

        /* Checks if point is within rectangle  */
        double dx = hitPoint.x() - anchor_point.x();
        double dz = hitPoint.z() - anchor_point.z();
        
        double halfWidth = width / 2.0;
        double halfDepth = depth / 2.0;
        

        if (Math.abs(dx) > halfWidth || Math.abs(dz) > halfDepth) {
            return null; // outside of rectangle
        }

        double u = (dx + halfWidth) / width; // from 0 to 1 in x direction
        double v = 1.0 - (dz + halfDepth) / depth; // from 0 to 1 in z direction
        Vec2 uv = new Vec2(u, v);

        Vec3 normal = new Vec3(0, 1, 0);
        return new Hit(t, hitPoint, normal, material, uv);
    }
    
}
