package kr.flab.ottsharing.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "party")
public class Party {
    @Id
    @Column(name = "party_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partyId;

    @Column(name = "ott_id")
    private String ottId;

    @Column(name = "ott_password")
    private String ottPassword;

    @Column(name = "is_full", columnDefinition = "TINYINT", length = 1)
    private boolean isFull;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "created_timestamp")
    @CreationTimestamp
    private LocalDateTime createdTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "updated_timestamp")
    @UpdateTimestamp
    private LocalDateTime updatedTime;
}
