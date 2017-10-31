package com.ef.log.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ef.data.LogData;
import com.ef.data.LogDataReport;

@Repository
public class SqlLogReport implements LogReport {

	private static final String LOG_SQL_SELECT_ALL = "select * from log_data";
	private static final String LOG_SQL_SELECT_BY_IP = "select * from log_data where ip=?";
	private static final String LOG_SQL_SELECT_IP_COUNT_BY_LOGED_TIME = "select ip, count(id) as count from log_data where loged_time BETWEEN ? and ? GROUP BY ip HAVING count(id) > ? ORDER BY count(id) desc";
	private static final String LOG_SQL_SELECT_IP_COUNT_BY_IP = "select ip,count(id) as count from log_data where ip=? GROUP BY ip";
	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void constructJdbcTemplate() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<LogData> loadAll() {
		return jdbcTemplate.query(LOG_SQL_SELECT_ALL, (rs, i) -> toLogData(rs));
	}

	@Override
	public List<LogData> findLogDataByIp(String ip) {
		return jdbcTemplate.query(LOG_SQL_SELECT_BY_IP, new Object[] { ip }, (rs, i) -> toLogData(rs));
	}

	@Override
	public LogDataReport findIpCountByIp(String ip) {
		return jdbcTemplate.queryForObject(LOG_SQL_SELECT_IP_COUNT_BY_IP, new Object[] { ip },
				(rs, i) -> toLogDataReport(rs));
	}

	@Override
	public List<LogDataReport> findIpCountBetweenLogedTimeAndThreshold(Timestamp startDate, Timestamp endDate,
			int threshold) {
		return jdbcTemplate.query(LOG_SQL_SELECT_IP_COUNT_BY_LOGED_TIME, new Object[] { startDate, endDate, threshold },
				(rs, i) -> toLogDataReport(rs));
	}

	private LogData toLogData(ResultSet rs) throws SQLException {
		LogData logData = new LogData();
		logData.setId(rs.getLong("id")).setDateTime(rs.getTimestamp("loged_time").toLocalDateTime())
				.setIp(rs.getString("ip")).setMethod(rs.getString("method")).setProtocol(rs.getString("protocol"))
				.setStatus(rs.getInt("status_code")).setUserAggent(rs.getString("user_aggent"));
		return logData;
	}

	private LogDataReport toLogDataReport(ResultSet rs) throws SQLException {
		LogDataReport logDataReport = new LogDataReport();
		logDataReport.setIp(rs.getString("ip")).setCount(rs.getLong("count"));
		return logDataReport;
	}
}
