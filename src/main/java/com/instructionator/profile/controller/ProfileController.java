/* 
 * 
 * This is poop code
 * 
 */
package com.instructionator.profile.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.instructionator.profile.entity.Profile;
import com.instructionator.profile.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

	private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	ProfileService profileService;
	
	@GetMapping("/get")
	public ResponseEntity<Profile> getProfileById(@RequestHeader("USERID") String userId){
		Profile p = profileService.getProfileByUserId(Long.valueOf(userId));
		LOG.info("Looking for a profile...");
		if(p!=null) {
			return new ResponseEntity<>(p, HttpStatus.OK);
		} 
		// Should never get a NOT FOUND for profile.
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/update")
	public ResponseEntity<Profile> updateProfile(@RequestHeader("USERID") String userId, @RequestBody Profile profile){
		Profile p = profileService.updateProfile(Long.valueOf(userId), profile);
		if(p != null) {
			return new ResponseEntity<>(p, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/addFavorite")
	public ResponseEntity<Boolean> addFavorite(@RequestParam("instructionId") Long instructionId, @RequestHeader("USERID") String userId){
		profileService.addFavorite(Long.valueOf(userId), instructionId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/removeFavorite")
	public ResponseEntity<Boolean> removeFavorite(@RequestParam("instructionId") Long instructionId, @RequestHeader("USERID") String userId){
		profileService.removeFavorite(Long.valueOf(userId), instructionId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Profile> createProfile(@RequestHeader("USERID") String userId, @RequestBody Profile profile){
		Profile p = profileService.createProfile(profile);
		if(p.getId() != null) {
			return new ResponseEntity<>(p, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
