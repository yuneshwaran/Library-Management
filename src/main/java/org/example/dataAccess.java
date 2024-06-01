package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dataAccess  {

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (title, author, published_year, available) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPublishedYear());
            stmt.setBoolean(4, book.isAvailable());
            stmt.executeUpdate();
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection conn = dbUtility.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublishedYear(rs.getInt("published_year"));
                book.setAvailable(rs.getBoolean("available"));
                books.add(book);
            }
        }
        return books;
    }

    public void updateBook(Book book) throws SQLException {
        String query = "UPDATE books SET title = ?, author = ?, published_year = ?, available = ? WHERE book_id = ?";
        try (Connection conn = dbUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPublishedYear());
            stmt.setBoolean(4, book.isAvailable());
            stmt.setInt(5, book.getBookId());
            stmt.executeUpdate();
        }
    }

    public void deleteBook(int bookId) throws SQLException {
        String query = "DELETE FROM books WHERE book_id = ?";
        try (Connection conn = dbUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }

    public void borrowBook(int bookId, String borrowerName) throws SQLException {
        String updateQuery = "UPDATE books SET available = FALSE WHERE book_id = ?";
        String insertQuery = "INSERT INTO borrowed_books (book_id, borrower_name) VALUES (?, ?)";
        try (Connection conn = dbUtility.getConnection()) {
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                 PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                conn.setAutoCommit(false);
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();

                insertStmt.setInt(1, bookId);
                insertStmt.setString(2, borrowerName);
                insertStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void returnBook(int bookId) throws SQLException {
        String updateQuery = "UPDATE books SET available = TRUE WHERE book_id = ?";
        String deleteQuery = "DELETE FROM borrowed_books WHERE book_id = ? AND return_date IS NULL";
        try (Connection conn = dbUtility.getConnection()) {
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                 PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                conn.setAutoCommit(false);
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();

                deleteStmt.setInt(1, bookId);
                deleteStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}

