package estore.order.client;


import estore.order.dto.PaymentResponseDto;
import estore.order.dto.ProcessPaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient
public interface PaymentClient {

    @PostMapping("/purchase")
    public PaymentResponseDto processPurchase(ProcessPaymentDto processPaymentDto);

}
