package kr.flab.ottsharing.repository;

import kr.flab.ottsharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyRepository extends JpaRepository<User, Integer> {

    User findByUserId(String userId);

}