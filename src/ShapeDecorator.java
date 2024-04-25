public class ShapeDecorator implements Shape{
    protected Shape decoratedShape;

    public ShapeDecorator(Shape shape) {
        this.decoratedShape = shape;
    }

    @Override
    public String toSVG(String parameters) {
        return decoratedShape.toSVG(parameters);
    }
}

//Utwórz klasę StrokeShapeDecorator posiadającą prywatne pola String color i double width, które powinny być ustawione w konstruktorze. Wywołaj metodę toSvg podobnie jak w zadaniu 2. formatując napis
//"stroke=\"%s\" stroke-width=\"%f\" "
//kolorem i grubością obrysu. Przetestuj udekorowanie tą klasą obiektów będących wynikiem poprzedniego zadania.