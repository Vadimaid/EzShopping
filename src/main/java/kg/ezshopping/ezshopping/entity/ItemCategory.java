package kg.ezshopping.ezshopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_category")
public class ItemCategory extends BaseEntity {

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private ItemCategory parent;

    @Column(name = "is_active")
    private Boolean isActive;

    public ItemCategory() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ItemCategory setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public ItemCategory getParent() {
        return parent;
    }

    public ItemCategory setParent(ItemCategory parent) {
        this.parent = parent;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public ItemCategory setActive(Boolean active) {
        isActive = active;
        return this;
    }
}
