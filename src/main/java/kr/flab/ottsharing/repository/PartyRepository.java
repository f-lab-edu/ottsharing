package kr.flab.ottsharing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.flab.ottsharing.entity.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party, Integer> {
    @Override
    Optional<Party> findById(Integer partyId);

    @Query(value = "select p from Party p where p.isFull = false order by p.createdTime asc")
    List<Party> findNotFullOldestParties();

    void deleteById(Integer partyId);
}
