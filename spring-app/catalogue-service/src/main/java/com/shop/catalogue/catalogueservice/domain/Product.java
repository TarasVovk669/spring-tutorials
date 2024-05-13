package com.shop.catalogue.catalogueservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity //when class is persistable
@Table(schema = "catalogue", name = "t_product")
@NamedQueries(
        @NamedQuery(name = "Product.getProductByIdCustom",
        query = "select p from Product p where p.id=:product_id")
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "c_name")
    private String name;

    @Column(name = "c_price")
    private BigDecimal price;

    @Column(name = "c_description")
    private String description;
}
