package com.ef;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@Slf4j
public class Command {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Duration duration;
	private int threshold;
	private String accessLogPath;

	private static final String DATETIME_STRING_FORMAT = "yyyy-MM-dd.HH:mm:ss";
	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_STRING_FORMAT,
			Locale.US);

	public Command(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String[] keyValue = args[i].split("=");
			if (keyValue.length == 2) {
				switch (keyValue[0]) {
				case "--startDate":
					startDate = LocalDateTime.parse(keyValue[1], DATETIME_FORMATTER);
					break;
				case "--duration":
					duration = Duration.parseDuration(keyValue[1]);
					if (duration.equals(Duration.DAILY))
						endDate = startDate.plusDays(1).minusSeconds(1);
					else
						endDate = startDate.plusHours(1).minusSeconds(1);
					break;
				case "--threshold":
					threshold = Integer.parseInt(keyValue[1]);
					break;
				case "--accesslog":
					accessLogPath = keyValue[1];
					break;
				default:
					break;
				}
			} else {
				log.warn("Invalid argument {}", keyValue);
			}
		}
	}

	enum Duration {
		HOURLY("hourly"), DAILY("daily");
		private String duration;

		Duration(String duration) {
			this.duration = duration;
		}

		public static Duration parseDuration(String duration) {
			switch (duration) {
			case "hourly":
				return Duration.HOURLY;
			case "daily":
				return Duration.DAILY;
			default:
				throw new IllegalArgumentException("No enum constant :" + duration);
			}
		}

		public String getValue() {
			return duration;
		}
	}
}
