package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.dto.ItemSizeResponseDto;
import kg.ezshopping.ezshopping.entity.ItemSize;
import kg.ezshopping.ezshopping.repository.ItemSizeRepository;
import kg.ezshopping.ezshopping.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    private final ItemSizeRepository itemSizeRepository;

    @Autowired
    public SizeServiceImpl(ItemSizeRepository itemSizeRepository) {
        this.itemSizeRepository = itemSizeRepository;
    }

    @Override
    public List<ItemSizeResponseDto> findAllAvailableSizes() {
        List<ItemSize> sizes = this.itemSizeRepository.findAll();
        if (sizes.isEmpty()) {
            return new ArrayList<>();
        }
        List<ItemSizeResponseDto> result = new ArrayList<>();
        for (ItemSize size : sizes) {
            ItemSizeResponseDto responseDto = new ItemSizeResponseDto();
            responseDto.setId(size.getId()).setName(size.getSizeName());
            result.add(responseDto);
        }
        return result;
    }
}
