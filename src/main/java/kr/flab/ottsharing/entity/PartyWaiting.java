package kr.flab.ottsharing.entity;

import java.time.LocalDateTime;

import javax.persistence.*;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "party_waiting")
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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name ="updated_timestamp")
    @UpdateTimestamp
    private LocalDateTime updatedTime;
}
