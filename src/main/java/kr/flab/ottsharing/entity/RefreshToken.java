package kr.flab.ottsharing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    private String key;

    private String value;

    @Column(name = "created_timestamp")
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(name = "updated_timestamp")
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }

}
