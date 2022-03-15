package kr.flab.ottsharing.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PartyWaiting {
    @Id
    @Column(name="waiting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer waitingId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name ="created_timestamp")
    @CreationTimestamp
    private LocalDateTime createdTime;

    public PartyWaiting(User user) {
        this.user = user;
    }
}
