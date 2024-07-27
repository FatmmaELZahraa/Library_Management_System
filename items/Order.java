package items;

import java.io.Serializable;

public class Order implements Serializable {
        private static int nextOrder = 1;
        private int orderId;
        private float price;
        private Book book;
        private int quantity;
        public Order(Book book, int quantity) {
            this.orderId=nextOrder++;
            this.book = book;
            this.quantity = quantity;
            this.price=book.getPrice();
        }
        public boolean check(int quantity,Book book) {
            if (book.getAmountbooks() < quantity&&quantity<=0) {
                return false;
            } else {
                return true;
            }
        }
        public Book getBook() {
            return book;
        }
        public float getPrice() {
            return price;
        }
        public int getOrderId() {
            return orderId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }


    }

