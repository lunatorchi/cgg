package app.sampler;

import cgg_tools.Color;
import cgg_tools.Mat4x4;
import cgg_tools.Sampler;
import cgg_tools.Vec2;
import cgg_tools.Vec3;

public record TransformSampler(Sampler sampler, Mat4x4 transformation) implements Sampler {

    /**
     * @param uv position
     * @return color at transformed uv point from sampler
     */
    @Override
    public Color getColor(Vec2 uv) {
        Vec3 uvPoint = new Vec3(uv.u(), uv.v(), 0.0);

        Mat4x4 inverse = Mat4x4.invert(transformation);
        Vec3 transform = Mat4x4.multiplyPoint(inverse, uvPoint);

        return sampler.getColor(new Vec2(transform.x(), transform.y()));
    }
}