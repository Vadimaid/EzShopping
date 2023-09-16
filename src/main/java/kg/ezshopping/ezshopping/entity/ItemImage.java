package kg.ezshopping.ezshopping.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_image")
public class ItemImage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @Column(name = "is_main")
    private Boolean isMain;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "image")
    private byte[] image;

    public ItemImage() {
    }

    public Item getItem() {
        return item;
    }

    public ItemImage setItem(Item item) {
        this.item = item;
        return this;
    }

    public Boolean getMain() {
        return isMain;
    }

    public ItemImage setMain(Boolean main) {
        isMain = main;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public ItemImage setImage(byte[] image) {
        this.image = image;
        return this;
    }
}
