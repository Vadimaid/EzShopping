package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.ItemColourResponseDto;

import java.util.List;

public interface ColourService {

    List<ItemColourResponseDto> findAllItemColours();

}
