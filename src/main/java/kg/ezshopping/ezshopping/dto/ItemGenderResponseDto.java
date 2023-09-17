package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Объект для передачи пола")
public class ItemGenderResponseDto {

    @Schema(description = "ID цвета")
    private Long id;

    @Schema(description = "Название пола")
    private String name;

    public ItemGenderResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public ItemGenderResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemGenderResponseDto setName(String name) {
        this.name = name;
        return this;
    }
}
