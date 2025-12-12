package app;

import java.util.ArrayList;
import java.util.List;

import cgg_tools.Color;
import cgg_tools.RandomUtil;
import cgg_tools.Vec2;

public class ColoredDiscs {
    private final List<DiscModel2D> discs;
    private final Color backgroundColor;

    /**
     * ColoredDiscs generates a defined number of discs with random colors in random places on the image.
     * @param numDiscs number of discs to be generated
     * @param minRadius the smallest radius of a disc
     * @param maxRadius the largest radius of a disc
     * @param backgroundColor background color behind the discs
     */
    public ColoredDiscs(int numDiscs, double minRadius, double maxRadius, Color backgroundColor){
        this.discs = new ArrayList<>();
        this.backgroundColor = backgroundColor;
        
        //generate random discs
        for ( int i = 0; i < numDiscs; i++){
            Vec2 center = new Vec2(RandomUtil.random(), RandomUtil.random()); // generates random coordinates for the center of a disc
            double radius = minRadius + RandomUtil.random() * (maxRadius - minRadius); //generates a random number between max and min radius
            Color color = RandomUtil.randomColor(); //random disc color

            discs.add(new DiscModel2D(center, radius, color)); //adds new disc to ArrayList
        }
    }

    /**
     * Returns color Of the current pixel position
     * @param pos current position
     * @return disc Color or backgroundColor depending on if the position is within or without a discs radius.
     */
    public Color getColor(Vec2 pos){
        DiscModel2D smallest = null;
        /**
         * Every discs radius that covers pos will be compared and the smallest will be set in variable smallest 
         * and then set to be in the foreground.
         */
        for (DiscModel2D disc : discs) {
            if (disc.coversPoint(pos)) {
                if (smallest == null || disc.cRadius() <smallest.cRadius()){
                    smallest = disc;
                }  
            }
        }
        if (smallest != null){
            return smallest.cColor();
        } else {
            return backgroundColor;
        }
        
    }
}
