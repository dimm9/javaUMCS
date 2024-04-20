import java.util.HashMap;
import java.util.Map;

public enum OPERATORS {
    EQUALS, NOT_EQUALS, MORE, LESS, MORE_EQ, LESS_EQ, LIKE, NOT_LIKE, PLUS, MINUS, MULTIPLY, DIVIDE;

    private static final Map<OPERATORS, String> symbols = new HashMap<>();
    static{
        symbols.put(EQUALS, "=");
        symbols.put(NOT_EQUALS, "!=");
        symbols.put(MORE, ">");
        symbols.put(LESS, "<");
        symbols.put(MORE_EQ, ">=");
        symbols.put(LESS_EQ, "<=");
        symbols.put(LIKE, "LIKE");
        symbols.put(NOT_LIKE, "NOT LIKE");
        symbols.put(PLUS, "+");
        symbols.put(MINUS, "-");
        symbols.put(MULTIPLY, "*");
        symbols.put(DIVIDE, "/");
    }


    public static String toString(OPERATORS op){
        return symbols.get(op);
    }


}
