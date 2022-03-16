package kr.flab.ottsharing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.flab.ottsharing.entity.PartyMember;
import kr.flab.ottsharing.entity.User;

public interface PartyMemberRepository extends JpaRepository<PartyMember,Integer> {

    List<PartyMember> findByUser(User user);




}
