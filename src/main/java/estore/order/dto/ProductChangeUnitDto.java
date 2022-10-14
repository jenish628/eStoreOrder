package estore.order.dto;

import estore.order.enumm.ProductEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductChangeUnitDto {

    private Long productId;
    private int quantity;
    private ProductEnum status;
}
