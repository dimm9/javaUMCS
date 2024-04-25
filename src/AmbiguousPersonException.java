public class AmbiguousPersonException extends Exception{

    public AmbiguousPersonException(Person p) {
        super("Same name " + p.getName());
    }
    public String getMessage(String name){
        return "Same name " + name;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println("Same name ");
    }
}