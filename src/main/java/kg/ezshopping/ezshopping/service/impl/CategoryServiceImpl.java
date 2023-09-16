package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.dto.ItemCategoryResponseDto;
import kg.ezshopping.ezshopping.entity.ItemCategory;
import kg.ezshopping.ezshopping.repository.ItemCategoryRepository;
import kg.ezshopping.ezshopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    @Autowired
    public CategoryServiceImpl(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    @Override
    public List<ItemCategoryResponseDto> findAllAvailableCategories(Long parentId) {
        List<ItemCategory> categories;
        if (Objects.isNull(parentId)) {
            categories = this.itemCategoryRepository.findAllByIsActiveAndParentIsNull(Boolean.TRUE);
        } else {
            categories = this.itemCategoryRepository.findAllByIsActiveAndParentId(Boolean.TRUE, parentId);
        }
        if (categories.isEmpty()) {
            return new ArrayList<>();
        }
        List<ItemCategoryResponseDto> result = new ArrayList<>();
        for (ItemCategory category : categories) {
            ItemCategoryResponseDto categoryResponseDto = new ItemCategoryResponseDto();
            categoryResponseDto.setId(category.getId()).setName(category.getCategoryName());
            result.add(categoryResponseDto);
        }
        return result;
    }
}
