package bookstore.service.impl;

import bookstore.dto.category.CategoryResponseDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import bookstore.entity.Category;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.CategoryMapper;
import bookstore.repository.categoryrepo.CategoryRepository;
import bookstore.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find category by id " + id));
    }

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto responseDto) {
        Category category = categoryMapper.toCategory(responseDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto update(Long id, CreateCategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't update category by id " + id));
        categoryMapper.updateCategory(requestDto,category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
