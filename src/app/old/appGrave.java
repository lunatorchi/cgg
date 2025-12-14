/* Here Is my old main with all the different scenes as it got to big */

// module "Computergrafik Grundlagen", authors Hartmut Schirmacher + Henrik Tramberend
// Berliner Hochschule für Technik
// contact hschirmacher@bht-berlin.de

package app.old;

import java.util.List;

import app.Camera;
import app.Image;
import app.Raytracer;
import app.Scene;
import app.light.DirectionalLight;
import app.light.Light;
import app.material.Material;
import app.material.PhongMaterial;
import app.shape.BackgroundShape;
import app.shape.CuboidShape;
import app.shape.DiscShape;
import app.shape.GroupShape;
import app.shape.SphereShape;
import app.tests.DirectionalLightTests;
import app.tests.SphereTests;
import cgg_tools.Color;
import cgg_tools.Mat4x4;
import cgg_tools.Vec3;


public class appGrave {

  public static Color multiply(Color c, double factor) {
    return new Color(c.r() * factor, c.g() * factor, c.b() * factor);
}

  public static void main(String[] args) {
    int width = 1280;
    int height = 720;
    double alpha = 50.0;
    Color background = new Color(1.0, 0.85, 0.88);
    var image = new Image(width, height);

    // Transform Matrix
    //Mat4x4 rotY = Mat4x4.rotate(0, 1, 0, 90);                    // turn left 30°
    Mat4x4 rotX = Mat4x4.rotate(1, 0, 0, -15);          // tilt down 45°
    Mat4x4 move = Mat4x4.move(0, 5, 10);                   // move slightly up
    Mat4x4 view = Mat4x4.multiply(move, rotX);

    // Creates a Camera now with view
    Camera cam = new Camera(alpha, width, height, view);

    
    // MATERIAL
    // SPHERES AND DISCS
    Material pink = new PhongMaterial(
      new Color(0.2, 0.08, 0.12),
      new Color(1.0, 0.4, 0.6),
      new Color(0.3, 0.3, 0.3),
      20.0
    );

    Material blue = new PhongMaterial(
      new Color(0.1, 0.14, 0.2),
      new Color(0.5, 0.7, 1.0),
      new Color(0.5, 0.5, 0.5),
      60.0
    );
    
    Material yellow = new PhongMaterial(
      new Color(0.2, 0.17, 0.08),
      new Color(1.0, 0.85, 0.4),
      new Color(0.8, 0.8, 0.8),
      200.0
    );

    Material magenta = new PhongMaterial(
      new Color(0.18, 0.06, 0.12),
      new Color(0.9, 0.3, 0.6),
      new Color(0.4, 0.4, 0.4),
      60.0
    );

    Material darkBlue = new PhongMaterial(
      new Color(0.08, 0.12, 0.19),
      new Color(0.4, 0.6, 0.95),
      new Color(0.5, 0.5, 0.5),
      40.0
    );
    
    Material purple = new PhongMaterial(
      new Color(0.15, 0.09, 0.19),
      new Color(0.75, 0.45, 0.95),
      new Color(0.6, 0.6, 0.6),
      80.0
    );
    
    Material orange = new PhongMaterial(
      new Color(0.2, 0.14, 0.06),
      new Color(1.0, 0.7, 0.3),
      new Color(0.2, 0.2, 0.2),
      15.0
    );

    Material yellowPlanet = new PhongMaterial(
      new Color(0.2, 0.18, 0.1),
      new Color(1.0, 0.9, 0.5),
      new Color(0.6, 0.6, 0.6),
      50.0
    );

    Material yellowPlanetRing = new PhongMaterial(
      new Color(0.2, 0.16, 0.08),
      new Color(1.0, 0.85, 0.4),
      new Color(0.4, 0.4, 0.4),
      100.0
    );
    
    // FLOOR
    Material floorBlue = new PhongMaterial(
      new Color(0.016, 0.01, 0.04),
      new Color(0.08, 0.05, 0.2),
      new Color(0.05, 0.05, 0.05),
      5.0
    );
    
    Material floorViolet = new PhongMaterial(
      new Color(0.04, 0.02, 0.07),
      new Color(0.2, 0.1, 0.35),
      new Color(0.1, 0.1, 0.1),
      10.0
    );
    
    Material floorPurple = new PhongMaterial(
      new Color(0.1, 0.04, 0.1),
      new Color(0.5, 0.2, 0.5),
      new Color(0.15, 0.15, 0.15),
      15.0
    );
    
    Material floorPink = new PhongMaterial(
      new Color(0.17, 0.07, 0.11),
      new Color(0.85, 0.35, 0.55),
      new Color(0.2, 0.2, 0.2),
      20.0
    );
    
    Material floorOrange = new PhongMaterial(
      new Color(0.2, 0.12, 0.1),
      new Color(1.0, 0.6, 0.5),
      new Color(0.25, 0.25, 0.25),
      25.0
    );
    
    Material floorYellow = new PhongMaterial(
      new Color(0.2, 0.17, 0.12),
      new Color(1.0, 0.85, 0.6),
      new Color(0.3, 0.3, 0.3),
      100.0
    );

    /* Cube Material */

    Material cubeBlue = new PhongMaterial(
      new Color(0.1, 0.14, 0.3),
      new Color(0.5, 0.7, 1.0),
      new Color(0.3, 0.3, 0.3),
      40.0
    );

    Material cubePurple = new PhongMaterial(
      new Color(0.12, 0.08, 0.16),
      new Color(0.75, 0.45, 0.95),
      new Color(0.4, 0.4, 0.4),
      60.0
    );

    Material cubePink = new PhongMaterial(
      new Color(0.2, 0.08, 0.12),
      new Color(1.0, 0.4, 0.6),
      new Color(0.3, 0.3, 0.3),
      20.0
    );

    Material cubeRed = new PhongMaterial(
      new Color(0.12, 0.02, 0.02),
      new Color(1.0, 0.3, 0.3),
      new Color(0.4, 0.4, 0.4),
      50.0
    );

    Material greenCube = new PhongMaterial(
      new Color(0.05, 0.1, 0.05),
      new Color(0.2, 0.9, 0.2),
      new Color(0.3, 0.3, 0.3),
      40.0
    );

    // BACKGROUND
    Material backgroundMaterial = new PhongMaterial(
      new Color(0.2, 0.08, 0.12),
      background,
      new Color(0.3, 0.3, 0.3),
      20.0
    );

    /* Transform Blue Sphere */
    SphereShape blueSphereLocal = new SphereShape(new Vec3(0, 0, 0), 0.8, blue);
    
    // -> stretch it 
    Mat4x4 scaleMatrixBlue = Mat4x4.scale(0.6, 2.0, 0.6);  
    
    // Move to original position
    Mat4x4 moveToPositionBlue = Mat4x4.move(0.2, 0.0, -3);
    
    //  combine scale with move
    Mat4x4 blueSphereTransformBlue = Mat4x4.multiply(moveToPositionBlue, scaleMatrixBlue);
    
    GroupShape transformedBlueSphere = new GroupShape(blueSphereTransformBlue, blueSphereLocal);

    /* Transform Purple */
    SphereShape purpleSphereLocal = new SphereShape(new Vec3(0,0,0), 0.6, purple);

    // -> stretch it 
    Mat4x4 scaleMatrixPurple = Mat4x4.scale(2.0, 1.0, 1.0);  
    
    // Move to original position
    Mat4x4 moveToPositionPurple = Mat4x4.move(0.8, 0.8, -3.2);
    
    //  combine scale with move
    Mat4x4 purpleSphereTransform = Mat4x4.multiply(moveToPositionPurple, scaleMatrixPurple);
    
    GroupShape transformedPurpleSphere = new GroupShape(purpleSphereTransform, purpleSphereLocal);

    // Scene with groupshape Transformation
     GroupShape shapes = new GroupShape(
      Mat4x4.identity(),

      new SphereShape(new Vec3(-1.2, 0.0, -3.2), 0.7, pink), // pink
      transformedBlueSphere, 
      //new SphereShape(new Vec3(0.2, 0.0, -3), 0.8, blue), // lighter blue
      new SphereShape(new Vec3(1.8, 0.1, -3.4), 0.3, yellow), // yellow
      new SphereShape(new Vec3(1.9, 0.0, -3.1), 0.1, magenta ), // magenta 
      new SphereShape(new Vec3(0.3, -0.1, -2.7), 0.6, darkBlue ), // darker blue
      transformedPurpleSphere,
      //new SphereShape(new Vec3(0.8, 0.8, -3.2), 0.6, purple), // purple
      new SphereShape(new Vec3(-2.7, 1.2, -4.0), 0.25, orange), // orange

      new BackgroundShape(backgroundMaterial), // backgroundd

      // "planet"
      new SphereShape(new Vec3(-2.5, 1.5, -7.0), 1.0, yellowPlanet), // big yellow sphere
      new DiscShape(new Vec3(-2.5, 1.5, -7.1), 1.3, yellowPlanetRing),  // "ring around big yellow sphere
      
      // Floor sunset style
      new DiscShape(new Vec3(0, -1.0, 0), 100.0, floorBlue), // dark blue
      new DiscShape(new Vec3(0, -0.95, 0), 80.0, floorViolet), // violet
      new DiscShape(new Vec3(0, -0.90, 0), 60.0, floorPurple), // purple
      new DiscShape(new Vec3(0, -0.85, 0), 40.0, floorPink), // pink
      new DiscShape(new Vec3(0, -0.80, 0), 20.0, floorOrange), // orange
      new DiscShape(new Vec3(0, -0.75, 0), 10.0, floorYellow) // yellow
    ); 

    /* Cube Scene */
    CuboidShape cube1 = new CuboidShape(new Vec3(0,0,0), new Vec3(1.3, 1.3, 2.3), cubePurple);
    CuboidShape cube2 = new CuboidShape(new Vec3(0,0,0), new Vec3(1.2, 1.2, 1.2), cubeBlue);
    CuboidShape cube3 = new CuboidShape(new Vec3(0,0,0), new Vec3(2.0, 1.0, 1.1), cubePink);
    CuboidShape cube4 = new CuboidShape(new Vec3(0,0,0), new Vec3(1.0, 1.0, 1.0), cubeRed);
    CuboidShape cube5 = new CuboidShape(new Vec3(0, 0, 0), new Vec3(2, 2.5, 2),greenCube);


    // Transform
    GroupShape transformedCube1 = new GroupShape(
      Mat4x4.multiply(Mat4x4.move(-4.0, 2.0, 0.0),
      Mat4x4.rotate(1,0,0, 30)),
      cube1
    );

    GroupShape transformedCube2 = new GroupShape(
      Mat4x4.multiply(Mat4x4.move(-1.5, 2.0, 0.0),
      Mat4x4.rotate(0,0,1, 50)),
      cube2
    );

    GroupShape transformedCube3 = new GroupShape(
      Mat4x4.multiply(Mat4x4.move(2.0, 2.0, 1.0),
      Mat4x4.rotate(0,1,0, 30)),
      cube3
    );

    GroupShape transformedCube4 = new GroupShape(
      Mat4x4.multiply(Mat4x4.move(4.0, 3.0, 0.0),
      Mat4x4.rotate(0,1,1, 35)),
      cube4
    );

    GroupShape transformedCube5 = new GroupShape(
      Mat4x4.multiply(Mat4x4.move(1.0, 0.5, -7.0),
      Mat4x4.rotate(0,1,1, 35)),
      cube5
    );

    GroupShape shapesCube = new GroupShape(
      Mat4x4.identity(),
      // new
      transformedCube1,
      transformedCube2,
      transformedCube3,
      transformedCube4,
      transformedCube5,

      // background
      new BackgroundShape(backgroundMaterial),

      // floor 
      new DiscShape(new Vec3(0, -1.0, 0), 100.0, floorBlue),
      new DiscShape(new Vec3(0, -0.95, 0), 80.0, floorViolet),
      new DiscShape(new Vec3(0, -0.90, 0), 60.0, floorPurple),
      new DiscShape(new Vec3(0, -0.85, 0), 40.0, floorPink),
      new DiscShape(new Vec3(0, -0.80, 0), 20.0, floorOrange),
      new DiscShape(new Vec3(0, -0.75, 0), 10.0, floorYellow)
    );
    
    // LIGHTS
    Light light1 = new DirectionalLight(
      new Vec3(-1, -1, -0.5),
      new Color(0.5, 0.5, 0.5)
    );
    
    Light light2 = new DirectionalLight(
      new Vec3(1, -1, -0.5),
      new Color(0.5, 0.5, 0.5)
    );
    
    // Color ambientLight = new Color(0.1, 0.1, 0.15);
    Color ambientLight = new Color(0.2, 0.2, 0.2);
    // Color ambientLight = new Color(0.08, 0.1, 0.2);
    // Color ambientLight = new Color(0.15, 0.12, 0.08);
    // Color ambientLight = new Color(0.05, 0.05, 0.05);
    // Color ambientLight = new Color(1, 1, 1);
    // Color ambientLight = new Color(0, 0, 0);

    Scene scene = new Scene(shapesCube, List.of(light1, light2), ambientLight);
    Raytracer raytracer = new Raytracer(cam, scene, background);

    
    
    image.sample(raytracer);
    image.writePNG("a05-cuboids");

    // runs our test method
    SphereTests.sphereTests();
    DirectionalLightTests.directionalLightTests();
  }
}
 */

