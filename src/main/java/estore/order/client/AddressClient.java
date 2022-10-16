package estore.order.client;


import estore.order.dto.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${feign.address.service.name}", url = "${feign.address.service.url}")

public interface AddressClient {


    @GetMapping("/addresses/{id}")
    AddressDto findAddressById(@RequestHeader("Authorization") String authorization, @PathVariable Long id   );

}
