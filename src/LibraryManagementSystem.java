import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Book {
    private final String title;
    private final String author;
    private final String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void toggleAvailability() {
        isAvailable = !isAvailable;
    }
}

class Member {
    private final String name;
    private final String email;
    private final List<Book> borrowedBooks;

    public Member(String name, String email) {
        List<Book> borrowedBooks1;
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.name = name;
        this.email = email;
        /*borrowedBooks1 = new ArrayList<>();*/
        borrowedBooks1 = Collections.synchronizedList(new ArrayList<>());
        //Borrowed books

        this.borrowedBooks = borrowedBooks1;
        for (Book book : borrowedBooks) {
           book.toggleAvailability();
        }

        //Borrowed books
        for (Book book : borrowedBooks) {
            System.out.println(String.format("Borrowed book: %s by %s", book.getTitle(), book.getAuthor()));
        }
        //return borrowedBooks;
        

    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) throws IllegalStateException {
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for borrowing.");
        }
        book.toggleAvailability();
        borrowedBooks.add(book);
    }

    public void returnBook(String isbn) {
        Book book = borrowedBooks.stream()
                .filter(b -> b.getISBN().equals(isbn))
                .findFirst()
                .orElse(null);
    }
    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.toggleAvailability();
        } else {
            throw new IllegalStateException("This book was not borrowed by the member.");
        }
    }
}

public class LibraryManagementSystem {
    private final List<Book> books;
    private final List<Member> members;
    private final Scanner scanner;

    public LibraryManagementSystem() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addBook() {
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        System.out.println("Enter book author:");
        String author = scanner.nextLine();
        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine();
        books.add(new Book(title, author, isbn));
        System.out.println("Book added successfully!");
    }

    public void registerMember() {
        System.out.println("Enter member name:");
        String name = scanner.nextLine();
        System.out.println("Enter member email:");
        String email = scanner.nextLine();
        try {
            members.add(new Member(name, email));
            System.out.println("Member registered successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void checkoutBook() {
        System.out.println("Enter member email:");
        String email = scanner.nextLine();
        Member member = members.stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (member == null) {
            System.out.println("Member not found!");
            return;
        }

        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine();
        Book book = books.stream()
                .filter(b -> b.getISBN().equals(isbn))
                .findFirst()
                .orElse(null);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        try {
            member.borrowBook(book);
            System.out.println("Book checked out successfully!");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchBooks() {
        System.out.println("Enter search query (title/author):");
        String query = scanner.nextLine().toLowerCase();
        books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(query) || b.getAuthor().toLowerCase().contains(query))
                .forEach(b -> System.out.println("Found: " + b.getTitle() + " by " + b.getAuthor()));
    }

    public void start() {
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Checkout Book");
            System.out.println("4. Search Books");
            System.out.println("5. Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    registerMember();
                    break;
                case "3":
                    checkoutBook();
                    break;
                case "4":
                    searchBooks();
                    break;
                case "5":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new LibraryManagementSystem().start();
    }
}
