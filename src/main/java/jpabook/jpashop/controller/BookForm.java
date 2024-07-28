package jpabook.japshop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BookForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    public String author;
    public String isbn;
}
