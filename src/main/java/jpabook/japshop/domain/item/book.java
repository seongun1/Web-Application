package jpabook.japshop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity@Getter
@Setter
@DiscriminatorValue("B")
public class book extends Item {

    private String author;
    private String isbn;


}
