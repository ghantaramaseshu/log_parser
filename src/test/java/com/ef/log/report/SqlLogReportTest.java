package com.ef.log.report;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ef.data.LogData;
import com.ef.data.LogDataReport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SqlLogReportConfig.class })
public class SqlLogReportTest {
	@Autowired
	private SqlLogReport report;

	@Test
	public void listAllTest() {
		List<LogData> logDataList = report.loadAll();
		assertThat(logDataList, hasSize(3));
	}

	@Test
	public void findLogDataByIpTest() {
		List<LogData> logDataList = report.findLogDataByIp("192.168.234.82");
		assertThat(logDataList, hasSize(2));
	}

	@Test
	public void findIpCountBetweenLogedTimeAndThresholdTest() {
		List<LogDataReport> logDataReportList = report.findIpCountBetweenLogedTimeAndThreshold(
				Timestamp.valueOf("2016-12-31 00:00:11.763"), Timestamp.valueOf("2017-01-02 00:00:11.763"), 1);
		assertThat(logDataReportList, hasSize(1));
		assertEquals("192.168.234.82", logDataReportList.get(0).getIp());
		assertEquals(Long.valueOf(2l), logDataReportList.get(0).getCount());
	}
}
