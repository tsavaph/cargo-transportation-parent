package ru.tsavaph.cargotransportationauthservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tsavaph.cargotransportationauthservice.domain.userinfo.UserInfoDto;

@FeignClient(name = "cargo-transportation-user-info-service")
public interface UserInfoService {

    @PostMapping(value = "${user-info-service.url}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void sendUserInfo(@RequestBody UserInfoDto userInfoDto);

}
