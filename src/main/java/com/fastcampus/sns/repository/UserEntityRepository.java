package com.fastcampus.sns.repository;

import com.fastcampus.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
  Optional<UserEntity> findByUserName(String userName);

}
