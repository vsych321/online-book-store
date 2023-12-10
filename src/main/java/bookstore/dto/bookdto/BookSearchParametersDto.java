package bookstore.dto.bookdto;

public record BookSearchParametersDto(
        String[] titles,
        String[] authors
){
}
