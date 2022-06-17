package com.hashedin.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.hashedin.config.Mapper;
import com.hashedin.domains.NetflixRecord;

public class CSVFileReaderImpl implements FileReader {

	private Mapper mapper;

	public CSVFileReaderImpl() {
		mapper = new Mapper();
	}

	@Override
	public Set<NetflixRecord> parseCSVFileIntoNetflixRecords(String filePath) {

		Set<NetflixRecord> records = new LinkedHashSet<>();
		try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
				Scanner sc = new Scanner(fileInputStream)) {

			List<String> headers = new ArrayList<>();

//			Get Headers
			if (sc.hasNextLine()) {
				headers = mapper.stringToCSVRecord(sc.nextLine());
			}

//			Get Content
			while (sc.hasNextLine()) {
				records.add(mapper.csvRecordToNetflixRecord(mapper.stringToCSVRecord(sc.nextLine()), headers));
			}
		} catch (NullPointerException e) {
			System.out.println("Please provide the file path");
		} catch (FileNotFoundException e) {
			System.out
					.println(String.format("File Path: %s doesn't exists. Please provide the correct path", filePath));
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return records;
	}
}
