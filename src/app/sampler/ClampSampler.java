package app.sampler;

import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

public record ClampSampler(Sampler sampler, Color borderColor)  implements Sampler{
    
    @Override
    public Color getColor(Vec2 uv) {
        double u = uv.u();
        double v = uv.v();

        // checks if u and v are outside bounds (0,1)
        if (u < 0.0 || u > 1.0 || v < 0.0 ||v > 1.0) {
            return borderColor; 
        }

        // returns getColor if uv is inside 0,1 
        return sampler.getColor(uv);
    }
}
