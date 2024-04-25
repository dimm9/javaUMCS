import java.util.Locale;

public class TransformationDecorator extends ShapeDecorator{
    private String transform;

    public TransformationDecorator(Shape shape, String transform) {
        super(shape);
        this.transform = transform;
    }

    @Override
    public String toSVG(String parameters) {
        return decoratedShape.toSVG(String.format(Locale.ENGLISH, "transform=\"%s\" %s", transform, parameters));
    }
    public static class Builder {
        private String transform;
        private Shape shape;

        public Builder(Shape shape) {
            this.transform = "";
            this.shape = shape;
        }

        public Builder translate(Vec2 translation) {
            this.transform += String.format(Locale.ENGLISH, "translate(%f %f)", translation.x, translation.y);
            return this;
        }

        public Builder rotate(Vec2 rotateCenter, double angle) {
            this.transform += String.format(Locale.ENGLISH, "rotate(%f %f %f)", angle, rotateCenter.x, rotateCenter.y);
            return this;
        }

        public Builder scale(Vec2 scaleVec) {
            this.transform += String.format(Locale.ENGLISH, "scale(%f %f)", scaleVec.x, scaleVec.y);
            return this;
        }
        public TransformationDecorator build() {
            return new TransformationDecorator(shape, transform);
        }
    }
}
