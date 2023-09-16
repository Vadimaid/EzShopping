package kg.ezshopping.ezshopping.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/items")
@Tag(
        name = "Items Controller",
        description = "Endpoint'ы для работы с товарами"
)
public class ItemController {


}
