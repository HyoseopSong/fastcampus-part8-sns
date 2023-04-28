package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.UserEntityRepository;
import com.fastcampus.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserEntityRepository userEntityRepository;
  private final BCryptPasswordEncoder encoder;

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Value("${jwt.token.expired-time-ms}")
  private Long expiredTimeMs;

  @Transactional
  public User join(String userName, String password) {
    userEntityRepository.findByUserName(userName).ifPresent(it -> {
      throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
    });
    UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));
    return User.fromEntity(userEntity);
  }

  public String login(String userName, String password) {
    UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

    if (!encoder.matches(password, userEntity.getPassword())) {
      throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
    }

    return JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs);
  }
}
