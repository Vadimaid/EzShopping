package kg.ezshopping.ezshopping.validator;

import kg.ezshopping.ezshopping.dto.StoreRequestDto;
import kg.ezshopping.ezshopping.entity.Store;
import kg.ezshopping.ezshopping.exception.InvalidDataException;
import kg.ezshopping.ezshopping.exception.StoreAlreadyExistsException;
import kg.ezshopping.ezshopping.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StoreValidator {
    private final StoreRepository storeRepository;

    private String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final String URL_REGEX =
            "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
                    "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
                    "([).!';/?:,][[:blank:]])?$";
    private final String PHONE_REGEX = "^\\+996\\(\\d{3}\\)\\s\\d{3}\\s\\d{3}$";
    private Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    private Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);


    @Autowired
    public StoreValidator(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void validateStoreRequestDto(StoreRequestDto storeRequestDto) throws InvalidDataException, StoreAlreadyExistsException {
        if(Objects.isNull(storeRequestDto)){
            throw new IllegalArgumentException("The store being created cannot be null!");
        }

        if(Objects.isNull(storeRequestDto.getFullName()) || storeRequestDto.getFullName().isEmpty()){
            throw new InvalidDataException("The store name cannot be null or empty");
        }
        this.checkStoreForUnique(storeRequestDto.getFullName());

        if(isValidEmail(storeRequestDto.getEmail())){
            throw new InvalidDataException("Invalid values for email");
        }
        if(Objects.isNull(storeRequestDto.getAddress()) || storeRequestDto.getAddress().isEmpty()){
            throw new InvalidDataException("The store address cannot be null or empty");
        }
        if(isValidUrl(storeRequestDto.getLink())){
            throw new InvalidDataException("Invalid values for link");
        }

        if(isValidPhone(storeRequestDto.getPhoneNumber())){
            throw new InvalidDataException("Invalid values for phone number");
        }
    }

    private boolean isValidEmail(String email) throws InvalidDataException {
        if (Objects.isNull(email) || email.isEmpty()) {
            throw new InvalidDataException("The store email cannot be null or empty");
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public boolean isValidUrl(String url) throws InvalidDataException {
        if (Objects.isNull(url) || url.isEmpty()) {
            throw new InvalidDataException("The link cannot be null or empty");
        }

        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }

    public boolean isValidPhone(String phoneNumber) throws InvalidDataException {
        if (Objects.isNull(phoneNumber) || phoneNumber.isEmpty()) {
            throw new InvalidDataException("The phone number cannot be null or empty");
        }
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }
    public void checkStoreForUnique(String storeName)
            throws StoreAlreadyExistsException
    {
        Optional<Store> unique =
                this.storeRepository.getStoreByFullName(storeName);
        if(unique.isPresent()) {
            throw new StoreAlreadyExistsException("A store with this name already exists in the system!");
        }
    }
}
