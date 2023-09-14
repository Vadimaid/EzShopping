package kg.ezshopping.ezshopping.service.impl;

import kg.ezshopping.ezshopping.dto.StoreRequestDto;
import kg.ezshopping.ezshopping.dto.StoreResponseDto;
import kg.ezshopping.ezshopping.entity.Store;
import kg.ezshopping.ezshopping.exception.InvalidDataException;
import kg.ezshopping.ezshopping.exception.StoreAlreadyExistsException;
import kg.ezshopping.ezshopping.mapper.StoreMapper;
import kg.ezshopping.ezshopping.repository.StoreRepository;
import kg.ezshopping.ezshopping.service.StoreRegisterService;
import kg.ezshopping.ezshopping.validator.StoreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StoreRegisterServiceImpl implements StoreRegisterService {
    private final StoreRepository storeRepository;
    private final StoreValidator storeValidator;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public StoreRegisterServiceImpl(StoreRepository storeRepository, StoreValidator storeValidator, PasswordEncoder passwordEncoder) {
        this.storeRepository = storeRepository;
        this.storeValidator = storeValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto) throws InvalidDataException, StoreAlreadyExistsException {
        if (Objects.isNull(storeRequestDto)){
            throw new IllegalArgumentException("The store being created cannot be null!");
        }
        this.storeValidator.validateStoreRequestDto(storeRequestDto);

        Store store = StoreMapper.mapToStoreEntity(storeRequestDto);
        store.setPassword(this.passwordEncoder.encode(storeRequestDto.getPassword()));
        store = this.storeRepository.save(store);
        return StoreMapper.mapEntityToStoreResponseDto(store);
    }



}
