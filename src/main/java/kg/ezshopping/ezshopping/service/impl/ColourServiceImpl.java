package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.dto.ItemColourResponseDto;
import kg.ezshopping.ezshopping.entity.ItemColour;
import kg.ezshopping.ezshopping.repository.ItemColourRepository;
import kg.ezshopping.ezshopping.service.ColourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColourServiceImpl implements ColourService {

    private final ItemColourRepository itemColourRepository;

    @Autowired
    public ColourServiceImpl(ItemColourRepository itemColourRepository) {
        this.itemColourRepository = itemColourRepository;
    }

    @Override
    public List<ItemColourResponseDto> findAllItemColours() {
        List<ItemColour> colours = this.itemColourRepository.findAll();
        if (colours.isEmpty()) {
            return new ArrayList<>();
        }
        List<ItemColourResponseDto> result = new ArrayList<>();
        for (ItemColour colour : colours) {
            ItemColourResponseDto responseDto = new ItemColourResponseDto();
            responseDto.setId(colour.getId()).setName(colour.getColourName());
            result.add(responseDto);
        }
        return result;
    }
}
