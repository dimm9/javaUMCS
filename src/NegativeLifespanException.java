public class NegativeLifespanException extends Exception{

    public NegativeLifespanException() {
        super("The person death date is earlier than person birth date");
    }
    public String getMessage(Person p){
        return "The death date is earlier than birth date "  + p.getName() + " " + p.getDeath().toString() + " " + p.getBirth().toString();
    }
}
