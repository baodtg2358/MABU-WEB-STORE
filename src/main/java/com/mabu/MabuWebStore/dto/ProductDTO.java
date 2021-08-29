package com.mabu.MabuWebStore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String picture;

    private Float price;

    private Boolean status;

    private String size;

    private int categoryID;
}
