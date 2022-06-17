package com.hashedin.services;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.hashedin.config.Mapper;
import com.hashedin.domains.NetflixRecord;
import com.hashedin.io.FileReader;

public class NetflixCSVReaderServiceImpl implements NetflixService {
	@Autowired
    	private DynamoDBMapper mapper;
	private Mapper mapper;
	private FileReader fileReader;
	private Set<NetflixRecord> records = new LinkedHashSet<>();
	private static final String QUERY_TIME_MSG = "Time taken to run query: ";
	private static final String FILE_PARSE_TIME_MSG = "Time taken to parse file: ";

	public NetflixCSVReaderServiceImpl(FileReader fileReader) {
		super();
		mapper = new Mapper();
		this.fileReader = fileReader;
	}
	
	@Override
	public void readFile(String filePath) {
		Instant startTime = Instant.now();
		this.records = this.fileReader.parseCSVFileIntoNetflixRecords(filePath);
		calculateAndPrintTimeElapsed(startTime, FILE_PARSE_TIME_MSG);
		System.out.println("Records Scanned: " + this.records.size());
	}

	@Override
	public List<NetflixRecord> filterRecordsByType(String type, int size) {
		
		Instant startTime = Instant.now();
		List<NetflixRecord> result = records.stream().filter(record -> {
			return type.equalsIgnoreCase(record.getType());
		}).limit(size).collect(Collectors.toList());
		calculateAndPrintTimeElapsed(startTime, QUERY_TIME_MSG);
		return result;
	}

	@Override
	public List<NetflixRecord> filterRecordsByGenre(Set<String> genres, int size) {
		Instant startTime = Instant.now();
		List<NetflixRecord> result = records.stream().filter(record -> {
			return record.getListedIn().containsAll(genres);
		}).limit(size).collect(Collectors.toList());
		calculateAndPrintTimeElapsed(startTime, QUERY_TIME_MSG);
		return result;
	}

	@Override
	public List<NetflixRecord> filterRecordsByTypeAndCountry(String type, String country, int size) {
		Instant startTime = Instant.now();
		List<NetflixRecord> result = records.stream().filter(record -> {
			return type.equalsIgnoreCase(record.getType()) && record.getCoutries().contains(country);
		}).limit(size).collect(Collectors.toList());
		calculateAndPrintTimeElapsed(startTime, QUERY_TIME_MSG);
		return result;
	}

	@Override
	public List<NetflixRecord> filterRecordsByDates(String startDateString, String endDateString, int size)
			throws DateTimeParseException {
		
		Instant startTime = Instant.now();
		LocalDate startDate = mapper.stringToLocalDate(startDateString);
		LocalDate endDate = mapper.stringToLocalDate(endDateString);

		if (startDate == null || endDate == null) {
			throw new DateTimeParseException("Unparsable Start or End Date",
					"Start Date: " + startDateString + " End Date: " + endDateString, 2);
		}

		List<NetflixRecord> result = records.stream().filter(record -> {
			return isDateUnderRange(record.getDateAdded(), startDate, endDate);
		}).limit(size).collect(Collectors.toList());
		calculateAndPrintTimeElapsed(startTime, QUERY_TIME_MSG);
		return result;
	}

	private boolean isDateUnderRange(LocalDate givenDate, LocalDate startDate, LocalDate endDate) {
		return givenDate != null && (startDate.isEqual(givenDate) || endDate.isEqual(givenDate)
				|| (startDate.isBefore(givenDate) && endDate.isAfter(givenDate)));
	}
	
	private void calculateAndPrintTimeElapsed(Instant startTime, String msg) {
		Instant endTime = Instant.now();
		Duration timeElapsed = Duration.between(startTime, endTime);
		System.out.println(msg + timeElapsed.toMillis() + " milliseconds.");
	}
}
