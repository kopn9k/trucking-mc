package com.po.trucking.car.data;

import com.po.trucking.car.dto.DriverIdAndNameDto;
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

@FeignClient(name = "drivers-ws", fallbackFactory = DriverFallbackFactory.class)
public interface DriversServiceClient {

    @GetMapping(path = "/api/v1/drivers/{id}")
    public ResponseEntity<DriverIdAndNameDto> getDriverIdAndNameDto(@PathVariable(name = "id") Long id);

}

@Component
class DriverFallbackFactory implements FallbackFactory<DriversServiceClient> {

    @Override
    public DriversServiceClient create(Throwable cause) {
        return new DriversServiceClientFallback(cause);
    }
}

@Slf4j
class DriversServiceClientFallback implements DriversServiceClient {

    private final Throwable cause;

    public DriversServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public ResponseEntity<DriverIdAndNameDto> getDriverIdAndNameDto(Long id) {
        log.info("In CarriersServiceClient" + cause.getLocalizedMessage());
        DriverIdAndNameDto driverIdAndNameDto = new DriverIdAndNameDto();
        driverIdAndNameDto.setId(id);
        return ResponseEntity.ok(driverIdAndNameDto);
    }

}
