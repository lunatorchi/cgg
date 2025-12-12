package app.tests;

import app.light.DirectionalLight;
import cgg_tools.Color;
import cgg_tools.Vec3;

public class DirectionalLightTests {
    
    public static void directionalLightTests() {
        System.out.println("\nDirectionalLight Tests");
        
        DirectionalLight light = new DirectionalLight(
            new Vec3(-1, -1, -0.5),
            Color.white
        );
        
        // Test 1 to_light(0,0,0) 
        // should return  normalize(1,1,0.5)
        Vec3 expected = Vec3.normalize(new Vec3(1, 1, 0.5));
        Vec3 actual = light.to_light(new Vec3(0, 0, 0));
        
        boolean test1 = Vec3.almostEqual(expected, actual);
        System.out.println(test1 ? "PASS" : "FAIL");
        
        // Test 2 color_at(0,0,0)
        // should return white
        Color colorActual = light.color_at(new Vec3(0, 0, 0));
        boolean test2 = colorActual.equals(Color.white);
        System.out.println(test2 ? "PASS" : "FAIL");
    }
}