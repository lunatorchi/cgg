package app.shape;

import app.Hit;
import app.Ray;

// interface for all shapes that ray can trace
public interface Shape {
    /**
     * intersection of ray with the shape
     * @param ray to intersect with
     * @return hit info if intersection exists or null
     */
    Hit intersect(Ray ray);
}
