package com.ftn.micro4.dto;

public class RatingDTO 
{
	private String comment ;
	private int ratingMark ;
	// provera
	// saljem idSobe, vracam listu rejtinga
	// prosecna ocena sobe
	//
	
	public RatingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RatingDTO(String comment, int ratingMark) 
	{
		super();
		this.comment = comment;
		this.ratingMark = ratingMark;
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

	
}
