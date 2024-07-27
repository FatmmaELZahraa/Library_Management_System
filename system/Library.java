package system;

import items.*;
import users.*;

import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {
    ArrayList<Book> books;
    ArrayList<Borrower> borrowers;
    ArrayList<Customer> customers;
    private Admin admin;

    public Library() {
        books = new ArrayList<>();
        borrowers = new ArrayList<>();
        customers = new ArrayList<>();
        admin = new Admin("ahmed", "samy", 0102526, "dfghkl;", "admin", "admin");
    }

    public boolean checkUser(String Uname) {
        for (Customer C : customers) {
            if (C.getUserName().equals(Uname)) {
                return false;
            }
        }
        return true;
    }

    public boolean checKEmail(String email) {
        for (Customer C : customers) {
            if (C.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPhone(long phone) {
        for (Customer C : customers) {
            if (C.getPhoneNumber() == phone) {
                return false;
            }
        }
        return true;
    }

    public void register(String fName, String lName, long phoneNumber, String email,String userName, String password) {
        System.out.println("If you want to sign up as a customer press 1 " + "\n" + "If you want to sign up as a borrower press 2 ");
        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();
        if (choice == 1) {
            Customer C = new Customer(fName, lName, phoneNumber, email, userName, password);
            customers.add(C);

        } else if (choice == 2) {
            Borrower B = new Borrower(fName, lName, phoneNumber, email, userName, password);
            borrowers.add(B);
        }
    }

    public User login(String userName, String password) {
        if (userName.equals("admin") && password.equals("admin")) {
            return admin;
        }
        for (Customer C : customers) {
            if (C.getUserName().equals(userName) && C.getPassword().equals(password)) {
                return C;
            }
        }
        for (Borrower B : borrowers) {
            if (B.getUserName().equals(userName) && B.getPassword().equals(password)) {
                return B;
            }
        }
        return null;
    }

    public void viewBooks() {
        for (Book b : books) {
            System.out.println(b);
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Borrower> getBorrower() {
        return borrowers;
    }

    public void borrowBook(Book book, Borrower borrower) {
        int index = books.indexOf(book);
        books.get(index).updatequantityBorrow(1,true);
        int indexBorrow=borrowers.indexOf(borrower);
        borrowers.get(indexBorrow).borrowBook(book);
    }

    public void viewBorrowerHistory(Borrower borrower) {
        int indexBorrow = borrowers.indexOf(borrower);
        borrowers.get(indexBorrow).viewTransactions();

    }

    public void returnBook(String title, Borrower borrower) {
        Book book=null;
        for(Book val:books){
            if(val.getTitle().equals(title)){
                book=val;
                break;
            }
        }
        int index;
        index = books.indexOf(book);
        books.get(index).updatequantityBorrow(1,true);
        int indexBorrow=borrowers.indexOf(borrower);
        borrowers.get(indexBorrow).returnBook(book);
    }

    public Book makeBook() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter title of the book");
        String title = input.nextLine();
        System.out.print("Enter author of the book");
        String author = input.nextLine();
        System.out.print("Enter publication Year of the book");
        int publicationYear = input.nextInt();
        System.out.print("Enter price of the book");
        float price = input.nextFloat();
        System.out.print("Enter amount of the book to buy");
        int amountBooks = input.nextInt();
        System.out.print("Enter amount of the book to borrow");
        int AmountBorrowBooks = input.nextInt();
        boolean available = false;
        if (amountBooks > 0) {
            available = true;
        }
        System.out.print("Enter type of the book");
        String type = input.next();
        Book book = new Book(title, author, publicationYear, price, amountBooks, available, type, AmountBorrowBooks);
        return book;
    }

    public Borrower makeBorrower() {
        //(String firstName, String lastName, String phoneNumber, String email, String userName, String password)
        Scanner input = new Scanner(System.in);
        System.out.print("Enter First Name ");
        String firstName = input.next();
        System.out.print("Enter Last Name ");
        String lastName = input.next();
        System.out.print("Enter phone number");
        long phoneNumber = input.nextLong();
        System.out.print("Enter email");
        String email = input.next();
        System.out.print("Enter user name");
        String userName = input.next();
        System.out.print("Enter password");
        String password = input.next();
        Borrower borrower = new Borrower(firstName, lastName, phoneNumber, email, userName, password);
        return borrower;
    }

    public int editBook(String title) {
        Scanner input = new Scanner(System.in);
        int found = -1;

        for (Book val : books) {
            if (val.getTitle().equals(title)) {
                System.out.print("Enter\n" + "1. to edit Price" + "\n" + "2. to edit amount of the book"+"\n3.to back");
                int ch;
                ch = input.nextInt();
                while (!(ch==1||ch==2||ch==3)) {
                    System.out.println("Invalid choice" + "\n" + "Enter" + "1. to edit Price" + "\n" + "2. to edit amount of the book"+"\n3.to back");
                    ch = input.nextInt();
                }
                if (ch == 1) {
                    System.out.println("Previous price = " + val.getPrice() + "Enter new Price: ");
                    float newPrice = input.nextFloat();
                    val.setPrice(newPrice);
                    return 1;
                } else if (ch == 2) {
                    System.out.println("Previous Amount of book = " + val.getAmountbooks() + "Enter new Amount: ");
                    int newAmount = input.nextInt();
                    val.setAmountbooks(newAmount);
                    return 1;
                } else if (ch==3) {
                    return 3;
                }
                break;
            }
        }
        return -1;
    }

    public int editBorrower(String userName) {
        Scanner input = new Scanner(System.in);
        int found = -1;
        for (Borrower val : borrowers) {
            if (val.getUserName().equals(userName)) {
                System.out.print("Enter" + "1. to edit user name" + "\n" + "2. to edit Phone number" + "\n" + "3. to edit email"+"\n4.to go back");
                int ch;
                ch = input.nextInt();
                while (!(ch==1||ch==2||ch==3||ch==4)) {
                    System.out.println("Invalid choice" + "\n" + "Enter" + "1. to edit user name" + "\n" + "2. to edit Phone number" + "\n" + "3. to edit email");
                    ch = input.nextInt();
                }
                if (ch == 1) {
                    System.out.println("Previous user name = " + val.getUserName() + "Enter new username: ");
                    String newUserName = input.next();
                    val.setUserName(newUserName);
                } else if (ch == 2) {
                    System.out.println("Previous phone number = " + val.getPhoneNumber() + "Enter new phone number: ");
                    long newPhoneNumber = input.nextLong();
                    val.setPhoneNumber(newPhoneNumber);
                } else if (ch == 3) {
                    System.out.println("Previous email = " + val.getEmail() + "Enter new email: ");
                    String newEmail = input.next();
                    val.setEmail(newEmail);
                } else if(ch==4){
                    return 4;
                }
                found = 1;
                break;
            }
        }
        return found;
    }
    public void viewRecommendations(Book book) {
        for (Book b : books) {
            if ((b.getType().equals(book.getType())&&b.isAvailable()) || (b.getAuthor().equals(book.getAuthor())&&b.isAvailable())) {
                System.out.println(b);
            }
        }
    }

    public boolean removeBorrower(String userName) {
        boolean found = false;
        for (Borrower val : borrowers) {
            if (val.getUserName().equals(userName)) {
                borrowers.remove(val);
                found = true;
                break;
            }
        }
        return found;
    }

    public Book search(int id) {
        Book bookFounded = null;
        for (Book book : books) {
            if (id == book.getId()) {
                bookFounded = book;
                break;
            }
        }
        return bookFounded;
    }
    public static Book searchBook(ArrayList<Book> books, String title, String author) {
        Book foundBook = null;
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                foundBook = book;
            }
        }
        if (foundBook != null) {

            System.out.println("Book found:");
            System.out.println("Title: " + foundBook.getTitle());
            System.out.println("Author: " + foundBook.getAuthor());
            // System.out.println("Author: " + foundBook.Price);
            //   System.out.println(" Publication_year: " + foundBook.Publication_year);
        } else {
            System.out.println("Book not found.");
            // recommended_books(books, author);
        }
        return foundBook;
    }

    public boolean search(String search) {
        int numOfBooksFound = 0;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(search.toLowerCase()) || book.getAuthor().toLowerCase().contains(search.toLowerCase())) {
                numOfBooksFound++;
                System.out.println(book);
            }
        }
        return numOfBooksFound != 0;
    }

    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void setBorrowers(ArrayList<Borrower> borrowers) {
        this.borrowers = borrowers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
}

