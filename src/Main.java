import java.time.Clock;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.Duration;
import java.time.Instant;


public class Main {
    public static void main(String[] args) {
        boolean quit = false;
        Scanner scan  = new Scanner(System.in);
        String[] booknames = {"Dark Tower", "Crime and punishment", "Atom bomb"};
        String[] authors = {"Stephen King", "Fyodor Dostoyevsky", "Bob Miller"};
        int[] years = {1987, 1867, 1991};
        Admin admin = new Admin();
        ArrayList<User> clients = new ArrayList<>();
        clients.add(new User("Bob", "111"));
        clients.add(new User("Tom", "222"));
        clients.add(admin);
        Library lib = new Library(3);
        Book[] books = {new Book(booknames[2], authors[2], years[2]), new Book(booknames[1], authors[1], years[1]), new Book(booknames[0], authors[0], years[0])};
        lib.addBook(2, books[0]);
        lib.addBook(1, books[1]);
        lib.addBook(0, books[2]);

        Clock baseClock = Clock.systemUTC();
        Instant startRentTime = null;
        while(!quit){
            String login, password;
            System.out.println("----------Library---------");
            System.out.println("Login: ");
            login = scan.nextLine();
            System.out.println("Password: ");
            password = scan.nextLine();
            boolean find = false;
            // we are using .equals() to compare classes(strings too)
            for(User client : clients){
                if(login.equals(client.login) && password.equals(client.getPassword())){
                    find = true;
                    break;
                }
            }
            if(find){
                System.out.println("Registration...");
            }
            else{
                System.out.println("Login or password is incorrect! Try again!");
            }
            if(find){
                User client = new User(login, password);
                if(login.equals("-")){
                    client = null;
                }

                if(client.login.equals("admin")) {
                    System.out.println("1 - delete Book");
                    System.out.println("2 - add Book");
                    System.out.println("3 - quit");
                    byte choose = scan.nextByte();
                    if (choose == 1) {
                        System.out.println("Choose book title to delete: ");
                        String title = scan.nextLine();
                        int year = scan.nextInt();
                        String author = scan.nextLine();
                        lib.deleteBook(new Book(title, author, year));
                    } else if (choose == 2) {
                        System.out.println("Choose book title to add: ");
                        String title = scan.nextLine();
                        lib.addBook(2, new Book(title, authors[2], years[2]));
                    }
                    else if(choose == 4){
                        quit = true;
                    }
                }
                else{
                    System.out.println("1 - rent a book");
                    System.out.println("2 - return a book");
                    System.out.println("3 - browse book catalogues");
                    System.out.println("4 - quit");
                    byte choose = scan.nextByte();
                    if(choose == 1){
                        client.rent();
                        System.out.println("Choose a title book to rent: ");
                        String title = scan.nextLine();
                        lib.rent(title);
                        startRentTime = Instant.now();
                    }
                    else if(choose == 2){
                        client.returnB();
                        System.out.println("Choose a title book to return: ");
                        String title = scan.nextLine();
                        lib.returnB(title);

                        if (startRentTime != null) {
                            Instant returnTime = Instant.now(); // Get the current time
                            Duration rentalDuration = Duration.between(startRentTime, returnTime);
                            long termin = rentalDuration.toMinutes(); // Get the duration in minutes

                            if (termin >= 30) {
                                System.out.println("You forgot to return a book. Now you have a debt!");
                                client.deptBooks++;
                            }
                        }
                    }
                    else if(choose == 3){
                        lib.showAll();
                    }
                    else if(choose == 4){
                        quit = true;
                    }
                }
            }
            else{
                System.out.println("Registration failed.");
            }

        }

    }
}