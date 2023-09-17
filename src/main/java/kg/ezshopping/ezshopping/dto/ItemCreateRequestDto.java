package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Объект для создания товара")
public class ItemCreateRequestDto {

    @Schema(description = "ID категории, нужно передавать конечный ID")
    private Long categoryId;

    @Schema(description = "Комментарий продавца к товару")
    private String itemComment;

    @Schema(description = "Цена товара")
    private Integer price;

    @Schema(description = "ID пола для которого предназначен товар")
    private Long genderId;

    @Schema(description = "Список доступных цветов товара")
    private List<Long> availableColours;

    @Schema(description = "Список доступных размеров товара")
    private List<Long> availableSizes;

    public ItemCreateRequestDto() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ItemCreateRequestDto setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getItemComment() {
        return itemComment;
    }

    public ItemCreateRequestDto setItemComment(String itemComment) {
        this.itemComment = itemComment;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public ItemCreateRequestDto setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public Long getGenderId() {
        return genderId;
    }

    public ItemCreateRequestDto setGenderId(Long genderId) {
        this.genderId = genderId;
        return this;
    }

    public List<Long> getAvailableColours() {
        return availableColours;
    }

    public ItemCreateRequestDto setAvailableColours(List<Long> availableColours) {
        this.availableColours = availableColours;
        return this;
    }

    public List<Long> getAvailableSizes() {
        return availableSizes;
    }

    public ItemCreateRequestDto setAvailableSizes(List<Long> availableSizes) {
        this.availableSizes = availableSizes;
        return this;
    }
}
