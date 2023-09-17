package kg.ezshopping.ezshopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_shopping_history")
public class UserShoppingHistory extends BaseEntity {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "bought_price")
    private Integer boughtPrice;

    public UserShoppingHistory() {
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserShoppingHistory setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AppUser getUser() {
        return user;
    }

    public UserShoppingHistory setUser(AppUser user) {
        this.user = user;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public UserShoppingHistory setItem(Item item) {
        this.item = item;
        return this;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public UserShoppingHistory setPublic(Boolean aPublic) {
        isPublic = aPublic;
        return this;
    }

    public Integer getBoughtPrice() {
        return boughtPrice;
    }

    public UserShoppingHistory setBoughtPrice(Integer boughtPrice) {
        this.boughtPrice = boughtPrice;
        return this;
    }
}
