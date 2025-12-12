package app.shape;

import app.Hit;
import app.Ray;
import app.material.Material;
import cgg_tools.Mat4x4;
import cgg_tools.Vec3;

public class CuboidShape implements Shape {

    private final GroupShape group;
    
    /**
     * Creates cuboid centered at origin with size 1x1x1
     * @param material Material  cube faces
     */
    public CuboidShape(Material material) {
        this(new Vec3(0, 0, 0), new Vec3(1, 1, 1), material);
    }
    
    /**
     * Creates a cuboid with center, size and material
     * @param center center of cube
     * @param size Size vector (width (x), height (y), depth(z))
     * @param material Material for all faces
     */
    public CuboidShape(Vec3 center, Vec3 size, Material material) {
        // creates rectangle at origin 0,0,0
        RectShape rectangle = new RectShape(new Vec3(0, 0, 0), 1, 1, material);
        
        // Top
        Mat4x4 topTransform = Mat4x4.move(0, 0.5, 0); // move up by 0.5 (y)
        GroupShape top = new GroupShape(topTransform, rectangle);
        
        // Bottom
        Mat4x4 bottomRotate = Mat4x4.rotate(1, 0, 0, 180); // rotate around x axis (180°)
        Mat4x4 bottomMove = Mat4x4.move(0, -0.5, 0); // move down by -0.5 (y)
        Mat4x4 bottomTransform = Mat4x4.multiply(bottomMove, bottomRotate); //combine
        GroupShape bottom = new GroupShape(bottomTransform, rectangle);
        
        // Left
        Mat4x4 leftRotate = Mat4x4.rotate(0, 0, 1, 90); // rotate around z axis (90°)
        Mat4x4 leftMove = Mat4x4.move(-0.5, 0, 0); // move left by -0.5 (x)
        Mat4x4 leftTransform = Mat4x4.multiply(leftMove, leftRotate); // combine
        GroupShape left = new GroupShape(leftTransform, rectangle);
        
        // Right
        Mat4x4 rightRotate = Mat4x4.rotate(0, 0, 1, -90); // rotate around z axis (-90°)
        Mat4x4 rightMove = Mat4x4.move(0.5, 0, 0); // move left by 0.5 (x)
        Mat4x4 rightTransform = Mat4x4.multiply(rightMove, rightRotate); // combine
        GroupShape right = new GroupShape(rightTransform, rectangle);
        
        // Back 
        Mat4x4 backRotate = Mat4x4.rotate(1, 0, 0, 90); // rotate around x axis (-90°)
        Mat4x4 backMove = Mat4x4.move(0, 0, 0.5); // move back by 0.5 (z)
        Mat4x4 backTransform = Mat4x4.multiply(backMove, backRotate); //combine
        GroupShape back = new GroupShape(backTransform, rectangle);
        
        // Front
        Mat4x4 frontRotate = Mat4x4.rotate(1, 0, 0, -90); // rotate around x axis (90°)
        Mat4x4 frontMove = Mat4x4.move(0, 0, -0.5); // move foward by -0.5 (z)
        Mat4x4 frontTransform = Mat4x4.multiply(frontMove, frontRotate); //combine
        GroupShape front = new GroupShape(frontTransform, rectangle);
        
        // transform -> Scale * move 
        Mat4x4 scale = Mat4x4.scale(size.x(), size.y(), size.z());
        Mat4x4 move = Mat4x4.move(center.x(), center.y(), center.z());
        Mat4x4 transform = Mat4x4.multiply(move, scale);
        
        // Create Groupshape with all sides of cube
        this.group = new GroupShape(
            transform,
            top, bottom, left, right, back, front
        );
    }
    
    @Override
    public Hit intersect(Ray ray) {
        return group.intersect(ray);
    }
    
}
