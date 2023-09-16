package kg.ezshopping.ezshopping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Объект для передачи цвета товара")
public class ItemColourResponseDto {

    @Schema(description = "ID цвета")
    private Long id;

    @Schema(description = "Название цвета")
    private String name;

    public ItemColourResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public ItemColourResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemColourResponseDto setName(String name) {
        this.name = name;
        return this;
    }
}
