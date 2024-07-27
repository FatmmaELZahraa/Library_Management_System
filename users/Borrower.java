package users;

import items.*;

import java.util.ArrayList;
import java.util.Date;


public class Borrower extends User {
    private ArrayList<Reservation> reservations;
    private ArrayList<Transaction> transactions;
    private ArrayList<Notification> notifications;
    private float fines;

    public Borrower(String firstName, String lastName, long phoneNumber, String email, String userName, String password) {
        super(firstName, lastName, phoneNumber, email, userName, password);
        reservations = new ArrayList<>();
        transactions = new ArrayList<>();
        notifications=new ArrayList<>();
        fines = 0.0f;
    }

    public  void welcomeHome(){
        System.out.println("Welcome "+userName+"in Borrower page");
    }
    public Reservation getReservationByBook(Book book) {
        for (Reservation r : reservations) {
            if (r.getBook().getId() == book.getId()) {
                return r;
            }
        }
        return null;
    }

    public void viewReservations() {
        System.out.println("Username: " + userName);
        for (Reservation r : reservations) {
            r.displayReservation();
        }
    }

    public void viewTransactions() {
        for (Transaction t : transactions) {
           t.displayTransaction();
        }
    }

    public float getFines() {
        return fines;
    }

    public void calculateFines() {
        for (Transaction t : transactions) {
            fines += t.getTransactionFine();
        }
    }

    public boolean checkAvailability(Book book) {
        return book.isBorrowAvailable();
    }

    public void borrowBook(Book book) {

        Transaction transaction = new Transaction(book, this);
        transactions.add(transaction);
        book.setBorrowAvailable(false);
        System.out.println(book.getTitle() + " book borrowed successfully!");
    }

    public void returnBook(Book book) {
        for (Transaction t : transactions) {
            if (t.getBook().getId() == book.getId()) {
                t.setReturnDate(new Date());
                System.out.println(book.getTitle() + " book returned successfully!");
            }
        }
    }

    public void addReservation(Book book) {
        Reservation reservationFound = getReservationByBook(book);

        if (reservationFound != null) {
            System.out.println(book.getTitle() + " book is already reserved!");
        } else {
            Reservation reservation = new Reservation(book, this);
            reservations.add(reservation);
            Notification n = new Notification(book.getTitle() + " book reserved successfully.",this);
            System.out.println(n);
        }
    }

    public void cancelReservation(Book book) {
        Reservation reservation = getReservationByBook(book);
        if (reservation != null) {
            reservations.remove(reservation);
            System.out.println("Your reservation has been successfully cancelled");
        } else {
            System.out.println("You did not reserve this book!");
        }
    }

    public void addReview(int rating, String comment, Book book) {
        Review review = new Review(rating, this, comment, book);
        book.addReview(review);
    }

    public void deleteReviewByID(Book book, int reviewId) {
        if (book.deleteReview(reviewId, this)) {
            System.out.println("The review has been deleted successfully");
        } else {
            System.out.println("You cannot delete another customer's review");
        }
    }

    public void addNotification() {
        Date currentDate = new Date();
        for (Reservation r : reservations) {
            if (r.getBook().isBorrowAvailable()) {
                Notification notification = new Notification(r.getBook().getTitle() + " book is available for borrowing. Please borrow it within three days.",this);
                notifications.add(notification);
            }
        }
        for (Transaction t : transactions) {
            if (t.getReturnDate() != null) {
                if (t.getDueDate().before(t.getReturnDate())) {
                    Notification notification = new Notification("You have exceeded the due date for" + t.getBook().getTitle() + " book. You must pay the fine!!!",this);
                    notifications.add(notification);
                }
            } else {
                if (t.getDueDate().before(currentDate)) {
                    Notification notification = new Notification("You have exceeded the due date for" + t.getBook().getTitle() + " book. Please return the book immediately and pay the fine!!!",this);
                    notifications.add(notification);
                }
            }
        }
    }

    public void removeNotification(int notificationId) {
        notifications.removeIf(n -> n.getNotificationId() == notificationId);
    }


    public void viewNotification() {
        for (Notification n : notifications) {
            n.displayNotification();
        }

    }
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

}