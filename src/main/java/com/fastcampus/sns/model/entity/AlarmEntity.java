package com.fastcampus.sns.model.entity;

import com.fastcampus.sns.model.AlarmArgs;
import com.fastcampus.sns.model.AlarmType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "\"alarm\"", indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
@Getter
@Setter
@SQLDelete(sql = "UPDATE \"alarm\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
public class AlarmEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // 알람을 받은 사람
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Enumerated(EnumType.STRING)
  private AlarmType alarmType;

  @JdbcTypeCode(SqlTypes.JSON)
  private AlarmArgs args;

  @Column(name = "registered_at")
  private Timestamp registeredAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "deleted_at")
  private Timestamp deletedAt;

  @PrePersist
  void registeredAt() {
    this.registeredAt = Timestamp.from(Instant.now());
  }

  @PreUpdate
  void updatedAt() {
    this.updatedAt = Timestamp.from(Instant.now());
  }

  public static AlarmEntity of(AlarmType alarmType, AlarmArgs args, UserEntity user) {
    AlarmEntity entity = new AlarmEntity();
    entity.setUser(user);
    entity.setAlarmType(alarmType);
    entity.setArgs(args);
    return entity;
  }
}
