package com.instructionator.profile.service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instructionator.profile.entity.Profile;
import com.instructionator.profile.repository.ProfileRepository;

@Service
public class ProfileService {

	private static final Logger LOG = LoggerFactory.getLogger(ProfileService.class);
	
	@Autowired
	ProfileRepository profileRepository;
	
	public Profile getProfileByUserId(Long uid) {
		try {
			Profile p = profileRepository.findByUid(uid).get();
			return p;
		} catch(NoSuchElementException e) {
			LOG.info(String.format("No profile found for %d, creating a new one.", uid));
			Profile p = new Profile();
			p.setUid(uid);
			p.setFavoritesList(new HashSet<Long>());
			p.setFirstName("");
			p.setLastName("");
			p = profileRepository.save(p);
			return p;
		}
	}
	
	public Profile createProfile(Profile profile) {
		try {
			Profile p = profileRepository.save(profile);
			return p;
		} catch (Exception e) {
			LOG.info(String.format("ERROR creating profile: %s", e.getMessage()));
			return null;
		}
	}
	
	public boolean addFavorite(Long userId, Long instructionId) {
		try {
			Profile p = profileRepository.getOneByUid(userId);
			if(p.getId() != null) {
				Set<Long> favoritesList = p.getFavoritesList();
				favoritesList.add(instructionId); 
				p.setFavoritesList(favoritesList);
				p = profileRepository.save(p);
				return true;
			} else {
				return false; // was unable to complete this request
			}
		} catch(NoSuchElementException e) {
			LOG.info(String.format("ERROR getting profile by uid %d: %s", userId, e.getMessage()));
			return false;
		}
	}
	
	public boolean removeFavorite(Long userId, Long instructionId) {
		try {
			Profile p = profileRepository.getOneByUid(userId);
			if(p.getId() != null) {
				Set<Long> favoritesList = p.getFavoritesList();
				return favoritesList.remove(instructionId);
			} else {
				return false; // was unable to complete this request
			}
		} catch(NoSuchElementException e) {
			LOG.info(String.format("ERROR getting profile by uid %d: %s", userId, e.getMessage()));
			return false;
		}
	}
	
	public Profile updateProfile(Long userId, Profile pNew) {
		try {
			LOG.info(String.format("Looking for %d", userId));
			Profile p = profileRepository.getOneByUid(userId);
			p.setFirstName(pNew.getFirstName());
			p.setLastName(pNew.getLastName());
			p = profileRepository.save(p);
			return p;
		} catch (Exception e) {
			return null;
		}
	}
}
