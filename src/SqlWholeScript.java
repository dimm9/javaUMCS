import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlWholeScript {
    List<SqlQuery> script;

    public SqlWholeScript() {
        this.script = new ArrayList<>();
    }

    public void addQuery(SqlQuery q){
        this.script.add(q);
    }
    public void saveScriptToFile(String path){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for(SqlQuery s : script) {
                writer.write(s.getQuery() + "\n");
                System.out.println(s.getQuery());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
