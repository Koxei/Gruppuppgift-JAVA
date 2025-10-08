package dev.library;

public class Book {
    private String name;
    private String genre;
    private String author;
    private boolean borrowed;
    private int daysBorrowed;

    public Book(String name, String genre, String author) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.borrowed = false;
        this.daysBorrowed = 0;
    }


    //Ändring: Ändrade från >= till >. Böcker ska vara sena EFTER 7 dagar.
    //Ändring: (daysBorrowed * 20) till ((daysBorrowed - 7) * 20).
    // För att bara dra avgift för SENA böcker och inte för ALLA dagar.
    //Gammal:
    /*    public int checkLateFee() {
        if (daysBorrowed >= 7) {
            System.out.println("The book is " + daysBorrowed + " days late");
            return daysBorrowed * 20;
        }
        return 0;
    }*/
    public int checkLateFee() {
        if (daysBorrowed > 7) {
            int lateDays = daysBorrowed - 7;
            System.out.println("The book is " + lateDays + " days late");
            return lateDays * 20;
        }
        return 0;
    }




    //Ändring: (this.daysBorrowed += -7) till (this.daysBorrowed = 0)
    // För att återställa dagarna till 0 och inte -7
    public void extendTime() {
        this.daysBorrowed = 0;
    }

    public void advanceDay() {
        this.daysBorrowed++;
    }

    public void borrowBook() {
        this.borrowed = true;
    }

    public void returnBook() {
        this.borrowed = false;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public int getDaysBorrowed() {
        return daysBorrowed;
    }
}









//GAMMAL KOD:
/*
 public class Book {
    private String name;
    private String genre;
    private String author;
    private boolean borrowed;
    private int daysBorrowed;

    public Book(String name, String genre, String author) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.borrowed = false;
        this.daysBorrowed = 0;
    }

    public int checkLateFee() {
        if (daysBorrowed >= 7) {
            System.out.println("The book is " + daysBorrowed + " days late");
            return daysBorrowed * 20;
        }
        return 0;
    }

    public void extendTime() {
        this.daysBorrowed += -7;
    }

    public void advanceDay() {
        this.daysBorrowed++;
    }

    public void borrowBook() {
        this.borrowed = true;
    }

    public void returnBook() {
        this.borrowed = false;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public int getDaysBorrowed() {
        return daysBorrowed;
    }
}
 */