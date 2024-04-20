import java.util.HashMap;
import java.util.Map;

public class SqlQuery {
    private StringBuilder query;

    public SqlQuery() {
        query = new StringBuilder();
    }

    public SqlQuery(Builder query) {
        this.query = query.query;
    }

    public StringBuilder getQuery() {
        return query;
    }

    public static class Builder {

        private StringBuilder query;
        private boolean hasAgFun;
        public void setQuery(StringBuilder query) {
            this.query = query;
        }

        public static Builder getInstance(){
            return new Builder();
        }

        public Builder() {
            hasAgFun = false;
            query = new StringBuilder();
            query.append("SELECT ");
        }
        public Builder addArguments(String...columns){
            int i=0;
            for(String s : columns){
                if(i != columns.length-1){
                    query.append(s).append(", ");
                }
                else{
                    query.append(s).append(" ");
                }
                if(s.contains("MAX") || s.contains("MIN") || s.contains("AVG") || s.contains("SUM") || s.contains("COUNT")){
                    hasAgFun = true;
                }
                i++;
            }
            return this;
        }
        public Builder from(String...arrays){
            query.append("FROM ");
            int i=0;
            for(String arr : arrays){
                if(i!=arrays.length-1){
                    query.append(arr).append(", ");
                }
                else{
                    query.append(arr).append(" ");
                }
                i++;
            }
            return this;
        }
        public Builder orderBy(boolean[] asc, String...column){
            query.append("ORDER BY ");
            int i=0;
            for(String col : column){
                query.append(col).append(" ");
                if(asc[i]){
                    query.append("ASC ");
                }
                else{
                    query.append("DESC ");
                }
                if(i==column.length-1){
                    query.append(" ");
                }
                else{
                    query.append(", ");
                }
                i++;
            }
            return this;
        }
        public Builder where(OPERATORS[] op, String[] expression, String[] column, boolean andOrOr){
            int count = expression.length;
            String andorOrStr = andOrOr ? " AND " : " OR ";
            query.append("WHERE ");
            for(int i=0; i<count; i++){
                query.append(column[i]).append(" ").append(OPERATORS.toString(op[i])).append(" ").append(expression[i]);
                if(i != count-1){
                    query.append(andorOrStr);
                }
            }
            return this;
        }
        public Builder groupBy(String...cols){
            if(!this.hasAgFun){
                System.out.println("You cant use GROUP BY without aggregation functions");
            }
            else{
                query.append("GROUP BY ");
                int i=0;
                for(String c : cols){
                    query.append(c).append(", ");
                    if(i==cols.length-1){
                        query.append(" ");
                    }
                    i++;
                }
            }
            return this;
        }
        public Builder union(SqlQuery query){
            this.query.append("UNION ").append(query.getQuery().toString());
            return this;
        }
        public Builder unionAll(SqlQuery query){
            this.query.append("UNION ALL").append(query.getQuery().toString());
            return this;
        }
        public Builder minus(SqlQuery query){
            this.query.append("MINUS ").append(query.getQuery().toString());
            return this;
        }
        public Builder intersect(SqlQuery query){
            this.query.append("INTERSECT ").append(query.getQuery().toString());
            return this;
        }
        public Builder endQuery(){
            query.append(";");
            return this;
        }
        public SqlQuery build(){
            return new SqlQuery(this);
        }
    }
}
