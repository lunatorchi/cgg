package app.sampler;

import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public record ClipSampler(Sampler sampler) implements Sampler{

    @Override
    public Color getColor(Vec2 uv) {
        var uv_clipped =new Vec2(
            Math.clamp(uv.u(), 0, 0.99),
            Math.clamp(uv.v(), 0, 0.99)
        );
        return sampler().getColor(uv_clipped);
    }
    
}
