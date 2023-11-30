package bookstore.service;

import bookstore.dto.book.BookDto;
import bookstore.dto.book.BookDtoWithoutCategoryIds;
import bookstore.dto.book.BookSearchParametersDto;
import bookstore.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDtoWithoutCategoryIds save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    void deleteById(Long id);

    List<BookDto> searchBookByParams(BookSearchParametersDto parameters, Pageable pageable);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    List<BookDtoWithoutCategoryIds> findBooksByCategoryId(Long id, Pageable pageable);
}
