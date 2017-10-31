package com.ef.log.writer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ef.data.LogData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SqlWriterConfig.class })
public class SqlLogWriterTest {
	
	@Autowired
	private LogWriter logWriter;
	
	@Test
	public void writeTest() {
		LogData logData1 = new LogData("2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"");
		LogData logData2 = new LogData("2017-01-01 00:00:21.164|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"");
		LogData logData3 = new LogData("2017-01-01 00:00:23.003|192.168.169.194|\"GET / HTTP/1.1\"|200|\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393\"");
		List<LogData> logDataList = Arrays.asList(logData1,logData2,logData3);
		int[][] result = logWriter.write(logDataList);
		assertThat(result, is(notNullValue()));
	}

}
