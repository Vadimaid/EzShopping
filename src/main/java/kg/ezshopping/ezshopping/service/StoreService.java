package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.StoreRequestDto;
import kg.ezshopping.ezshopping.dto.StoreResponseDto;
import kg.ezshopping.ezshopping.entity.Store;
import kg.ezshopping.ezshopping.exception.InvalidDataException;
import kg.ezshopping.ezshopping.exception.StoreAlreadyExistsException;
import kg.ezshopping.ezshopping.exception.StoreNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface StoreService {
    Store findStoreById(Long storeId) throws StoreNotFoundException;
    StoreResponseDto updateStoreInfo(StoreRequestDto storeRequestDto, Long storeId) throws StoreNotFoundException, InvalidDataException, StoreAlreadyExistsException;
    StoreResponseDto deleteStoreById(Long storeId) throws StoreNotFoundException;
    List<StoreResponseDto> getAllStores() throws StoreNotFoundException;
    List<StoreResponseDto> getAllStoresRegisteredAfterSomeDate(LocalDateTime registeredAfter) throws StoreNotFoundException;
    Long numberOfAllStores();
    List<StoreResponseDto> searchShops(String name) throws InvalidDataException, StoreNotFoundException;
}
