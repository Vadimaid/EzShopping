package kg.ezshopping.ezshopping.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "item")
public class Item extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private AppUser shop;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private ItemCategory itemCategory;

    @Column(name = "item_comment")
    private String itemComment;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private ItemGender gender;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "item_colour_available",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "colour_id")
    )
    private List<ItemColour> availableColours;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "item_size_available",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    private List<ItemSize> availableSizes;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> imageList;

    @OneToMany(mappedBy = "item")
    private List<ItemComment> itemComments;

    public Item() {
    }

    public AppUser getShop() {
        return shop;
    }

    public Item setShop(AppUser shop) {
        this.shop = shop;
        return this;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public Item setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
        return this;
    }

    public String getItemComment() {
        return itemComment;
    }

    public Item setItemComment(String itemComment) {
        this.itemComment = itemComment;
        return this;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public Item setAvailable(Boolean available) {
        isAvailable = available;
        return this;
    }

    public List<ItemColour> getAvailableColours() {
        return availableColours;
    }

    public Item setAvailableColours(List<ItemColour> availableColours) {
        this.availableColours = availableColours;
        return this;
    }

    public List<ItemSize> getAvailableSizes() {
        return availableSizes;
    }

    public Item setAvailableSizes(List<ItemSize> availableSizes) {
        this.availableSizes = availableSizes;
        return this;
    }

    public List<ItemImage> getImageList() {
        return imageList;
    }

    public Item setImageList(List<ItemImage> imageList) {
        this.imageList = imageList;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public Item setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public ItemGender getGender() {
        return gender;
    }

    public Item setGender(ItemGender gender) {
        this.gender = gender;
        return this;
    }

    public List<ItemComment> getItemComments() {
        return itemComments;
    }

    public Item setItemComments(List<ItemComment> itemComments) {
        this.itemComments = itemComments;
        return this;
    }
}
