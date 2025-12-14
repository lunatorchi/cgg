package app.shape;

import java.util.ArrayList;
import java.util.Arrays;

import app.Hit;
import app.Ray;
import cgg_tools.Mat4x4;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

public class GroupShape implements Shape {
    private ArrayList<Shape> shapes = new ArrayList<>();
    private final Mat4x4 transformMatrix;
    private final Mat4x4 inverseMatrix;
    private final Mat4x4 transposeInverseMatrix;

    /**
     * Creates group with given shapes
     * @param shapes variable amount of shapes to save
     */
    public GroupShape(Mat4x4 transformMatrix, Shape... shapes) {
        this.shapes = new ArrayList<>(Arrays.asList(shapes));
        this.transformMatrix = transformMatrix;

        // calculate inverse Matrix -> should only be done once, thats why we do it during the construction
        this.inverseMatrix = Mat4x4.invert(transformMatrix);

        // "Normalen werden daher mit der Transponierten der Inversen transformiert"
        // n'=(M^-1)^T * n
        this.transposeInverseMatrix = Mat4x4.transpose(this.inverseMatrix);
    }

    // adds shape to group
    public void add(Shape s) {
        shapes.add(s);
    }

    /**
     * finds closest intersection of shapes
     * @param ray to intersect
     * @return closest hit ot null if no shape was hit
     */
    @Override
    public Hit intersect(Ray ray) {
        /* transforms origin of ray from parent to local */
        Vec3 local_origin = Mat4x4.multiplyPoint(inverseMatrix, ray.origin());
        /*  transforms direction of ray */
        Vec3 local_direction = Vec3.normalize(Mat4x4.multiplyDirection(inverseMatrix, ray.direction()));

        /* creats new ray in local coordinates */
        Ray local_ray = new Ray(local_origin, local_direction, ray.tMin(), ray.tMax());

        Hit closest = null;
        for (Shape s : shapes) {
            Hit h = s.intersect(local_ray);
            if ( h != null && (closest == null || h.t() < closest.t())){
                closest = h;
            }
        }

        if (closest == null){
            return null;
        }

        /* transforms hit point back from local to parent */
        Vec3 local_parent = Mat4x4.multiplyPoint(transformMatrix, closest.point());

        /* transforms normal of vec back to parent system */
        Vec3 normal_parent = Mat4x4.multiplyDirection(transposeInverseMatrix, closest.normal());

        /* normalizes normal vec */
        normal_parent = Vec3.normalize(normal_parent);
        Vec2 uv = closest.uv();

        return new Hit(
            closest.t(),    // distance to hit point
            local_parent,   // hit point in parent system
            normal_parent,  // normal in parent system
            closest.material(), // material of hit shape
            uv
        );

    }
}
