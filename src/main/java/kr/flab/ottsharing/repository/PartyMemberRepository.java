package kr.flab.ottsharing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.flab.ottsharing.entity.Party;
import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;

@Repository
public interface PartyMemberRepository extends JpaRepository<PartyMember,Integer> {

    @Query("select pm.party from PartyMember pm where pm.user=:user")
    Optional<Party> findPartyByUser(@Param("user") User user);

    @Query("select pm.user from PartyMember pm where pm.party=:party")
    List<User> findUsersByParty(@Param("party") Party party);

}
