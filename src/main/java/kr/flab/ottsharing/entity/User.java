package kr.flab.ottsharing.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user")
@Getter
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_userid", unique = true)
    private String userId;

    @Column(name = "user_password")
    private String userPassword;

    @Column(unique = true)
    private String email;

    private Long money;

    @Column(name = "created_timestamp")
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(name = "updated_timestamp")
    @UpdateTimestamp
    private LocalDateTime updatedTime;
}
