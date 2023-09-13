package kg.ezshopping.ezshopping.dto;

import static kg.ezshopping.ezshopping.entity.StoreTestEntity.*;
import static org.junit.jupiter.api.Assertions.*;

public class StoreDtoTestProvider {
    public static StoreRequestDto getTestStoreRequestDto(){
        return new StoreRequestDto()
                .setFullName(STORE_NAME)
                .setEmail(EMAIL)
                .setLink(LINK)
                .setAddress(ADDRESS);
    }
}