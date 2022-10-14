package estore.order.client;

import estore.order.dto.ProductChangeUnitDto;
import estore.order.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${feign.product.service.name}", url = "${feign.product.service.url}")
public interface ProductClient {
//    public ProductDto getProduct(Long id){
//        return ProductDto.builder()
//                .id(id)
//                .availableUnits(100)
//                .price(100)
//                .build();


    @PutMapping("/products/changeUnits")
    ProductDto changeProductUnit(
            @RequestHeader("Authorization") String authorization,
            ProductChangeUnitDto productChangeUnitDto);


//    }




}
