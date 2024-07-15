public class User implements Loan{
    public String login;
    private String password;
    private int rentedBooks;
    public int deptBooks;

    public User(){

    }
    public User(String login, String password){
        this.login = login;
        this.password = password;
        rentedBooks = 0;
        deptBooks = 0;
    }

    public void rent(){
        rentedBooks++;
    }
    public String getPassword(){
        return password;
    }
    public String getLogin(){
        return login;
    }
    public void returnB(){
        deptBooks++;
    }
}
