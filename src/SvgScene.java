import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SvgScene {

    protected static SvgScene scene = null;
    private List<Shape> polygons = new ArrayList<>();
    private List<String> defs = new ArrayList<>();
    private static int index = 0;
    public void add(Shape poly){
        polygons.add(poly);
    }
    public int addDef(String def){
        String d = String.format(def, ++index);
        defs.add(d);
        return index;
    }
    public static SvgScene getScene() {
        if(scene == null){
            scene = new SvgScene();
        }
        return scene;
    }
    public void save(String path){
        StringBuilder text = new StringBuilder();
        text.append("<!DOCTYPE html>\n<html>\n<body>\n<svg width=\"1024\" height=\"768\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        text.append("<defs>\n");
        for(String s : defs){
            text.append(s + "\n");
        }
        text.append("</defs>\n");
        for(Shape s : polygons){
           text.append(s.toSVG(""));
        }
        text.append("</svg>\n</body>\n</html>");
        try(FileWriter writer = new FileWriter(path)) {
            writer.write(text.toString());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
