package com.instructionator.profile.entity;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Profile {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long uid;
	private String firstName;
	private String lastName;
	@ElementCollection
	private Set<Long> favoritesList;
	public Set<Long> getFavoritesList() {
		return favoritesList;
	}
	public void setFavoritesList(Set<Long> favoritesList) {
		this.favoritesList = favoritesList;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getId() {
		return id;
	}
}
