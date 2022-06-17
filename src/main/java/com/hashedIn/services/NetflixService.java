package com.hashedin.services;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

import com.hashedin.domains.NetflixRecord;

public interface NetflixService {

	void readFile(String filePath);
	List<NetflixRecord> filterRecordsByType(String type, int size);
	List<NetflixRecord> filterRecordsByGenre(Set<String> genres, int size);
	List<NetflixRecord> filterRecordsByTypeAndCountry(String type, String country, int size);
	List<NetflixRecord> filterRecordsByDates(String startDate, String endDate, int size) throws DateTimeParseException;
}
