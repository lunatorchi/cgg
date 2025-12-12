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
import app.shape.ClosedCylinderShape;
import app.shape.AlienShape;
import app.shape.CuboidShape;
import app.shape.DiscShape;
import app.shape.GroupShape;
import app.shape.OpenCylinderShape;
import app.shape.SphereShape;
import app.tests.DirectionalLightTests;
import app.tests.SphereTests;
import cgg_tools.Color;
import cgg_tools.Mat4x4;
import cgg_tools.Vec3;


public class appCre {

  public static Color multiply(Color c, double factor) {
    return new Color(c.r() * factor, c.g() * factor, c.b() * factor);
}

  public static void main(String[] args) {
    int width = 1280;
    int height = 720;
    double alpha = 35.0;
    Color background = new Color(1.0, 0.6, 0.2);
    var image = new Image(width, height);

    // Transform Matrix
    //Mat4x4 rotY = Mat4x4.rotate(0, 1, 0, 90);
    Mat4x4 rotX = Mat4x4.rotate(1, 0, 0, -1);
    Mat4x4 move = Mat4x4.move(0, 1, 15);
    Mat4x4 view = Mat4x4.multiply(move, rotX);

    // Creates a Camera now with view
    Camera cam = new Camera(alpha, width, height, view);

    
    // MATERIAL
    // FLOOR
    Material floorGray = new PhongMaterial(
        new Color(0.05, 0.05, 0.05),
        new Color(0.5, 0.5, 0.5),
        new Color(0.3, 0.3, 0.3),
        20.0
    );
    // BACKGROUND
    Material backgroundMaterial = new PhongMaterial(
      new Color(0.2, 0.08, 0.12),
      background,
      new Color(0.3, 0.3, 0.3),
      20.0
    );

    Material creatureBodyRed = new PhongMaterial(
        new Color(0.1, 0.02, 0.02),
        new Color(1.0, 0.1, 0.1),
        new Color(0.3, 0.3, 0.3),
        50.0
    );

    Material creatureJointRed = new PhongMaterial(
        new Color(0.1, 0.02, 0.02),
        new Color(1.0, 0.1, 0.1),
        new Color(0.3, 0.3, 0.3),
        50.0
    );

    Material creatureHeadBlue = new PhongMaterial(
        new Color(0.2, 0.08, 0.12),
        new Color(0.0, 0.0, 0.8),
        new Color(0.3, 0.3, 0.3),
        20.0
    );

    Material blackHat = new PhongMaterial(
        new Color(0.0, 0.0, 0.0),
        new Color(0.0, 0.0, 0.0),
        new Color(0.1, 0.1, 0.1),
        80.0
    );

// Creature 1 -> disney knees pose
AlienShape creature1 = new AlienShape(
    creatureBodyRed,
    creatureJointRed,
    creatureHeadBlue,
    blackHat,
    creatureJointRed,
    0.5,
    1.5,
    0.12,
    15,
    -25,
    10,
    -10,
    -20,
    -5
);

Mat4x4 creature1Transform = Mat4x4.move(-8.0, 2.5, -4.0);
GroupShape transformedCreature1 = new GroupShape(creature1Transform, creature1);

// Creature 2 -> Ballerina
AlienShape creature2 = new AlienShape(
    creatureBodyRed,
    creatureJointRed,
    creatureHeadBlue,
    blackHat,
    creatureJointRed,
    0.5,
    1.5,
    0.12,
    0,
    10,
    0,
    -40,
    -60,
    -10
);

Mat4x4 creature2Transform = Mat4x4.move(2.0, 2.5, -4.0);
GroupShape transformedCreature2 = new GroupShape(creature2Transform, creature2);

// Creature 3 -> She was a fairy
AlienShape creature3 = new AlienShape(
    creatureBodyRed,
    creatureJointRed,
    creatureHeadBlue,
    blackHat,
    creatureJointRed,
    0.5,
    1.8,
    0.12,
    280,
    20,
    0,
    -80,
    -30,
    0    
);

Mat4x4 creature3Transform = Mat4x4.move(-3.25, 3.5, -4.0);
GroupShape transformedCreature3 = new GroupShape(creature3Transform, creature3);

// Scene with all "dancing" Creatures
GroupShape creatureScene = new GroupShape(
    Mat4x4.identity(),
    
    transformedCreature1,
    transformedCreature2,
    transformedCreature3,
    
    new BackgroundShape(backgroundMaterial),
    
    // Floor
    new DiscShape(new Vec3(0, -1.0, 0), 100.0, floorGray)
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
    
    Color ambientLight = new Color(0.1, 0.1, 0.15);
    //Color ambientLight = new Color(0.6, 1.0, 1.0);

Scene creatureSceneObj = new Scene(creatureScene, List.of(light1, light2), ambientLight);
Raytracer raytracerCreatures = new Raytracer(cam, creatureSceneObj, background);

image.sample(raytracerCreatures);
image.writePNG("a06-creatures");
  }
}
