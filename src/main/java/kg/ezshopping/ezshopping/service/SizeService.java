package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.ItemSizeResponseDto;

import java.util.List;

public interface SizeService {

    List<ItemSizeResponseDto> findAllAvailableSizes();

}
