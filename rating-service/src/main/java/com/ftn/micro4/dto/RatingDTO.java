package com.ftn.micro4.dto;

public class RatingDTO 
{
	private String comment ;
	private String ratingMark ;
	// provera
	// saljem idSobe, vracam listu rejtinga
	// prosecna ocena sobe
	//
	
	public RatingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RatingDTO(String comment, String ratingMark) 
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

	public String getRatingMark() {
		return ratingMark;
	}

	public void setRatingMark(String ratingMark) {
		this.ratingMark = ratingMark;
	}

	
}
