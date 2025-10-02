package dev.library;


import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {

    @Test
    public void testUserCanOnlyBorrowOneBookPerDay() {
        Library library = new Library();
        
        library.borrowBook("Simon");
        library.borrowBook("Hello book");
        
        assertEquals("User should only be able to borrow one book per day", 
                     1, library.listBorrowedBooks(false).size());
    }
    
    // safsdfsdg
}