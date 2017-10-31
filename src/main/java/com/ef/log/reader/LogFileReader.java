package com.ef.log.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.ef.data.LogData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFileReader implements LogReader {
	private String path;

	public LogFileReader(String path) {
		this.path = path;
	}

	@Override
	public List<LogData> read() {
		List<LogData> logDataList = Collections.EMPTY_LIST;
		log.info("Reading log file from path - {}", path);
		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			log.info("Log file loaded.");
			logDataList = stream.map(logLine -> new LogData(logLine)).collect(Collectors.toList());
		} catch (IOException e) {
			log.error("IOException while reading Data from filePath: " + path, e);
		}
		return logDataList;
	}

}
