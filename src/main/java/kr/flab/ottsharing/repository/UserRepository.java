package kr.flab.ottsharing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.flab.ottsharing.entity.User;

public interface UserRepository extends JpaRepository<User,String> {

    @Override
    Optional<User> findById(String userId);
}
