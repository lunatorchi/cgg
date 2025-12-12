package app;

import cgg_tools.Mat4x4;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

/**
 * This Class implements a simple stationary Camera in (0,0,0) looking along the negative z-axis
 * 
 */
public class Camera {
    
    // view in degrees
    private final double alpha; 
    
    // image width
    private final int width;

    // image height
    private final int height;

    // view matrix for camera transformation
    private final Mat4x4 viewMatrix;

    /**
     * Creates a new Camera with view Matrix transformation
     * @param alpha field of view.
     * @param width of image
     * @param height of height
     * @param viewMatrix matrix for transformation
     */
    public Camera(double alpha, int width, int height, Mat4x4 viewMatrix) {
        this.alpha = alpha;
        this.width = width;
        this.height = height;
        this.viewMatrix = viewMatrix;
    }

    /**
     * Generates a Ray that starts from the cameras position 
     * and passes through the given position.
     * @param pos image position
     * @return a Ray that starts at the camera origin position
     */
    public Ray generate_ray(Vec2 pos) {
        
        double alphaRad = Math.toRadians(alpha); // converts camera opening angle (alpha) from degrees to radians.
        double ratio = (double) width / height; // aspect ratio of image -> needed so fields of view (horizontal and vertical) match image dimensions.

        double h = 2.0 * Math.tan(alphaRad / 2.0);
        double w = h * ratio;

        double x = (pos.x() - 0.5) * w;
        double y = (0.5 - pos.y()) * h;
        double z = -1.0;

        Vec3 origin = new Vec3(0, 0, 0);
        Vec3 direction = Vec3.normalize(new Vec3(x, y, z));

        // Transformed ray: x'(t) = V*x + t*V*d = x' + t*d'
        Vec3 tOrigin = Mat4x4.multiplyPoint(viewMatrix, origin);
        Vec3 tDirection = Vec3.normalize(Mat4x4.multiplyDirection(viewMatrix, direction));
        

        return new Ray(tOrigin, tDirection, 0.0, Double.POSITIVE_INFINITY);
    }
}