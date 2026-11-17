import dao.BookDAO;
import model.Book;

import java.util.List;

public class BookTest {
    public static void main(String[] args) {
        BookDAO dao = new BookDAO();

        boolean added = dao.addBook(new Book("Test Book", "Some Author", "Testing", 2));
        System.out.println("Added: " + added);

        System.out.println("--- All books ---");
        for (Book b : dao.getAllBooks()) {
            System.out.println(b);
        }

        List<Book> found = dao.searchByTitle("Test");
        System.out.println("--- Search 'Test' found " + found.size() + " ---");

        if (!found.isEmpty()) {
            Book b = found.get(0);
            b.setTitle("Test Book Updated");
            System.out.println("Updated: " + dao.updateBook(b));
            System.out.println("Deleted: " + dao.deleteBook(b.getBookId()));
        }
    }
}
