package kg.ezshopping.ezshopping.service;

import com.querydsl.core.types.Predicate;
import kg.ezshopping.ezshopping.dto.StoreResponseDto;
import kg.ezshopping.ezshopping.entity.Store;
import kg.ezshopping.ezshopping.entity.StoreTestEntity;
import kg.ezshopping.ezshopping.exception.InvalidDataException;
import kg.ezshopping.ezshopping.exception.StoreNotFoundException;
import kg.ezshopping.ezshopping.repository.StoreRepository;
import kg.ezshopping.ezshopping.service.impl.StoreServiceImpl;
import kg.ezshopping.ezshopping.validator.StoreValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.when;


@ExtendWith(value = MockitoExtension.class)
class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private StoreValidator storeValidator;
    private StoreService storeService;

    @BeforeEach
    private void beforeEach(){
        this.storeService = new StoreServiceImpl(
                this.storeRepository,
                this.storeValidator
        );
    }

    @Test
    public void findStoreById_OK(){
        Store store = StoreTestEntity.getTestStoreEntity();
        when(this.storeRepository.getStoreById(
                        Mockito.eq(store.getId())
                ))
                .thenAnswer(invocationOnMock -> Optional.of(store));

        try {
            Store result =
                    this.storeService.findStoreById(store.getId());
            Assertions.assertEquals(store.getId(),result.getId());
            Assertions.assertEquals(store.getFullName(),result.getFullName());
        }catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void findStoreById_StoreNotFound(){
        Store store = StoreTestEntity.getTestStoreEntity();
        when(this.storeRepository.getStoreById(
                        Mockito.eq(store.getId())
                ))
                .thenAnswer(invocationOnMock -> Optional.empty());

        Exception exception = Assertions.assertThrows(
                StoreNotFoundException.class,
                () -> this.storeService.findStoreById(store.getId())
        );
        Assertions.assertEquals(
                String.format("No store with ID 1 found!", store.getId()),
                exception.getMessage()
        );
    }

    @Test
    public void findStoreById_InvalidId(){
        Exception exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> this.storeService.findStoreById(0L)
        );
        Assertions.assertEquals(
                "The store id cannot be null! The store id cannot be less than 1!",
                exception.getMessage()
        );
    }

    @Test
    public void searchShops_OK(){
        Store store = StoreTestEntity.getTestStoreEntity();

                Mockito
                        .when(this.storeRepository.findAll(Mockito.any(Predicate.class)))
                .thenAnswer(invocationOnMock -> List.of(store));

        try {
            List<StoreResponseDto> resultList =
                    this.storeService.searchShops(
                            StoreTestEntity.STORE_NAME
                    );
            Assertions.assertEquals(1, resultList.size());

        }catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void searchShops_StoreNotFound(){
        Store store = StoreTestEntity.getTestStoreEntity();

        Exception exception = Assertions.assertThrows(
                StoreNotFoundException.class,
                () -> this.storeService.searchShops(store.getFullName())
        );
        Assertions.assertEquals("Store not found",
                exception.getMessage()
        );
    }
}