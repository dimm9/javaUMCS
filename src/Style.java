import java.util.Locale;

public class Style {
    public final String fillColor;
    public final String strokeColor;
    public final double strokeWidth;


    public Style(String fillColor, String strokeColor, double strokeWidth) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }
    public String toSVG(){
        return String.format(Locale.ENGLISH," style=\"fill:%s;stroke:%s;stroke-width:%f\"",
                fillColor,
                strokeColor,
                strokeWidth
        );
    }
}

//Zdefiniuj klasę Style o finalnych,
// publicznych polach klasy String: fillColor, strokeColor oraz Double:
// strokeWidth. Napisz trójargumentowy konstruktor ustawiający te wartości.
// Napisz publiczną metodę toSvg() zwracającą napis, będący opcją style, którą można umieścić np.
// w tagu <polygon>.

