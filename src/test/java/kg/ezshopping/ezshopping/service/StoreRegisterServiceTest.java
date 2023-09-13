package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.StoreDtoTestProvider;
import kg.ezshopping.ezshopping.dto.StoreRequestDto;
import kg.ezshopping.ezshopping.dto.StoreResponseDto;
import kg.ezshopping.ezshopping.entity.Store;
import kg.ezshopping.ezshopping.entity.StoreTestEntity;
import kg.ezshopping.ezshopping.repository.StoreRepository;
import kg.ezshopping.ezshopping.service.impl.StoreRegisterServiceImpl;
import kg.ezshopping.ezshopping.validator.StoreValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;


@ExtendWith(value = MockitoExtension.class)
class StoreRegisterServiceTest {
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private StoreValidator storeValidator;
    private StoreRegisterService storeRegisterService;

    @BeforeEach
    private void beforeEach(){
        this.storeRegisterService = new StoreRegisterServiceImpl(
                this.storeRepository,
                this.storeValidator
        );
    }

    @Test
    public void testRegisterNewStore_OK(){
        StoreRequestDto storeRequestDto =
                StoreDtoTestProvider.getTestStoreRequestDto();

        Mockito
                .when(this.storeRepository.save(Mockito.any(Store.class)))
                .thenAnswer(invocationOnMock -> {
                    Store store =
                            (Store) invocationOnMock.getArguments()[0];
                    store.setCreatedAt(LocalDateTime.now());
                    return store.setId(StoreTestEntity.STORE_ID);
                });
        try {
            Mockito
                    .doNothing()
                    .when(this.storeValidator)
                    .validateStoreRequestDto(Mockito.any(StoreRequestDto.class));

            StoreResponseDto responseDto = this.storeRegisterService.createStore(storeRequestDto);
            Assertions.assertEquals(storeRequestDto.getFullName(), responseDto.getFullName());
            Assertions.assertEquals(storeRequestDto.getEmail(),responseDto.getEmail());
            Assertions.assertEquals(storeRequestDto.getPhoneNumber(),responseDto.getPhoneNumber());
            Assertions.assertEquals(storeRequestDto.getLink(),responseDto.getLink());
            Assertions.assertEquals(storeRequestDto.getAddress(),responseDto.getAddress());
        }catch (Exception ex){
            Assertions.fail(ex.getMessage());
        }
    }

}