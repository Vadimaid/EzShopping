package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Объект для загрузки изображения товара")
public class ItemImageUploadDto {

    @Schema(description = "Изображение в формате Base64")
    private String itemImage;

    @Schema(description = "Является ли изображение основным")
    private Boolean isMain;

    public ItemImageUploadDto() {
    }

    public String getItemImage() {
        return itemImage;
    }

    public ItemImageUploadDto setItemImage(String itemImage) {
        this.itemImage = itemImage;
        return this;
    }

    public Boolean getMain() {
        return isMain;
    }

    public ItemImageUploadDto setMain(Boolean main) {
        isMain = main;
        return this;
    }
}
