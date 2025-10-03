package dev.library;


import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class LibraryTest {

    //=======================================
    //The user may only borrow 1 book per day
    //=======================================
    @Test
    public void borrowBook_whenFirstBorrowOfDay_shouldSucceed() {
        Library library = new Library();
        
        library.borrowBook("Harry Potter");
        
        assertEquals("User should be able to borrow this one book.", 
                     1, library.listBorrowedBooks(false).size());
                     System.out.print("andradan");
    }
    @Test
    public void borrowBook_whenBorrowingTwoBooksSameDay_shouldFail() {
        Library library = new Library();
        
        library.borrowBook("Harry Potter");
        library.borrowBook("Ondskan");
        
        assertEquals("Only the first borrow should succeed on the same day", 
                     1, library.listBorrowedBooks(false).size());
    }

    @Test
    public void borrowBook_whenBorrowingOnSeparateDays_shouldAllowNewBorrow() {
        Library library = new Library();

        library.borrowBook("Harry Potter");
        library.advanceDay(); 
        library.borrowBook("Ondskan"); 

        assertEquals(2, library.listBorrowedBooks(false).size());
    }



    //==========================================================
    //The user may return as many books as he/she wishes per day
    //==========================================================
    @Test
    public void returnBook_whenSingleBookBorrowed_shouldBeRemovedFromBorrowedList() {
        Library library = new Library();

        library.borrowBook("Harry Potter");
        library.returnBook("Harry Potter");

        assertEquals("Borrowed list should be empty after returning the only book",
                    0, library.listBorrowedBooks(false).size());
    }

    @Test
    public void returnBook_whenAllBorrowedBooksReturned_shouldEmptyBorrowedList() {
        Library library = new Library();

        library.borrowBook("Harry Potter");
        library.borrowBook("Ondskan");
        library.borrowBook("It ends with us");

        library.returnBook("Harry Potter");
        library.returnBook("Ondskan");
        library.returnBook("It ends with us");

        assertEquals("Borrowed list should be empty after returning all books", 
                    0, library.listBorrowedBooks(false).size());
    }

    //===============================================================================================================
    //The user can use the extend function to reset the book back to 0 days borrowed, this counts as borrowing 1 book
    //===============================================================================================================
    @Test
    public void extendTime_whenCalled_shouldResetDaysBorrowedToZero() {
    Library library = new Library();

    library.borrowBook("Harry Potter");
    for (int i = 0; i < 5; i++) {
        library.advanceDay();
    }

    int daysAfterExtend = library.extendTime("Harry Potter");

    assertEquals("After extending, days borrowed should be reset to 0", 0, daysAfterExtend);

    //borrowed book should still exists in listBorrowedBooks
    ArrayList<Book> borrowed = library.listBorrowedBooks(false);
    assertEquals("Borrowed list should still contain the book after extending", 1, borrowed.size());
    assertEquals("The book should still be 'Harry Potter'", "Harry Potter", borrowed.get(0).getName());
    }   

    @Test
    public void borrowThenExtend_sameDay_shouldFail() {
        Library library = new Library();

        library.borrowBook("Harry Potter");
        library.extendTime("Harry Potter");

        assertEquals("Borrow or extend counts as 1 action per day, so second action should fail", 
                    1, library.listBorrowedBooks(false).size());
    }

    @Test
    public void extendWithoutPriorBorrow_sameDay_shouldSucceed() {
        Library library = new Library();

        library.borrowBook("Harry Potter");
        library.advanceDay(); 
        library.extendTime("Harry Potter");

        Book book = library.listBorrowedBooks(false).get(0);

        assertEquals("Extending a book on a day without other borrowings should reset days borrowed", 
                    0, book.getDaysBorrowed());

        assertEquals("Borrowed list should still contain only the extended book", 
                    1, library.listBorrowedBooks(false).size());
    }


    
   

}