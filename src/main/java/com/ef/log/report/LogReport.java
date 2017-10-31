package com.ef.log.report;

import java.sql.Timestamp;
import java.util.List;

import com.ef.data.LogData;
import com.ef.data.LogDataReport;

public interface LogReport {

	public List<LogData> loadAll();
	public List<LogData> findLogDataByIp(String ip) ;
	public LogDataReport findIpCountByIp(String ip);
	public List<LogDataReport> findIpCountBetweenLogedTimeAndThreshold(Timestamp startDate, Timestamp endDate,
			int threshold);
}
