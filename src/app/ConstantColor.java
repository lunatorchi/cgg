package app;
import cgg_tools.Color;
import cgg_tools.Sampler;
import cgg_tools.Vec2;

/**
 * sampler returns the same color for any uv coordinates
 */
public class ConstantColor implements Sampler{

    private final Color color;

    /**
     * creates constant color sampler
     * @param color to return for all coordinates
     */
    public ConstantColor(Color color){
        this.color = color;
    }
    
    @Override
    public Color getColor(Vec2 uv){
        return color;
    }
}
