package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserEntityRepository userEntityRepository;

  public User join(String userName, String password) {
    userEntityRepository.findByUserName(userName).ifPresent(it -> {
      throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
    });

    UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, password));
    return User.fromEntity(userEntity);
  }

  public String login(String userName, String password) {
    UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

    if(userEntity.getPassword().equals(password)) {
      throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, "");
    }

    return "";
  }
}
