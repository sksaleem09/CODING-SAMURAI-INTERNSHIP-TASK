package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import util.DBConnection;

public class BookDAO {

   public void addBook(String title, String author) {
    String query = "INSERT INTO books (title, author, status) VALUES (?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, title);
        pstmt.setString(2, author);
        pstmt.setString(3, "Available");
        pstmt.executeUpdate();

    } catch (SQLException e) {
        System.err.println("Error adding book: " + e.getMessage());
    }
}


    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                books.add(new Book(bookId, title, author, status));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
        return books;
    }

    public void borrowBook(int bookId) {
        String query = "UPDATE books SET status = ? WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "Borrowed");
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error borrowing book: " + e.getMessage());
        }
    }

    public void returnBook(int bookId) {
        String query = "UPDATE books SET status = ? WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "Available");
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error returning book: " + e.getMessage());
        }
    }

    public boolean bookExists(int bookId) {
        String query = "SELECT * FROM books WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking book existence: " + e.getMessage());
        }
        return false;
    }
}
