package com.hashedin.domains;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NetflixRecord {

	private String showId;
	private String type;
	private String title;
	private List<String> directors = new ArrayList<>();
	private List<String> cast = new ArrayList<>();
	private Set<String> coutries = new HashSet<>();
	private LocalDate dateAdded;
	private String releaseYear;
	private String rating;
	private String duration;
	private Set<String> listedIn = new HashSet<>();
	private String description;
	
	public NetflixRecord() {
		super();
	}

	public NetflixRecord(String showId, String type, String title, List<String> director, List<String> cast,
			Set<String> coutries, LocalDate dateAdded, String releaseYear, String rating, String duration,
			Set<String> listedIn, String description) {
		super();
		this.showId = showId;
		this.type = type;
		this.title = title;
		this.directors = director;
		this.cast = cast;
		this.coutries = coutries;
		this.dateAdded = dateAdded;
		this.releaseYear = releaseYear;
		this.rating = rating;
		this.duration = duration;
		this.listedIn = listedIn;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "NetflixRecord [type=" + type + ", title=" + title + ", coutries=" + coutries + ", dateAdded="
				+ dateAdded + ", releaseYear=" + releaseYear + ", rating=" + rating + ", duration=" + duration
				+ ", listedIn=" + listedIn + "]";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cast == null) ? 0 : cast.hashCode());
		result = prime * result + ((coutries == null) ? 0 : coutries.hashCode());
		result = prime * result + ((dateAdded == null) ? 0 : dateAdded.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((directors == null) ? 0 : directors.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((listedIn == null) ? 0 : listedIn.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((releaseYear == null) ? 0 : releaseYear.hashCode());
		result = prime * result + ((showId == null) ? 0 : showId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NetflixRecord other = (NetflixRecord) obj;
		if (cast == null) {
			if (other.cast != null)
				return false;
		} else if (!cast.equals(other.cast))
			return false;
		if (coutries == null) {
			if (other.coutries != null)
				return false;
		} else if (!coutries.equals(other.coutries))
			return false;
		if (dateAdded == null) {
			if (other.dateAdded != null)
				return false;
		} else if (!dateAdded.equals(other.dateAdded))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (directors == null) {
			if (other.directors != null)
				return false;
		} else if (!directors.equals(other.directors))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (listedIn == null) {
			if (other.listedIn != null)
				return false;
		} else if (!listedIn.equals(other.listedIn))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (releaseYear == null) {
			if (other.releaseYear != null)
				return false;
		} else if (!releaseYear.equals(other.releaseYear))
			return false;
		if (showId == null) {
			if (other.showId != null)
				return false;
		} else if (!showId.equals(other.showId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getDirector() {
		return directors;
	}

	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}

	public List<String> getCast() {
		return cast;
	}

	public void setCast(List<String> cast) {
		this.cast = cast;
	}

	public Set<String> getCoutries() {
		return coutries;
	}

	public void setCoutries(Set<String> coutries) {
		this.coutries = coutries;
	}

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Set<String> getListedIn() {
		return listedIn;
	}

	public void setListedIn(Set<String> listedIn) {
		this.listedIn = listedIn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
