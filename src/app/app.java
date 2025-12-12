// module "Computergrafik Grundlagen", authors Hartmut Schirmacher + Henrik Tramberend
// Berliner Hochschule für Technik
// contact hschirmacher@bht-berlin.de

package app;

import java.util.List;

import app.light.DirectionalLight;
import app.light.Light;
import app.material.Material;
import app.material.PhongMaterial;
import app.shape.BackgroundShape;
import app.shape.AlienShape;
import app.shape.DiscShape;
import app.shape.GroupShape;
import app.tests.DirectionalLightTests;
import app.tests.SphereTests;
import cgg_tools.Color;
import app.ConstantColor;
import cgg_tools.ConstantColorSampler;
import cgg_tools.Mat4x4;
import cgg_tools.Vec3;

public class app {

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
    // Mat4x4 rotY = Mat4x4.rotate(0, 1, 0, 90);
    Mat4x4 rotX = Mat4x4.rotate(1, 0, 0, -1);
    Mat4x4 move = Mat4x4.move(-2.0, 1, 15);
    Mat4x4 view = Mat4x4.multiply(move, rotX);

    // Creates a Camera now with view
    Camera cam = new Camera(alpha, width, height, view);

    // MATERIAL
    // FLOOR
    Material floorGray = new PhongMaterial(
      new ConstantColor(new Color(0.05, 0.05, 0.05)),
      new ConstantColor(new Color(0.5, 0.5, 0.5)),
      new ConstantColor(new Color(0.3, 0.3, 0.3)),
      20.0
    );

    // BACKGROUND
    Material backgroundMaterial = new PhongMaterial(
      new ConstantColor(new Color(0.2, 0.08, 0.12)),
      new ConstantColor(background),
      new ConstantColor(new Color(0.3, 0.3, 0.3)),
      20.0
    );

    Material alienBodyRed = new PhongMaterial(
      new ConstantColor(new Color(0.1, 0.02, 0.02)),
      new ConstantColor(new Color(1.0, 0.1, 0.1)),
      new ConstantColor(new Color(0.3, 0.3, 0.3)),
      50.0
    );

    Material alienJointRed = new PhongMaterial(
      new ConstantColor(new Color(0.1, 0.02, 0.02)),
      new ConstantColor(new Color(1.0, 0.1, 0.1)),
      new ConstantColor(new Color(0.3, 0.3, 0.3)),
      50.0
    );

    Material alienHeadBlue = new PhongMaterial(
      new ConstantColor(new Color(0.2, 0.08, 0.12)),
      new ConstantColor(new Color(0.0, 0.0, 0.8)),
      new ConstantColor(new Color(0.3, 0.3, 0.3)),
      20.0
    );

    Material blackHat = new PhongMaterial(
      new ConstantColor(new Color(0.0, 0.0, 0.0)),
      new ConstantColor(new Color(0.0, 0.0, 0.0)),
      new ConstantColor(new Color(0.1, 0.1, 0.1)),
      80.0
    );

    // alien 1 -> disney knees pose
    AlienShape alien1 = new AlienShape(
      alienBodyRed,
      alienJointRed,
      alienHeadBlue,
      blackHat,
      alienJointRed,
      0.5,
      2.0,
      0.2,
      15,
      -25,
      10,
      -10,
      -20,
      -5
    );

    Mat4x4 alien1Transform = Mat4x4.move(-8.0, 3.5, -4.0);
    GroupShape transformedAlien1 = new GroupShape(alien1Transform, alien1);

    // alien 2 -> Ballerina
    AlienShape alien2 = new AlienShape(
      alienBodyRed,
      alienJointRed,
      alienHeadBlue,
      blackHat,
      alienJointRed,
      0.4,
      1.0,
      0.1,
      0,
      10,
      0,
      -40,
      -60,
      -10
    );

    Mat4x4 alien2Transform = Mat4x4.move(2.0, 1.8, -4.0);
    GroupShape transformedAlien2 = new GroupShape(alien2Transform, alien2);

    // alien 3 -> She was a fairy
    AlienShape alien3 = new AlienShape(
      alienBodyRed,
      alienJointRed,
      alienHeadBlue,
      blackHat,
      alienJointRed,
      0.6,
      1.8,
      0.12,
      280,
      20,
      0,
      -80,
      -30,
      0);

    Mat4x4 alien3Transform = Mat4x4.move(-3.25, 3.5, -4.0);
    GroupShape transformedAlien3 = new GroupShape(alien3Transform, alien3);

    // Scene with all "dancing" Aliens
    GroupShape alienScene = new GroupShape(
      Mat4x4.identity(),
      transformedAlien1,
      transformedAlien2,     
      transformedAlien3,

      new BackgroundShape(backgroundMaterial),

      // Floor
      new DiscShape(new Vec3(0, -1.0, 0), 100.0, floorGray)
    );

    // LIGHTS
    Light light1 = new DirectionalLight(
      new Vec3(-1, -1, -0.5),
      new Color(0.5, 0.5, 0.5));

    Light light2 = new DirectionalLight(
      new Vec3(1, -1, -0.5),
      new Color(0.5, 0.5, 0.5));

    Color ambientLight = new Color(0.1, 0.1, 0.15);
    // Color ambientLight = new Color(0.6, 1.0, 1.0);

    Scene alienSceneObj = new Scene(alienScene, List.of(light1, light2), ambientLight);
    Raytracer raytracer = new Raytracer(cam, alienSceneObj, background);

    image.sample(raytracer);
    image.writePNG("a06-jointythings");

    // runs test method
    SphereTests.sphereTests();
    DirectionalLightTests.directionalLightTests();
  }
}
