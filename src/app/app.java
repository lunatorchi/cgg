// module "Computergrafik Grundlagen", authors Hartmut Schirmacher + Henrik Tramberend
// Berliner Hochschule für Technik
// contact hschirmacher@bht-berlin.de

package app;

import java.util.List;

import app.light.DirectionalLight;
import app.light.Light;
import app.material.DiffuseEmitterMaterial;
import app.material.DiffuseMaterial;
import app.material.Material;
import app.material.MirrorMaterial;
import app.material.PhongMaterial;
import app.sampler.CheckerboardSampler;
import app.sampler.Raytracer;
import app.sampler.StratifiedSampler;
import app.shape.BackgroundShape;
import app.shape.GroupShape;
import app.shape.RectShape;
import app.shape.SphereShape;
import app.tests.DirectionalLightTests;
import app.tests.SphereTests;
import cgg_tools.Color;
import cgg_tools.Mat4x4;
import cgg_tools.Sampler;
import cgg_tools.Vec3;

public class app {

  public static Color multiply(Color c, double factor) {
    return new Color(c.r() * factor, c.g() * factor, c.b() * factor);
  }

  public static void main(String[] args) {
    int width = 960;
    int height = 540;
    double alpha = 70.0;
    Color background = new Color(0.2, 0.2, 0.2);
    var image = new Image(width, height);

    // Transform Matrix
    // Mat4x4 rotY = Mat4x4.rotate(0, 1, 0, 90);
    Mat4x4 rotX = Mat4x4.rotate(1, 0, 0, -10);
    Mat4x4 move = Mat4x4.move(0, 0.8, -2);
    Mat4x4 view = Mat4x4.multiply(move, rotX);

    // Creates a Camera now with view
    Camera cam = new Camera(alpha, width, height, view);

    Material pinkDiffuse = new DiffuseMaterial(new Color(0.8, 0, 0.5));
    Material greyDiffuse = new DiffuseMaterial(new Color(0.7, 0.7, 0.7));
    Material blueDiffuse = new DiffuseMaterial(new Color(0.3, 0.1, 0.7));

    Material backgroundEmitter = new DiffuseEmitterMaterial(new Color(0.2, 0.25, 0.4));
    Material lightEmitter = new DiffuseEmitterMaterial(new Color(0.9, 0.9, 0.9));

    Material blackSphere = new PhongMaterial(
      new ConstantColor(Color.black),
      new ConstantColor(Color.black),
      new ConstantColor(Color.white),
      80.0
    );

    Sampler checkerboard = new CheckerboardSampler(
      80,
      Color.white,
      Color.black  
    );

    Material mirrorMaterial = new MirrorMaterial(new Color(0.9, 0.9, 0.9));

    GroupShape scene = new GroupShape(
      Mat4x4.identity(),
      new RectShape(new Vec3(0, 0, 0), 30.0, 30.0, greyDiffuse),
  
      new SphereShape(
          new Vec3(-0.9, 0.3, -3.2),
          0.3,
          mirrorMaterial
      ),

      new SphereShape(
          new Vec3(0, 3, -3),
          1.2,
          lightEmitter
      ),

      new SphereShape(
          new Vec3(-0.6, 0.6, -4),
          0.6,
          pinkDiffuse
      ),
      
      new SphereShape(
        new Vec3(0.8, 0.8, -4),
        0.8,
        blackSphere 
      ),

      new SphereShape(
        new Vec3(0.1, 0.4, -3.2),
        0.35,
        blueDiffuse
      ),

      new BackgroundShape(backgroundEmitter)
    );

    // LIGHTS
    Light light1 = new DirectionalLight(
      new Vec3(-1, -1, -0.5),
      new Color(0.5, 0.5, 0.5));

    Light light2 = new DirectionalLight(
      new Vec3(1, -1, -0.5),
      new Color(0.5, 0.5, 0.5));

    Light light = new DirectionalLight(
      new Vec3(0, -1, 1), // direkt von oben nach unten
      Color.white);

    Color ambientLight = new Color(0.5, 0.5, 0.5);
    // Color ambientLight = new Color(0.6, 1.0, 1.0);

    Scene sceneObj = new Scene(scene, List.of(light), Color.black);
    Raytracer raytracer = new Raytracer(cam, sceneObj, Color.black);

    image.sample(new StratifiedSampler(raytracer, 16 , width, height));
    image.writePNG("a09-own-scene");

   /*  //Grid Sampling
    var gridSampler4 = new GridSampler(raytracer, 16, width, height);
    image.sample(gridSampler4);
    image.writePNG("a08-grid-16x16");

    //Stratified Sampling
    var stratSampler4 = new StratifiedSampler(raytracer, 16, width, height);
    image.sample(stratSampler4);
    image.writePNG("a08-stratified-16x16");
 */

    // runs test method
    SphereTests.sphereTests();
    DirectionalLightTests.directionalLightTests();
  }
}
