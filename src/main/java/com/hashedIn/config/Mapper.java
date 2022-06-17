package com.hashedin.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.hashedin.domains.NetflixRecord;

public class Mapper {

	private String[] validDateFormats = { "d-MMM-yy", "MMMM d, yyyy" };

	public NetflixRecord csvRecordToNetflixRecord(List<String> csvRecord, List<String> headers) {

		NetflixRecord record = new NetflixRecord();

		if (headers.size() != csvRecord.size()) {
			return record;
		}

		for (int i = 0; i < headers.size(); ++i) {

			String header = headers.get(i).trim().toLowerCase();
			String value = csvRecord.get(i).trim();

			if (!header.equals("date_added")) {
				value = value.toLowerCase();
			}

			switch (header) {

			case "show_id":
				record.setShowId(value);
				break;
			case "type":
				record.setType(value);
				break;
			case "title":
				record.setTitle(value);
				break;
			case "director":
				record.setDirectors(new ArrayList<>(split(value, ",").collect(Collectors.toList())));
				break;
			case "cast":
				record.setCast(new ArrayList<>(split(value, ",").collect(Collectors.toList())));
				break;
			case "country":
				record.setCoutries(new HashSet<>(split(value, ",").collect(Collectors.toSet())));
				break;
			case "date_added":
				record.setDateAdded(stringToLocalDate(value));
				break;
			case "release_year":
				record.setReleaseYear(value);
				break;
			case "rating":
				record.setRating(value);
				break;
			case "duration":
				record.setDuration(value);
				break;
			case "listed_in":
				record.setListedIn(new HashSet<>(split(value, ",").collect(Collectors.toSet())));
				break;
			case "description":
				record.setDescription(value);
				break;
			}
		}
		return record;
	}

	public List<String> stringToCSVRecord(String line) {
		List<String> list = new ArrayList<>();
		StringBuilder strBuilder = new StringBuilder();
		boolean quoteFlag = false;

		for (char ch : line.toCharArray()) {
			if (ch != '"' && ch != ',') {
				strBuilder.append(ch);
			} else {
				if (ch == ',') {
					if (!quoteFlag) {
						list.add(strBuilder.toString());
						strBuilder = new StringBuilder();
					} else {
						strBuilder.append(ch);
					}
				} else if (ch == '"') {
					if (!quoteFlag) {
						quoteFlag = true;
					} else {
						quoteFlag = false;
					}
				}
			}
		}
		return list;
	}

	public LocalDate stringToLocalDate(String dateString) {
		return stringToLocalDate(dateString, this.validDateFormats, 0, null);
	}

	public LocalDate stringToLocalDate(String dateString, String[] validDateFormats, int i, LocalDate date) {
		if (i >= validDateFormats.length) {
			return date;
		}

		try {
			date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(validDateFormats[i]));
			return date;
		} catch (DateTimeParseException e) {
			return stringToLocalDate(dateString, validDateFormats, ++i, date);
		}
	}

	private Stream<String> split(String str, String delimeter) {
		return Arrays.stream(str.split(delimeter)).map(String::trim);
	}
}
