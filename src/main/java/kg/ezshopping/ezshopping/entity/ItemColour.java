package kg.ezshopping.ezshopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "item_colour")
public class ItemColour extends BaseEntity {

    @Column(name = "colour_name")
    private String colourName;

    public ItemColour() {
    }

    public String getColourName() {
        return colourName;
    }

    public ItemColour setColourName(String colourName) {
        this.colourName = colourName;
        return this;
    }
}
