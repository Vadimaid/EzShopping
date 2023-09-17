package kg.ezshopping.ezshopping.entity;

import kg.ezshopping.ezshopping.types.UserItemStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_items")
public class UserItem extends BaseEntity {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private UserItemStatus status;

    public UserItem() {
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserItem setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AppUser getUser() {
        return user;
    }

    public UserItem setUser(AppUser user) {
        this.user = user;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public UserItem setItem(Item item) {
        this.item = item;
        return this;
    }

    public UserItemStatus getStatus() {
        return status;
    }

    public UserItem setStatus(UserItemStatus status) {
        this.status = status;
        return this;
    }
}
