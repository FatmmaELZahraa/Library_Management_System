package items;
import users.*;

import java.io.Serializable;

public class Reservation implements Serializable {
    private Borrower borrower;
    private Book book;
    private boolean isReserved;

    public Reservation(Book book, Borrower borrower) {
        this.book = book;
        this.borrower = borrower;
        isReserved = false;
    }
    public  Book getBook() {
        return book;
    }
    public  Borrower getUser() {
        return borrower;
    }
    public void reservedBook() {
        if (!isReserved) {
            System.out.println("reservation   is  successful" + "  " + book.getTitle() + " " + "by" + " " + borrower.getUserName());
            isReserved = true;
        } else {
            System.out.println(" the book is  already reserved.");
        }
    }
    public void displayReservation(){
        System.out.println("Reserved book: "+book.getTitle());
    }

}


