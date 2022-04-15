package kr.flab.ottsharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.flab.ottsharing.entity.User;

@Repository
public interface MoneyRepository extends JpaRepository<User, Integer> {

    User findOneByUserId(String userId);

}
