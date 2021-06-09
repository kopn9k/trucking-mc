package com.po.trucking.driver.data;

import com.po.trucking.driver.dto.UserEmailAndIdDto;
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

@FeignClient(name = "users-ws", fallbackFactory = UsersFallbackFactory.class)
public interface UsersServiceClient {

    @GetMapping(path = "/api/v1/users/{id}")
    public ResponseEntity<UserEmailAndIdDto> getUserEmailById(@PathVariable(name = "id") Long id);

}

@Component
class UsersFallbackFactory implements FallbackFactory<UsersServiceClient> {

    @Override
    public UsersServiceClient create(Throwable cause) {
        return new UsersServiceClientFallback(cause);
    }
}

@Slf4j
class UsersServiceClientFallback implements UsersServiceClient {

    private final Throwable cause;

    public UsersServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public ResponseEntity<UserEmailAndIdDto> getUserEmailById(Long id) {
        log.info("In UsersServiceClient" + cause.getLocalizedMessage());
        UserEmailAndIdDto userEmailAndIdDto = new UserEmailAndIdDto();
        userEmailAndIdDto.setId(id);
        return ResponseEntity.ok(userEmailAndIdDto);
    }

}
