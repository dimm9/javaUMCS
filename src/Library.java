public class Library extends Book{
    public Book[][] books;
    public int amount;
    public final String[] genders = {"horror", "science", "drama"};

    public Library(int amount){
        this.amount = amount;
        books = new Book[3][amount];
        for(int i=0; i < 3; i++){
            for(int j=0; j<amount; j++) {
                books[i][j] = null;
            }
        }
    }

    public void rent(String bookName){
        for(int i=0; i < 3; i++){
            for(int j=0; j<amount; j++) {
                if(bookName != null) {
                    if (bookName.equals(books[i][j].getTitle())) {
                        books[i][j].rent();
                    }
                    ;
                }
            }
        }
    }
    public void returnB(String bookName){
        for(int i=0; i < 3; i++){
            for(int j=0; j<amount; j++) {
                if(bookName.equals(books[i][j].getTitle())){
                    books[i][j].returnB();
                }
            }
        }
    }

    public void showAll(){
        for(int i=0; i<3; i++){
            System.out.println(genders[i] + " ");
            for(int j=0; j<amount; j++) {
                if(books[i][j]!=null) {
                    books[i][j].showInfo();
                }
            }
        }
    }
    public void addBook(int gender, Book book){
        boolean found = false;
            for(int j=0; j<amount; j++) {
                if(books[gender][j] == null){
                    found = true;
                    books[gender][j] = new Book();
                    books[gender][j].copy(book);
                    break;
                }
            }
        if(!found){
            System.out.println("The library is full!");
        }
    }

    public void deleteBook(Book book){
        boolean find = false;
        for(int i=0; i<3; i++){
            for(int j=0; j<amount; j++){
                if(books[i][j].equals(book)){
                    find = true;
                    books[i][j] = null;
                }
            }
        }
        if(!find){
            System.out.println("There is no such book!");
        }
    }

}
