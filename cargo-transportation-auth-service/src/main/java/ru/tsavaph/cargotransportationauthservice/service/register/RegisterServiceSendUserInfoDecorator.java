package ru.tsavaph.cargotransportationauthservice.service.register;

import lombok.RequiredArgsConstructor;
import ru.tsavaph.cargotransportationauthservice.domain.AuthenticationResponse;
import ru.tsavaph.cargotransportationauthservice.domain.RegisterRequest;
import ru.tsavaph.cargotransportationauthservice.domain.UserInfoDto;
import ru.tsavaph.cargotransportationauthservice.feign.UserInfoService;

@RequiredArgsConstructor
public class RegisterServiceSendUserInfoDecorator implements RegisterService {
    private final RegisterService registerService;
    private final UserInfoService userInfoService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var userInfo = UserInfoDto.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .build();

        userInfoService.sendUserInfo(userInfo);

        return registerService.register(request);
    }
}
