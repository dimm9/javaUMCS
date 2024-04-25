public class Polygon implements Shape{
    private Vec2[] points;

    public Polygon(Vec2[] points) {
        this.points = points;
    }
    public Polygon(Polygon polygon)
    {
        this.points = new Vec2[polygon.points.length];
        for(int i = 0; i<polygon.points.length; i++)
        {
            points[i] = new Vec2(polygon.points[i].x, polygon.points[i].y);
        }
    }
    @Override
    public String toSVG(String parameters){
        StringBuilder poly = new StringBuilder();
        poly.append("\n<polygon points=\"");
        for(Vec2 point: points){
            poly.append(point.x).append(",").append(point.y).append(" ");
        }
        poly.append("\"");
        poly.append(parameters);
        poly.append("/>");
        return poly.toString();
    }
    public static Polygon square(Segment s){
        double halfLen = s.distance()/2;
        Vec2 center = new Vec2((s.getP2().x + s.getP1().x)/2, (s.getP2().y + s.getP1().y)/2);
        Segment half = new Segment(center, s.getP1());
        Segment[] parts = Segment.perpend(half, center, halfLen);
        return new Polygon(new Vec2[]{s.getP1(), parts[0].getP2(), s.getP2(), parts[1].getP2()});
    }

}
