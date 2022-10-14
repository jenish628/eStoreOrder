package estore.order.client;

import estore.order.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductClient {
    public ProductDto getProduct(Long id){
        return ProductDto.builder()
                .id(id)
                .availableUnits(100)
                .price(100)
                .build();
    }




}
