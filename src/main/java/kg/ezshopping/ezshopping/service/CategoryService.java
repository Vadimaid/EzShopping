package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.ItemCategoryResponseDto;

import java.util.List;

public interface CategoryService {

    List<ItemCategoryResponseDto> findAllAvailableCategories(Long parentId);

}
