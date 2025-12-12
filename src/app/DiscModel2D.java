package app;
import cgg_tools.Color;
import cgg_tools.Vec2;

public record DiscModel2D (Vec2 cCenter, double cRadius, Color cColor ) {
    /**
     * It makes sure that the current position is lesser or equal to the radius of the circles/discs.
     * @param pos current position.
     * @return true, if pos is within the radius.
     */
    boolean coversPoint(Vec2 pos){
        Vec2 diff = Vec2.subtract(cCenter, pos);
        double d = Vec2.length(diff);
        return d<= cRadius;
    }  
}
