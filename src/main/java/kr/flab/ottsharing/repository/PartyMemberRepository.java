package kr.flab.ottsharing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;

@Repository
public interface PartyMemberRepository extends JpaRepository<PartyMember, Integer> {
    Optional<PartyMember> findOneByUser(User user);

    List<PartyMember> findByParty(Party party);

    void deleteAllByParty(Party party);

    void deleteById(Integer partyMemberId);

    @Query("select pm from PartyMember pm where DAY(pm.createdTime) = ?1")
    List<PartyMember> findByCreatedDay1To28(int day);
    
    @Query("select pm from PartyMember pm where DAY(pm.createdTime) >= 28 AND DAY(pm.createdTime) <= 31")
    List<PartyMember> findByCreatedDay28To31();
}
