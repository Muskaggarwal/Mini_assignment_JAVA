package com.hashedin.io;

import java.util.Set;

import com.hashedin.domains.NetflixRecord;

public interface FileReader {
	
	public Set<NetflixRecord> parseCSVFileIntoNetflixRecords(String filePath);
}
