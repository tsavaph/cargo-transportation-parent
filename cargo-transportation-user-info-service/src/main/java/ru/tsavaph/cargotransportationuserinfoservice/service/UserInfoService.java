package ru.tsavaph.cargotransportationuserinfoservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsavaph.cargotransportationuserinfoservice.UserInfoMapper;
import ru.tsavaph.cargotransportationuserinfoservice.domain.UserInfoDto;
import ru.tsavaph.cargotransportationuserinfoservice.repository.UserInfoRepository;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoDto saveUserInfo(UserInfoDto userInfoDto) {
        userInfoRepository.save(UserInfoMapper.map(userInfoDto));
        return userInfoDto;
    }

    public UserInfoDto getUserInfo(String login) {
        var entity = userInfoRepository.findByLogin(login).orElseThrow();
        return UserInfoMapper.map(entity);
    }

}