/* CYLINDER SCENE

// MATERIAL
    // SPHERES AND DISCS
    Material pink = new PhongMaterial(
      new Color(0.2, 0.08, 0.12),
      new Color(1.0, 0.4, 0.6),
      new Color(0.3, 0.3, 0.3),
      20.0
    );

    Material blue = new PhongMaterial(
      new Color(0.1, 0.14, 0.2),
      new Color(0.5, 0.7, 1.0),
      new Color(0.5, 0.5, 0.5),
      60.0
    );
    
    Material yellow = new PhongMaterial(
      new Color(0.2, 0.17, 0.08),
      new Color(1.0, 0.85, 0.4),
      new Color(0.8, 0.8, 0.8),
      200.0
    );

    Material magenta = new PhongMaterial(
      new Color(0.18, 0.06, 0.12),
      new Color(0.9, 0.3, 0.6),
      new Color(0.4, 0.4, 0.4),
      60.0
    );

    Material darkBlue = new PhongMaterial(
      new Color(0.08, 0.12, 0.19),
      new Color(0.4, 0.6, 0.95),
      new Color(0.5, 0.5, 0.5),
      40.0
    );
    
    Material purple = new PhongMaterial(
      new Color(0.15, 0.09, 0.19),
      new Color(0.75, 0.45, 0.95),
      new Color(0.6, 0.6, 0.6),
      80.0
    );
    
    Material orange = new PhongMaterial(
      new Color(0.2, 0.14, 0.06),
      new Color(1.0, 0.7, 0.3),
      new Color(0.2, 0.2, 0.2),
      15.0
    );

    Material yellowPlanet = new PhongMaterial(
      new Color(0.2, 0.18, 0.1),
      new Color(1.0, 0.9, 0.5),
      new Color(0.6, 0.6, 0.6),
      50.0
    );

    Material yellowPlanetRing = new PhongMaterial(
      new Color(0.2, 0.16, 0.08),
      new Color(1.0, 0.85, 0.4),
      new Color(0.4, 0.4, 0.4),
      100.0
    );
    
    // FLOOR
    Material floorBlue = new PhongMaterial(
      new Color(0.016, 0.01, 0.04),
      new Color(0.08, 0.05, 0.2),
      new Color(0.05, 0.05, 0.05),
      5.0
    );
    
    Material floorViolet = new PhongMaterial(
      new Color(0.04, 0.02, 0.07),
      new Color(0.2, 0.1, 0.35),
      new Color(0.1, 0.1, 0.1),
      10.0
    );
    
    Material floorPurple = new PhongMaterial(
      new Color(0.1, 0.04, 0.1),
      new Color(0.5, 0.2, 0.5),
      new Color(0.15, 0.15, 0.15),
      15.0
    );
    
    Material floorPink = new PhongMaterial(
      new Color(0.17, 0.07, 0.11),
      new Color(0.85, 0.35, 0.55),
      new Color(0.2, 0.2, 0.2),
      20.0
    );
    
    Material floorOrange = new PhongMaterial(
      new Color(0.2, 0.12, 0.1),
      new Color(1.0, 0.6, 0.5),
      new Color(0.25, 0.25, 0.25),
      25.0
    );
    
    Material floorYellow = new PhongMaterial(
      new Color(0.2, 0.17, 0.12),
      new Color(1.0, 0.85, 0.6),
      new Color(0.3, 0.3, 0.3),
      100.0
    );

    // BACKGROUND
    Material backgroundMaterial = new PhongMaterial(
      new Color(0.2, 0.08, 0.12),
      background,
      new Color(0.3, 0.3, 0.3),
      20.0
    );

    Material capMaterial = new PhongMaterial(
      new Color(0.15, 0.15, 0.15),
      new Color(0.7, 0.7, 0.7),
      new Color(0.5, 0.5, 0.5),
      80.0
    );

    Material green = new PhongMaterial(
      new Color(0.05, 0.1, 0.05),
      new Color(0.2, 0.9, 0.2),
      new Color(0.3, 0.3, 0.3),
      40.0
    );
    // Create open Cylinder
    OpenCylinderShape openCylinderBlue = new OpenCylinderShape(2.0, -0.5, 1.0, blue);
    OpenCylinderShape openCylinderPink = new OpenCylinderShape(0.3, -0.8, 2.0, pink);

    // Create Closed Cylinders
    ClosedCylinderShape closedCylinderGreen = new ClosedCylinderShape(1.4, -1.0, 1.0, green, green);
    ClosedCylinderShape closedCylinderOrange = new ClosedCylinderShape(0.9, -0.5, 0.5, orange, orange);
    ClosedCylinderShape closedCylinderPurple = new ClosedCylinderShape(0.5, -1.5, 1.5, purple, purple);

    // Transform Open Cylinder Blue
    GroupShape transformedOpenCylinderBlue = new GroupShape(
      Mat4x4.multiply(
        Mat4x4.move(-6.0, 1.81, -5.0),
        Mat4x4.rotate(1, 0, 1,90),
        Mat4x4.scale(0.8, 2.0, 0.8)
      ),
      openCylinderBlue
    );

    // Transform Open Cylinder Pink
    GroupShape transformedOpenCylinderPink = new GroupShape(
      Mat4x4.multiply(
        Mat4x4.move(0.0, 4.5, -5.0),
        Mat4x4.multiply(
          Mat4x4.rotate(1, 0, 0, 90),
          Mat4x4.scale(1.0, 4.5, 1.0)
        )
      ),
      openCylinderPink
    );

    // Transform Closed Cylinder Green
    GroupShape transformedClosedCylinderGreen= new GroupShape(
      Mat4x4.multiply(
        Mat4x4.move(3.5, 2.6, -4.0),
        Mat4x4.multiply(
          Mat4x4.rotate(1, 0, 1, 45),
          Mat4x4.scale(1.5, 0.6, 1.5)
        )
      ),
      closedCylinderGreen
    );

    // Transform Closed Cylinder Orange
    GroupShape transformedClosedCylinderOrange = new GroupShape(
      Mat4x4.multiply(
        Mat4x4.move(-1.5, 1.5, -3.5),
        Mat4x4.multiply(
          Mat4x4.rotate(0, 1, 0, 30),  
          Mat4x4.scale(0.7, 3.0, 0.7)
        )
      ),
      closedCylinderOrange
    );

    // Transform Closed Cylinder Purple
    GroupShape transformedClosedCylinderPurple = new GroupShape(
      Mat4x4.multiply(
        Mat4x4.move(1.5, 0.5, -4.8),
        Mat4x4.multiply(
          Mat4x4.rotate(1, 1, 0, 90),
          Mat4x4.scale(0.7, 3.0, 0.7)
        )
      ),
      closedCylinderPurple
    );

    // Scene with groupshape Transformation
     GroupShape shapes = new GroupShape(
      Mat4x4.identity(),
    
      // Open cylinders
      transformedOpenCylinderBlue,
      transformedOpenCylinderPink,
      

      // Closed cylinders
      transformedClosedCylinderGreen,
       
      transformedClosedCylinderOrange,
      transformedClosedCylinderPurple,

      new BackgroundShape(backgroundMaterial), // background
      
      // Floor sunset style
      new DiscShape(new Vec3(0, -1.0, 0), 100.0, floorBlue), // dark blue
      new DiscShape(new Vec3(0, -0.95, 0), 80.0, floorViolet), // violet
      new DiscShape(new Vec3(0, -0.90, 0), 60.0, floorPurple), // purple
      new DiscShape(new Vec3(0, -0.85, 0), 40.0, floorPink), // pink
      new DiscShape(new Vec3(0, -0.80, 0), 20.0, floorOrange), // orange
      new DiscShape(new Vec3(0, -0.75, 0), 10.0, floorYellow) // yellow
    ); 
    */