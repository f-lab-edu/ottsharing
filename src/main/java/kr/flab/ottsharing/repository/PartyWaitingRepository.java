package kr.flab.ottsharing.repository;

import kr.flab.ottsharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.flab.ottsharing.entity.PartyWaiting;

@Repository
public interface PartyWaitingRepository extends JpaRepository<PartyWaiting, Integer> {
    boolean existsByUser(User user);

    void deleteByUser(User user);

    Iterable<PartyWaiting> findTop3ByOrderByCreatedTimeAsc();

    boolean existsBy();
}
