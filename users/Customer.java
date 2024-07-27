package users;
import items.*;
import java.util.*;
public class Customer extends User {


    private Cart currunt_Cart;
    ArrayList<Cart>cartHistory;
    // ArrayList<Book>reserved_books=new ArrayList<>();
    ArrayList<Reservation>reserved_books=new ArrayList<>();
    Review r;
    private static Scanner scanner =new Scanner(System.in);
    public Customer(String firstName,String lastName,long phoneNumber,String email, String userName,String password) {
        super(firstName,lastName, phoneNumber,email, userName,password);
        cartHistory= new ArrayList<>();
        currunt_Cart=new Cart();
    }
    public Cart getCurrunt_Cart() {
        return currunt_Cart;
    }
    public void removeItemFromCart(){
        System.out.println("Enter the id of book \nId:");
        int removeId = scanner.nextInt();
        currunt_Cart.removeoneorder(removeId);
    }
    //public void updateCartItemQuantity(int quantity){

    public void ViewOrdersHistory() {
        if (!cartHistory.isEmpty()) {
            System.out.println("Order History:");
            int ordernumber = 1;
            for (Cart cartitem : cartHistory) {
                System.out.println("Order " + ordernumber++ + " :");
                cartitem.viewCartItems();
            }
        } else {
            System.out.println(" No Order History Available.");
        }
    }
    public void reserve_book(Reservation reservation ){
        reserved_books.add(reservation);
        System.out.println("Book has been reserved successfully.");
    }
    public boolean check__book_availability(Book book, int quantity){
        return true;
    }
    /* public void send_notification(){
         for (Reservation reserved : reserved_books) {
             if(reserved.getReserve_book().Amount_books==reserved.getQuantity()){
                 System.out.println(reserved.getReserve_book().title+"has been available You can continue your buy operation now.");
                 reserved_books.remove(reserved);
             }
             else
                 continue;
         }
     }
     /*public void display_reservation(){
         System.out.println("Books you reserved are:");
         for (Reservation reserved : reserved_books) {
             System.out.println(reserved.getReserve_book().title+"  "+ reserved.getQuantity());
         }
         }
     public void display_reservation(){
         if (!reserved_books.isEmpty()) {
             System.out.println("The books you reserved are:");
             for (Reservation reserved : reserved_books) {
                 reserved.display_reservation();
             }
         }
         else {
             System.out.println("There are no reserved books .");
         }
     }*/


    public void checkout() {
        System.out.println("Total price:" + currunt_Cart.calcPrice());
        float discount = currunt_Cart.discount();
        if (discount != 0.0f) {
            float priceAfterDiscount = 100.0f - discount;
            System.out.println("You have discount" + priceAfterDiscount + "% \nYour total price after dicount:" + discount);
        }
        System.out.println("Enter the number of option you want to use\n1-Cash\n2-Credit Card");
        int paymentOption = scanner.nextInt();
        if(paymentOption==1){
            currunt_Cart.setPaymentMethod("Cash");
        }
        else{
            currunt_Cart.setPaymentMethod("Credit Card");
        }
        cartHistory.add(currunt_Cart);
        currunt_Cart=new Cart();
    }

    public void Make_review(Book book, String comment, int rating){
        r=new Review(rating,this,comment,book);
        book.addReview(r);
    }
    public boolean buy(Book currentBook,int quantity){
        Order order = new Order(currentBook, quantity);
        if (order.check(quantity, currentBook)) {
            currunt_Cart.addItemtoCart(order);
            return true;
        }
        else{
            return false;
        }
    }
    public void Delete_review(Book book,int id){
        if(book.deleteReview(id,this)){
            System.out.println("The review has been deleted successfully");
        }
        else{
            System.out.println("You cannot delete another customer's review");
        }
    }
    public  void welcomeHome(){
        System.out.println("Welcome "+userName+" "+"in customer page");
    }
}

