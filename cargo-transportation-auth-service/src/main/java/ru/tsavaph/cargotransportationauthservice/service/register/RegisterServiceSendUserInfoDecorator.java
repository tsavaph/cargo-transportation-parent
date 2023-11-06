package ru.tsavaph.cargotransportationauthservice.service.register;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.tsavaph.cargotransportationauthservice.domain.AuthenticationResponse;
import ru.tsavaph.cargotransportationauthservice.domain.RegisterRequest;
import ru.tsavaph.cargotransportationauthservice.domain.UserInfoDto;

@RequiredArgsConstructor
public class RegisterServiceSendUserInfoDecorator implements RegisterService {
    private final RegisterService registerService;
    private final RestTemplate restTemplate;

    @Value("${user-info-service.url}")
    private String url;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var sendUserInfo = UserInfoDto.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .build();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(sendUserInfo, headers);
        restTemplate.postForEntity(url, entity, String.class);

        return registerService.register(request);
    }
}
