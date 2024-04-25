public class ParentingAgeException extends Exception{

    public ParentingAgeException() {
        super("Parent is younger than 15 years old");
    }

    @Override
    public String getMessage() {
        return "Too young parents. Do you want to add them?(Y)";
    }

}