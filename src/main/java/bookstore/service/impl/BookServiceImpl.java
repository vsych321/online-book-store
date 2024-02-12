package bookstore.service.impl;

import bookstore.dto.bookdto.BookDto;
import bookstore.dto.bookdto.BookDtoWithoutCategoryIds;
import bookstore.dto.bookdto.BookSearchParametersDto;
import bookstore.dto.bookdto.CreateBookRequestDto;
import bookstore.entity.Book;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.BookMapper;
import bookstore.repository.book.BookRepository;
import bookstore.repository.book.BookSpecificationBuilder;
import bookstore.repository.category.CategoryRepository;
import bookstore.service.BookService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder specificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public BookDtoWithoutCategoryIds save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toBook(requestDto);
        setCategories(book,requestDto.categoryIds());
        return bookMapper.toDtoWithoutCategories(bookRepository.save(book));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                                "Can't find book with id " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<BookDto> searchBookByParams(BookSearchParametersDto parameters, Pageable pageable) {
        Specification<Book> bookSpecification = specificationBuilder.build(parameters);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Can't update book by id " + id)
        );
        bookMapper.updateBook(requestDto,book);
        setCategories(book, requestDto.categoryIds());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findBooksByCategoryId(Long id) {
        return bookRepository.findAllByCategoryId(id)
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    private void setCategories(Book book, List<Long> categoryIds) {
        book.setCategories(categoryIds.stream()
                .map(categoryRepository::getReferenceById)
                .collect(Collectors.toSet()));
    }
}
