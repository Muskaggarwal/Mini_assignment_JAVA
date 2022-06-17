package com.hashedin;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.hashedin.domains.NetflixRecord;
import com.hashedin.io.CSVFileReaderImpl;
import com.hashedin.services.NetflixCSVReaderServiceImpl;
import com.hashedin.services.NetflixService;

public class CSVFileReaderApplication {

	private static final String FILEPATH = "src/main/resources/netflix_titles.csv";
	private static final String INVALID_INPUT_ERROR_MSG = "Please enter valid input.";
	private static final String RECORDS_INPUT_MSG = "Please enter number of records you want:";
	private static final String TYPE_INPUT_MSG = "Please enter the content Type: ";
	private static final String GENRE_INPUT_MSG = "Please enter comma(,) seprated list of Genres: ";
	private static final String TYPE_AND_COUNTRY_INPUT_MSG = "Please enter colon(:) seprated input(e.g. Type : Country): ";
	private static final String DATE_FILTER_INPUT_MSG = "Please enter Start and End Date separated by colon(:) either in 'd-MMM-yy' or 'MMMM d, yyyy' format(e.g 4-Aug-15 : September 5, 2019): ";
	private NetflixService netflixService;
	private List<NetflixRecord> result = new ArrayList<>();
	private Scanner sc;

	public CSVFileReaderApplication() {
		this.netflixService = new NetflixCSVReaderServiceImpl(new CSVFileReaderImpl());
	}

	public static void main(String[] args) {
		CSVFileReaderApplication app = new CSVFileReaderApplication();
		app.loadApplication();
	}

	public void loadApplication() {

		this.loadDataFromFile(FILEPATH);
		this.sc = new Scanner(System.in);
		boolean render = true;
		while (render) {
			try {
				render = this.renderUI();
			} catch (InterruptedException e) {
				System.out.println("Something bad happened: " + e.getMessage());
			}
		}
		sc.close();
	}

	public void loadDataFromFile(String filePath) {
		this.netflixService.readFile(filePath);
	}

	public boolean renderUI() throws InterruptedException {

		boolean resultReady = false;
		String menuOption = "";
		this.showMenu();
		menuOption = sc.nextLine().trim();
		switch (menuOption) {
		case "1":
			resultReady = this.loadTypeFilter();
			break;
		case "2":
			resultReady = this.loadGenreFilter();
			break;
		case "3":
			resultReady = this.loadTypeAndCountryFilter();
			break;
		case "4":
			resultReady = this.loadDateFilter();
			break;
		case "5":
			return false;
		default:
			showInvalidInput();
			return true;
		}
		if (resultReady) {
			showResult(result);
			resultReady = false;
		}
		return true;
	}

	private boolean loadDateFilter() throws InterruptedException {
		int noOfRecords = 0;
		List<String> startAndEndDate = new ArrayList<>(2);
		noOfRecords = this.inputNoOfRecords();
		if (noOfRecords < 1) {
			showInvalidInput();
			return false;
		}
		System.out.println(DATE_FILTER_INPUT_MSG);
		startAndEndDate = Arrays.stream(sc.nextLine().trim().split(":")).map(String::trim).collect(Collectors.toList());
		if (startAndEndDate.size() != 2 || startAndEndDate.get(0).isEmpty() || startAndEndDate.get(1).isEmpty()) {
			showInvalidInput();
			return false;
		}
		try {
			this.result = this.netflixService.filterRecordsByDates(startAndEndDate.get(0), startAndEndDate.get(1),
					noOfRecords);
			return true;
		} catch (DateTimeParseException e) {
			showInvalidInput(e.getMessage());
			return false;
		}
	}

	private boolean loadTypeAndCountryFilter() throws InterruptedException {
		int noOfRecords = 0;
		List<String> typeAndCountry = new ArrayList<>(2);
		noOfRecords = this.inputNoOfRecords();
		if (noOfRecords < 1) {
			showInvalidInput();
			return false;
		}
		System.out.println(TYPE_AND_COUNTRY_INPUT_MSG);
		typeAndCountry = Arrays.stream(sc.nextLine().trim().toLowerCase().split(":")).map(String::trim)
				.collect(Collectors.toList());
		if (typeAndCountry.size() != 2 || typeAndCountry.get(0).isEmpty() || typeAndCountry.get(1).isEmpty()) {
			showInvalidInput();
			return false;
		}
		result = this.netflixService.filterRecordsByTypeAndCountry(typeAndCountry.get(0), typeAndCountry.get(1),
				noOfRecords);
		return true;
	}

	private boolean loadGenreFilter() throws InterruptedException {
		int noOfRecords = 0;
		Set<String> genres = new HashSet<>();
		noOfRecords = this.inputNoOfRecords();
		if (noOfRecords < 1) {
			showInvalidInput();
			return false;
		}
		System.out.println(GENRE_INPUT_MSG);
		genres = Arrays.stream(sc.nextLine().trim().toLowerCase().split(",")).map(String::trim)
				.collect(Collectors.toSet());
		if(genres.isEmpty() || genres.contains("")) {
			showInvalidInput();
			return false;
		}
		result = this.netflixService.filterRecordsByGenre(genres, noOfRecords);
		return true;
	}

	private boolean loadTypeFilter() throws InterruptedException {
		int noOfRecords = 0;
		String type = "";
		noOfRecords = this.inputNoOfRecords();
		if (noOfRecords < 1) {
			this.showInvalidInput();
			return false;
		}
		System.out.println(TYPE_INPUT_MSG);
		type = sc.nextLine().trim().toLowerCase();
		if(type.isEmpty()) {
			this.showInvalidInput();
			return false;
		}
		this.result = this.netflixService.filterRecordsByType(type, noOfRecords);
		return true;
	}

	private void showResult(List<NetflixRecord> result) throws InterruptedException {
		if(result.isEmpty()) {
			System.out.println("Sorry, No Records to Display");
		} else {
			System.out.println("Total Records Showing: " + result.size());
			result.forEach(System.out::println);
		}
		Thread.sleep(3000);
		IntStream.range(0, 5).forEach(i -> System.out.println());
	}

	private void showInvalidInput() throws InterruptedException {
		System.out.println(INVALID_INPUT_ERROR_MSG);
		Thread.sleep(3000);
		IntStream.range(0, 5).forEach(i -> System.out.println());
	}

	private void showInvalidInput(String msg) throws InterruptedException {
		System.out.println(msg);
		Thread.sleep(3000);
		IntStream.range(0, 5).forEach(i -> System.out.println());
	}

	private int inputNoOfRecords() {
		int noOfRecords = 0;
		System.out.println(RECORDS_INPUT_MSG);
		try {
			noOfRecords = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
		}
		return noOfRecords;
	}

	private void showMenu() {
		String menu = "Choose any of the option below:\r\n" + "1. Filter content by Type\r\n"
				+ "2. Filter content by Genre\r\n" + "3. Filter content by Type and Country\r\n"
				+ "4. Filter content by Date Added\r\n" + "5. Exit\r\n" + "Please Enter Your Choice:";
		System.out.println(menu);
	}
}
