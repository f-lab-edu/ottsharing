package kr.flab.ottsharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.flab.ottsharing.entity.PartyWaiting;

@Repository
public interface PartyWaitingRepository extends JpaRepository<PartyWaiting, Integer> {
    @Override
    <S extends PartyWaiting> S save(S entity);

    Iterable<PartyWaiting> findTop3ByOrderByCreatedTimeAsc();

    @Override
    void deleteById(Integer id);
}
