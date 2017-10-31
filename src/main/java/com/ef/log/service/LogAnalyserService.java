package com.ef.log.service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.Command;
import com.ef.data.LogData;
import com.ef.data.LogDataReport;
import com.ef.log.reader.LogFileReader;
import com.ef.log.report.SqlLogReport;
import com.ef.log.writer.LogWriter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LogAnalyserService {

	@Autowired
	private LogWriter logWriter;

	@Autowired
	private SqlLogReport report;

	public boolean importData(String file) {
		try {
			LogFileReader logReader = new LogFileReader(file);
			List<LogData> logDataList = logReader.read();
			logWriter.write(logDataList);
			log.info("Imported completed");
		} catch (Exception ex) {
			log.error("Exception while importing Data to SQL", ex);
			return false;
		}
		return true;
	}

	public void analyse(Command command) {
		importData(command.getAccessLogPath());
		getReport(command);
	}

	public List<LogDataReport> getReport(Command command) {
		List<LogDataReport> result = Collections.EMPTY_LIST;
		try {
			result = report.findIpCountBetweenLogedTimeAndThreshold(Timestamp.valueOf(command.getStartDate()),
					Timestamp.valueOf(command.getEndDate()), command.getThreshold());
			printReport(result);
		} catch (Exception e) {
			log.error("Exception while Genarating Report", e);
		}
		return result;
	}

	private void printReport(List<LogDataReport> reportList) {
		log.info("-------Report start----------");
		reportList.stream().forEach(logData -> log.info("IP : {} Count {}", logData.getIp(), logData.getCount()));
		log.info("-------Report end----------");
	}

}
