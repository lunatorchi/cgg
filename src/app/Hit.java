package app;

import app.material.Material;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

/**
 * Represents the result of ray intersection
 *
 * @param t      ray pos where the hit occurs
 * @param point  the 3D pos where the intersection os
 * @param normal the normalized surface at intersection
 * @param color  color at the hit point
 */
public record Hit(double t, Vec3 point, Vec3 normal, Material material, Vec2 uv) {}
