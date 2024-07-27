package system;

import users.*;
import items.*;
import java.net.SocketTimeoutException;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Library library = new Library();
        Book b1=new Book("fatma","esraa",2022,150f,5,true,"programming",5);
        Book b2=new Book("java","fatma",2022,150f,0,false,"programming",5);
        Book b3=new Book("c++","esraa",2022,150f,5,true,"programming",5);
        Book b4=new Book("python","esraa",2022,150f,5,true,"novel",5);
        Book b5=new Book("laila","loli",2022,150f,5,true,"novel",5);
        Book b6=new Book("ali","loli",2022,150f,5,true,"drama",5);
        Book b7=new Book("code","mohamed",2022,150f,5,true,"drama",5);
        library.getBooks().add(b1);
        library.getBooks().add(b2);
        library.getBooks().add(b3);
        library.getBooks().add(b4);
        library.getBooks().add(b5);
        library.getBooks().add(b6);
        library.getBooks().add(b7);
        readData(library);
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean check;
        while(true){
            do {
                try {
                    System.out.println("If you want to sign up press 1 " + "\n" + "If you want to sign in Enter 2");
                    choice = scanner.nextInt();
                    check = true;
                } catch (Exception e) {
                    System.out.println("Invalid input!Please enter number from above!");
                    choice = 0;
                    check = false;
                    scanner.nextLine();
                }
            } while (!check);
            switch (choice) {
                case 1: {
                    signUp(library);
                    break;
                }
                case 2: {
                    User currentUser = signIn(library);
                    if (currentUser instanceof Admin) {
                        Admin admin = (Admin) currentUser;
                        menuAdmin(library, admin);
                        return;
                    } else if (currentUser instanceof Customer) {
                        Customer currentCustomer = (Customer) currentUser;
                        customerFunctions(library, currentCustomer);
                        return;
                    } else if (currentUser instanceof Borrower) {
                        Borrower borrower = (Borrower) currentUser;
                        borrowerFunctions(library, borrower);
                        return;
                    }
                    break;
                }
                default: {
                    System.out.println("Invalid number! please try again.");
                }
            }
        }
    }


    public static void borrowerFunctions(Library library, Borrower borrower) {
        System.out.println("press:" + "\n" + "1. View Notifications" + "2.Borrow book" + "\n" + "3. Return Book" + "\n" + "4.view Borrowing History" + "\n" + "5. Check Reservations" + "\n" + "\"6. check availability for a specific book" + "\n" + "7.logout" + "\n");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        while (true) {
            switch (choice) {
                case 1: {
                    viewNotifications(library, borrower);
                    break;
                }
                case 2: {
                    BorrowBook(library, borrower);
                    break;
                }
                case 3: {
                    returnBook(library, borrower);
                    break;
                }
                case 4: {
                    viewBorrowingHistory(library, borrower);
                    break;
                }
                case 5: {
                    checkReservations(library, borrower);
                    break;
                }
                case 6: {
                    checkBookAvailability(library, borrower);
                    break;
                }
                case 7: {
                    writeData(library);
                    System.exit(0);
                    break;
                }
            }
        }
    }

    public static void checkBookAvailability(Library library, Borrower borrower) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the book Title ");
        String search = input.next();
        String a;
        do {

            if (library.search(search)) {
                System.out.println("please Enter id of book you want to check");
                int id = input.nextInt();
                Book foundBook = library.search(id);
                boolean isAvailable = borrower.checkAvailability(foundBook);
                if (isAvailable) {
                    System.out.println("Book is available.");
                } else {
                    System.out.println("Book is not available.");
                }
                a = "n";
            } else {
                System.out.println("Sorry, Book not found\nDo you want to try again(y/n)?");
                a = input.next();
            }

        } while (a == "y" || a == "Y");

    }

    public static void viewNotifications(Library library, Borrower borrower) {
        borrower.addNotification();
        borrower.viewNotification();
        borrowerFunctions(library, borrower);
    }

    public static void checkReservations(Library library, Borrower borrower) {
        System.out.println("enter the title of book");
        Scanner title = new Scanner(System.in);
        boolean found = false;
        for (Reservation reserv : borrower.getReservations()) {
            if (reserv.getBook().getTitle().equals(title)) {
                //call if return
                if (reserv.getBook().isBorrowAvailable()) {
                    System.out.println("the book is available do you want to borrow it? (y/n)");
                    Scanner input = new Scanner(System.in);
                    String a = input.next();
                    if (a == "y" || a == "Y") {
                        library.borrowBook(reserv.getBook(), borrower);
                    }

                } else {
                    System.out.println(" sorry the book is not available to borrow now");

                }
                found = true;
                break;

            }
        }
        if (found == false) {
            System.out.println("you don't reserved this book");

        }
    }

    public static void BorrowBook(Library library, Borrower borrower) {
        // can add case to view all books by type
        Scanner input = new Scanner(System.in);
        System.out.println("Press:\n1.to display book by types\n2.search for a specific book\n3.go back");
        int ch = input.nextInt();
        switch (ch) {
            case 1: {
                displayBooksOfType(library, borrower);
                break;
            }
            case 2: {
                System.out.println("Enter book Title or Author ");
                String search = input.next();
                String a;
                do {

                    if (library.search(search)) {
                        System.out.println("please Enter id of book you want to borrow");
                        int id = input.nextInt();
                        Book book = library.search(id);
                        library.borrowBook(book, borrower);
                        a = "n";
                    } else {
                        System.out.println("Sorry, this book not found\nDo you want to try again(y/n)?");
                        a = input.next();
                    }


                } while (a == "y" || a == "Y");

            }
            case 3: {
                borrowerFunctions(library, borrower);
                break;
            }
        }

    }

    private static void displayBooksOfType(Library library, Borrower borrower) {
        Scanner input = new Scanner(System.in);
        int count = 0;
        for (String type : Book.getBookTypes()) {
            ++count;
            System.out.println(count + type);
        }
        System.out.println("pleas Enter type of books You Want");
        int chType = input.nextInt();
        chType--;
        String selectedType = Book.getBookTypes().get(chType);
        System.out.println("Books of type " + selectedType + ":");
        int index = 0;
        for (Book book : library.getBooks()) {
            if (book.getType().equals(selectedType)) {
                index++;
                System.out.println(index + ". " + book.getTitle());
            }
        }
        System.out.println("Enter book that you want to borrow");
        int chBook = input.nextInt();
        chBook--;
        Book book = library.getBooks().get(chBook);
        library.borrowBook(book, borrower);


    }

    public static void returnBook(Library library, Borrower borrower) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter book Title ");
        String title = input.next();
        library.returnBook(title, borrower);
    }

    public static void viewBorrowingHistory(Library library, Borrower borrower) {
        library.viewBorrowerHistory(borrower);
    }

    public static void menuAdmin(Library library, Admin admin) {
        Scanner input = new Scanner(System.in);
        {
            do {
                System.out.println("press:" + "\n" + "1. Add new Book" + "\n" + "2. Update Book's details" + "\n" + "3. Add New Borrower");
                System.out.println("4.Update Borrower details" + "\n" + "5.Remove Borrowers" + "6.to log out");
                int choice = input.nextInt();

                switch (choice) {
                    case 1: {
                        AddNewBook(library, admin);
                        break;
                    }
                    case 2: {
                        updateBookDeatails(library, admin);
                        break;

                    }
                    case 3: {
                        addBorrwer(library, admin);
                        break;
                    }
                    case 4: {
                        updateBorrowerDetails(library, admin);
                        break;
                    }
                    case 5: {
                        removeBorrower(library, admin);
                        break;
                    }
                    case 6: {

                        writeData(library);
                        System.exit(0);
                    }
                }
            } while (true);
        }
    }

    public static void AddNewBook(Library library, Admin admin) {
        Book book = library.makeBook();
        admin.addBook(library, book);
    }

    public static void updateBookDeatails(Library library, Admin admin) {

        System.out.print("Enter book title that you want to update");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.next();
        int operation = library.editBook(title);
        if (operation == 1) {
            System.out.println("Book details was updated Successfully");
        } else if (operation == -1) {
            System.out.println("Book not found");
            updateBookDeatails(library, admin);
        } else if (operation == 3) {
            menuAdmin(library, admin);
        }
    }

    public static void addBorrwer(Library library, Admin admin) {
        Borrower borrower = library.makeBorrower();
        admin.addBorrower(library, borrower);
    }

    public static void updateBorrowerDetails(Library library, Admin admin) {
        System.out.print("Enter user name that you want to update");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.next();
        int operation = library.editBorrower(userName);
        if (operation == 1) {
            System.out.println("Borrower details was updated Successfully");

        } else if (operation == -1) {
            System.out.println("Borrower not found");
            updateBorrowerDetails(library, admin);
        } else if (operation == 4) {
            menuAdmin(library, admin);
        }
    }

    public static void removeBorrower(Library library, Admin admin) {
        System.out.print("Enter user name of borrower you want to remove");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.next();
        if (library.removeBorrower(userName)) {
            System.out.println("Borrower was removed Successfully");
        } else {
            System.out.println("Borrower not found");
            removeBorrower(library, admin);
        }


    }


    public static void signUp(Library library) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your First name");
        String Fname = scanner.next();
        System.out.println("Enter your Last name");
        String Lname = scanner.next();
        System.out.println("Enter your Phone");
        long phoneNumber = scanner.nextLong();
        scanner.nextLine();
        while (!library.checkPhone(phoneNumber)) {
            System.out.println("This phone already exist" + "\n" + "Enter another Phone Number");
            phoneNumber = scanner.nextLong();
            scanner.nextLine();
        }
        System.out.println("Enter your User name:");
        String Uname = scanner.next();
        while (!library.checkUser(Uname)) {
            System.out.println("This user name already exist" + "\n" + "Enter another user name");
            Uname = scanner.next();
        }
        System.out.println("Enter your Email");
        String email = scanner.next();
        while (!library.checKEmail(email)) {
            System.out.println("This email already exist" + "\n" + "Enter another email");
            email = scanner.next();
        }
        System.out.println("Enter your Password:");
        String password = scanner.next();
        library.register(Fname, Lname, phoneNumber,  email,Uname, password);
    }

    public static User signIn(Library library) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your User name:");
        String userName = scanner.next();
        System.out.println("Enter your Password:");
        String password = scanner.next();
        boolean login = false;
        do {
            if (library.login(userName, password) != null) {
                login = true;
            } else {
                login = false;
                System.out.println("Enter your User name:");
                userName = scanner.next();
                System.out.println("Enter your Password:");
                password = scanner.next();
            }
        } while (!login);
        return library.login(userName, password);
    }

    public static void customerFunctions(Library library, Customer customer) {
        while (true) {
            customer.welcomeHome();
            int choice;
            boolean check;
            do {
                try {
                    System.out.println("Press: ");
                    System.out.println("1. Buy books");
                    System.out.println("2. Make review");
                    System.out.println("3. Delete review");
                    System.out.println("4. View orders history");
                    System.out.println("5. Logout");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    check = true;
                } catch (Exception e) {
                    System.out.println("Invalid input!Please enter number from above!");
                    choice=0;
                    check = false;
                    scanner.nextLine();
                }
            }while(!check);
            switch (choice) {
                case 1: {
                    buy(library, customer);
                    break;
                }
                case 2: {
                    makeReview(library,customer);
                    break;
                }
                case 3: {
                    deleteReview(library,customer);
                    break;
                }
                case 4: {
                    customer.ViewOrdersHistory();
                    break;
                }
                case 5: {
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Invalid choice,please try again.");
                    break;
                }
            }
        }
    }

    public static void buy(Library library, Customer customer) {
        while (true) {
            int decision;
            boolean check;
            do {
                try {
                    System.out.println("Press: ");
                    System.out.println("1. View books");
                    System.out.println("2. Search for a specific book");
                    if (customer.getCurrunt_Cart().getOrders().isEmpty()) {
                        System.out.println("3. Go back to main menu");
                    } else {
                        System.out.println("3.Checkout order");
                    }
                    decision = scanner.nextInt();
                    scanner.nextLine();
                    check = true;
                } catch (Exception e) {
                    System.out.println("Invalid input!Please enter number from above!");
                    decision = 0;
                    check = false;
                    scanner.nextLine();
                }
            } while (!check);
            switch (decision) {
                case 1: {
                    viewBooksBuy(library, customer);
                    break;
                }
                case 2: {
                    searchBookBuy(library, customer);
                    break;
                }
                case 3: {
                    if (customer.getCurrunt_Cart() != null) {
                        checkoutOrder(customer,library);
                    }
                    return;
                }
                default: {
                    System.out.println("Invalid choice,please try again.");
                    break;
                }
            }
        }
    }

    public static void viewBooksBuy(Library library, Customer customer) {
        int bookId;
        viewAllBooks(library);
        System.out.println("Do you want to see the details of a specific book? Enter y for Yes,anything else for No");
        char seeDetails = scanner.next().charAt(0);
        scanner.nextLine();
        if (seeDetails == 'y' || seeDetails == 'Y') {
            do {
                boolean check;
                do {
                    try {
                        System.out.println("Enter the ID of book you want to see its details:");
                        bookId = scanner.nextInt();
                        scanner.nextLine();
                        check = true;
                    } catch (Exception e) {
                        System.out.println("Invalid input!Please enter number from above!");
                        bookId=0;
                        check = false;
                        scanner.nextLine();
                    }
                }while(!check);


                if (bookId > library.getBooks().size() || bookId == 0||bookId<0) {
                    System.out.println("Invalid number. Please enter an appropriate ID");
                }
            } while (bookId > library.getBooks().size() || bookId == 0||bookId<0);
            Book currentBook =library.search(bookId);
            currentBook.bookDetails();
            currentBook.displayReviews();
            if (currentBook.isAvailable()) {
                handleBuyBook(currentBook, customer);
            } else {
                viewRecommendations(library,currentBook,customer);
            }
        }
    }

    public static void viewAllBooks(Library library) {
        System.out.println("The books we have are:");
        library.viewBooks();
    }

    public static void searchBookBuy(Library library, Customer customer) {
        int bookId;
        System.out.println("Enter the name of book or name of author");
        String search = scanner.next();
        if (!library.search(search)) {
            System.out.println("This book is not in the library");
        } else {
            System.out.println("Do you want to see the book details? y for YES");
            char option = scanner.next().charAt(0);
            scanner.nextLine();
            if (option == 'y') {
                do {
                    boolean check;
                    do {
                        try {
                            System.out.println("Enter the ID of book you want to see its details:");
                            bookId = scanner.nextInt();
                            scanner.nextLine();
                            check = true;
                        } catch (Exception e) {
                            System.out.println("Invalid input!Please enter number from above!");
                            bookId=0;
                            check = false;
                            scanner.nextLine();
                        }
                    }while(!check);

                    if (bookId >library.getBooks().size() || bookId == 0||bookId<0) {
                        System.out.println("Invalid number. Please enter an appropriate ID");
                    }
                }while(bookId >library.getBooks().size() || bookId == 0||bookId<0);
                Book searchedBook = library.search(bookId);
                searchedBook.bookDetails();
                searchedBook.displayReviews();
                if(searchedBook.isAvailable()) {
                    handleBuyBook(searchedBook, customer);
                }
                else {
                    viewRecommendations(library,searchedBook,customer);
                }
            }
        }
    }
    public static void viewRecommendations(Library library,Book currentBook, Customer customer) {
        int bookId;
        System.out.println("This book is NOT AVAILABLE right now");
        System.out.println("Here are some recommendations: ");
        library.viewRecommendations(currentBook);
        System.out.println("Do you want to see the book details? y for YES");
        char seeDetails = scanner.next().charAt(0);
        scanner.nextLine();
        if (seeDetails == 'y' || seeDetails == 'Y') {
            do {
                boolean check;
                do {
                    try {
                        System.out.println("Enter the ID of book you want to see its details:");
                        bookId = scanner.nextInt();
                        scanner.nextLine();
                        check = true;
                    } catch (Exception e) {
                        System.out.println("Invalid input!Please enter number from above!");
                        bookId=0;
                        check = false;
                        scanner.nextLine();
                    }
                }while(!check);

                if (bookId > library.getBooks().size() || bookId == 0||bookId<0) {
                    System.out.println("Invalid number. Please enter an appropriate ID");
                }
            } while (bookId > library.getBooks().size() || bookId == 0||bookId<0);
            Book recommendedBook = library.search(bookId);
            recommendedBook.bookDetails();
            recommendedBook.displayReviews();
            handleBuyBook(recommendedBook, customer);
        }
    }
    public static void handleBuyBook(Book currentBook, Customer customer) {
        boolean checker = true;
        if(customer.getCurrunt_Cart()!=null&&customer.getCurrunt_Cart().findBook(currentBook.getId())){
            while(true) {
                int choice;
                boolean check;
                do {
                    try {
                        System.out.println("THIS BOOK IS IN THE CART");
                        System.out.println("Press: ");
                        System.out.println("1. Go back");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        check = true;
                    } catch (Exception e) {
                        System.out.println("Invalid input!Please enter number from above!");
                        choice=0;
                        check = false;
                        scanner.nextLine();
                    }
                }while(!check);
                if (choice == 1) {
                    return;
                } else {
                    System.out.println("Invalid choice,please try again.");
                }
            }
        }
        else {
            do {
                int choice;
                boolean check;
                do {
                    try {
                        System.out.println("Press: ");
                        System.out.println("1. Add this book to cart");
                        System.out.println("2. Go back");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        check = true;
                    } catch (Exception e) {
                        System.out.println("Invalid input!Please enter number from above!");
                        choice=0;
                        check = false;
                        scanner.nextLine();
                    }
                }while(!check);
                switch (choice) {
                    case 1: {
                        buyBook(currentBook, customer);
                        break;
                    }
                    case 2: {
                        return;
                    }
                    default: {
                        System.out.println("Invalid choice,please try again.");
                        checker = false;
                        break;
                    }
                }
            } while (!checker);
        }
    }

    public static void buyBook(Book currentBook, Customer customer) {
        boolean check;
        boolean buy;
        int quantity;
        int choice;
        boolean flag;
        do {
            try {
                System.out.println("Enter the quantity you want.");
                quantity = scanner.nextInt();
                scanner.nextLine();
                flag = true;
            } catch (Exception e) {
                System.out.println("Invalid input!Please enter number from above!");
                quantity=0;
                flag = false;
                scanner.nextLine();
            }
        }while(!flag);
        do {
            if (customer.buy(currentBook, quantity)) {
                System.out.println("Book added to cart successfully");
                check = true;
                buy = false;
            } else {
                System.out.println("The number of quantity  you requested is invalid,please enter a positive number that within the currently available quantity\nThe Number which Available Now Is " + currentBook.getAmountbooks());
                do {
                    boolean check_decision;
                    int decision;
                    do {
                        try {
                            System.out.println("If you want to continue buy process enter appropriate quantity or press 0 to cancel buying this book");
                            decision = scanner.nextInt();
                            scanner.nextLine();
                            check_decision = true;
                        } catch (Exception e) {
                            System.out.println("Invalid input!Please enter number from above!");
                            decision = 0;
                            check_decision = false;
                            scanner.nextLine();
                        }
                    } while (! check_decision);
                    if (decision > 0) {
                        quantity = decision;
                        check = true;
                        buy = true;
                    } else if (decision < 0) {
                        System.out.println("Invalid number, please enter a positive number or 0.");
                        check = false;
                        buy = false;
                    } else {
                        check = true;
                        buy = false;
                    }
                } while (!check);
            }
        } while (buy);
    }

    public static void checkoutOrder(Customer customer,Library library) {

        boolean confirmation = false;
        boolean checker = true;
        do {
            do {
                if(!customer.getCurrunt_Cart().getOrders().isEmpty()){
                    customer.getCurrunt_Cart().viewCartItems();
                    int orderConfirmation;
                    boolean check;
                    do {
                        try {
                            System.out.println("If you want to change quantity of book press 1 \nif you want to remove book press 2\n if you want to confirm order press 3\n if you want to cancel order press 4. ");
                            orderConfirmation = scanner.nextInt();
                            scanner.nextLine();
                            check = true;
                        } catch (Exception e) {
                            System.out.println("Invalid input!Please enter number from above!");
                            orderConfirmation = 0;
                            check = false;
                            scanner.nextLine();
                        }
                    } while (!check);
                    switch (orderConfirmation) {
                        case 1:
                            changeQuantity(customer);
                            break;
                        case 2:
                            customer.removeItemFromCart();
                            break;
                        case 3:
                            customer.checkout();
                            confirmation = true;
                            break;
                        case 4:
                            customer.getCurrunt_Cart().clearCart();
                            int backHome ;
                            do {
                                try {
                                    System.out.println("Your order has been canceled successfully.Enter 0 to back home.");
                                    backHome = scanner.nextInt();
                                    scanner.nextLine();
                                    check = true;
                                } catch (Exception e) {
                                    System.out.println("Invalid input!Please enter number from above!");
                                    backHome = 0;
                                    check = false;
                                    scanner.nextLine();
                                }
                            } while (!check);
                            confirmation = true;
                            break;
                        default: {
                            System.out.println("Invalid choice,please try again.");
                            checker = false;
                            break;
                        }
                    }
                }
                else{
                    System.out.println("Your cart is empty.");
                    confirmation = true;
                }
            } while (!checker);
        } while (!confirmation);
    }

    public static void makeReview(Library library, Customer customer) {
        int id;
        System.out.print("Enter the book name or author name: ");
        String search = scanner.next();
        if(!library.search(search)){
            System.out.println("This book is not in the library");
        }
        else{
            do {
                boolean check;
                do {
                    try {
                        System.out.print("Enter the ID of the book you want to make review to it : ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        check = true;
                    } catch (Exception e) {
                        System.out.println("Invalid input!Please enter number from above!");
                        id = 0;
                        check = false;
                        scanner.nextLine();
                    }
                } while (!check);
                if (id >library.getBooks().size() || id == 0||id<0) {
                    System.out.println("Invalid number. Please enter an appropriate ID");
                }
            }while (id >library.getBooks().size() || id == 0||id<0);
            Book book =library.search(id);
            book.bookDetails();
            book.displayReviews();
            System.out.println("\n\nEnter a comment");
            String comment = scanner.next();
            int rating ;
            boolean check;
            do {
                try {
                    System.out.println("Enter a rating");
                    rating = scanner.nextInt();
                    scanner.nextLine();
                    check = true;
                } catch (Exception e) {
                    System.out.println("Invalid input!Please enter valid number.");
                    rating = 0;
                    check = false;
                    scanner.nextLine();
                }
            } while (!check);
            customer.Make_review(book, comment, rating);
            book.bookDetails();
            book.displayReviews();
        }
    }

    public static void  deleteReview(Library library, Customer customer) {
        int reviewNum;
        System.out.print("Enter the book name or author name: ");
        String search = scanner.next();
        if(!library.search(search)){
            System.out.println("This book is not in the library");
        }
        else{
            int id;
            boolean check;
            do {
                try {
                    System.out.print("Enter the iD of the book you want delete your review on: ");
                    id = scanner.nextInt();
                    check = true;
                } catch (Exception e) {
                    System.out.println("Invalid input!Please enter number from above!");
                    id = 0;
                    check = false;
                    scanner.nextLine();
                }
            } while (!check);
            Book book =library.search(id);
            book.bookDetails();
            book.displayReviews();
            do {
                do {
                    try {
                        System.out.print("\n\nEnter the number of the review you want to delete it: ");
                        reviewNum = scanner.nextInt();
                        check = true;
                    } catch (Exception e) {
                        System.out.println("Invalid input!Please enter number from above!");
                        reviewNum = 0;
                        check = false;
                        scanner.nextLine();
                    }
                } while (!check);
                if (reviewNum > book.getReviews().size() || reviewNum == 0||reviewNum<0) {
                    System.out.println("Invalid number. Please enter an appropriate review number");
                }
            }while (reviewNum > book.getReviews().size() || reviewNum == 0||reviewNum<0);
            customer.Delete_review(book,reviewNum);
            book.bookDetails();

            book.displayReviews();
        }
    }
    public static   void changeQuantity(Customer customer){
        System.out.println("Enter the id of book and its new quantity\nId:");
        int modifyId = scanner.nextInt();
        scanner.nextLine();
        Order currentOrder=customer.getCurrunt_Cart().findOrder(modifyId);
        boolean check;
        do{
            System.out.println("Quantity:");
            int newQuantity = scanner.nextInt();
            scanner.nextLine();
            customer.getCurrunt_Cart().changequantity(modifyId);
            if (currentOrder.check(newQuantity,currentOrder.getBook())) {
                customer.getCurrunt_Cart().changeItemtoCart(currentOrder,newQuantity);
                System.out.println("Tha quantity changed successfully");
                check = true;

            } else {
                System.out.println("The number of quantity  you requested is invalid,please enter a positive number that within the currently available quantity\nThe Number which Available Now Is " + currentOrder.getBook().getAmountbooks());
                check=false;
            }

        }while (!check);
    }
    public static void writeData(Library library) {
        Files file = new Files();
        file.writeBooks(library.getBooks());
        file.writeBorrows(library.getBorrower());
        file.writeCustomers(library.getCustomers());
    }

    public static void readData(Library library) {
        Files file = new Files();
        file.writeBooks(library.getBooks());
        file.writeBorrows(library.getBorrower());
        file.writeCustomers(library.getCustomers());
        library.setBooks(file.readBooks());
        library.setBorrowers(file.readBorrows());
        library.setCustomers(file.readCustomers());
    }
}




