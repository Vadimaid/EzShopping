package kg.ezshopping.ezshopping.controller;

import kg.ezshopping.ezshopping.dto.StoreRequestDto;
import kg.ezshopping.ezshopping.dto.StoreResponseDto;
import kg.ezshopping.ezshopping.exception.InvalidDataException;
import kg.ezshopping.ezshopping.exception.StoreAlreadyExistsException;
import kg.ezshopping.ezshopping.exception.StoreNotFoundException;
import kg.ezshopping.ezshopping.service.StoreRegisterService;
import kg.ezshopping.ezshopping.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final StoreRegisterService storeRegisterService;
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreRegisterService storeRegisterService, StoreService storeService) {
        this.storeRegisterService = storeRegisterService;
        this.storeService = storeService;
    }

    @PostMapping("/register")
    StoreResponseDto createStore(
            @RequestBody StoreRequestDto storeRequestDto)
            throws InvalidDataException, StoreAlreadyExistsException {
        return this.storeRegisterService.createStore(storeRequestDto);
    }

    @PutMapping("/update")
    public StoreResponseDto updateStoreInfo(
            @RequestBody StoreRequestDto storeRequestDto,
            @RequestParam Long storeId) throws StoreNotFoundException, InvalidDataException, StoreAlreadyExistsException {
        return this.storeService.updateStoreInfo(storeRequestDto,storeId);
    }

    @DeleteMapping("delete")
    public StoreResponseDto deleteStoreById(
            @RequestParam Long storeId) throws StoreNotFoundException {
        return this.storeService.deleteStoreById(storeId);
    }

    @GetMapping("/get_all_stores")
    public List<StoreResponseDto> getAllStores() throws StoreNotFoundException {
        return this.storeService.getAllStores();
    }

    @GetMapping("/get_all_after_date")
    public List<StoreResponseDto> getAllStoresRegisteredAfterSomeDate(
            @RequestParam(required = false) LocalDateTime registeredAfter) throws StoreNotFoundException {
        return this.storeService.getAllStoresRegisteredAfterSomeDate(registeredAfter);
    }

    @GetMapping("/get_all_by_name")
    public List<StoreResponseDto> searchShops(
            @RequestParam String name) throws InvalidDataException, StoreNotFoundException{
        return this.storeService.searchShops(name);
    }

    @GetMapping("/store_count")
    public Long numberOfAllStores(){
        return this.storeService.numberOfAllStores();
    }
}
