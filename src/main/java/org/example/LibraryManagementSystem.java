package org.example;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {

    private static final dataAccess bookDAO = new dataAccess();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        viewAllBooks();
                        break;
                    case 3:
                        updateBook();
                        break;
                    case 4:
                        deleteBook();
                        break;
                    case 5:
                        borrowBook();
                        break;
                    case 6:
                        returnBook();
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addBook() throws SQLException {
        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        System.out.println("Enter book author:");
        String author = scanner.nextLine();
        System.out.println("Enter published year:");
        int publishedYear = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishedYear(publishedYear);
        book.setAvailable(true);

        bookDAO.addBook(book);
        System.out.println("Book added successfully!");
    }

    private static void viewAllBooks() throws SQLException {
        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            System.out.println(book.getBookId() + ": " + book.getTitle() + " by " + book.getAuthor() +
                    " (" + book.getPublishedYear() + ")");
        }
    }

    private static void updateBook() throws SQLException {
        System.out.println("Enter book ID to update:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.println("Enter new book title:");
        String title = scanner.nextLine();
        System.out.println("Enter new book author:");
        String author = scanner.nextLine();
        System.out.println("Enter new published year:");
        int publishedYear = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Book book = new Book();
        book.setBookId(bookId);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishedYear(publishedYear);
        book.setAvailable(true);

        bookDAO.updateBook(book);
        System.out.println("Book updated successfully!");
    }

    private static void deleteBook() throws SQLException {
        System.out.println("Enter book ID to delete:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        bookDAO.deleteBook(bookId);
        System.out.println("Book deleted successfully!");
    }

    private static void borrowBook() throws SQLException {
        System.out.println("Enter book ID to borrow:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.println("Enter your name:");
        String borrowerName = scanner.nextLine();

        bookDAO.borrowBook(bookId, borrowerName);
        System.out.println("Book borrowed successfully!");
    }

    private static void returnBook() throws SQLException {
        System.out.println("Enter book ID to return:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        bookDAO.returnBook(bookId);
        System.out.println("Book returned successfully!");
    }
}
