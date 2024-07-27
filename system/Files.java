package system;

import items.Book;
import users.Borrower;
import users.Customer;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.*;


public class Files implements Serializable{
    private static File fileBook = new File("books.txt");
    private static File fileBorrower = new File("borrowers.txt");
    private static File fileCustomer = new File("customers.txt");
    public static void writeBooks(ArrayList<Book> books) {
        try (ObjectOutputStream ooBooks = new ObjectOutputStream(new FileOutputStream(fileBook))) {
            ooBooks.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBorrows(ArrayList<Borrower> borrowers) {
        try (ObjectOutputStream ooBorrowers = new ObjectOutputStream(new FileOutputStream(fileBorrower))) {
            ooBorrowers.writeObject(borrowers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCustomers(ArrayList<Customer> customers) {
        try (ObjectOutputStream ooCustomers = new ObjectOutputStream(new FileOutputStream(fileCustomer))) {
            ooCustomers.writeObject(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Book> readBooks() {
        try (ObjectInputStream InBooks = new ObjectInputStream(new FileInputStream(fileBook))) {
            ArrayList<Book> books=null;
            books = (ArrayList<Book>) InBooks.readObject();
            return books;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Borrower> readBorrows() {
        try (ObjectInputStream InBorrowers = new ObjectInputStream(new FileInputStream(fileBorrower))) {
            ArrayList<Borrower> borrowers=null;
            borrowers = (ArrayList<Borrower>) InBorrowers.readObject();
            return borrowers;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Customer> readCustomers () {
        try (ObjectInputStream InCustomers = new ObjectInputStream(new FileInputStream(fileCustomer))) {
            ArrayList<Customer> customers=null;
            customers = (ArrayList<Customer>) InCustomers.readObject();
            return customers;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
