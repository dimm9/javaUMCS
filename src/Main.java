
public class Main {
    public static void main(String[] args){
        Segment[] arr = new Segment[2];
        for(int i=0; i<2; i++){
            float x1, y1;
            float max = 5;
            float min = 1;
            x1 = (float)(Math.random() * (max - min)+ 1) + min;
            y1 = (float)(Math.random() * (max - min)+ 1) + min;
            SvgScene scene = SvgScene.getScene();
            Shape ellipse = new Ellipse(new Style("red", "blue", 3), new Vec2(600, 600), 100, 100);
            ellipse = new SolidFillShapeDecorator(ellipse, "red");
            ellipse = new DropShadowDecorator(ellipse);
            scene.add(ellipse);
            Shape square = Polygon.square(new Segment(new Vec2(428, 454), new Vec2(212, 658)));
            square = new GradientFillShapeDecorator.Builder()
                    .setShape(square)
                    .addStop(0, "red")
                    .addStop(0.3, "green")
                    .addStop(1, "cyan")
                    .build();
            square = new DropShadowDecorator(square);
            scene.add(square);
            Polygon polyS = new Polygon(new Vec2[]{new Vec2(200, 10), new Vec2(550, 50), new Vec2(100, 100)});

            TransformationDecorator.Builder builder = new TransformationDecorator.Builder(new SolidFillShapeDecorator(polyS, "blue"));
            TransformationDecorator s = builder.rotate(new Vec2(0, 0), 30).scale(new Vec2(1, 1.5)).build();
            scene.add(s);
            scene.save("file.html");

        }
    }
}