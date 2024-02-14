package bookstore.service;

import bookstore.dto.bookdto.BookDto;
import bookstore.dto.bookdto.BookDtoWithoutCategoryIds;
import bookstore.dto.bookdto.BookSearchParametersDto;
import bookstore.dto.bookdto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDtoWithoutCategoryIds save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    void deleteById(Long id);

    List<BookDto> searchBookByParams(BookSearchParametersDto parameters, Pageable pageable);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    List<BookDtoWithoutCategoryIds> findBooksByCategoryId(Long id);
}
