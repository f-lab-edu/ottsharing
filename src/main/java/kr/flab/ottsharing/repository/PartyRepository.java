package kr.flab.ottsharing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.flab.ottsharing.entity.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party,Integer> {
    @Override
    <S extends Party> S save(S entity);

    @Override
    Optional<Party> findById(Integer integer);

    Iterable<Party> findByIsFullFalse();
}
