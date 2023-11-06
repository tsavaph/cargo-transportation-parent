package ru.tsavaph.cargotransportationuserinfoservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsavaph.cargotransportationuserinfoservice.domain.UserInfoDto;
import ru.tsavaph.cargotransportationuserinfoservice.service.UserInfoService;

@RestController
@RequestMapping("/api/v1/user-info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @PostMapping("/save")
    public ResponseEntity<UserInfoDto> saveUserInfo(@RequestBody UserInfoDto userInfoDto) {
        return ResponseEntity.ok(userInfoService.saveUserInfo(userInfoDto));
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable String login) {
        return ResponseEntity.ok(userInfoService.getUserInfo(login));
    }

}
