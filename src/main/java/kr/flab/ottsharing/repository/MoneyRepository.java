package kr.flab.ottsharing.repository;

import kr.flab.ottsharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository extends JpaRepository<User, Integer> {

    User findByUserId(String userId);

}
