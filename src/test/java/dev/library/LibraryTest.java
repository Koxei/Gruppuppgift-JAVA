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

    //Simon
    //=======================================
    //The user may only borrow 1 book per day
    //=======================================
    @Test
    public void borrowBook_whenFirstBorrowOfDay_shouldSucceed() {
        library.borrowBook("Harry Potter");
        
        assertEquals("User should be able to borrow this one book.", 
                     1, library.listBorrowedBooks(false).size());
    }
    
    @Test
    public void borrowBook_whenBorrowingTwoBooksSameDay_shouldFail() {
        library.borrowBook("Harry Potter");
        library.borrowBook("Ondskan");
        
        assertEquals("Only the first borrow should succeed on the same day", 
                     1, library.listBorrowedBooks(false).size());
    }

    @Test
    public void borrowBook_whenBorrowingOnSeparateDays_shouldAllowNewBorrow() {
        library.borrowBook("Harry Potter");
        library.advanceDay(); 
        library.borrowBook("Ondskan"); 

        assertEquals(2, library.listBorrowedBooks(false).size());
    }

    //===============================================================================================================
    //The user can use the extend function to reset the book back to 0 days borrowed, this counts as borrowing 1 book
    //===============================================================================================================
    @Test
    public void extendTime_whenCalled_shouldResetDaysBorrowedToZero() {
        library.borrowBook("Harry Potter");
        for (int i = 0; i < 5; i++) {
            library.advanceDay();
        }

        int daysAfterExtend = library.extendTime("Harry Potter");

        assertEquals("After extending, days borrowed should be reset to 0", 0, daysAfterExtend);

        ArrayList<Book> borrowed = library.listBorrowedBooks(false);
        assertEquals("Borrowed list should still contain the book after extending", 1, borrowed.size());
        assertEquals("The book should still be 'Harry Potter'", "Harry Potter", borrowed.get(0).getName());
    }   

    @Test
    public void borrowThenExtend_sameDay_shouldFail() {
        library.borrowBook("Harry Potter");
        library.extendTime("Harry Potter");

        assertEquals("Borrow or extend counts as 1 action per day, so second action should fail", 
                    1, library.listBorrowedBooks(false).size());

         Book book = library.listBorrowedBooks(false).get(0);
         assertEquals("Extend should fail on same day as borrow, days should remain at 0", 
                     0, book.getDaysBorrowed());
    }

    @Test
    public void extendWithoutPriorBorrow_sameDay_shouldSucceed() {
        library.borrowBook("Harry Potter");
        library.advanceDay(); 
        library.extendTime("Harry Potter");

        Book book = library.listBorrowedBooks(false).get(0);

        assertEquals("Extending a book on a day without other borrowings should reset days borrowed", 
                    0, book.getDaysBorrowed());

        assertEquals("Borrowed list should still contain only the extended book", 
                    1, library.listBorrowedBooks(false).size());
    }

    //HZ
    //====================================================
    //The user may at most have 5 borrowed books at a time
    //====================================================
    @Test
    public void borrowBook_whenBorrowingUpToFiveBooks_shouldSucceed() {
        library.borrowBook("Harry Potter");
        library.advanceDay();
        library.borrowBook("Hitchhiker's guide to the galaxy");
        library.advanceDay();
        library.borrowBook("It ends with us");
        library.advanceDay();
        library.borrowBook("Ondskan");
        library.advanceDay();
        library.borrowBook("Tempelriddaren");

        ArrayList<Book> borrowed = library.listBorrowedBooks(false);
        assertEquals("User should be able to borrow up to 5 books", 5, borrowed.size());
    }

    @Test
    public void borrowBook_whenAttemptingToExceedFiveBooks_shouldFail() {
        library.borrowBook("Harry Potter");
        library.advanceDay();
        library.borrowBook("Hitchhiker's guide to the galaxy");
        library.advanceDay();
        library.borrowBook("It ends with us");
        library.advanceDay();
        library.borrowBook("Ondskan");
        library.advanceDay();
        library.borrowBook("Tempelriddaren");
        library.advanceDay();
        library.borrowBook("Harry Potter");

        ArrayList<Book> borrowed = library.listBorrowedBooks(false);
        assertEquals("User should not be able to borrow more than 5 books", 5, borrowed.size());
    }

    //==========================================================
    //The user may return as many books as he/she wishes per day
    //==========================================================
    @Test
    public void returnBook_whenSingleBookBorrowed_shouldBeRemovedFromBorrowedList() {
        library.borrowBook("Harry Potter");
        library.returnBook("Harry Potter");

        assertEquals("Borrowed list should be empty after returning the only book",
                    0, library.listBorrowedBooks(false).size());
    }

    @Test
    public void returnBook_whenAllBorrowedBooksReturned_shouldEmptyBorrowedList() {
        library.borrowBook("Harry Potter");
        library.advanceDay();
        library.borrowBook("Ondskan");
        library.advanceDay();
        library.borrowBook("It ends with us");

        library.returnBook("Harry Potter");
        library.returnBook("Ondskan");
        library.returnBook("It ends with us");

        assertEquals("Borrowed list should be empty after returning all books", 
                    0, library.listBorrowedBooks(false).size());
    }

    //Nour
    //===========================================
    //The user may only borrow one book per title
    //===========================================
    @Test
    public void borrowBook_whenAttemptingToBorrowSameTitleTwice_shouldFail() {
        library.borrowBook("Harry Potter");
        library.borrowBook("Harry Potter");

        int borrowedCount = library.listBorrowedBooks(false).size();

        assertEquals("User should only be able to borrow one book per title", 1, borrowedCount);
    }

    @Test
    public void borrowBook_whenBorrowingOneTitle_shouldSucceed() {
        library.borrowBook("Harry Potter");

        int borrowedCount = library.listBorrowedBooks(false).size();

        assertEquals("User should be able to borrow one book per title", 1, borrowedCount);
    }

    //===================================================================
    //The user may only borrow a book for 7 days straight
    //For every day the book is late, the user has to pay a fine of 20 kr
    //===================================================================
    @Test
    public void returnBook_whenBookIsLate_shouldCharge20KrPerLateDay() {
        library.borrowBook("Harry Potter");

        for (int i = 0; i < 10; i++) {
            library.advanceDay();
        }

        int lateFee = library.returnBook("Harry Potter");

        assertEquals("Late fee should be 20 kr per late day (3 late days = 60 kr)", 60, lateFee);
    }

}