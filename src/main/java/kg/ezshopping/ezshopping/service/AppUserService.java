package kg.ezshopping.ezshopping.service;

import kg.ezshopping.ezshopping.dto.AppUserResponseDto;
import kg.ezshopping.ezshopping.dto.ClientUserRegisterDto;
import kg.ezshopping.ezshopping.dto.ShopUserRegisterDto;
import kg.ezshopping.ezshopping.exception.*;

public interface AppUserService {

    AppUserResponseDto getCurrentAuthorizedUser();

    AppUserResponseDto registerNewClient(ClientUserRegisterDto requestDto) throws LoginAlreadyExistsException, InvalidUserCredentialsException;

    AppUserResponseDto registerNewShop(ShopUserRegisterDto requestDto) throws LoginAlreadyExistsException, InvalidUserCredentialsException;


}
