package bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bookstore.entity.Book;
import bookstore.repository.book.BookRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {
        "classpath:database/categories/add-categories.sql",
        "classpath:database/books/add-books.sql",
        "classpath:database/books_categories/insert-books-and-categories.sql"})
@Sql(scripts = {
        "classpath:database/books_categories/delete-books_categories.sql",
        "classpath:database/books/delete-books.sql",
        "classpath:database/categories/delete-categories.sql",
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Verify findAllByCategoriesId() method works")
    void findAllByCategoriesId_ValidCategoryId_ReturnsBookByCategory() {
        List<Book> actual = bookRepository.findAllByCategoryId(1L);
        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("Return empty list from non-existing category id")
    void findAllByCategoriesId_NonExistingCategoryId_ReturnsEmptyList() {
        List<Book> actual = bookRepository.findAllByCategoryId(7L);
        assertEquals(0, actual.size());
    }
}
