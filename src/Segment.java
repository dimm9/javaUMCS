public class Segment {
    private Vec2 p1, p2;

    public Segment(Vec2 p1, Vec2 p2){
        this.p1 = new Vec2(p1.x, p1.y);
        this.p2 = new Vec2(p2.x, p2.y);
    }
    public Vec2 getP1(){
        return p1;
    }
    public Vec2 getP2(){
        return p2;
    }

    public double distance(){
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public String toSvg(){
        String svg = "<line x1=\\" + p1.x + "\\ y1=\\" + p1.y + "\\ x2=\\" + p2.x + "\\ y2=\\" + p2.y + "\\style=stroke:green;stroke-width:6 />";
        return svg;
    }
    /*
    public static Segment[] perpend(Segment segment, Point point){
        double dx = segment.p2.x - segment.p1.x ;
        double dy = segment.p2.y - segment.p1.y;
        double new_dx1=-dy;
        double new_dy1=dx;
        double new_dx2=dy;
        double new_dy2=-dx;
        double endx1 = point.x + new_dx1;
        double endy1 = point.x + new_dy1;
        double endx2 = point.x + new_dx2;
        double endy2 = point.x + new_dy2;
        Point first_end = new Point(endx1,endy1);
        Point second_end = new Point(endx2,endy2);
        Segment first = new Segment(point, first_end);
        Segment second =new Segment(point, second_end);
        return new Segment[]{first,second};
    }

     */
    public static Segment[] perpend(Segment segment, Vec2 point){
        double vecX = segment.p2.x - segment.p1.x ;
        double vecY = segment.p2.y - segment.p1.y;
        Vec2 newVec2 = new Vec2(vecX, -vecY);
        Vec2 newVec1 = new Vec2(-vecX, vecY);
        Vec2 endPoint1 = new Vec2(newVec1.x + point.x, newVec1.y + point.y);
        Vec2 endPoint2 = new Vec2(newVec2.x + point.x, newVec2.y + point.y);
        return new Segment[]{new Segment(point, endPoint1), new Segment(point, endPoint2)};
    }
    public static Segment[] perpend(Segment segment, Vec2 center, double length) {
        double dx = ((segment.getP2().x - segment.getP1().x) / segment.distance()) * length;
        double dy = ((segment.getP2().y - segment.getP1().y) / segment.distance()) * length;
        return new Segment[]{
                new Segment(center, new Vec2(
                        center.x - dy, center.y + dx
                )),
                new Segment(center, new Vec2(
                        center.x + dy, center.y - dx
                )),
        };
    }
}
