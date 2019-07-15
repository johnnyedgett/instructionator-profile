package com.instructionator.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instructionator.profile.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findByUid(Long uid);
	Profile getOneByUid(Long uid);
}
