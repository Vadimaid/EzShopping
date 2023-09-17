package kg.ezshopping.ezshopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "item_gender")
public class ItemGender extends BaseEntity {

    @Column(name = "gender_name")
    private String genderName;

    public ItemGender() {
    }

    public String getGenderName() {
        return genderName;
    }

    public ItemGender setGenderName(String genderName) {
        this.genderName = genderName;
        return this;
    }
}
