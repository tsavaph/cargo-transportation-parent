package ru.tsavaph.cargotransportationauthservice.service.register;


import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import ru.tsavaph.cargotransportationauthservice.domain.AuthenticationResponse;
import ru.tsavaph.cargotransportationauthservice.domain.RegisterRequest;
import ru.tsavaph.cargotransportationauthservice.domain.UserInfoDto;
import ru.tsavaph.cargotransportationauthservice.exception.UserInfoServiceException;
import ru.tsavaph.cargotransportationauthservice.feign.UserInfoService;

@RequiredArgsConstructor
public class RegisterServiceSendUserInfoDecorator implements RegisterService {
    private final RegisterService registerService;
    private final UserInfoService userInfoService;

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        var response = registerService.register(request);

        var userInfo = UserInfoDto.builder()
                .name(request.getName())
                .login(request.getPhoneNumber())
                .email(request.getEmail())
                .build();
        try {
            userInfoService.sendUserInfo(userInfo);
        } catch (FeignException exception) {
            throw new UserInfoServiceException(
                    exception.getMessage(),
                    HttpStatus.valueOf(exception.status())
            );
        }
        return response;
    }
}
