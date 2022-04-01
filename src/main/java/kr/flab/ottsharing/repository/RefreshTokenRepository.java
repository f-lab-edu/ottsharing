package kr.flab.ottsharing.repository;

import kr.flab.ottsharing.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//String으로 바꿈
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
        Optional<RefreshToken> findByKey(String key);
}
