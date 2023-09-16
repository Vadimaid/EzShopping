package kg.ezshopping.ezshopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "item_size")
public class ItemSize extends BaseEntity {

    @Column(name = "size_name")
    private String sizeName;

    public ItemSize() {
    }

    public String getSizeName() {
        return sizeName;
    }

    public ItemSize setSizeName(String sizeName) {
        this.sizeName = sizeName;
        return this;
    }
}
