package dev.library;


import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {

    @Test
    public void testUserCanOnlyBorrowOneBookPerDay() {
        // Arrange
        Library library = new Library();
        
        // Act - Try to borrow two books in the same "day"
        library.borrowBook("Harry Potter");
        library.borrowBook("Hitchhiker's guide to the galaxy");
        
        // Assert - User should only have 1 book borrowed, not 2
        assertEquals("User should only be able to borrow one book per day", 
                     1, library.listBorrowedBooks(false).size());
    }
}