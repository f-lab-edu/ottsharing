package kr.flab.ottsharing.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User {

    @Id
    @Column(name = "user_id")
    private Long userid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
<<<<<<< HEAD
    @Column(name = "created_timestamp")
=======
>>>>>>> 85689b106cf631d77955d3b07325afe74d01b703
    private LocalDateTime createdTimeOfUser;
}
