package com.ftn.micro4.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Rating 
{
	@Id
	private Long id;
	
	@NotNull
	private String comment;
	
	@NotNull
	private int ratingMark;
	
	@NotNull
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Room room;
	
	private boolean approved;
	
	public Rating() {
	}

	public Rating(@NotNull String comment, @NotNull int ratingMark, @NotNull User user, Room room,
			boolean approved) {
		super();
		this.comment = comment;
		this.ratingMark = ratingMark;
		this.user = user;
		this.room = room;
		this.approved = approved;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRatingMark() {
		return ratingMark;
	}

	public void setRatingMark(int ratingMark) {
		this.ratingMark = ratingMark;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	

}
