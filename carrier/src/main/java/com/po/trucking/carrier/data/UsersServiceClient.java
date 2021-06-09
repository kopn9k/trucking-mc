package com.po.trucking.carrier.data;

import com.po.trucking.carrier.dto.UserDtoWithPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "users-ws", fallbackFactory = UsersServiceClientFactory.class)
public interface UsersServiceClient {

    @PostMapping(value = "")
    public ResponseEntity<UserDtoWithPassword> saveUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authorizationHeader, @RequestBody UserDtoWithPassword userDtoWithPassword);

}

@Component
class UsersServiceClientFactory implements FallbackFactory<UsersServiceClient> {

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
    public ResponseEntity<UserDtoWithPassword> saveUser(String authorizationHeader, UserDtoWithPassword userDtoWithPassword) {
        log.info("In UsersServiceClient" + cause.getLocalizedMessage());
        return ResponseEntity.ok(userDtoWithPassword);
    }

}
