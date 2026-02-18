package gui;

import dao.BookDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Book;

public class LibraryDashboard extends JFrame implements ActionListener {
    private final JTextField bookIdField;
    private final JTextField titleField;
    private final JTextField authorField;
    private final JButton addButton;
    private final JButton borrowButton;
    private final JButton returnButton;
    private final JButton refreshButton;
    private final JTable booksTable;
    private final DefaultTableModel tableModel;
    private final BookDAO bookDAO;

    public LibraryDashboard() {
        bookDAO = new BookDAO();

        setTitle("Library Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdField = new JTextField();
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField();

        addButton = new JButton("Add Book");
        borrowButton = new JButton("Borrow Book");
        returnButton = new JButton("Return Book");
        refreshButton = new JButton("Refresh");

        inputPanel.add(bookIdLabel);
        inputPanel.add(bookIdField);
        inputPanel.add(titleLabel);
        inputPanel.add(titleField);
        inputPanel.add(authorLabel);
        inputPanel.add(authorField);
        inputPanel.add(addButton);
        inputPanel.add(borrowButton);
        inputPanel.add(new JLabel());
        inputPanel.add(returnButton);
        inputPanel.add(new JLabel());
        inputPanel.add(refreshButton);

        addButton.addActionListener(e -> addBook());
        borrowButton.addActionListener(e -> borrowBook());
        returnButton.addActionListener(e -> returnBook());
        refreshButton.addActionListener(e -> loadBooks());

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tableModel = new DefaultTableModel(new String[] { "Book ID", "Title", "Author", "Status" }, 0);
        booksTable = new JTable(tableModel);
        booksTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && booksTable.getSelectedRow() != -1) {
                    showBookDetails();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(booksTable);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        loadBooks();
        setVisible(true);
    }

    private void loadBooks() {
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            tableModel.addRow(new Object[] { book.getBookId(), book.getTitle(), book.getAuthor(), book.getStatus() });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Not used - lambdas handle events directly
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();

        if (title.isEmpty() || author.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill Title and Author!",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        bookDAO.addBook(title, author);
        JOptionPane.showMessageDialog(this, "Book added successfully!");

        titleField.setText("");
        authorField.setText("");
        bookIdField.setText(""); // optional
        loadBooks();
    }

    private void borrowBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to borrow!", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookId = (int) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 3);

        if (status.equals("Borrowed")) {
            JOptionPane.showMessageDialog(this, "This book is already borrowed!", "Borrow Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        bookDAO.borrowBook(bookId);
        JOptionPane.showMessageDialog(this, "Book borrowed successfully!");
        loadBooks();
    }

    private void returnBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to return!", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookId = (int) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 3);

        if (status.equals("Available")) {
            JOptionPane.showMessageDialog(this, "This book is already available!", "Return Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        bookDAO.returnBook(bookId);
        JOptionPane.showMessageDialog(this, "Book returned successfully!");
        loadBooks();
    }

    private void showBookDetails() {
        int selectedRow = booksTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a book!",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String bookId = tableModel.getValueAt(selectedRow, 0).toString();
        String title = tableModel.getValueAt(selectedRow, 1).toString();
        String author = tableModel.getValueAt(selectedRow, 2).toString();
        String status = tableModel.getValueAt(selectedRow, 3).toString();

        String message = "Book ID : " + bookId + "\n" +
                "Title   : " + title + "\n" +
                "Author  : " + author + "\n" +
                "Status  : " + status;

        JOptionPane.showMessageDialog(this,
                message,
                "Book Details",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
