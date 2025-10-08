package cgg_tools;

public record ConstantColorSampler(Color color) implements Sampler {

  @Override
  public Color getColor(Vec2 at) {
    return color;
  }
}
