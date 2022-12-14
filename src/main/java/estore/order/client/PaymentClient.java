package estore.order.client;


import estore.order.dto.PaymentResponseDto;
import estore.order.dto.ProcessPaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${feign.payment.service.name}", url = "${feign.payment.service.url}")
public interface PaymentClient {

    @PostMapping("/purchase")
    public PaymentResponseDto processPurchase(@RequestHeader("Authorization") String authorization, ProcessPaymentDto processPaymentDto);

}
