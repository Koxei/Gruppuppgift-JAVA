package dev.library;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LibraryTest {
    private Library library;

    @Before
    public void setUp() {
        library = new Library();
    }
     
    @Test
    public void testBorrowUpToFiveBooks() {
        library.borrowBook("Harry Potter");
        library.borrowBook("Hitchhiker's guide to the galaxy");
        library.borrowBook("It ends with us");
        library.borrowBook("Ondskan");
        library.borrowBook("Tempelriddaren");

        ArrayList<Book> borrowed = library.listBorrowedBooks(false);
        assertEquals(5, borrowed.size());
    }
    
    @Test
    public void testCannotBorrowMoreThanFiveBooks() {
        library.borrowBook("Harry Potter");
        library.borrowBook("Hitchhiker's guide to the galaxy");
        library.borrowBook("It ends with us");
        library.borrowBook("Ondskan");
        library.borrowBook("Tempelriddaren");
        library.borrowBook("Harry Potter");

        ArrayList<Book> borrowed = library.listBorrowedBooks(false);
        assertEquals(5, borrowed.size());
    }

    @Test
    public void testReturnBookDecreasesBorrowedCount() {
        library.borrowBook("Harry Potter");
        library.returnBook("Harry Potter");

        ArrayList<Book> borrowed = library.listBorrowedBooks(false);
        assertEquals(0, borrowed.size());
    }
    @Test
    public void testCannotBorrowForMoreThanSevenDays() {
    library.borrowBook("Harry Potter", 10); 

    
    assertEquals(0, library.listBorrowedBooks(false).size());
}
    @Test
public void testCanBorrowForFiveDays() {
    library.borrowBook("Harry Potter", 5); 

    
    assertEquals(0, library.listBorrowedBooks(false).size());
}

@Test
public void testBorrowForMoreThanTwoDays() {
    library.borrowBook("Harry Potter", 2); //

   
    assertEquals(0, library.listBorrowedBooks(false).size());
}




}
