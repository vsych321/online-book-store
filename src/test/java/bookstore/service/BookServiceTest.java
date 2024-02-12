package bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bookstore.dto.bookdto.BookDto;
import bookstore.dto.bookdto.BookDtoWithoutCategoryIds;
import bookstore.dto.bookdto.CreateBookRequestDto;
import bookstore.entity.Book;
import bookstore.entity.Category;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.BookMapper;
import bookstore.mapper.impl.BookMapperImpl;
import bookstore.repository.book.BookRepository;
import bookstore.repository.category.CategoryRepository;
import bookstore.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private BookMapper bookMapper = new BookMapperImpl();

    @Test
    @DisplayName("Throwing the Exception by non valid id")
    public void getBookById_nonValidId_throwsException() {
        Long id = 10L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.getById(id));
    }

    @Test
    @DisplayName("Verify save() method works")
    public void save_ValidBookWithoutCategoryIds_ReturnBookDto() {
        Category category = getCategory();
        final Book unsavedBook = getBook();
        Book savedBook = getBook();
        savedBook.setId(1L);
        savedBook.setCategories(Set.of(category));

        when(categoryRepository.getReferenceById(anyLong())).thenReturn(category);
        when(bookRepository.save(unsavedBook)).thenReturn(savedBook);

        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "title", "author", "isbn",
                BigDecimal.TEN, "description",
                "cover_image", List.of(1L)
        );
        BookDtoWithoutCategoryIds expected = new BookDtoWithoutCategoryIds(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthor(),
                savedBook.getIsbn(),
                savedBook.getPrice(),
                savedBook.getDescription(),
                savedBook.getCoverImage()
                );
        BookDtoWithoutCategoryIds actual = bookService.save(requestDto);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get the book by its id check")
    public void getBookById_ValidId_ReturnBookDto() {
        Long id = 1L;
        Book book = getBook();
        book.setId(1L);
        book.setCategories(Set.of(getCategory()));
        BookDto expected = getBookDto();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        BookDto actual = bookService.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify deleteById() method works")
    public void deleteById_ValidId_ReturnValidData() {
        Long id = 1L;
        bookService.deleteById(id);
        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Verify update() method works")
    public void update_ValidId_ReturnBookDto() {
        Book book = getBook();
        book.setId(1L);
        CreateBookRequestDto update = new CreateBookRequestDto(
                "Book with update", "Test author", "332756727655627",
                BigDecimal.TEN, "description",
                "cover_image", List.of(1L)
        );

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        book.setTitle(update.title());
        when(categoryRepository.getReferenceById(1L)).thenReturn(getCategory());
        when(bookRepository.save(book)).thenReturn(book);

        BookDto expected = new BookDto(1L,
                "Book with update", "Test author", "332756727655627",
                BigDecimal.TEN, "description",
                "cover_image", List.of(1L)
        );
        BookDto actual = bookService.update(book.getId(), update);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get books by each category")
    public void findBooksByCategoryId_ValidId_ReturnList() {
        Long categoryId = 1L;
        BookDtoWithoutCategoryIds bookDtoWithoutCategoryIds = new BookDtoWithoutCategoryIds(
                1L, "title", "author", "4316453432", BigDecimal.TEN,
                "description", "cover_image"
        );
        List<BookDtoWithoutCategoryIds> expected = List.of(bookDtoWithoutCategoryIds);
        when(bookRepository.findAllByCategoryId(1L)).thenReturn(List.of(getBook()));
        List<BookDtoWithoutCategoryIds> actual = bookService.findBooksByCategoryId(categoryId);

        assertEquals(expected.size(), actual.size());
    }

    private static BookDto getBookDto() {
        BookDto expected = new BookDto(1L, "title", "author", "isbn",
                BigDecimal.TEN, "description", "cover_image", List.of(1L));
        return expected;
    }

    private static Category getCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("historic");
        category.setDescription("desc");
        category.setDeleted(false);
        return category;
    }

    private static Book getBook() {
        Book book = new Book();
        book.setTitle("title");
        book.setAuthor("author");
        book.setIsbn("isbn");
        book.setPrice(BigDecimal.TEN);
        book.setDescription("description");
        book.setCoverImage("cover_image");
        return book;
    }
}
