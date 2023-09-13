package kg.ezshopping.ezshopping.mapper;

import kg.ezshopping.ezshopping.dto.StoreRequestDto;
import kg.ezshopping.ezshopping.dto.StoreResponseDto;
import kg.ezshopping.ezshopping.entity.Store;

public class StoreMapper {
    public static Store mapToStoreEntity(StoreRequestDto storeRequestDto){
        return new Store()
                .setFullName(storeRequestDto.getFullName())
                .setLogin(storeRequestDto.getLogin())
                .setPassword(storeRequestDto.getPassword())
                .setPhoneNumber(storeRequestDto.getPhoneNumber())
                .setEmail(storeRequestDto.getEmail())
                .setLink(storeRequestDto.getLink())
                .setAddress(storeRequestDto.getAddress());
    }

    public static StoreResponseDto mapEntityToStoreResponseDto(Store store){
        return new StoreResponseDto()
                .setId(store.getId())
                .setFullName(store.getFullName())
                .setEmail(store.getEmail())
                .setAddress(store.getAddress())
                .setLink(store.getLink())
                .setPhoneNumber(store.getPhoneNumber())
                .setCreatedAt(store.getCreatedAt());
    }
}
