package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.ItemGenderResponseDto;

import java.util.List;

public interface GenderService {

    List<ItemGenderResponseDto> findAllItemGenders();

}
