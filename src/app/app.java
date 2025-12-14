// module "Computergrafik Grundlagen", authors Hartmut Schirmacher + Henrik Tramberend
// Berliner Hochschule für Technik
// contact hschirmacher@bht-berlin.de

package app;

import java.util.List;

import app.light.DirectionalLight;
import app.light.Light;
import app.material.Material;
import app.material.PhongMaterial;
import app.sampler.uvDebugSampler;
import app.shape.BackgroundShape;
import app.shape.AlienShape;
import app.shape.DiscShape;
import app.shape.GroupShape;
import app.shape.RectShape;
import app.tests.DirectionalLightTests;
import app.tests.SphereTests;
import cgg_tools.Color;
import app.ConstantColor;
import cgg_tools.ConstantColorSampler;
import cgg_tools.Mat4x4;
import cgg_tools.Sampler;
import cgg_tools.Vec3;

public class app {

  public static Color multiply(Color c, double factor) {
    return new Color(c.r() * factor, c.g() * factor, c.b() * factor);
  }

  public static void main(String[] args) {
    int width = 800;
    int height = 600;
    double alpha = 60.0;
    Color background = new Color(1.0, 0.6, 0.2);
    var image = new Image(width, height);

    // Transform Matrix
    // Mat4x4 rotY = Mat4x4.rotate(0, 1, 0, 90);
    Mat4x4 rotX = Mat4x4.rotate(1, 0, 0, -60);
    Mat4x4 move = Mat4x4.move(0, 2, 2);
    Mat4x4 view = Mat4x4.multiply(move, rotX);

    // Creates a Camera now with view
    Camera cam = new Camera(alpha, width, height, view);

    // MATERIAL

    // uv debug material
    Sampler uvDebug = new uvDebugSampler();
        Material uvMaterial = new PhongMaterial(
            uvDebug, 
            uvDebug,
            uvDebug, 
            1.0
        );

    RectShape rect = new RectShape(
      new Vec3(0, 0, 0), // center origin
      1.0,  // width from -0.5 to 0.5
      1.0,  // depth from -0.5 to 0.5
      uvMaterial
    );

    DiscShape disc = new DiscShape(
      new Vec3(1.3, 0, 0),
      0.5,
      uvMaterial
    );

    // FLOOR
    Material floorGray = new PhongMaterial(
      new ConstantColor(new Color(0.05, 0.05, 0.05)),
      new ConstantColor(new Color(0.5, 0.5, 0.5)),
      new ConstantColor(new Color(0.3, 0.3, 0.3)),
      20.0
    );

    // BACKGROUND
    Material backgroundMaterial = new PhongMaterial(
      new ConstantColor(new Color(0.1, 0.1, 0.15)),
      new ConstantColor(Color.black),
      new ConstantColor(Color.black),
      1.0
    );

    GroupShape rectScene = new GroupShape(
      Mat4x4.identity(),
      rect,
      disc,
      new BackgroundShape(backgroundMaterial)
    );

    // LIGHTS
    Light light1 = new DirectionalLight(
      new Vec3(-1, -1, -0.5),
      new Color(0.5, 0.5, 0.5));

    Light light2 = new DirectionalLight(
      new Vec3(1, -1, -0.5),
      new Color(0.5, 0.5, 0.5));

    Light light = new DirectionalLight(
      new Vec3(0, -1, 0),  // direkt von oben nach unten
      Color.white
    );

    Color ambientLight = new Color(0.5, 0.5, 0.5);
    // Color ambientLight = new Color(0.6, 1.0, 1.0);

    Scene sceneObj = new Scene(rectScene, List.of(light), ambientLight);
    Raytracer raytracer = new Raytracer(cam, sceneObj, background);

    image.sample(raytracer);
    image.writePNG("a06-rectuv");

    // runs test method
    SphereTests.sphereTests();
    DirectionalLightTests.directionalLightTests();
  }
}
