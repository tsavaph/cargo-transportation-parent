package ru.tsavaph.cargotransportationuserinfoservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsavaph.cargotransportationuserinfoservice.mapper.UserInfoMapper;
import ru.tsavaph.cargotransportationuserinfoservice.domain.UserInfoDto;
import ru.tsavaph.cargotransportationuserinfoservice.exception.UserInfoNotFoundException;
import ru.tsavaph.cargotransportationuserinfoservice.repository.UserInfoRepository;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Transactional
    public UserInfoDto saveUserInfo(UserInfoDto userInfoDto) {
        var userInfoEntity = UserInfoMapper.map(userInfoDto);
        var user = userInfoRepository.findByLogin(userInfoDto.getLogin());
        if (user.isPresent()) {
            userInfoEntity.setId(user.get().getId());
        }
        userInfoRepository.save(userInfoEntity);
        return userInfoDto;
    }

    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(String login) {
        var entity = userInfoRepository.findByLogin(login)
                .orElseThrow(() -> new UserInfoNotFoundException("User info is not found"));
        return UserInfoMapper.map(entity);
    }

}
