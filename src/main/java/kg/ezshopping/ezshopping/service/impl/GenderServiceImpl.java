package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.dto.ItemGenderResponseDto;
import kg.ezshopping.ezshopping.entity.ItemGender;
import kg.ezshopping.ezshopping.repository.ItemGenderRepository;
import kg.ezshopping.ezshopping.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {

    private final ItemGenderRepository itemGenderRepository;

    @Autowired
    public GenderServiceImpl(ItemGenderRepository itemGenderRepository) {
        this.itemGenderRepository = itemGenderRepository;
    }

    @Override
    public List<ItemGenderResponseDto> findAllItemGenders() {
        List<ItemGender> genders = this.itemGenderRepository.findAll();
        if (genders.isEmpty()) {
            return new ArrayList<>();
        }
        List<ItemGenderResponseDto> result = new ArrayList<>();
        for (ItemGender gender : genders) {
            ItemGenderResponseDto responseDto = new ItemGenderResponseDto();
            responseDto.setId(gender.getId()).setName(gender.getGenderName());
            result.add(responseDto);
        }
        return result;
    }
}
