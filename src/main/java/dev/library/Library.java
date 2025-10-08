package dev.library;

import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private ArrayList<Book> booksInStockList;
    private ArrayList<Book> borrowedBooksList;
    private Scanner scanner;
    //Lade till nedan: Ny variabel för att spåra hur många böcker man har lånat/förlängt.
    private int booksBorrowedToday;

    public static final boolean AUTHOR = true;
    public static final boolean GENRE = false;

    public Library() {
        this.booksInStockList = new ArrayList<Book>();
        this.borrowedBooksList = new ArrayList<Book>();
        scanner = new Scanner(System.in);
        //Lade till nedan: Initializer för booksBorroedToday
        this.booksBorrowedToday = 0;
        stockLibrary();
    }

    //GAMMAL ArrayList<Book> borrowBook
    /*
    public ArrayList<Book> borrowBook(String title) {

        for (int i = 0; i < booksInStockList.size(); i++) {
            Book book = booksInStockList.get(i);
            if (book.getName().equalsIgnoreCase(title)) {
                this.borrowedBooksList.add(book);
            }
        }
        System.out.println();

        return borrowedBooksList;
    }
    */
 public ArrayList<Book> borrowBook(String title) {
        // Lade till nedan: RULE 1 - Only 1 book per day ("borrow" eller "extend" funkar räknas som samma funktion)
        if (booksBorrowedToday >= 1) {
            System.out.println("You can only borrow 1 book per day!");
            return borrowedBooksList;
        }

        // Lade till nedan: Rule 2 - Max 5 borrowed books at a time
        if (borrowedBooksList.size() >= 5) {
            System.out.println("You already have 5 books borrowed!");
            return borrowedBooksList;
        }

        // Lade till nedan: Rule 3 - Only 1 book per title
        for (Book borrowedBook : borrowedBooksList) {
            if (borrowedBook.getName().equalsIgnoreCase(title)) {
                System.out.println("You already have this book borrowed!");
                return borrowedBooksList;
            }
        }

        // Lade till: Kolla för !book.isBorrowed() för att inta låna utt sama bok två gånger
        // Lade till: Incrementer för booksborrowedToday counter
        for (int i = 0; i < booksInStockList.size(); i++) {
            Book book = booksInStockList.get(i);
            if (book.getName().equalsIgnoreCase(title) && !book.isBorrowed()) {
                book.borrowBook();
                this.borrowedBooksList.add(book);
                this.booksBorrowedToday++;
                System.out.println("Successfully borrowed: " + title);
                break;
            }
        }
        System.out.println();

        return borrowedBooksList;
    }





    public int returnBook(String title) {

        int totalLateFee = 0;

        for (int i = 0; i < borrowedBooksList.size(); i++) {
            Book book = borrowedBooksList.get(i);
            if (book.getName().equalsIgnoreCase(title)) {
                int fee = book.checkLateFee();
                totalLateFee += fee;

                // Lade till nedan: Fattades funktion för att faktiskt ta bort en bok.
                book.returnBook();
                borrowedBooksList.remove(i);
                System.out.println("Returned: " + title);
                break;
                //
            }
        }
        System.out.println("You owe us " + totalLateFee + " kr in late fees.");
        System.out.println();

        return totalLateFee;
    }





    public ArrayList<Book> listBorrowedBooks(boolean includeDaysBorrowed) {
        for (int i = 0; i < borrowedBooksList.size(); i++) {
            Book book = borrowedBooksList.get(i);
            System.out.print("Title: " + book.getName());
            if (includeDaysBorrowed) {
                System.out.println(", Days borrowed: " + book.getDaysBorrowed());
            } else {
                System.out.println();
            }
        }
        System.out.println();
        return borrowedBooksList;
    }




    public int extendTime(String title) {
        // Lade till nedan: Rule 7 - Extend counts as borrowing 1 book (1 action per day)
        if (booksBorrowedToday >= 1) {
            System.out.println("You can only borrow or extend 1 book per day!");
            return Integer.MIN_VALUE;
        }
        //

        for (int i = 0; i < borrowedBooksList.size(); i++) {
            Book book = borrowedBooksList.get(i);
            if (book.getName().equalsIgnoreCase(title)) {
                book.extendTime();
                // Lade till nedan: Incrementa counter eftersom Extend funkar på samma sätt som att låna en bok.
                this.booksBorrowedToday++;
                System.out.println("Extended borrowing time for: " + title);
                //
                return book.getDaysBorrowed();
            }
        }

        return Integer.MIN_VALUE;
    }



    // Ändrade alla == till .equals() för att jämföra det rätt.
    public void listBorrowedBooksBy(boolean author) {
        if (author) {
            System.out.println("Authors: ");
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                System.out.println(book.getAuthor());
            }
            System.out.print("> ");

            String input = scanner.nextLine();
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                // == till .equals()
                if (book.getAuthor().equals(input)) {
                //
                    System.out.println("Title: " + book.getName());
                }
            }

        } else {
            System.out.println("Genres: ");
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                System.out.println(book.getGenre());
            }
            System.out.print("> ");

            String input = scanner.nextLine();
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                // == till .equals()
                if (book.getGenre().equals(input)) {
                //
                    System.out.println("Title: " + book.getName());
                }
            }
        }
    }


    //Gammal advanceDay
    //
     /*public void advanceDay() {
        for (int i = 0; i < booksInStockList.size(); i++) {
            booksInStockList.get(i).advanceDay();
        }
    }*/

    // ÄNDRADE: Från booksInStockList till borrowedBooksList
    // Lade till: Reset daily borrow counter
    public void advanceDay() {
        // Lade till nedan: Återställ "daily borrow counter" för ny dag.
        this.booksBorrowedToday = 0;

        //ÄNDRADE: Ändrade från "booksInStockList" till "borrowedBooksList"
        // Dagar ska bara "advance" för lånade böcker, inte de som finns i lager.
        for (int i = 0; i < borrowedBooksList.size(); i++) {
            borrowedBooksList.get(i).advanceDay();
        }
    }

    public ArrayList<Book> listAvailableBooks() {
        for (int i = 0; i < booksInStockList.size(); i++) {
            System.out.println("Title: " + booksInStockList.get(i).getName());
        }

        return booksInStockList;
    }




    private void stockLibrary() {
        for (int i = 0; i < 5; i++) {
            Book book = new Book("Harry Potter", "Fantasy", "J.K Rowling");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 10; i++) {
            Book book = new Book("Hitchhiker's guide to the galaxy", "Sci-Fi", "Douglas Adams");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 3; i++) {
            Book book = new Book("It ends with us", "Romance", "Colleen Hoover");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 4; i++) {
            Book book = new Book("Ondskan", "Fictional Autobiography", "Jan Guillou");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 5; i++) {
            Book book = new Book("Tempelriddaren", "Historical Fiction", "Jan Guillou");
            booksInStockList.add(book);
        }
        //LADE TILL NEDAN: extra book
         for (int i = 0; i < 5; i++) {
            Book book = new Book("Game of Thrones", "Thriller Adventure", "George R.R. Martin");
            booksInStockList.add(book);
        }
    }
}
















