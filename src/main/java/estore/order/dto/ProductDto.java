package estore.order.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private int availableUnits;
    private double price;

}
