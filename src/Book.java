public class Book implements Loan {
    private String title;
    private int year;
    private boolean isTaken;
    private String author;
    private static final int term = 30;
    private boolean inDept;
    private int dayCount = 0;

    public Book(){

    }
    public Book(String title, String author, int year){
        this.title = title;
        this.author = author;
        this.year = year;
        this.isTaken = true;
        this.inDept = false;
    }
    public void copy(Book b1){
        this.author = b1.author;
        this.title = b1.title;
        this.year = b1.year;
        this.isTaken = b1.isTaken;
        this.inDept = b1.inDept;
    }
    public void cD(){
        dayCount++;
    }
    public void checkDept(){
        if(dayCount > 30){
            inDept = true;
        }
    }

    public void showInfo(){
        System.out.println("Title: " + title + " | Author: " + author + " | Year: " + year + " | IsTaken: " + isTaken);
    }

    public String getTitle(){
        return title;
    }
    public void rent(){
        isTaken = true;
    }
    public void returnB(){
        isTaken = false;
    }
}