//GAMMAL KOD:
/*import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private ArrayList<Book> booksInStockList;
    private ArrayList<Book> borrowedBooksList;
    private Scanner scanner;

    public static final boolean AUTHOR = true;
    public static final boolean GENRE = false;

    public Library() {
        this.booksInStockList = new ArrayList<Book>();
        this.borrowedBooksList = new ArrayList<Book>();
        scanner = new Scanner(System.in);
        stockLibrary();
    }

    public ArrayList<Book> borrowBook(String title) {

        for (int i = 0; i < booksInStockList.size(); i++) {
            Book book = booksInStockList.get(i);
            if (book.getName().equalsIgnoreCase(title)) {
                this.borrowedBooksList.add(book);
            }
        }
        System.out.println();

        return borrowedBooksList;
    }

    public int returnBook(String title) {

        int totalLateFee = 0;

        for (int i = 0; i < borrowedBooksList.size(); i++) {
            Book book = borrowedBooksList.get(i);
            if (book.getName().equalsIgnoreCase(title)) {
                int fee = book.checkLateFee();
                totalLateFee += fee;
            }
        }
        System.out.println("You owe us " + totalLateFee + " kr in late fees.");
        System.out.println();

        return totalLateFee;
    }

    public ArrayList<Book> listBorrowedBooks(boolean includeDaysBorrowed) {
        for (int i = 0; i < borrowedBooksList.size(); i++) {
            Book book = borrowedBooksList.get(i);
            System.out.print("Title: " + book.getName());
            if (includeDaysBorrowed) {
                System.out.println(", Days borrowed: " + book.getDaysBorrowed());
            } else {
                System.out.println();
            }
        }
        System.out.println();
        return borrowedBooksList;
    }

    public int extendTime(String title) {

        for (int i = 0; i < borrowedBooksList.size(); i++) {
            Book book = borrowedBooksList.get(i);
            if (book.getName().equalsIgnoreCase(title)) {
                book.extendTime();
                return book.getDaysBorrowed();
            }
        }

        return Integer.MIN_VALUE;
    }

    public void listBorrowedBooksBy(boolean author) {
        if (author) {
            System.out.println("Authors: ");
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                System.out.println(book.getAuthor());
            }
            System.out.print("> ");

            String input = scanner.nextLine();
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                if (book.getAuthor() == input) {
                    System.out.println("Title: " + book.getName());
                }
            }

        } else {
            System.out.println("Genres: ");
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                System.out.println(book.getGenre());
            }
            System.out.print("> ");

            String input = scanner.nextLine();
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                if (book.getGenre() == input) {
                    System.out.println("Title: " + book.getName());
                }
            }
        }
    }

    public void advanceDay() {
        for (int i = 0; i < booksInStockList.size(); i++) {
            booksInStockList.get(i).advanceDay();
        }
    }

    public ArrayList<Book> listAvailableBooks() {
        for (int i = 0; i < booksInStockList.size(); i++) {
            System.out.println("Title: " + booksInStockList.get(i).getName());
        }

        return booksInStockList;
    }

    private void stockLibrary() {
        for (int i = 0; i < 5; i++) {
            Book book = new Book("Harry Potter", "Fantasy", "J.K Rowling");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 10; i++) {
            Book book = new Book("Hitchhiker's guide to the galaxy", "Sci-Fi", "Douglas Adams");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 3; i++) {
            Book book = new Book("It ends with us", "Romance", "Colleen Hoover");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 4; i++) {
            Book book = new Book("Ondskan", "Fictional Autobiography", "Jan Guillou");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 5; i++) {
            Book book = new Book("Tempelriddaren", "Historical Fiction", "Jan Guillou");
            booksInStockList.add(book);
        }
    }
}*/
