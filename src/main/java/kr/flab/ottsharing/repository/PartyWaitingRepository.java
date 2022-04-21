package kr.flab.ottsharing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.flab.ottsharing.entity.PartyWaiting;
import kr.flab.ottsharing.entity.User;

@Repository
public interface PartyWaitingRepository extends JpaRepository<PartyWaiting, Integer> {
    boolean existsByUser(User user);

    void deleteByUser(User user);

    List<PartyWaiting> findByOrderByCreatedTimeAsc();

    boolean existsBy();
}
