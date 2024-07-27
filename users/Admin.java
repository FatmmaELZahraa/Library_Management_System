
package users;
import items.*;
import system.Library;
public class Admin extends User {


    public Admin(String ahmed, String samy, long i, String s, String admin, String string){
        super("Ahmed","Ali",(long)01145,"ahmedali@gmail.com","admin","admin");
    }
    public void addBook(Library library,Book book) {
        library.getBooks().add(book);
    }


    public void updateBookInfo(Book book, float newPrice, boolean newAvailability) {
        book.setPrice(newPrice);
        book.setAvailable(newAvailability);
        System.out.println("Book information updated: " + book.getTitle());
    }
    public void addBorrower(Library library, Borrower borrower) {
        library.getBorrower().add(borrower);
    }

    public void updateBorrowerDetails(Borrower borrower, long newPhoneNumber, String newEmail) {
        borrower.setPhoneNumber(newPhoneNumber);
        borrower.setEmail(newEmail);
        System.out.println("Borrower details updated: " + borrower.getUserName());
    }
    public  void welcomeHome(){
        System.out.println("Welcome in Admin page");
    }

}