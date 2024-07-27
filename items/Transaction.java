package items;
import users.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable {
    private Book book;
    private Borrower borrower;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    boolean isReturn;
    private final float fine=5f;

    public Transaction(Book book, Borrower borrower) {
        this.book = book;
        this.borrower = borrower;
        Date thisDate;
        thisDate = new Date();
        borrowDate=thisDate;
        Date temp;
        temp = new Date(borrowDate.getTime() + 7 * 24 * 60 * 60 * 1000);
        this.dueDate = temp;
        isReturn = false;
    }

    public boolean isLate() {
        if (isReturn)
            return false;
        long days = (dueDate.getTime() - borrowDate.getTime()) / (24 * 60 * 60 * 1000);
        if (days > 7)
            return true;
        return false;
    }

    public int remainingDays() {
        if (isReturn)
            return -1;
        return (int)(dueDate.getTime() - borrowDate.getTime()) / (24 * 60 * 60 * 1000);

    }

    public void returnBook() {
        isReturn = true;
        book.updatequantityBorrow(1, false);
        Date thisDate;
        thisDate = new Date();
        returnDate=thisDate;
    }

    public void displayTransaction() {
        System.out.print("User Name: " + borrower.getFirstName() + "  Book Title:  " + book.getTitle() + " at date: ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.print(sdf.format(borrowDate));
        if (isReturn) {
            System.out.println(" And returned at date: " + sdf.format(returnDate));
        } else
            System.out.println(" And doesn't have returned yet for. You should return book before "+dueDate);

    }

    public float getTransactionFine(){
        int test=remainingDays();
        if(test==-1)
            return 0;
        return test*5;
    }
    public long getBorrowDate() {
        return borrowDate.getTime();
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }
    public Date getDueDate(){
        return dueDate;
    }
}

