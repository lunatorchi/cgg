package app.tests;

import app.Hit;
import app.Ray;
import app.material.Material;
import app.shape.SphereShape;
import cgg_tools.Vec3;

public class SphereTests {
    public static void sphereTests() {
        Material testMaterial = new DummyMaterial();
        SphereShape s1 = new SphereShape(new Vec3(0,0,-2), 1, testMaterial);

        // Test 1 ray hits the sphere
        Ray r1 = new Ray(new Vec3(0,0,0), new Vec3(0,0,-1), 0, Double.POSITIVE_INFINITY);
        Hit h1 = s1.intersect(r1);
        if(h1 != null && Math.abs(h1.t() - 1.0) < 1e-6) 
            System.out.println("Test 1 PASS");
        else 
            System.out.println("Test 1 FAIL");

        // Test 2 ray misses the sphere
        Ray r2 = new Ray(new Vec3(0,0,0), Vec3.normalize(new Vec3(0,1,-1)), 0, Double.POSITIVE_INFINITY);
        Hit h2 = s1.intersect(r2);
        System.out.println(h2 == null ? "Test 2 PASS" : "Test 2 FAIL");

        // Test 3 ray hits from below
        SphereShape s2 = new SphereShape(new Vec3(0,-1,-2), 1, testMaterial);
        Ray r3 = new Ray(new Vec3(0,0,0), Vec3.normalize(new Vec3(0,-1,-2)), 0, Double.POSITIVE_INFINITY);
        Hit h3 = s2.intersect(r3);
        System.out.println(h3 != null ? "Test 3 PASS" : "Test 3 FAIl");

        // Test 4 ray starts behind the sphere
        Ray r4 = new Ray(new Vec3(0,0,-4), new Vec3(0,0,-1), 0, Double.POSITIVE_INFINITY);
        Hit h4 = s1.intersect(r4);
        System.out.println(h4 == null ? "Test 4 PASS" : "Test 4 FAIL");

        // Test 5 ray starts infront of the sphere but tMax is limited instead of infinite
        SphereShape s3 = new SphereShape(new Vec3(0,0,-4), 1, testMaterial);
        Ray r5 = new Ray(new Vec3(0,0,0), new Vec3(0,0,-1), 0, 2); // tMax = 2
        Hit h5 = s3.intersect(r5);
        System.out.println(h5 == null ? "Test 5 PASS" : "Test 5 FAIL");
    }
}
