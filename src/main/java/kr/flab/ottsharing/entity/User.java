package kr.flab.ottsharing.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_userid",unique = true)
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
