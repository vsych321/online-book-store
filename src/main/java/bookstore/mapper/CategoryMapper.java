package bookstore.mapper;

import bookstore.dto.categorydto.CategoryResponseDto;
import bookstore.dto.categorydto.CreateCategoryRequestDto;
import bookstore.entity.Category;
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
public interface CategoryMapper {

    Category toCategory(CreateCategoryRequestDto requestDto);

    CategoryResponseDto toDto(Category category);

    void updateCategory(CreateCategoryRequestDto dto, @MappingTarget Category category);
}
