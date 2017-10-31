package com.ef.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class LogData {

	private static final String LOG_STRING_PATTERN = "(\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3})\\|(\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\|\"(\\w+)[\\s\\/]+([\\w\\/\\d.]+)\"\\|(\\d{1,3})\\|\"([^\"]*)\"";
	private static final Pattern LOG_PATTERN = Pattern.compile(LOG_STRING_PATTERN);
	private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-d HH:mm:ss.SSS");
	private static final int LOG_PATTERN_GROUP_TIME = 1, LOG_PATTERN_GROUP_IP = 2, LOG_PATTERN_GROUP_METHOD = 3,
			LOG_PATTERN_GROUP_PROTOCOL = 4, LOG_PATTERN_GROUP_STATUS = 5, LOG_PATTERN_GROUP_USER_AGGENT = 6;
	private Long id;
	private LocalDateTime dateTime;
	private String ip;
	private String method;
	private String protocol;
	private Integer status;
	private String userAggent;

	private String logLine;

	public LogData(String logLine) {
		this.logLine = logLine;
		Matcher logMatcher = LOG_PATTERN.matcher(logLine);
		if (logMatcher.find()) {
			dateTime = LocalDateTime.parse(logMatcher.group(LOG_PATTERN_GROUP_TIME), DATE_TIME_FORMATTER);
			ip = logMatcher.group(LOG_PATTERN_GROUP_IP);
			method = logMatcher.group(LOG_PATTERN_GROUP_METHOD);
			protocol = logMatcher.group(LOG_PATTERN_GROUP_PROTOCOL);
			status = Integer.valueOf(logMatcher.group(LOG_PATTERN_GROUP_STATUS));
			userAggent = logMatcher.group(LOG_PATTERN_GROUP_USER_AGGENT);
		}else {
			log.error("Invalid log pattern - {}", logLine);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logLine == null) ? 0 : logLine.hashCode());
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
		LogData other = (LogData) obj;
		if (logLine == null) {
			if (other.logLine != null)
				return false;
		} else if (!logLine.equals(other.logLine))
			return false;
		return true;
	}
	
	

}
