package com.po.trucking.user.data;

import com.po.trucking.user.dto.CarrierDto;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "carriers-ws", fallbackFactory = CarriersFallbackFactory.class)
public interface CarriersServiceClient {

    @GetMapping(path = "/api/v1/carriers/{id}")
    public ResponseEntity<CarrierDto> getCarrier(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authorizationHeader, @PathVariable(name = "id") Long id);

}

@Component
class CarriersFallbackFactory implements FallbackFactory<CarriersServiceClient> {

    @Override
    public CarriersServiceClient create(Throwable cause) {
        return new CarriersServiceClientFallback(cause);
    }
}

@Slf4j
class CarriersServiceClientFallback implements CarriersServiceClient {

    private final Throwable cause;

    public CarriersServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public ResponseEntity<CarrierDto> getCarrier(String authorizationHeader, Long id) {
        log.info("In CarriersServiceClient" + cause.getLocalizedMessage());
        CarrierDto carrierDto = new CarrierDto();
        carrierDto.setId(id);
        return ResponseEntity.ok(carrierDto);
    }

}

