package bookstore.service;

import bookstore.dto.categorydto.CategoryResponseDto;
import bookstore.dto.categorydto.CreateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CreateCategoryRequestDto requestDto);

    CategoryResponseDto update(Long id, CreateCategoryRequestDto responseDto);

    void deleteById(Long id);
}
