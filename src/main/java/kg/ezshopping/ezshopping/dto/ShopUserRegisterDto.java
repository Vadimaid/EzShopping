package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Объект для создания нового магазина")
public class ShopUserRegisterDto extends AppUserRegisterDto {

    @Schema(description = "Адрес магазина", required = true)
    @NotNull
    @NotEmpty
    private String address;

    @Schema(description = "Ссылка на инстаграм аккаунт", required = true)
    @NotNull
    @NotEmpty
    private String instagramAccount;

    public ShopUserRegisterDto() {
    }

    public String getAddress() {
        return address;
    }

    public ShopUserRegisterDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getInstagramAccount() {
        return instagramAccount;
    }

    public ShopUserRegisterDto setInstagramAccount(String instagramAccount) {
        this.instagramAccount = instagramAccount;
        return this;
    }
}
