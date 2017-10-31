package com.ef.log.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.ef.data.LogData;

@Repository
public class SqlLogWriter implements LogWriter {

	private static final String LOG_SQL_INSERT = "insert into log_data (loged_time,ip,method,protocol,status_code,user_aggent) values (?,?,?,?,?,?)";

	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void constructJdbcTemplate() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int[][] write(List<LogData> logDataList) {

		return jdbcTemplate.batchUpdate(LOG_SQL_INSERT, logDataList, 1000,
				new ParameterizedPreparedStatementSetter<LogData>() {

					@Override
					public void setValues(PreparedStatement ps, LogData logData) throws SQLException {
						ps.setTimestamp(1, Timestamp.valueOf(logData.getDateTime()));
						ps.setString(2, logData.getIp());
						ps.setString(3, logData.getMethod());
						ps.setString(4, logData.getProtocol());
						ps.setInt(5, logData.getStatus());
						ps.setString(6, logData.getUserAggent());
					}

				});
	}

}
