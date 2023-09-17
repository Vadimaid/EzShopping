package kg.ezshopping.ezshopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "item_comment")
public class ItemComment extends BaseEntity {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private AppUser createdBy;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "rating")
    private Integer rating;

    public ItemComment() {
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ItemComment setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public ItemComment setItem(Item item) {
        this.item = item;
        return this;
    }

    public AppUser getCreatedBy() {
        return createdBy;
    }

    public ItemComment setCreatedBy(AppUser createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public ItemComment setCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public ItemComment setRating(Integer rating) {
        this.rating = rating;
        return this;
    }
}
