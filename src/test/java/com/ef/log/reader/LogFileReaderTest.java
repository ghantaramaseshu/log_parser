package com.ef.log.reader;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ef.data.LogData;

public class LogFileReaderTest {
	@Test
	public void fileReaderTest() {
		LogData expectedLogData1 = new LogData("2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"");
		LogData expectedLogData2 = new LogData("2017-01-01 00:00:21.164|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"");
		LogData expectedLogData3 = new LogData("2017-01-01 00:00:23.003|192.168.169.194|\"GET / HTTP/1.1\"|200|\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393\"");
		List<LogData> expectedList = Arrays.asList(expectedLogData1,expectedLogData2,expectedLogData3);
		LogFileReader logReader = new LogFileReader("D:\\WORK-SPACE\\Bank\\parser\\src\\test\\resources\\test.log");
		List<LogData> logDataList = logReader.read();
		assertThat(logDataList.size(),is(3));
		assertThat(logDataList,is(expectedList));
	}
}
