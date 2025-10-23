import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
// Here we needed to import the necessary classes. Hashmap and map for storing the data and the scanner to read the user input.
public class LibrarySystem {
    public static Map<String, Book> library = new HashMap<>();
    public static Scanner scanner = new Scanner(System.in);
    // Here we are assigning our maps to the library being that of book and library. And then setting up our scanner to read the user inputs.
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("Library System Menu:");
            System.out.println("1. Add Books");
            System.out.println("2. Borrow Books");
            System.out.println("3. Return Books");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBooks();
                    break;
                case 2:
                    borrowBooks();
                    break;
                case 3:
                    returnBooks();
                    break;
                case 4:
                    System.out.println("Exiting the program");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again");
            }
        } while (choice != 4);
    }
//Here is the start of our code and the building of our library menu. We are implementing a while loop that ends when user input equals 4 allowing for the program to end.


    public static void addBooks() {
        System.out.println("Enter book details:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        //Declaring a method called add books that allows us to add books to our library
        if (library.containsKey(title)) {
            library.get(title).addQuantity(quantity);
            System.out.println("Book updated");
        } else {
            library.put(title, new Book(title, author, quantity));
            System.out.println("Book added");
        }
        //This sets up two conditions the first one checks if the book is already in the library and if it does it updates its quantity and then prints the success message. If not it will make a new entry adding the book detail, then gives the message of book added.
    }

    public static void borrowBooks() {
        System.out.println("Enter details of the book");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Quantity to borrow: ");
        int quantityToBorrow = scanner.nextInt();
        scanner.nextLine();

        if (library.containsKey(title) && library.get(title).getQuantity() >= quantityToBorrow) {
            library.get(title).subtractQuantity(quantityToBorrow);
            System.out.println("Books borrowed successfully");
        } else {
            System.out.println("Error: Books not available");
        }
        // does the same thing has add book method but the details are slightly different
    }

    public static void returnBooks() {
        System.out.println("Enter details to return books:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Quantity to return: ");
        int quantityToReturn = scanner.nextInt();
        scanner.nextLine();

        if (library.containsKey(title)) {
            library.get(title).addQuantity(quantityToReturn);
            System.out.println("Books returned");
        } else {
            System.out.println("Error: Invalid title");
        }
    }

    static class Book {
        public String title;
        public String author;
        public int quantity;
        // declaring what a book is including its title, author, and quantity
        public Book(String title, String author, int quantity) {
            this.title = title;
            this.author = author;
            this.quantity = quantity;
        }

        public void addQuantity(int quantity) {
            this.quantity += quantity;
        }

        public void subtractQuantity(int quantity) {
            this.quantity -= quantity;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
