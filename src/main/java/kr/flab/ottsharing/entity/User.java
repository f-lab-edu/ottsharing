package kr.flab.ottsharing.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_timestamp")
    private LocalDateTime createdTime;
}
