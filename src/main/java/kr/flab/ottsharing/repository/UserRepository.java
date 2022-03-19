package kr.flab.ottsharing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.flab.ottsharing.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    User findOneByUserId(String userId);

    void deleteById(Integer id);
}
