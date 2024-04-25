import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Person implements Serializable{

    private String name;
    private LocalDate birth;
    private LocalDate death;
    public List<Person> getParents() {
        return parents;
    }

    private List<Person> parents;
    public void addParent(Person p){
        parents.add(p);
    }
    public String getName() {
        return name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public LocalDate getDeath() {
        return death;
    }

    public Person(String name, LocalDate birth, LocalDate death) {
        this.name = name;
        this.birth = birth;
        this.death = death;
        this.parents = new ArrayList<>();
    }
    public static Person fromCsvLine(String line){
        String[] parts = line.split(",", -1);
        String name = parts[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birth = LocalDate.parse(parts[1], formatter);
        LocalDate death = null;
        if(!parts[2].isEmpty()){
            death = LocalDate.parse(parts[2], formatter);
        }
        return new Person(name, birth, death);
    }

    public void handleParentAge(Parents p) throws ParentingAgeException {
        LocalDate childBirth = p.getChild().getBirth();
        for (Person parent : p.getChild().getParents()) {
            LocalDate parentBirth = parent.getBirth();
            LocalDate parentDeath = parent.getDeath();
            long age = ChronoUnit.YEARS.between(parentBirth, childBirth);
            if (parentDeath != null && (parentDeath.isBefore(childBirth) || age < 15)) {
                System.out.println("Parents are too young. Do you want to continue?(Y)");
                Scanner scan = new Scanner(System.in);
                String choice = scan.nextLine();
                if (!choice.equals("Y")) {
                    throw new ParentingAgeException();
                } else {
                    System.out.println("Adding young or not born parents");
                }
            }
        }
    }

    public static List<Person> fromCsv(String path){
        List<Person> people = new ArrayList<>();
        Map<String, Parents> relatives = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                Parents parents = Parents.fromCsvLine(line);
                Person p = parents.getChild();
                try{
                    people.add(p);
                    p.checkLifeSpan();
                    p.checkSame(people);
                    p.handleParentAge(parents);
                    relatives.put(p.getName(), parents);
                } catch (NegativeLifespanException e) {
                    System.err.println(e.getMessage(p));
                } catch (AmbiguousPersonException e) {
                    e.printStackTrace();
                } catch (ParentingAgeException e) {
                    System.out.println(e.getMessage());
                }
            }
            Parents.linkParentsToChild(relatives);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return people;
    }
    public void checkLifeSpan() throws NegativeLifespanException{
        if(this.death != null && this.death.isBefore(this.birth)){
            throw new NegativeLifespanException();
        }
    }
    public void checkSame(List<Person> people) throws AmbiguousPersonException{
        Set<String> unique = new HashSet<>();
        for(Person p : people){
            if(unique.contains(p.name)){
                throw new AmbiguousPersonException(p);
            }else{
                unique.add(p.name);
            }
        }
    }
    @Override
    public String toString() {
        String f = "Name: " + getName() + "  Birth: " + getBirth().toString();
        if(death != null) f += "  Death: " + getDeath().toString();
        f += "  Parents: ";
        if(!parents.isEmpty()){
            for(Person parent : parents){
                f +=  parent.getName() + " | ";
            }
        }
        else{
            f += "None";
        }
        return f;
    }
    public static void toBinaryFile(String path, List<Person> people) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(people);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Person> fromBinaryFile(String path) {
        List<Person> people;
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(path))) {
            people = (List<Person>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public String umlPersonData(){
        // <pobiera, zwraca>
        Function<Person, String> delSpaces = person -> person.getName().replaceAll(" ", "");
        Function<Person, String> addObj = person -> "object " + delSpaces.apply(person);

        StringBuilder peopleObj = new StringBuilder();
        StringBuilder connections = new StringBuilder();
        peopleObj.append(addObj.apply(this)).append("\n");
        parents.forEach(parent -> {
            peopleObj.append(addObj.apply(parent)).append("\n");
            connections.append(String.format("%s <-- %s\n", delSpaces.apply(parent), delSpaces.apply(this)));
        });
        return String.format("@startuml\n%s\n%s\n@enduml", peopleObj.toString(), connections.toString());
    }
    public String umlPersonData(Function<String, String> postProcess, Predicate<Person> condition){
        // <pobiera, zwraca>
        Function<Person, String> delSpaces2 = person -> person.getName().replaceAll(" ", "");
        Function<Person, String> delSpaces1 = person -> (condition.test(person)) ? postProcess.apply(person.getName().replaceAll(" ", "")) : delSpaces2.apply(person);
        Function<Person, String> addObj = person -> "object " + delSpaces1.apply(person);

        StringBuilder peopleObj = new StringBuilder();
        StringBuilder connections = new StringBuilder();
        peopleObj.append(addObj.apply(this)).append("\n");
        parents.forEach(parent -> {
            peopleObj.append(addObj.apply(parent)).append("\n");
            connections.append(String.format("%s <-- %s\n", delSpaces2.apply(parent), delSpaces2.apply(this)));
        });
        return String.format("@startuml\n%s\n%s\n@enduml", peopleObj.toString(), connections.toString());
    }
    public static String umlPeople(List<Person> people){
        StringBuilder uml = new StringBuilder();
        Function<Person, String> delSpaces = person -> person.getName().replaceAll(" ", "");
        Function<Person, String> addObj = person -> "object " + delSpaces.apply(person);
        uml.append("@startuml");
        uml.append(people.stream()
                .map(person -> "\n" + addObj.apply(person))
                .collect(Collectors.joining()));
        uml.append(people.stream()
                .flatMap(person -> person.parents.isEmpty() ? Stream.empty() : person.parents.stream()
                .map(parent -> "\n" + delSpaces.apply(parent) + " <-- " + delSpaces.apply(person))).collect(Collectors.joining()));
        uml.append("\n@enduml");
        return uml.toString();
    }
    public static List<Person> substringList(List<Person> people, String substring){
        return people.stream().filter(p -> p.getName().contains(substring)).toList();
    }
    public static List<Person> sortByBirth(List<Person> people){
        return people.stream().sorted(Comparator.comparingInt(p -> p.getBirth().getYear())).toList();
    }
    public static List<Person> deadSorted(List<Person> people){
        return people.stream() //converting list to stream
                .filter(p -> p.getDeath() != null) //filtrujemy tych osob ktore umarli
                .sorted(Comparator.comparingInt((Person p) -> (p.getDeath().getYear() - p.getBirth().getYear())).reversed()) //sortujemy(reversed() - malejaco)
                .toList();
    }
    public static Person oldest(List<Person> people){
        return  people.stream()
                .filter(p -> p.getDeath() == null)
                .sorted(Comparator.comparingInt((Person p1) -> (int)p1.getBirth().toEpochDay()))
                .toList()
                .getFirst();
    }
        /*
    public static String umlPeople(List<Person> people, Predicate<Person> condition, Function<String, String> postProcess){
        // <pobiera, zwraca>
        Function<String, String> delSpaces = person -> person.replaceAll(" ", "");
        Function<String, String> addObj = person -> "object " + delSpaces.apply(person);
        Function<String, String> addObjPostprocess = addObj.andThen(postProcess);
        Map<Boolean, List<Person>> grouped = people.stream().collect(Collectors.partitioningBy(condition));
        Set<String> objects = grouped
                .get(true)
                .stream()
                .map(person -> person.name)
                .map(addObjPostprocess)
                .collect(Collectors.toSet());
        objects.addAll(grouped.get(true).stream()
                .map(person -> person.name)
                .map(addObjPostprocess)
                .collect(Collectors.toSet()));
        Set<String> relations = people.stream()
                       .flatMap(person -> person.parents.stream()
                       .map(parent -> delSpaces.apply(parent.name) + "<--" + delSpaces.apply(person.name)))
                       .collect(Collectors.toSet());
        String objectsString = "\n" + objects;
        String relationsString = "\n" + relations;
        return String.format("@startuml\n%s\n%s\n@enduml", objectsString, relationsString);
    }
     */

}