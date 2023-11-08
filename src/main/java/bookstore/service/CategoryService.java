package bookstore.service;

import bookstore.dto.category.CategoryResponseDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CreateCategoryRequestDto requesDto);

    CategoryResponseDto update(Long id, CreateCategoryRequestDto responseDto);

    void deleteById(Long id);
}
