package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.ItemCreateRequestDto;
import kg.ezshopping.ezshopping.dto.ItemCreateResponseDto;
import kg.ezshopping.ezshopping.dto.ItemImageUploadDto;

public interface ItemService {

    ItemCreateResponseDto createNewItem(ItemCreateRequestDto source);

    ItemCreateResponseDto uploadImageToItem(Long itemId, ItemImageUploadDto source);

}
