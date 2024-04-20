//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SqlQuery.Builder query = new SqlQuery.Builder();
        query.addArguments("last_name", "salary").from("emp").orderBy(new boolean[]{true}, "salary").endQuery();
        SqlWholeScript script = new SqlWholeScript();
        script.addQuery(query.build());
        script.saveScriptToFile("script1.sql");
    }
}