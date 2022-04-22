package kr.flab.ottsharing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.flab.ottsharing.entity.PartyWaiting;
import kr.flab.ottsharing.entity.User;

@Repository
public interface PartyWaitingRepository extends JpaRepository<PartyWaiting, Integer> {
    boolean existsByUser(User user);

    Optional<PartyWaiting> findByUser(User user);

    List<PartyWaiting> findTop3ByOrderByCreatedTimeAsc();

    boolean existsBy();
}
