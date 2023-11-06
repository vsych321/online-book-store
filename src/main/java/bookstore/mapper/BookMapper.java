package bookstore.mapper;

import bookstore.dto.BookDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.entity.Book;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl"
)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toBook(CreateBookRequestDto requestDto);

    void updateBook(CreateBookRequestDto dto, @MappingTarget Book book);
}
