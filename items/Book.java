package items;

import users.Customer;
import users.User;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.*;


public class Book implements Serializable {
    private static ArrayList<String> bookTypes = new ArrayList<>();
    private static int nextBookId = 1;
    private int id;
    private String title;
    private String author;
    private int Publication_year;
    private float Price;
    private String type;
    //float discount_C=0.1f;
    private boolean available;
    // boolean available=true;
    private boolean isBorrowAvailable;
    private double rating;
    private int Amountbooks;
    private ArrayList<Review> reviews;
    private int AmountBorrowBooks;
    // if amount >0 for book ,bool available=true,where customer make order for book amount --
    //  private boolean getTitle;


    public Book(String title, String author, int Publication_year, float Price, int Amount_books, boolean available, String type, int AmountBorrowBooks) {
        this.id = nextBookId++;
        this.title = title;
        this.author = author;
        this.Publication_year = Publication_year;
        this.Price = Price;
        this.Amountbooks = Amount_books;
        if (Amount_books > 0)
            this.available = true;
        this.isBorrowAvailable = true;
        this.type = type;
        if (AmountBorrowBooks > 0)
            this.AmountBorrowBooks = AmountBorrowBooks;
        reviews = new ArrayList<Review>();
    }

    // Book(String rating, String review) {
    // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    //   }

    public String toString() {
        return "Id: " + id + "   Title: " + title;
    }

    public int getAmountBorrowBooks() {
        return AmountBorrowBooks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublication_year() {
        return Publication_year;
    }

    public void setPublication_year(int publication_year) {
        Publication_year = publication_year;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isBorrowAvailable() {
        return isBorrowAvailable;
    }

    public void setBorrowAvailable(boolean borrowAvailable) {
        isBorrowAvailable = borrowAvailable;
    }

    public int getAmountbooks() {
        return Amountbooks;
    }

    public void setAmountbooks(int amount_books) {
        Amountbooks = amount_books;
    }

    public double getRating() {
        return rating;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void updatequantityBorrow(int variable, boolean value) {
        if (value == false) {
            AmountBorrowBooks -= variable;
        } else
            AmountBorrowBooks += variable;
    }

    //try to work function----------->
    //try to search by title and ckeck it is boolean-->
    public void availablebooks() {
        if (getAmountbooks() > 0) {
            System.out.println(getTitle());
        }
    }

//if(book.amount=0){
    // available=false;
    // }
    //else
    // available=true;

    // for (Book book : books){
    //if(book!=null && book.getPrice()<= Price){
// System.out.println(book.title+" "+book.Price);
    // }
// }
    /* public static void searchBook(ArrayList<Book> books, String title, String author) {

          Book foundBook = null;
          for (Book book : books) {
              if (book.title.equals(title)|| book.author.equals(author)) {
                  foundBook = book;

              }
          }
          if (foundBook != null) {

              System.out.println("Book found:");
              System.out.println("Title: " + foundBook.title);
              System.out.println("Author: " + foundBook.author);
              System.out.println("Author: " + foundBook.Price);
              System.out.println(" Publication_year: " + foundBook.Publication_year);
          } else {
              System.out.println("Book not found.");
              recommended_books(books, author);
          }

      }*/
    public static int searchBooks(ArrayList<Book> books, String subword) {
        int count = 0;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(subword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(subword.toLowerCase())) {
                count++;
                System.out.println("Found matching book: ");
                System.out.println("Title: " + book.id);
                System.out.println("Title: " + book.title);
                System.out.println("Author: " + book.author);
                System.out.println("Author: " + book.Price);
                System.out.println(" Publication_year: " + book.Publication_year);

            }

        }
        return count;
    }

    static void filterAndDisplayProgrammingBooks(ArrayList<Book> books, String searchTerm) {
        System.out.println("Programming Books:");
        books.stream()
                .filter(book -> book.getTitle().contains("programming"))
                .forEach(book -> System.out.println(book.getTitle()));
    }

    /**
     * @param books
     */
    static void filterAndDisplayScienceBooks(ArrayList<Book> books) {
        System.out.println("Science Books:");
        books.stream()
                .filter(book -> book.getTitle().contains("Science"))
                .forEach(book -> System.out.println(book.getTitle()));

    }


    public void bookDetails() {
        System.out.println("Title: " + title);
        System.out.println("Type: " + type);
        System.out.println("Author: " + author);
        System.out.println("Price: " + Price);
        System.out.println(" Publication_year: " + Publication_year);

        System.out.println();
    }

    /**
     *
     */
    public void addReview(Review review) {
        reviews.add(review);
    }

    public boolean deleteReview(int reviewNum, User user) {
        if (reviews.get(reviewNum - 1).getUser().getUserId() == user.getUserId()) {
            reviews.remove(reviews.get(reviewNum - 1));
            return true;
        } else {
            return false;

        }
    }
    public void displayReviews(){
        if (!reviews.isEmpty()) {
            System.out.println("Reviews: ");
            int reviewNum = 1;
            for (Review r : reviews) {
                System.out.print(reviewNum++);
                r.print();
            }
        }
    else {
            System.out.println(" No reviews yet on this book.");
        }
    }

    public double getAverageRating() {
        int totalRating = 0;
        int count = 0;
        for (Review review : reviews) {
            if (review.getTitle().equals(title)) {
                totalRating += review.getNumOfStar();
                count++;
            }
        }
        if (count > 0) {
            double rating = (double) totalRating / count;
            return rating;
        } else {
            return 0; // Return 0 if no reviews found
        }
    }

    public static ArrayList<String> getBookTypes(){
        return bookTypes;
    }
    public static void setBookTypes(String type){
        bookTypes.add(type);
    }


/*
    public void viewAverageRating() {
        System.out.println("1. Add a review");
        System.out.println("2. Get average rating for a book");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter the book title: ");
                String Title = scanner.next();
                System.out.print("Enter the rating: ");
                int rating = scanner.nextInt();
                System.out.print("Enter the book title: ");
                String userName = scanner.next();
                System.out.print("Enter the rating: ");
                String comment = scanner.next();
                addReview(title, rating,userName,comment);
                break;

            case 2:
                System.out.print("Enter the book title: ");
                String enteredTitle = scanner.next();
                double averageRating = getAverageRating(enteredTitle);

                if (averageRating > 0) {
                    System.out.println("Average Rating for '" + enteredTitle + "': " + averageRating);
                } else {
                    System.out.println("No reviews found for the book with title '" + enteredTitle + "'.");
                }
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }

    }
*/
}
    /* in main

        System.out.println("\nEnter book title or author name to find your book");
        //String lowerTitle = scanner.next();
        //String lowerAuthor = scanner.next();
        System.out.print("Enter a search term: ");
        String searchTerm = scanner.next();
        //String term= scanner.next();
        // Filter and display books based on search term
        if (searchTerm.equals("p")) {
            filterAndDisplayProgrammingBooks(books, searchTerm);
        } else if (searchTerm.equals("S")) {

            filterAndDisplayScienceBooks(books);
        }*/

