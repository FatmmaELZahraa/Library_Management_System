package items;

import java.io.Serializable;
import java.util.*;
public class Cart implements Serializable {
    private Date orderDate;
    private  static int nextCart_Id=1;
    private   int Cart_Id;
    private  String paymentMethod;
    ArrayList<Order>orders;
    public Cart (){
        this.orders=new ArrayList<>();
        this.orderDate=new Date();
        this.Cart_Id=nextCart_Id++;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void addItemtoCart(Order neworder)
    {
        orders.add(neworder);
    }

    public void removeoneorder(int item)
    {
        orders.remove(item-1);
    }
    public void clearCart()
    {
        orders.clear();
    }
    public void viewCartItems() {
        int size = orders.size();
        for (int i = 0; i < size; i++) {
            System.out.println("Order Id:"+orders.get(i).getOrderId());
            System.out.println("Book Title: " + orders.get(i).getBook().getTitle());
            System.out.println("Quantity: " + orders.get(i).getQuantity());
            System.out.println("Price: " + orders.get(i).getPrice());
        }
    }
    public float calcPrice() {
        float price =0;
        int size=orders.size();
        for(int i=0;i<size;i++){

            price=price + (orders.get(i).getQuantity()*orders.get(i).getPrice());
        }
        return price;
    }
    public float discount () {
        int numOfBook = 0;
        float priceAfterDiscount = 0;
        int size = orders.size();
        for (int i = 0; i < size; i++) {
            numOfBook = numOfBook + (orders.get(i).getQuantity());
        }
        if (numOfBook >= 3){
            priceAfterDiscount = (calcPrice() * 90 / 100);
        return  priceAfterDiscount;
    }
        else if (numOfBook >= 10){
            priceAfterDiscount = (calcPrice() * 80 / 100);
        return priceAfterDiscount;
    }
        else{
            return 0.0f;
        }
    }
    public boolean findBook(int bookId){
         boolean found=false;
        for (Order o:orders) {
            if (o.getBook().getId() == bookId) {
                found = true;
                break;
            }
        }
        return found;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void changequantity(int id) {
        Order neworder = orders.get(id - 1);
        int num = neworder.getBook().getAmountbooks();
        int num1 = neworder.getQuantity();
        neworder.getBook().setAmountbooks(num + num1);
    }
    public void changeItemtoCart(Order neworder,int quantity) {
        int num = neworder.getBook().getAmountbooks();
        neworder.getBook().setAmountbooks(num - quantity);
        neworder.setQuantity(quantity);
    }
    public Order findOrder(int id) {
        Order order = null;
        for (Order o : orders) {
            if (o.getOrderId() == id) {
                order = o;
                break;
            }
        }
        return order;
    }
}