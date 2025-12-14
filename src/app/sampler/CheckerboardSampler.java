package app.sampler;

import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public record CheckerboardSampler(int num_rows, Color c1, Color c2) implements Sampler {

    @Override
    public Color getColor(Vec2 uv) {
        int nu = (int)Math.floor((uv.u()*num_rows) % num_rows);
        int nv = (int)Math.floor((uv.v()*num_rows) % num_rows);
        return (nu+nv)%2==0? c1: c2;
    }
    
}
