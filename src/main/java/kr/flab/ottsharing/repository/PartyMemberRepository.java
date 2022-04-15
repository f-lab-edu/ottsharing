package kr.flab.ottsharing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;

@Repository
public interface PartyMemberRepository extends JpaRepository<PartyMember, Integer> {
    Optional<PartyMember> findOneByUser(User user);

    List<PartyMember> findByParty(Party party);

    void deleteAllByParty(Party party);
}
