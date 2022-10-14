package estore.order.client;


import estore.order.dto.VerifyUserTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${feign.auth.service.name}", url = "${feign.auth.service.url}")
public interface AuthClient {

    @PostMapping("/verity-token")
    boolean verifyUser(VerifyUserTokenDto verifyUserTokenDto);
}
