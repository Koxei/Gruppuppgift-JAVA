package dev.library;


import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {

    @Test
    public void testUserCanOnlyBorrowOneBookPerDay() {
        Library library = new Library();
        
        
        library.borrowBook("Harry Potter");
        library.borrowBook("Hitchhiker's guide to the galaxy");
        
     
        assertEquals("User should only be able to borrow one book per day", 
                     1, library.listBorrowedBooks(false).size());

                     System.out.println("hej");
    }
    
}