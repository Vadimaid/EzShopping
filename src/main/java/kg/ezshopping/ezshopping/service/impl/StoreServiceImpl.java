package kg.ezshopping.ezshopping.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kg.ezshopping.ezshopping.dto.StoreRequestDto;
import kg.ezshopping.ezshopping.dto.StoreResponseDto;
import kg.ezshopping.ezshopping.entity.QStore;
import kg.ezshopping.ezshopping.entity.Store;
import kg.ezshopping.ezshopping.exception.InvalidDataException;
import kg.ezshopping.ezshopping.exception.StoreAlreadyExistsException;
import kg.ezshopping.ezshopping.exception.StoreNotFoundException;
import kg.ezshopping.ezshopping.mapper.StoreMapper;
import kg.ezshopping.ezshopping.repository.StoreRepository;
import kg.ezshopping.ezshopping.service.StoreService;
import kg.ezshopping.ezshopping.validator.StoreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreValidator storeValidator;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository, StoreValidator storeValidator) {
        this.storeRepository = storeRepository;
        this.storeValidator = storeValidator;
    }

    @Override
    public Store findStoreById(Long storeId) throws StoreNotFoundException,IllegalArgumentException {
        if (Objects.isNull(storeId) || storeId < 1L){
            throw new IllegalArgumentException("The store id cannot be null! The store id cannot be less than 1!");
        }

        Optional<Store> storeOptional =
                this.storeRepository.getStoreById(storeId);

        if (storeOptional.isEmpty()){
            throw new StoreNotFoundException(String.format("No store with ID %d found!", storeId));
        }
        return storeOptional.get();
    }

    @Override
    public StoreResponseDto updateStoreInfo(StoreRequestDto storeRequestDto, Long storeId) throws StoreNotFoundException, InvalidDataException, StoreAlreadyExistsException {
        if (Objects.isNull(storeId)|| storeId < 1L){
            throw new IllegalArgumentException("The store id cannot be null or The store id cannot be less than 1!");
        }
        this.storeValidator.validateStoreRequestDto(storeRequestDto);

        Optional<Store> optionalStore =
                this.storeRepository.getStoreById(storeId);

        if (optionalStore.isEmpty()){
            throw new StoreNotFoundException(String.format("No store with ID %d found!", storeId));
        }

        Store store = optionalStore.get();
        this.storeRepository.save(store);
        return StoreMapper.mapEntityToStoreResponseDto(store);
    }

    @Override
    public StoreResponseDto deleteStoreById(Long storeId) throws StoreNotFoundException {
        if (Objects.isNull(storeId)|| storeId < 1L){
            throw new IllegalArgumentException("The store id cannot be null or The store id cannot be less than 1!");
        }
        Optional<Store> optionalStore =
                this.storeRepository.getStoreById(storeId);

        if (optionalStore.isEmpty()){
            throw new StoreNotFoundException(String.format("No store with ID %d found!", storeId));
        }
        Store store = optionalStore.get();
        store.setActive(Boolean.FALSE);
        return StoreMapper.mapEntityToStoreResponseDto(this.storeRepository.save(store));
    }

    @Override
    public List<StoreResponseDto> getAllStores() throws StoreNotFoundException {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        Iterable<Store> storeIterable =
                this.storeRepository.findAll(booleanBuilder.getValue());

        List<StoreResponseDto> storeResponseDtoList =
                StreamSupport
                        .stream(storeIterable.spliterator(),false)
                        .map(StoreMapper::mapEntityToStoreResponseDto)
                        .collect(Collectors.toList());

        if (storeResponseDtoList.isEmpty()){
            throw new StoreNotFoundException("Store not found");
        }
        return storeResponseDtoList;
    }

    @Override
    public List<StoreResponseDto> getAllStoresRegisteredAfterSomeDate(LocalDateTime registeredAfter) throws StoreNotFoundException {
        BooleanBuilder booleanBuilder = new BooleanBuilder(
                this.predicateForSearchStoreByDate(registeredAfter)
        );

        Iterable<Store> storeIterable =
                this.storeRepository.findAll(booleanBuilder.getValue());
        List<StoreResponseDto> storeResponseDtoList =
                StreamSupport
                        .stream(storeIterable.spliterator(),false)
                        .map(StoreMapper::mapEntityToStoreResponseDto)
                        .collect(Collectors.toList());


        if (storeResponseDtoList.isEmpty()){
            throw new StoreNotFoundException("Store not found");
        }

        return storeResponseDtoList;
    }

    @Override
    public Long numberOfAllStores() {
        return this.storeRepository.count();
    }

    @Override
    public List<StoreResponseDto> searchShops(String name) throws InvalidDataException, StoreNotFoundException {
        BooleanBuilder booleanBuilder = new BooleanBuilder(
                this.getSearchPredicateByParameter(name)
        );
        Iterable<Store> storeIterable =
               this.storeRepository.findAll(booleanBuilder.getValue());
        List<StoreResponseDto> storeResponseDtoList =
                StreamSupport
                        .stream(storeIterable.spliterator(),false)
                        .map(StoreMapper::mapEntityToStoreResponseDto)
                        .collect(Collectors.toList());
        if (storeResponseDtoList.isEmpty()){
            throw new StoreNotFoundException("Store not found");
        }
        return storeResponseDtoList;
    }

    private Predicate predicateForSearchStoreByDate(LocalDateTime registeredAfter){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QStore root = QStore.store;
        if (Objects.nonNull(registeredAfter)){
            booleanBuilder.and(root.createdAt.goe(registeredAfter));
        }
        return booleanBuilder;
    }
    private Predicate getSearchPredicateByParameter(String name) throws InvalidDataException {
        QStore root = QStore.store;
        BooleanBuilder builder = new BooleanBuilder();

        if (Objects.isNull(name) || name.isEmpty()) {
            throw new InvalidDataException("Invalid value");
        } else {
            builder.and(root.fullName.containsIgnoreCase(name));
        }
        return builder;
    }
}
