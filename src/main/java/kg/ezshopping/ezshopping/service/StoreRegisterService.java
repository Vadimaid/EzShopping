package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.StoreRequestDto;
import kg.ezshopping.ezshopping.dto.StoreResponseDto;
import kg.ezshopping.ezshopping.exception.InvalidDataException;
import kg.ezshopping.ezshopping.exception.StoreAlreadyExistsException;
import org.springframework.transaction.annotation.Transactional;

public interface StoreRegisterService {
    @Transactional
    StoreResponseDto createStore(StoreRequestDto storeRequestDto) throws InvalidDataException, StoreAlreadyExistsException;

}
