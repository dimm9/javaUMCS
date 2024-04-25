import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parents implements Serializable {
    private List<String> names;
    private Person child;
    public Parents(List<String> names, Person child) {
        this.names = names;
        this.child = child;
    }
    public static Parents fromCsvLine(String line){
        Person child = Person.fromCsvLine(line);
        List<String> names = new ArrayList<>();
        String[] values = line.split(",", -1);
        for(int i = 3; i <= 4 ; i++){
            if(!values[i].isEmpty())
                names.add(values[i]);
        }
        return new Parents(names, child);
    }

    public static void linkParentsToChild(Map<String, Parents> relatives){
        for(Parents parent : relatives.values()){
            Person child = parent.child;
            for(String name : parent.names){
                if(relatives.containsKey(name)) {
                    Parents temp = relatives.get(name);
                    Person newParentPerson = temp.getChild();
                    child.addParent(newParentPerson);
                }else{
                    System.out.println("Parent info missing for " + name);
                }
            }
        }
    }
    public Person getChild() {
        return child;
    }

}
