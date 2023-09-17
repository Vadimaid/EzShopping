package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.dto.ItemCreateRequestDto;
import kg.ezshopping.ezshopping.dto.ItemCreateResponseDto;
import kg.ezshopping.ezshopping.dto.ItemImageUploadDto;
import kg.ezshopping.ezshopping.entity.Item;
import kg.ezshopping.ezshopping.entity.ItemCategory;
import kg.ezshopping.ezshopping.entity.ItemColour;
import kg.ezshopping.ezshopping.entity.ItemGender;
import kg.ezshopping.ezshopping.entity.ItemImage;
import kg.ezshopping.ezshopping.entity.ItemSize;
import kg.ezshopping.ezshopping.repository.ItemCategoryRepository;
import kg.ezshopping.ezshopping.repository.ItemColourRepository;
import kg.ezshopping.ezshopping.repository.ItemGenderRepository;
import kg.ezshopping.ezshopping.repository.ItemImageRepository;
import kg.ezshopping.ezshopping.repository.ItemRepository;
import kg.ezshopping.ezshopping.repository.ItemSizeRepository;
import kg.ezshopping.ezshopping.service.AppUserService;
import kg.ezshopping.ezshopping.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final ItemGenderRepository itemGenderRepository;
    private final ItemColourRepository itemColourRepository;
    private final ItemSizeRepository itemSizeRepository;
    private final AppUserService appUserService;
    private final ItemImageRepository itemImageRepository;

    @Autowired
    public ItemServiceImpl(
            ItemRepository itemRepository,
            ItemCategoryRepository itemCategoryRepository,
            ItemGenderRepository itemGenderRepository,
            ItemColourRepository itemColourRepository,
            ItemSizeRepository itemSizeRepository,
            AppUserService appUserService,
            ItemImageRepository itemImageRepository) {
        this.itemRepository = itemRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemGenderRepository = itemGenderRepository;
        this.itemColourRepository = itemColourRepository;
        this.itemSizeRepository = itemSizeRepository;
        this.appUserService = appUserService;
        this.itemImageRepository = itemImageRepository;
    }

    @Override
    public ItemCreateResponseDto createNewItem(ItemCreateRequestDto source) {
        Item item = new Item();
        ItemCategory category = this.itemCategoryRepository.getReferenceById(source.getCategoryId());
        ItemGender gender = this.itemGenderRepository.getReferenceById(source.getGenderId());
        List<ItemColour> colours = this.itemColourRepository.findAllByIdIn(source.getAvailableColours());
        List<ItemSize> sizes = this.itemSizeRepository.findAllByIdIn(source.getAvailableSizes());

        item
                .setShop(this.appUserService.getAuthorizedUser())
                .setItemCategory(category)
                .setItemComment(source.getItemComment())
                .setPrice(source.getPrice())
                .setGender(gender)
                .setAvailable(Boolean.TRUE)
                .setAvailableColours(colours)
                .setAvailableSizes(sizes);
        this.itemRepository.save(item);
        return new ItemCreateResponseDto().setId(item.getId());
    }

    @Override
    public ItemCreateResponseDto uploadImageToItem(Long itemId, ItemImageUploadDto source) {
        Item item = this.itemRepository.getReferenceById(itemId);
        ItemImage itemImage = new ItemImage();
        itemImage
                .setItem(item)
                .setMain(source.getMain())
                .setImage(Base64Utils.decodeFromString(source.getItemImage()));
        this.itemImageRepository.save(itemImage);
        return new ItemCreateResponseDto().setId(itemId);
    }
}
